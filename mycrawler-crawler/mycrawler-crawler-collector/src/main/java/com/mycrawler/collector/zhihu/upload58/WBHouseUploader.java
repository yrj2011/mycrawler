package com.mycrawler.collector.zhihu.upload58;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.mycrawler.collector.zhihu.ZhihuConfiguration;
import com.mycrawler.collector.zhihu.processor58.WBDataProcessor;
import com.mycrawler.common.domain.es.Document;
import com.mycrawler.webporter.data.BaseAssembler;
import com.mycrawler.webporter.data.DataProcessor;
import com.mycrawler.webporter.data.FileRawInput;

public class WBHouseUploader {
    public static void upload() {
        String index = "mywb";
        String type = "house";
        ZhihuConfiguration configuration = new ZhihuConfiguration();

        String folder = configuration.getFolloweeDataPath();
        DataProcessor<File, Document> processor = new WBDataProcessor();

        WBElasticsearchUploader outPipeline = new WBElasticsearchUploader(index, type);
        outPipeline.setTimeout(5, TimeUnit.MINUTES);

        BaseAssembler.create(new FileRawInput(folder), processor)
                .addOutPipeline(outPipeline)
                .thread(1)
                .run();

        System.out.println("out sent :" + outPipeline.getCount());
        System.out.println(outPipeline.getBulkProcessor());
    }

    public static void main(String[] args) throws InterruptedException {
        WBHouseUploader.upload();
        synchronized (WBHouseUploader.class) {
        	WBHouseUploader.class.wait();			
		}
    }

}
