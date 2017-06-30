package com.github.yrj.activemq.tourial;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQNotifier implements INotifier{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Destination notify_destination;
    
    private MessageProducer notify_producer;
    
    private Session session;
    
	private Connection connection;
	
	private String notify_queue_name;
	private String connection_url;
	private boolean is_persistent;
	private boolean is_topic_mode = false;
	
	protected ActiveMQNotifier(String notify_queue_name,String connection_url,boolean is_persistent){
		this.notify_queue_name = notify_queue_name;
		this.connection_url = connection_url;
		this.is_persistent = is_persistent;
		this.is_topic_mode = false;
	}

	protected ActiveMQNotifier(String notify_queue_name,String connection_url,boolean is_persistent, boolean is_topic_mode){
		this.notify_queue_name = notify_queue_name;
		this.connection_url = connection_url;
		this.is_persistent = is_persistent;
		this.is_topic_mode = is_topic_mode;
	}
	
	@Override
	public void init(){
		ActiveMQConnectionFactory connectionFactory = ActiveMQConnectionFactoryFactory.create(notify_queue_name, connection_url);
		try {
			//register
			connection = connectionFactory.createConnection();
			connection.start();
			
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			if (this.is_topic_mode) {
			    notify_destination = session.createTopic(notify_queue_name);
			} else {
			    notify_destination = session.createQueue(notify_queue_name);
			}
			
			notify_producer = session.createProducer(notify_destination);
			//���ý���ʱ���Ϊtrue����ʾʹ�÷�����ʱ����Ϊʱ������������Ա�����Ϊ�ͻ��˺ͷ�����ʱ�䲻ͬ����ɵĶ�������
			notify_producer.setDisableMessageTimestamp(true);
			if(is_persistent){
				notify_producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			}
			else{
				notify_producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	@Override
	public synchronized void notify(Serializable msg,String type){
		try {
			ObjectMessage obj_msg = session.createObjectMessage(msg);
			obj_msg.setStringProperty("type", type);
			logger.info(String.format("notify|send:%d|type:%s", System.currentTimeMillis(),type));
			notify_producer.send(notify_destination,obj_msg);
			logger.info(String.format("notify|send_complete:%d|type:%s", System.currentTimeMillis(),type));
			session.commit();
			logger.info(String.format("notify|commit_complete:%d|type:%s", System.currentTimeMillis(),type));
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	@Override
	public void destroy(){
		try {
			connection.close();
			session.close();
			notify_producer.close();
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}

}
