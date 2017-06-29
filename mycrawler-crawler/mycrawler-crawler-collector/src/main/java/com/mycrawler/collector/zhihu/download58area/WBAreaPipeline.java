package com.mycrawler.collector.zhihu.download58area;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycrawler.common.utils.AppendFile;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

/**
 * 
* @ClassName: WBAreaPipeline
* @Description:
* @author yangrenjiang
* @date 2017年6月18日 下午7:40:01
*
 */
public class WBAreaPipeline extends FilePipeline {
    public static final String WB_SH_URL_TXT = "D:/webmagic/58/sh_url.txt";

	private Logger logger = LoggerFactory.getLogger(getClass());

    static final String RESPONSE = "response";

    public WBAreaPipeline() {
        setPath("/data/webporter/");
    }

    public WBAreaPipeline(String path) {
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            Map<String, Object> results = resultItems.getAll();
            List<String> recordUrlList = (List<String>) results.get(RESPONSE);
            if(recordUrlList !=null)
            for (String url : recordUrlList) {
            	AppendFile.method2(WB_SH_URL_TXT, url+"\n");
			}
        } catch (Exception e) {
            logger.warn("write file error", e);
        }
    }
}
