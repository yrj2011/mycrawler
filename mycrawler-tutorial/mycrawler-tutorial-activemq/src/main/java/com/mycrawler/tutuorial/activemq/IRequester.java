package com.mycrawler.tutuorial.activemq;

import java.io.Serializable;

import javax.jms.ObjectMessage;

public interface IRequester {
	public void init();
	/**
	 * @param msg
	 * @param type
	 * @return
	 */
	public ObjectMessage request(Serializable msg,String type);
	/**
	 * @param msg
	 * @param type
	 * @param timeout
	 * @return
	 */
	public ObjectMessage request(Serializable msg,String type,long timeout);
	/**
	 * @param msg
	 * @param type
	 * @param timeout
	 * @param retry
	 * @return
	 */
	public ObjectMessage request(Serializable msg, String type, boolean retry, long timeout);
	/**
	 */
	public void destroy();
}
