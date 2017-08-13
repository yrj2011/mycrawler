package com.mycrawler.tutorial.activemq;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.junit.Before;
import org.junit.Test;

import com.mycrawler.common.utils.LoggerUtils;
import com.mycrawler.tutuorial.activemq.ActiveMQNotifierFactory;
import com.mycrawler.tutuorial.activemq.ActiveMQRequesterFactory;
import com.mycrawler.tutuorial.activemq.ActiveMQResponserFactory;
import com.mycrawler.tutuorial.activemq.INotifier;
import com.mycrawler.tutuorial.activemq.IRequester;
import com.mycrawler.tutuorial.activemq.IResponser;
import com.mysql.cj.core.util.LogUtils;

public class RequestTest  implements MessageListener{
	private static final int QUEUENUM = 1;
	private String requestQueueName = "my_queue_1";
	private String notifyQueueName = "my_notify_queue_1";
	private String connectionUrl = "failover:(tcp://localhost:61616)?"
			+ "maxReconnectDelay=1000&jms.prefetchPolicy.all=1000&"
			+ "jms.optimizeAcknowledge=true&jms.useCompression=true";
	private boolean isPersistent = false;
	private String  message = "1234567890"+
								"abcdefghijklmnloprstuvwxyz"+
								"ABCDEFGHIJKLMNLOPRSTUVWXYZ"+
								"!@#$%^&*()"+
								 "1234567890"+
									"abcdefghijklmnloprstuvwxyz"+
									"ABCDEFGHIJKLMNLOPRSTUVWXYZ"+
									"!@#$%^&*()"+
									"1234567890"+
									"abcdefghijklmnloprstuvwxyz"+
									"ABCDEFGHIJKLMNLOPRSTUVWXYZ"+
									"!@#$%^&*()"+
									 "1234567890"+
										"abcdefghijklmnloprstuvwxyz"+
										"ABCDEFGHIJKLMNLOPRSTUVWXYZ"+
										"!@#$%^&*()";
	@Before
	public void init(){
		LoggerUtils.init();
	}
	@Test
	public void testNotify(){
		INotifier[] notifys = new INotifier[100];
		for (int i=0;i<QUEUENUM;i++) {
			INotifier notifier = ActiveMQNotifierFactory.getInstance().createNotifier(notifyQueueName+i, connectionUrl, isPersistent);
			notifier.init();
			notifys[i	]=notifier;
		}
//		
		System.out.println("notifier init !");
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {		
			notifys[i%QUEUENUM].notify(message+System.currentTimeMillis(), String.class.getName());
		}
		System.out.println("waste:"+(System.currentTimeMillis()-start));
	}
	
	//@Test
	public void testRequest(){
		IRequester requester = ActiveMQRequesterFactory.getInstance().createRequester(requestQueueName, connectionUrl, isPersistent);
		requester.init();
		System.out.println("request init !");
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			requester.request(message+System.currentTimeMillis(), String.class.getName());
		}
		System.out.println("waste:"+(System.currentTimeMillis()-start));
	}
	
	//@Test
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
