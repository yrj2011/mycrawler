package com.mycrawler.tutuorial.activemq;

import javax.jms.MessageListener;

public interface IReceiverFactory {
    public IReceiver createReceiver(String notify_queue_name, String connection_url, MessageListener listerner);
    public IReceiver createReceiver(String notify_queue_name, String connection_url, MessageListener listerner, boolean is_block_mode, boolean is_topic_mode);
}
