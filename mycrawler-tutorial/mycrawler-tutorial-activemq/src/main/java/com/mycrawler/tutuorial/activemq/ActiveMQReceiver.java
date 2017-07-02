package com.mycrawler.tutuorial.activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQReceiver implements IReceiver,MessageListener{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String receiveQueueName;
	private String connectionUrl; 
	private MessageListener listerner;
	
	private Destination receiveDestination;
    private MessageConsumer receiveConsumer;
    private Session session;
	private Connection connection;
	private boolean isBlockMode = false;
	private boolean isTopicMode = false;
	
	protected ActiveMQReceiver(String receiveQueueName,String connectionUrl,MessageListener listerner){
		this.receiveQueueName = receiveQueueName;
		this.connectionUrl = connectionUrl;
		this.listerner = listerner;
		this.isBlockMode = false;
		this.isTopicMode = false;
	}
	
	protected ActiveMQReceiver(String receiveQueueName,String connectionUrl){
		this.receiveQueueName = receiveQueueName;
		this.connectionUrl = connectionUrl;
		this.isBlockMode = true;
		this.isTopicMode = false;
	}
	
	protected ActiveMQReceiver(String receiveQueueName,String connectionUrl,MessageListener listerner, boolean isBlockMode, boolean isTopicMode){
		this.receiveQueueName = receiveQueueName;
		this.connectionUrl = connectionUrl;
		this.listerner = listerner;
		this.isBlockMode = isBlockMode;
		this.isTopicMode = isTopicMode;
	}

	@Override
	public void init(){
		ActiveMQConnectionFactory connectionFactory = ActiveMQConnectionFactoryFactory.create(receiveQueueName, connectionUrl);
		//connectionFactory.setAlwaysSessionAsync(false);
		connectionFactory.setMaxThreadPoolSize(10);

		try {
			//register
			connection = connectionFactory.createConnection();
			connection.start();
			
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			if (this.isTopicMode) {
			    receiveDestination = session.createTopic(receiveQueueName);
			} else {
			    receiveDestination = session.createQueue(receiveQueueName);
			}
			
			receiveConsumer = session.createConsumer(receiveDestination);
			
			if (!this.isBlockMode) {
			    receiveConsumer.setMessageListener(this);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	@Override
	public void destroy(){
		try {
			receiveConsumer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message msg) {
		try {
			logger.info(String.format("onMessage|received:%d", System.currentTimeMillis()));
			listerner.onMessage(msg);
			logger.info(String.format("onMessage|received process complete:%d", System.currentTimeMillis()));
			session.commit();
			logger.info(String.format("onMessage|received commit complete:%d", System.currentTimeMillis()));
		} catch (Exception e) {
			logger.error("",e);
		}

	}

	@Override
	public ObjectMessage receive() {
	    try {
		    ObjectMessage obj_response = (ObjectMessage)receiveConsumer.receive();
		    session.commit();
		    return obj_response;
		} catch (JMSException e) {
			logger.error("",e);
		}
		
		return null;
	}
}
