package com.mycrawler.tutuorial.activemq;

import javax.jms.MessageListener;

public class ActiveMQReceiverFactory implements IReceiverFactory {
    private static ActiveMQReceiverFactory instance = null;

    public static ActiveMQReceiverFactory getInstance() {
	if (instance == null) {
	    synchronized (ActiveMQReceiverFactory.class) {
		instance = new ActiveMQReceiverFactory();
		return instance;
	    }
	} else {
	    return instance;
	}
    }

    @Override
    public IReceiver createReceiver(String queueName, String connectionUrl, MessageListener listerner) {
    	IReceiver receiver = new ActiveMQReceiver(queueName, connectionUrl, listerner);
		return receiver;
    }

    @Override
    public IReceiver createReceiver(String queueName, String connectionUrl, MessageListener listerner, boolean isBlockMode, boolean isTopicMode) {
    	IReceiver receiver = new ActiveMQReceiver(queueName, connectionUrl, listerner, isBlockMode, isTopicMode);
		return receiver;
    }
}
