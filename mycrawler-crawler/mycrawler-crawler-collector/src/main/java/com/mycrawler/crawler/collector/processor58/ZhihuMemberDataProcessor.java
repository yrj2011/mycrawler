package com.mycrawler.crawler.collector.processor58;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mycrawler.common.domain.es.Document;
import com.mycrawler.common.utils.FileHelper;
import com.mycrawler.crawler.collector.ZhihuConfiguration;
import com.mycrawler.crawler.core.webporter.data.BaseAssembler;
import com.mycrawler.crawler.core.webporter.data.ConsoleOutpipeline;
import com.mycrawler.crawler.core.webporter.data.DataProcessor;
import com.mycrawler.crawler.core.webporter.data.FileRawInput;
import com.mycrawler.crawler.core.webporter.data.HashSetDuplicateRemover;

import us.codecraft.webmagic.selector.Json;

/**
 * 从原始数据生成满足 Elasticsearch 格式的 json 数据
 */
public class ZhihuMemberDataProcessor implements DataProcessor<File, Document> {

    private HashSetDuplicateRemover<String> duplicateRemover = new HashSetDuplicateRemover<>();

    @Override
    public List<Document> process(File inItem) {
        String s = readMember(inItem);
        List<Document> documents = null;

        if (!StringUtils.isEmpty(s)) {
            documents = new ArrayList<>(1);
            Json json = new Json(s);
            String id = json.jsonPath("$.id").get();
            if (!duplicateRemover.isDuplicate(id)) {
                documents.add(new Document(id, s));
            }
        }
        return documents;
    }

    public static String readMember(File inItem) {
        List<String> followees = FileHelper.processFile(inItem, br -> {
            br.readLine();//pass first line
            String s = br.readLine();
            return Collections.singletonList(s);
        }).orElse(new ArrayList<>());

        return followees.size() == 0 ? null : followees.get(0);
    }

    public static void main(String[] args) {
        ZhihuConfiguration configuration = new ZhihuConfiguration();
        String folder = configuration.getMemberDataPath();
        DataProcessor<File, Document> processor = new ZhihuMemberDataProcessor();
        ConsoleOutpipeline<Document> outPipeline = new ConsoleOutpipeline<>();

        BaseAssembler.create(new FileRawInput(folder), processor)
                .addOutPipeline(i -> {
                }) // 需要打印时替换为 outPipeline
                .thread(10)
                .run();

        System.out.println("out sent :" + outPipeline.getCount());
    }

}
