package com.mycrawler.tutuorial.activemq;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TemporaryQueue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQResponser implements IResponser,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 949972131865314781L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Connection connection;
	private Session session;
	private Destination request_destination;
	private MessageProducer response_producer;
	private MessageConsumer request_consumer;
	
	private String request_queue_name;
	private String connection_url;
	private boolean is_persistent;
	private MessageListener listener;
	
	public  ActiveMQResponser(){}
	
	protected ActiveMQResponser(String request_queue_name,String connection_url,boolean is_persistent,MessageListener listener){
		this.request_queue_name = request_queue_name;
		this.connection_url = connection_url;
		this.is_persistent = is_persistent;
		this.listener = listener;
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
			
			response_producer = session.createProducer(null);
			//���ý���ʱ���Ϊtrue����ʾʹ�÷�����ʱ����Ϊʱ������������Ա�����Ϊ�ͻ��˺ͷ�����ʱ�䲻ͬ����ɵĶ�������
			response_producer.setDisableMessageTimestamp(true);
			if(is_persistent){
				response_producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			}
			else{
				response_producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			}
			response_producer.setDisableMessageTimestamp(true);
			request_consumer = session.createConsumer(request_destination);
			request_consumer.setMessageListener(listener);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	@Override
	public void response(Serializable msg,String messageId,Destination destination,String type){
		try {
			ObjectMessage obj_msg = session.createObjectMessage(msg);
			obj_msg.setJMSCorrelationID(messageId);
			obj_msg.setStringProperty("type", type);
			logger.info(String.format("response|send:%d|type:%s", System.currentTimeMillis(),type));
			response_producer.send(destination,obj_msg);
			logger.info(String.format("response|send_complete:%d|type:%s", System.currentTimeMillis(),type));
			session.commit();
			logger.info(String.format("response|commit_complete:%d|type:%s", System.currentTimeMillis(),type));
		} catch (InvalidDestinationException e) {
			logger.info("",e);
			//DestinationΪTemporaryQueue���ѱ�ɾ�����޷�����ִ�����
			if(destination instanceof TemporaryQueue){
				try {
					session.commit();
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	@Override
	public void response(){
		try {
			session.commit();
			logger.info(String.format("response|commit_complete:%d", System.currentTimeMillis()));
		} catch (Exception e) {
			logger.error("",e);
		}
	}
		
	@Override
	public void destroy(){
		try {
			connection.close();
			session.close();
			response_producer.close();
			request_consumer.close();
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}
	

}
