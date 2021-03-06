package com.mycrawler.crawler.collector.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mycrawler.common.domain.es.Document;
import com.mycrawler.crawler.core.webporter.data.DataProcessor;
import com.mycrawler.crawler.core.webporter.data.HashSetDuplicateRemover;

import us.codecraft.webmagic.selector.Json;

/**
 * 从原始数据生成满足 Elasticsearch 格式的 json 数据
 */
public class ZhihuFolloweeDataProcessor implements DataProcessor<File, Document> {

    private HashSetDuplicateRemover<String> duplicateRemover = new HashSetDuplicateRemover<>();

    @Override
    public List<Document> process(File inItem) {
        String s = MemberURLTokenGenerator.readFollowees(inItem);
        List<Document> documents = null;

        if (!StringUtils.isEmpty(s)) {
            documents = new ArrayList<>(20);
            Json json = new Json(s);
            List<String> users = json.jsonPath("$.data[*].[*]").all();
            List<String> ids = json.jsonPath("$.data[*].id").all();
            int i = 0;
            for (String id : ids) {
                if (!duplicateRemover.isDuplicate(id)) {
                    documents.add(new Document(id, users.get(i)));
                }
                i++;
            }
        }
        return documents;
    }

}
