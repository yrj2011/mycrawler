package com.mycrawler.collector.zhihu.download58area;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycrawler.collector.zhihu.ZhihuConfiguration;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.selector.Html;

/**
 * 
* @ClassName: ZhihuFolloweePageProcessor
* @Description: 
* @author yangrenjiang
* @date 2017年6月18日 下午6:26:33
*
 */
public class WBAreaPageProcessor implements PageProcessor {
	
	Logger logger = LoggerFactory.getLogger(WBAreaPageProcessor.class);
    private Site site = new ZhihuConfiguration().getSite();
    public static final String START_URL ="http://sh.58.com/zufang/pn1";
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    // 上海市区获取
    String areaXPath ="/html/body/div[3]/div[1]/div[3]/dl[1]/dd/a[%d]";
    // 上海街道获取
    String minAreaXPath ="/html/body/div[3]/div[1]/div[3]/dl[1]/dd/div/a[%d]";
    String hezu ="hezu";
    String zufang ="zufang";
    //http://sh.58.com/+区域名称+/【hezu/ zufang】+/厅室+租金/pn【页数】
    // hezu 是厅室+租金
    String[] hezuTingShis = {"c1","c2","c3"};
    String[] hezuZuJins = {"k1","k2","k3","k4","k5","k6","k7"};
    //zufang 租金+厅室
    String[] zufangZuJins = {"b8","b9","b10","b11","b12","b13","b14","b15"};
    String[] zufangTingShis = {"j1","j2","j3","j4","j5"};
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool(); 
    private  PrintWriter printWriter = null;
    public WBAreaPageProcessor(){
	   initFile();
    }
    public void process(Page page) {
    	Html html = page.getHtml();
    	
    	List<String> totalAList2 = html.xpath("/html/body/div[3]/div[1]/div[3]/dl[1]/dd/div/a").all();
    	List<String> recordUrlList = new ArrayList<>();
    	if(totalAList2 != null){
    		for (int i=1;i<=totalAList2.size();i++) {
    			 /*System.out.println(html.xpath(String.format(minAreaXPath,i)).links());*/
    			String minAreaBaseUrl = html.xpath(String.format(minAreaXPath,i)).links().toString();
    			if(minAreaBaseUrl.contains(zufang)){
    				for (String tingshi : zufangTingShis) {
    					for (String zujin : zufangZuJins) {
    						String url=minAreaBaseUrl;
							url+=zujin;
							url+=tingshi;
							url +="/pn1";
    						recordUrlList.add(url);
    						
    					}
    				}
    				minAreaBaseUrl = minAreaBaseUrl.replace(zufang, hezu);
    			}
    			for (String tingshi : hezuTingShis) {
					for (String zujin : hezuZuJins) {
						String url=minAreaBaseUrl;
						url+=zujin;
						url+=tingshi;
						url +="/pn1";
						recordUrlList.add(url);
					}
				}
    		}
    	}
    	page.putField(WBAreaPipeline.RESPONSE, recordUrlList);
    	List<String> totalAList = html.xpath("/html/body/div[3]/div[1]/div[3]/dl[1]/dd/a").all();
    	if(totalAList != null){
    		List<String> urls = new ArrayList<>();
	    	for (int i=1;i<=totalAList.size();i++) {
	    			try {
	    	    		   urls.add(html.xpath(String.format(areaXPath,i)).links().toString());
					} catch (Exception e) {
						logger.error("",e);
					}
	    		  
			}
	    	
	        page.addTargetRequests(urls);
    	}
    }

    public Site getSite() {
        return site;
    }

    public void initFile(){
    	try {
    		printWriter = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(
							new File("D:/webmagic/58/sh.url")), "UTF-8"));
		}catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     */
    public static void main(String[] args) {
        String pipelinePath = new ZhihuConfiguration().getFolloweePath();
        int crawlSize = 100_0000;
        Spider.create(new WBAreaPageProcessor())
                .setScheduler(//new QueueScheduler()
                        new FileCacheQueueScheduler(pipelinePath)
                                .setDuplicateRemover(new BloomFilterDuplicateRemover(crawlSize)))
                .addPipeline(new WBAreaPipeline(pipelinePath))
                .addUrl(START_URL)
                .thread(1)
                .run();
    }
}
