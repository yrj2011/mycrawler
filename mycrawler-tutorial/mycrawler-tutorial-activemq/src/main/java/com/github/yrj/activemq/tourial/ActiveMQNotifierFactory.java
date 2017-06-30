package com.github.yrj.activemq.tourial;

public class ActiveMQNotifierFactory implements INotifierFactory {
    private static ActiveMQNotifierFactory instance = null;

    public static ActiveMQNotifierFactory getInstance() {
	if (instance == null) {
	    synchronized (ActiveMQReceiverFactory.class) {
		instance = new ActiveMQNotifierFactory();
		return instance;
	    }
	} else {
	    return instance;
	}
    }

    @Override
    public INotifier createNotifier(String queueName, String connectionUrl, boolean isPersistent) {
    	INotifier notifier = new ActiveMQNotifier(queueName, connectionUrl, isPersistent);
    	return notifier;
    }

    @Override
    public INotifier createNotifier(String queueName, String connectionUrl, boolean isPersistent, boolean isTopicMode) {
    	INotifier notifier = new ActiveMQNotifier(queueName, connectionUrl, isPersistent, isTopicMode);
    	return notifier;
    }
}
