package com.github.yrj.elasticsearch.tourial;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.util.Date;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
public class ESBulk {
	private ESClient esclient = new ESClient();
	public void init(){
		esclient.init();
	}
	public static void main(String[] args) {
		
		ESBulk bulker = new ESBulk();
		bulker.init();
		bulker.bulk();
	}

	
	public void bulk(){
		try {
			TransportClient client = esclient.getClient();
			BulkRequestBuilder bulkRequest = esclient.getClient().prepareBulk();

			// either use client#prepare, or use Requests# to directly build index/delete requests
			bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
			        .setSource(jsonBuilder()
			                    .startObject()
			                        .field("user", "kimchy")
			                        .field("postDate", new Date())
			                        .field("message", "trying out Elasticsearch")
			                    .endObject()
			                  )
			        );

			bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
			        .setSource(jsonBuilder()
			                    .startObject()
			                        .field("user", "kimchy")
			                        .field("postDate", new Date())
			                        .field("message", "another post")
			                    .endObject()
			                  )
			        );

			BulkResponse bulkResponse = bulkRequest.get();
			if (bulkResponse.hasFailures()) {
			    // process failures by iterating through each bulk response item
			}
		} catch (Exception e) {
		}
	}
	
	
	
}
