package com.mycrawler.collector.zhihu.processor58;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.swing.plaf.basic.BasicToolBarUI.DockingListener;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.mycrawler.data.elasticsearch.Document;
import com.mycrawler.file.ReadFromFile;
import com.mycrawler.webporter.data.DataProcessor;

/**
 * 从原始数据生成满足 Elasticsearch 格式的 json 数据
 */
public class WBDataProcessor implements DataProcessor<File, Document> {

	static int id=0;
    @Override
    public List<Document> process(File inItem) {
    	try {
    		Set<String> jsons = ReadFromFile.readFileByLines(inItem.getAbsolutePath());
            List<Document>  documents = new ArrayList<>(20);
            if(jsons != null && jsons.size() > 0){
            	
            	for (String s : jsons) {
            		String s2 = s.replace("=", "\":\"");
            		s2 = s2.replace(",", "\",\"");
            		s2 = s2.replace("{", "{\"");
            		s2 = s2.replace("}", "\"}");
            		//System.out.println(s2);
            		if(StringUtils.isNotEmpty(s) && !"null".equalsIgnoreCase(s))
            			documents.add(new Document(UUID.randomUUID().toString(), s2));
            		//System.out.println(id);
    			}
            }
            if(documents == null || documents.size() < 1){
            	System.out.println(inItem.getAbsolutePath()+" document is empty!");
            	inItem.delete();
            }
            return documents;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
        
    }

}
