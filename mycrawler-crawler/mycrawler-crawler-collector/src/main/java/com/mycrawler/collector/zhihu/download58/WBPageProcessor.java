package com.mycrawler.collector.zhihu.download58;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycrawler.collector.zhihu.ZhihuConfiguration;
import com.mycrawler.collector.zhihu.download58area.WBAreaPipeline;
import com.mycrawler.common.utils.StringHelper;
import com.mycrawler.file.ReadFromFile;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.selector.Html;

/**
 * Created by brian on 16/11/24.
 *
 * 爬取知乎用户的关注者
 * step 1: 运行该类的 main 方法开始爬取
 */
public class WBPageProcessor implements PageProcessor {
	Logger logger = LoggerFactory.getLogger(WBPageProcessor.class);
    private Site site = new ZhihuConfiguration().getSite();
    public static final String START_URL ="http://sh.58.com/zufang/pn1";
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    String titleXPath ="/html/body/div[3]/div[1]/div[5]/div[2]/ul/li[%d]/div[2]/h2/a/text()";
	String createTimeXPath ="/html/body/div[3]/div[1]/div[5]/div[2]/ul/li[%d]/div[3]/div[1]/text()";
	String houseTypeXPath ="/html/body/div[3]/div[1]/div[5]/div[2]/ul/li[%d]/div[2]/p[1]/text()";
	String address1XPath ="/html/body/div[3]/div[1]/div[5]/div[2]/ul/li[%d]/div[2]/p[2]/a[1]/text()";
	String address2XPath ="/html/body/div[3]/div[1]/div[5]/div[2]/ul/li[%d]/div[2]/p[2]/a[2]/text()";
	String address3XPath ="/html/body/div[3]/div[1]/div[5]/div[2]/ul/li[%d]/div[2]/p[2]/a/text()";
	String priceXPath="/html/body/div[3]/div[1]/div[5]/div[2]/ul/li[%d]/div[3]/div[2]/b/text()";
	String operatorXPath ="/html/body/div[3]/div[1]/div[5]/div[2]/ul/li[%d]/div[2]/div/span/span/a/text()";
    public void process(Page page) {
    	Html html = page.getHtml();
    	
    	List<String> totalLiList = html.xpath("/html/body/div[3]/div[1]/div[5]/div[2]/ul/li").all();
    	if(totalLiList == null || totalLiList.size() < 1)
    		return;
    	List<Map<String,String>> houseList = new ArrayList<>();
    	for (int i=1;i<=totalLiList.size();i++) {
    			try {
    				   Map<String,String> house = new HashMap<>();
    	    		   house.put("title",html.xpath(String.format(titleXPath,i)).toString());
    	    	       house.put("createTime",html.xpath(String.format(createTimeXPath,i)).toString());
    	    	       house.put("houseType",html.xpath(String.format(houseTypeXPath,i)).toString());
    	    	       house.put("address1",html.xpath(String.format(address1XPath,i)).toString());
    	    	       house.put("address2",html.xpath(String.format(address2XPath,i)).toString());
    	    	       house.put("address3",html.xpath(String.format(address3XPath,i)).toString());
    	    	       house.put("price",html.xpath(String.format(priceXPath,i)).toString());
    	    	       house.put("operator",html.xpath(String.format(operatorXPath,i)).toString());
    	    	       house.put("createDate", SDF.format(new Date()));
    	    	       houseList.add(house);
				} catch (Exception e) {
					logger.error("",e);
				}
    		  
		}
    	
        page.putField(WBPipeline.URL, page.getUrl());
        page.putField(WBPipeline.RESPONSE, houseList);
        String url = page.getUrl().toString();
        String[] params = url.split("/");
        List<String> urls = new ArrayList<>();
        for (String param : params) {
			if(param.startsWith("pn")){
				Integer pageIndex = Integer.parseInt(param.replace("pn", ""));
				urls.add(START_URL.replace("pn1", "pn"+(pageIndex+1)));
				break;
			}
		}
        page.addTargetRequests(urls);
    }

    public Site getSite() {
        return site;
    }

    public static String generateFolloweeUrl(String urlToken) {
        final String URL_TEMPLATE = "https://www.zhihu.com/api/v4/members/%s/followees";
        final String QUERY_PARAMS = "?include=data%5B*%5D.url_token&offset=0&per_page=30&limit=30";

        String encoded = StringHelper.urlEncode(urlToken);
        return String.format(URL_TEMPLATE, encoded) + QUERY_PARAMS;
    }

    public static List<String> generateFolloweeUrls(List<String> urlTokens) {
        List<String> urls = new ArrayList<>(20);
        urlTokens.stream().map(WBPageProcessor::generateFolloweeUrl).forEach(urls::add);
        return urls;
    }

    /**
     * 下载关注列表的用户数据,用于提取 url_tokens
     * @param args 无须其他参数
     */
    public static void main(String[] args) {
    	String pipelinePath = new ZhihuConfiguration().getFolloweePath();
    	int crawlSize = 100_0000;
    	Set<String> url = ReadFromFile.readFileByLines(WBAreaPipeline.WB_SH_URL_TXT);
        Spider.create(new WBPageProcessor())
                .setScheduler(//new QueueScheduler()
                        new FileCacheQueueScheduler(pipelinePath)
                                .setDuplicateRemover(new BloomFilterDuplicateRemover(crawlSize)))
                .addPipeline(new WBPipeline(pipelinePath))
                .addUrl(url.toArray(new String[]{}))
                .thread(1)
                .run();
       
    }
}
