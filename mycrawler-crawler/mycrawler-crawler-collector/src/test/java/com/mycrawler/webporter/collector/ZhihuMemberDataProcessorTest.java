package com.mycrawler.webporter.collector;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mycrawler.collector.zhihu.processor.ZhihuMemberDataProcessor;
import com.mycrawler.common.domain.es.Document;
import com.mycrawler.webporter.data.DataProcessor;

public class ZhihuMemberDataProcessorTest {
    @Test
    public void testProcess() {
        String path = getClass().getResource("/").getPath() + "/member.json";
        File file = new File(path);
        String id = "b0989c4ac16a4dc6939bd04945b5a5db";

        DataProcessor<File, Document> processor = new ZhihuMemberDataProcessor();
        List<Document> documents = processor.process(file);
        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertEquals(id, documents.get(0).getId());
        //System.out.println(documents.get(0));
    }
}
