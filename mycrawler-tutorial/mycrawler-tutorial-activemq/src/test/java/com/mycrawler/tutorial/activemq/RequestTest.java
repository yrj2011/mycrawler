package com.mycrawler.tutorial.activemq;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.junit.Test;

import com.mycrawler.tutuorial.activemq.ActiveMQRequesterFactory;
import com.mycrawler.tutuorial.activemq.ActiveMQResponserFactory;
import com.mycrawler.tutuorial.activemq.IRequester;
import com.mycrawler.tutuorial.activemq.IResponser;

public class RequestTest  implements MessageListener{
	private String requestQueueName = "my_queue_1";
	private String connectionUrl = "failover:(tcp://localhost:61616)?"
			+ "maxReconnectDelay=1000&jms.prefetchPolicy.all=1000&"
			+ "jms.optimizeAcknowledge=true&jms.useCompression=true";
	private boolean isPersistent = false;
	@Test
	public void testRequest(){
		IRequester requester = ActiveMQRequesterFactory.getInstance().createRequester(requestQueueName, connectionUrl, isPersistent);
		requester.init();
		System.out.println();
		synchronized (requester) {
			try {
				requester.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testResponse(){
		IResponser responser = ActiveMQResponserFactory.getInstance().createResponser(requestQueueName, connectionUrl, isPersistent,this);
		responser.init();
		System.out.println();
		synchronized (responser) {
			try {
				responser.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onMessage(Message message) {
		System.out.println();
		
	}
	
}
