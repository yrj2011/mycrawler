package com.mycrawler.tutorial.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptService;
public class ESUpdate {
	private ESClient esclient = new ESClient();
	public void init(){
		esclient.init();
	}
	public static void main(String[] args) {
		
		ESUpdate updater = new ESUpdate();
		updater.init();
		updater.update();
	}
	
	public void update(){
		try {
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.index("index");
			updateRequest.type("type");
			updateRequest.id("1");
			updateRequest.doc(jsonBuilder()
			        .startObject()
			            .field("gender", "male")
			        .endObject());
			esclient.getClient().update(updateRequest).get();
		} catch (Exception e) {
		}
	}
	
	public void update2(){
		try {
			/*esclient.getClient().prepareUpdate("ttl", "doc", "1")
	        .setScript(new Script("ctx._source.gender = \"male\""  , ScriptService.ScriptType.INLINE, null, null))
	        .get();*/

			esclient.getClient().prepareUpdate("ttl", "doc", "1")
	        .setDoc(jsonBuilder()               
	            .startObject()
	                .field("gender", "male")
	            .endObject())
	        .get();
		} catch (Exception e) {
		}
	}
	
	public void update3(){
		try {
			UpdateRequest updateRequest = new UpdateRequest("ttl", "doc", "1")
			        .script(new Script("ctx._source.gender = \"male\""));
			esclient.getClient().update(updateRequest).get();
		} catch (Exception e) {
		}
	}
	

	public void update4(){
		try {
			UpdateRequest updateRequest = new UpdateRequest("index", "type", "1")
			        .doc(jsonBuilder()
			            .startObject()
			                .field("gender", "male")
			            .endObject());
			esclient.getClient().update(updateRequest).get();
		} catch (Exception e) {
		}
	}
	
	public void update5(){
		try {
			IndexRequest indexRequest = new IndexRequest("index", "type", "1")
			        .source(jsonBuilder()
			            .startObject()
			                .field("name", "Joe Smith")
			                .field("gender", "male")
			            .endObject());
			UpdateRequest updateRequest = new UpdateRequest("index", "type", "1")
			        .doc(jsonBuilder()
			            .startObject()
			                .field("gender", "male")
			            .endObject())
			        .upsert(indexRequest);              
			esclient.getClient().update(updateRequest).get();
		} catch (Exception e) {
		}
	}
	
	
}
