package com.mycrawler.tutorial.elasticsearch;

import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
public class ESDelete {
	private ESClient esclient = new ESClient();
	public void init(){
		esclient.init();
	}
	public static void main(String[] args) {
		
		ESDelete getter = new ESDelete();
		getter.init();
		getter.get();
	}
	
	public void get(){
		try {
			GetResponse response = esclient.getClient().prepareGet("twitter", "tweet", "1").get();
			Map<String,Object> soruce = response.getSource();
			System.out.println(soruce);
		} catch (Exception e) {
		}
	}
}
