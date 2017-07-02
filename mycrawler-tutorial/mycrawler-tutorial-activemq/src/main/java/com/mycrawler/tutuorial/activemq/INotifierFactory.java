package com.mycrawler.tutuorial.activemq;

public interface INotifierFactory {
	public INotifier createNotifier(String notify_queue_name,String connection_url,boolean is_persistent);
	public INotifier createNotifier(String notify_queue_name,String connection_url,boolean is_persistent, boolean is_topic_mode);
}
