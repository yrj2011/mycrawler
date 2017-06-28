package com.github.yrj.elasticsearch.tourial;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ESClient implements Closeable{
	
	private TransportClient client ;
	
	public TransportClient getClient() {
		return client;
	}
	
	public void init(){
		try {
			client = new PreBuiltTransportClient(Settings.EMPTY)
			        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void close() throws IOException {
		if(client != null){
			client.close();
		}
	}
	
	
}
