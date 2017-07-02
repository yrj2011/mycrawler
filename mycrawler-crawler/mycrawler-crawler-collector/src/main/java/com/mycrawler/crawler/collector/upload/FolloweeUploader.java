package com.mycrawler.crawler.collector.upload;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.mycrawler.common.domain.es.Document;
import com.mycrawler.crawler.collector.ZhihuConfiguration;
import com.mycrawler.crawler.collector.processor.ZhihuFolloweeDataProcessor;
import com.mycrawler.crawler.core.webporter.data.BaseAssembler;
import com.mycrawler.crawler.core.webporter.data.DataProcessor;
import com.mycrawler.crawler.core.webporter.data.FileRawInput;

public class FolloweeUploader {
    public static void upload() {
        String index = "zhihu";
        String type = "followee";
        ZhihuConfiguration configuration = new ZhihuConfiguration();

        String folder = configuration.getFolloweeDataPath();
        DataProcessor<File, Document> processor = new ZhihuFolloweeDataProcessor();

        ZhihuElasticsearchUploader outPipeline = new ZhihuElasticsearchUploader(index, type);
        outPipeline.setTimeout(5, TimeUnit.MINUTES);

        BaseAssembler.create(new FileRawInput(folder), processor)
                .addOutPipeline(outPipeline)
                .thread(10)
                .run();

        System.out.println("out sent :" + outPipeline.getCount());
        System.out.println(outPipeline.getBulkProcessor());
    }

    public static void main(String[] args) {
        FolloweeUploader.upload();
    }

}
