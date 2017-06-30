package com.github.yrj.activemq.tourial;

import java.io.Serializable;

import javax.jms.Destination;

public interface IResponser {
	public void init();
	public void destroy();
	public void response(Serializable msg,String messageId,Destination destination,String type);
	public void response();
}
