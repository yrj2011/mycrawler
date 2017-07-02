package com.mycrawler.tutorial.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;
public class ESIndex {
	private ESClient esclient = new ESClient();
	public void init(){
		esclient.init();
	}
	public static void main(String[] args) {
		
		ESIndex indexer = new ESIndex();
		indexer.init();
		indexer.createIndex2();
	}
	
	public XContentBuilder createBuilder(){
		try {
			XContentBuilder builder = jsonBuilder()
				    .startObject()
				        .field("user", "kimchy")
				        .field("postDate", new Date())
				        .field("message", "trying out Elasticsearch")
				    .endObject();
			return builder;
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}
	
	public void createIndex(){
		try {
			IndexResponse response = esclient.getClient().prepareIndex("twitter", "tweet", "1")
			        .setSource(jsonBuilder()
			                    .startObject()
			                        .field("user", "kimchy1")
			                        .field("postDate", new Date())
			                        .field("message", "trying out Elasticsearch1")
			                    .endObject()
			                  )
			        .get();
		} catch (Exception e) {
		}
		
	}
	public void createIndex2(){
		try {
			String json = "{" +
			        "\"user\":\"kimchy123\"," +
			        "\"postDate\":\"2013-01-30\"," +
			        "\"message\":\"trying out Elasticsearch\"" +
			    "}";

			IndexResponse response = esclient.getClient().prepareIndex("twitter", "tweet")
			        .setSource(json)
			        .get();
			// Index name
			String _index = response.getIndex();
			// Type name
			String _type = response.getType();
			// Document ID (generated or not)
			String _id = response.getId();
			// Version (if it's the first time you index this document, you will get: 1)
			long _version = response.getVersion();
			// status has stored current instance statement.
			RestStatus status = response.status();
			System.out.println(status);
		} catch (Exception e) {
		}
		
	}
}
