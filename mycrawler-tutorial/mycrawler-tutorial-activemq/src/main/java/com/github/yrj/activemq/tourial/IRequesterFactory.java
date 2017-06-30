package com.github.yrj.activemq.tourial;

public interface IRequesterFactory {
	/**
	 * ʹ��TempQueue
	 * @param request_queue_name
	 * @param connection_url
	 * @param is_persistent
	 * @return
	 */
	public IRequester createRequester(String request_queue_name,String connection_url,boolean is_persistent);
}
