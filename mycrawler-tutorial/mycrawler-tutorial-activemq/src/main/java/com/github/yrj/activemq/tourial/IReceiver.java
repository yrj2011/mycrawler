package com.github.yrj.activemq.tourial;

import javax.jms.ObjectMessage;

public interface IReceiver {
	public void init();
	public void destroy();
	public ObjectMessage receive();
}
