package com.mycrawler.tutuorial.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycrawler.common.utils.ActiveMQProperties;
import com.mycrawler.common.utils.SettingProperties;
import com.mycrawler.common.utils.StringUtils;

public class ActiveMQConnectionFactoryFactory {
	private static final Logger logger = LoggerFactory.getLogger(ActiveMQConnectionFactoryFactory.class);
	
	private final static String MQ_CONNECTION_URL = SettingProperties.instance.getMQConnectionURL();
	private final static String MQ_LOCAL_CONNECTION_URL = SettingProperties.instance.getLocalMessageQueueConnectionUrl();
	
	public static ActiveMQConnectionFactory create(String queueName, String connectionUrl) {
		ActiveMQConnectionFactory connectionFactory = null;
		
		boolean isInnerQueue = isInnerQueue(connectionUrl);
		
		boolean isShareConnectionsOn = ActiveMQProperties.instance.isShareConnectionsOn(isInnerQueue);
		logger.info(String.format("connectionUrl:%s|queueName:%s|isInnerQueue:%s|isShareConnectionsOn?:%s", 
				connectionUrl, queueName, isInnerQueue, isShareConnectionsOn));
		if (isShareConnectionsOn) {
			String connectionName = ActiveMQProperties.instance.getConnnectionName(queueName);
			logger.info(String.format("connectionName:%s", connectionName));
			connectionFactory = new SharedActiveMQConnectionFactory(
					SettingProperties.instance.getMQUsername(),
					SettingProperties.instance.getMQPassword(), 
					connectionUrl, 
					connectionName);
		} else {
			connectionFactory = new ActiveMQConnectionFactory(
					SettingProperties.instance.getMQUsername(), 
					SettingProperties.instance.getMQPassword(), 
					connectionUrl);
		}
		
		return connectionFactory;
	}
	
	private static boolean isInnerQueue(String connectionUrl) {
		if (StringUtils.isEmpty(connectionUrl)) {
			logger.warn(String.format("check connectionUrl|wrong format:", connectionUrl));
			return true;
		}
		if (StringUtils.equals(MQ_CONNECTION_URL, connectionUrl)) {
			return false;
		} else if (StringUtils.equals(MQ_LOCAL_CONNECTION_URL, connectionUrl)) {
			return true;
		} else {
			logger.warn(String.format("check connectionUrl|wrong format:", connectionUrl));
			return true;
		}
	}
	
}
