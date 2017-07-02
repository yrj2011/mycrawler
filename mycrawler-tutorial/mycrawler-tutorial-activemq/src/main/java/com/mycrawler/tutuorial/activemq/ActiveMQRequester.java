package com.mycrawler.tutuorial.activemq;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActiveMQRequester implements IRequester{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Destination request_destination;
    private Destination response_destination;
    
    private MessageProducer request_producer;
    private MessageConsumer response_consumer;
    
    private Session session;
    
	private Connection connection;
	
	private String request_queue_name;
	private String connection_url; 
	private boolean is_persistent;
	
	protected ActiveMQRequester(String request_queue_name,String response_queue_name,String connection_url,boolean is_persistent){
		this.request_queue_name = request_queue_name;
		this.connection_url = connection_url;
		this.is_persistent = is_persistent;
	}
	
	@Override
	public void init(){
		ActiveMQConnectionFactory connectionFactory = ActiveMQConnectionFactoryFactory.create(request_queue_name, connection_url);
		
		try {
			//register
			connection = connectionFactory.createConnection();
			connection.start();
			
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			request_destination = session.createQueue(request_queue_name);

			response_destination = session.createTemporaryQueue();
			
			request_producer = session.createProducer(request_destination);
			request_producer.setDisableMessageTimestamp(true);
			if(is_persistent){
				request_producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			}
			else{
				request_producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			}
			request_producer.setDisableMessageTimestamp(false);
			response_consumer = session.createConsumer(response_destination);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	@Override
	public synchronized ObjectMessage request(Serializable msg,String type){
		try {
			ObjectMessage obj_msg = session.createObjectMessage(msg);
			obj_msg.setJMSReplyTo(response_destination);
			obj_msg.setStringProperty("type", type);
			//���ʱ���
			logger.info(String.format("request_sync|send:%d|type:%s", System.currentTimeMillis(),type));
			request_producer.send(request_destination,obj_msg);
			logger.info(String.format("request_sync|send_complete:%d|type:%s", System.currentTimeMillis(),type));
			session.commit();
			logger.info(String.format("request_sync|commit_complete:%d|type:%s", System.currentTimeMillis(),type));
			String messageId = obj_msg.getJMSMessageID();
			while(true){
				ObjectMessage obj_response = (ObjectMessage)response_consumer.receive();
				logger.info(String.format("request_sync|received:%d|type:%s", System.currentTimeMillis(),type));
				session.commit();
				logger.info(String.format("request_sync|received_commit_complete:%d|type:%s", System.currentTimeMillis(),type));
				if(obj_response.getJMSCorrelationID().equals(messageId)){
					logger.info(String.format("request_sync|received_complete:%d|type:%s", System.currentTimeMillis(),type));
					return obj_response;
				}
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}
	@Override
	public void destroy(){
		try {
			connection.close();
			session.close();
			response_consumer.close();
			request_producer.close();
		} catch (Exception e) {
			logger.info("",e);
		}
		
	}
	@Override
	public synchronized ObjectMessage request(Serializable msg, String type, long timeout) {
		try {
			ObjectMessage obj_msg = session.createObjectMessage(msg);
			obj_msg.setJMSReplyTo(response_destination);
			obj_msg.setStringProperty("type", type);
			logger.info(String.format("request_async|send:%d|type:%s", System.currentTimeMillis(),type));
			request_producer.send(request_destination,obj_msg);
			logger.info(String.format("request_async|send_complete:%d|type:%s", System.currentTimeMillis(),type));
			session.commit();
			logger.info(String.format("request_async|commit_complete:%d|type:%s", System.currentTimeMillis(),type));
			String messageId = obj_msg.getJMSMessageID();
			while(true){
				ObjectMessage obj_response = (ObjectMessage)response_consumer.receive(timeout);
				logger.info(String.format("request_async|received:%d|type:%s", System.currentTimeMillis(),type));
				session.commit();
				logger.info(String.format("request_async|received_commit_complete:%d|type:%s", System.currentTimeMillis(),type));
				if(obj_response==null || obj_response.getJMSCorrelationID().equals(messageId)){
					if(obj_response==null){
						logger.info(String.format("request_async|received_timeout:%d|type:%s", System.currentTimeMillis(),type));
					}
					else{
						logger.info(String.format("request_async|received_complete:%d|type:%s", System.currentTimeMillis(),type));
					}
					return obj_response;
				}
			}
			
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	@Override
	public synchronized ObjectMessage request(Serializable msg, String type, boolean retry, long timeout) {
		boolean needRetry = true;
		ObjectMessage obj_response = null;
		long retryWaitTime = 2000;
		long maxRetryWaitTime = timeout;
		while(needRetry) {
			try {
				ObjectMessage obj_msg = session.createObjectMessage(msg);
				obj_msg.setJMSReplyTo(response_destination);
				obj_msg.setStringProperty("type", type);
				logger.info(String.format("request_async|send:%d|type:%s", System.currentTimeMillis(),type));
				request_producer.send(request_destination,obj_msg);
				logger.info(String.format("request_async|send_complete:%d|type:%s", System.currentTimeMillis(),type));
				session.commit();
				logger.info(String.format("request_async|commit_complete:%d|type:%s", System.currentTimeMillis(),type));
				String messageId = obj_msg.getJMSMessageID();
				while(true){
					obj_response = (ObjectMessage)response_consumer.receive(timeout);
					logger.info(String.format("request_async|received:%d|type:%s", System.currentTimeMillis(),type));
					session.commit();
					logger.info(String.format("request_async|received_commit_complete:%d|type:%s", System.currentTimeMillis(),type));
					if(obj_response==null || obj_response.getJMSCorrelationID().equals(messageId)){
						if(obj_response==null){
							logger.info(String.format("request_async|received_timeout:%d|type:%s", System.currentTimeMillis(),type));
							needRetry = retry;
							if(needRetry) {
								logger.info("request|retry_request_again|sleep sleepTime:" + retryWaitTime);
								Thread.sleep(retryWaitTime);
							}
						}
						else{
							logger.info(String.format("request_async|received_complete:%d|type:%s", System.currentTimeMillis(),type));
							needRetry = false;
						}
						break;
					}
				}
				
			} catch (Exception e) {
				logger.info(String.format("request|encounter_exception:%d|type:%s", System.currentTimeMillis(), type));
				needRetry = retry;
				obj_response = null;
				logger.error("",e);
			}
			if(needRetry) {
				retryWaitTime = retryWaitTime * 2;
				if(retryWaitTime > maxRetryWaitTime) {
					retryWaitTime = maxRetryWaitTime;
				}
				logger.info("request|retry_request_again|waitTime:" + retryWaitTime);
			}
		}
		return obj_response;
	}
}
