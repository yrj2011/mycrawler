package com.github.yrj.elasticsearch.tourial;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.util.Date;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
public class ESMulti {
	private ESClient esclient = new ESClient();
	public void init(){
		esclient.init();
	}
	public static void main(String[] args) {
		
		ESMulti bulker = new ESMulti();
		bulker.init();
		bulker.bulk();
	}

	
	public void bulk(){
		try {
			MultiGetResponse multiGetItemResponses = esclient.getClient().prepareMultiGet()
				    .add("twitter", "tweet", "1")           
				    .add("twitter", "tweet", "2", "3", "4") 
				    .add("another", "type", "foo")          
				    .get();

				for (MultiGetItemResponse itemResponse : multiGetItemResponses) { 
				    GetResponse response = itemResponse.getResponse();
				    if (response.isExists()) {                      
				        String json = response.getSourceAsString(); 
				    }
				}
		} catch (Exception e) {
		}
	}
	
	
	
}
