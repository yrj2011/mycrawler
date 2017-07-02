package com.mycrawler.tutorial.elasticsearch;

import org.elasticsearch.action.delete.DeleteResponse;
public class ESGet {
	private ESClient esclient = new ESClient();
	public void init(){
		esclient.init();
	}
	public static void main(String[] args) {
		
		ESGet deteter = new ESGet();
		deteter.init();
		deteter.get();
	}
	
	public void get(){
		try {
			DeleteResponse response = esclient.getClient().prepareDelete("twitter", "tweet", "1").get();
			System.out.println(response);
		} catch (Exception e) {
		}
	}
}
