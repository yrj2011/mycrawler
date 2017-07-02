package com.mycrawler.crawler.collector.download58;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

/**
 * Created by yangrenjiang on 16/12/18.
 * 用户详细信息的 pipeline
 */
public class WBPipeline extends FilePipeline {
    private Logger logger = LoggerFactory.getLogger(getClass());

    static final String URL = "url";
    static final String RESPONSE = "response";

    /**
     * create a ZhihuPipeline with default path"/data/webporter/"
     */
    public WBPipeline() {
        setPath("/data/webporter/");
    }

    public WBPipeline(String path) {
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".html")), "UTF-8"));
            Map<String, Object> results = resultItems.getAll();

            printWriter.println(results.get(URL));
            List<Map<String,String>> houseList = (List<Map<String, String>>) results.get(RESPONSE);
            if(houseList !=null)
            for (Map<String, String> map : houseList) {
            	printWriter.println(map);				
			}
            printWriter.close();
        } catch (IOException e) {
            logger.warn("write file error", e);
        }
    }
}
