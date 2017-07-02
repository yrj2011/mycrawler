package com.mycrawler.tutuorial.activemq;

import javax.jms.MessageListener;

public interface IResponserFactory {
	public IResponser createResponser(String request_queue_name,String connection_url,boolean is_persistent,MessageListener listener);
}
