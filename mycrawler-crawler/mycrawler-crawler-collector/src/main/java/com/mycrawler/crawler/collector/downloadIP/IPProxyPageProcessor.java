package com.mycrawler.crawler.collector.downloadIP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycrawler.crawler.collector.ZhihuConfiguration;
import com.mycrawler.crawler.config.impl.IPProxyConfiguration;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 
* @ClassName: ZhihuFolloweePageProcessor
* @Description: 
* @author yangrenjiang
* @date 2017年6月18日 下午6:26:33
*
 */
public class IPProxyPageProcessor implements PageProcessor {
	
	Logger logger = LoggerFactory.getLogger(IPProxyPageProcessor.class);
    private Site site = new ZhihuConfiguration().getSite();
    private PrintWriter printWriter;
    public static final String START_URL ="http://www.goubanjia.com/free/index.shtml";
    public static final String START_URL_1 ="http://www.goubanjia.com/free/index%d.shtml";
    String IPROWSPATTER ="/*[@id=\"list\"]/table/tbody/tr"; 
    String IPROWPATTER ="/*[@id=\"list\"]/table/tbody/tr[%d]"; 
    static boolean search = false;
    public IPProxyPageProcessor(){
	   initFile();
    }
    public void process(Page page) {
    	Html html = page.getHtml();
    	
    	Selectable selected = html.xpath("//*[@id=\"list\"]/table/tbody/tr");
        List<Selectable>  iprows = selected.nodes(); 
    	if(iprows != null && iprows.size() >0){
    		for (int i = 0; i < iprows.size(); i++) {
    			Selectable iprow = iprows.get(i);
    			List<Selectable> cols = iprow.xpath("td").nodes();
    			List<Selectable> ips = cols.get(0).xpath("td/*").nodes();
    			String str = "";
    			for (int j = 0; j < ips.size(); j++) {
    					Selectable  st = ips.get(j);
    					String lableStr= st.toString();
    					if(lableStr.contains("display") && lableStr.contains("none")){
    						continue;
    					}
						List<String> values = st.xpath("*/text()").all();
						if(j < ips.size() -1)
							str +=concat(values, (s)->(s!=null&&s.length() >0||!"null".equalsIgnoreCase(s)));
						else{
							str +=":"+concat(values, (s)->(s!=null&&s.length() >0||!"null".equalsIgnoreCase(s)));
						}
						
				}
    			System.out.println(str);
    			printWriter.write(str+"\n");
			}
    	}
		printWriter.flush();
    	List<String> result = new ArrayList<>();
    	page.putField(IPProxyPipeline.RESPONSE, result);
    	if(!search){
    		List<String> urls = new ArrayList<>();
	    	for (int i = 1; i <= 500; i++) {
	    		urls.add(String.format(START_URL_1, i));
			}
	    	page.addTargetRequests(urls);
	    	search = true;
    	}
    
    	
    }
    
    public  String concat(List<String> names, Predicate<String> condition) {
    	String str="";
        for(String name: names)  {
           if(condition.test(name)) {
        	   str+=name;
           }
        }
        return str;
      }
	

    public Site getSite() {
        return site;
    }

    public void initFile(){
    	try {
    		printWriter = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(
							new File("D:/webmagic/ip/ip.txt")), "UTF-8"));
		}catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     */
    public static void main(String[] args) {
        String pipelinePath = new IPProxyConfiguration().getFolloweePath();
        int crawlSize = 100_0000;
        Spider.create(new IPProxyPageProcessor())
                .setScheduler(//new QueueScheduler()
                        new FileCacheQueueScheduler(pipelinePath)
                                .setDuplicateRemover(new BloomFilterDuplicateRemover(crawlSize)))
                .addPipeline(new IPProxyPipeline(pipelinePath))
                .addUrl(START_URL)
                .thread(5)
                .run();
       
    }
}
