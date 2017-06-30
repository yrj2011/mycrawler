package com.github.yrj.activemq.tourial;

import javax.jms.MessageListener;

public class ActiveMQResponserFactory implements IResponserFactory {
	private static ActiveMQResponserFactory instance = null;
	public static ActiveMQResponserFactory getInstance(){
		if(instance == null){
			synchronized (ActiveMQResponserFactory.class) {
				instance = new ActiveMQResponserFactory();
				return instance;
			}
		}
		else{
			return instance;
		}
	}
	@Override
	public IResponser createResponser(String queueName,String connectionUrl, boolean isPersistent,MessageListener listener) {
		ActiveMQResponser responser = new ActiveMQResponser(queueName, connectionUrl, isPersistent, listener);
		return responser;
	}

}
