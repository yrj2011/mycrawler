package com.github.yrj.activemq.tourial;

public class ActiveMQRequesterFactory implements IRequesterFactory {
	private static ActiveMQRequesterFactory instance = null;
	public static ActiveMQRequesterFactory getInstance(){
		if(instance == null){
			synchronized (ActiveMQRequesterFactory.class) {
				instance = new ActiveMQRequesterFactory();
				return instance;
			}
		}
		else{
			return instance;
		}
	}
	

	
	@Override
	public IRequester createRequester(String requestQueueName, String connectionUrl, boolean isPersistent) {
		return new ActiveMQRequester(requestQueueName, null, connectionUrl, isPersistent);
	}

}
