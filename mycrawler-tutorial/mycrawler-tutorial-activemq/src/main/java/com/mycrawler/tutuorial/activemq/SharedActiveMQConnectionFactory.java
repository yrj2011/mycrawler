package com.mycrawler.tutuorial.activemq;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.TopicConnection;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycrawler.common.utils.StringUtils;


public class SharedActiveMQConnectionFactory extends ActiveMQConnectionFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(SharedActiveMQConnectionFactory.class);
	
	private String key;
	
	private static Map<String, Connection> sharedConnectionMap = new HashMap<String, Connection>();
	
	private static final String CONNECTION_NAME_CONNECTOR = "_";
	
	public static final String CONN_GROUP = "connGroup";
	
	public SharedActiveMQConnectionFactory(String key) {
        super();
        this.key = key;
    }

    public SharedActiveMQConnectionFactory(String brokerURL, String key) {
        super(brokerURL);
        this.key = key;
    }

    public SharedActiveMQConnectionFactory(URI brokerURL, String key) {
        super(brokerURL);
        this.key = key;
    }

    public SharedActiveMQConnectionFactory(String userName, String password, URI brokerURL, String key) {
        super(userName, password, brokerURL);
        this.key = key;
    }

    public SharedActiveMQConnectionFactory(String userName, String password, String brokerURL, String key) {
        super(userName, password, brokerURL);
        this.key = key;
    }
	
    private String getConnectionName(String userName, String password) {
		StringBuilder sb = new StringBuilder(key);
		sb.append(CONNECTION_NAME_CONNECTOR).append(this.brokerURL.hashCode());
		if (!StringUtils.isEmpty(userName)) {
			sb.append(CONNECTION_NAME_CONNECTOR).append(userName);
		} else if (!StringUtils.isEmpty(this.userName)) {
			sb.append(CONNECTION_NAME_CONNECTOR).append(this.userName);
		}
		if (!StringUtils.isEmpty(password)) {
			sb.append(CONNECTION_NAME_CONNECTOR).append(password);
		} else if (!StringUtils.isEmpty(this.password)) {
			sb.append(CONNECTION_NAME_CONNECTOR).append(this.password);
		}
		/**
		 * 使用说明：
		 * 当需要重复创建同名的连接，可以设置属于不同group。
		 * 可在启动时指定：-Dgroup={group}
		 * 也可在程序中指定不同的分组：System.setProperty("group", "{group}")
		 * 通常，可以忽略以下代码。
		 * 如：
		 * 在启动仿真小车时，一个java进程按顺序启动多个小车，每个小车持有自己的连接，
		 * 不同小车的连接同名，则可以在启动第一辆前设置group={driveunitID1}，
		 * 第一辆启动完成、启动第二辆前设置group={driveunitID2}，则不同车持有自己的连接
		 * 这是此处代码存在的主要原因。
		 */
		String instance = System.getProperty(CONN_GROUP, null);
		if (!StringUtils.isEmpty(instance)) {
			sb.append(CONNECTION_NAME_CONNECTOR).append(instance);
		}
		return sb.toString();
	}
    
	private <S extends Connection> S getSharedConnection(String connectionName) {
		
		if (sharedConnectionMap.containsKey(connectionName)) {
			Connection connection =  sharedConnectionMap.get(connectionName);
			if ((connection != null && checkClosed(connection))) {
				logger.info(String.format("getSharedConnection:%s", connectionName));
				return (S) connection;
			}
		}
		return null;
	}
	
	private boolean checkClosed(Connection connection) {
		try {
			connection.start();
		} catch (JMSException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public TopicConnection createTopicConnection() throws JMSException {
		String connectionName = getConnectionName(null, null);
		TopicConnection connection = getSharedConnection(connectionName);
		if (connection != null) {
			return connection;
		}
		connection = super.createTopicConnection();
		logger.info(String.format("createTopicConnection() with name:%s", connectionName));
		sharedConnectionMap.put(connectionName, connection);
		return connection;
	}
	
	@Override
	public TopicConnection createTopicConnection(String userName,
			String password) throws JMSException {
		String connectionName = getConnectionName(userName, password);
		TopicConnection connection = getSharedConnection(connectionName);
		if (connection != null) {
			return connection;
		}
		connection = super.createTopicConnection(userName, password);
		logger.info(String.format("createTopicConnection(%s,%s) with name:%s", userName, password, connectionName));
		sharedConnectionMap.put(connectionName, connection);
		return connection;
	}
	@Override
	public QueueConnection createQueueConnection() throws JMSException {
		String connectionName = getConnectionName(null, null);
		QueueConnection connection = getSharedConnection(connectionName);
		if (connection != null) {
			return connection;
		}
		connection = super.createQueueConnection();
		logger.info(String.format("createQueueConnection() with name:%s", connectionName));
		sharedConnectionMap.put(connectionName, connection);
		return connection;
	}
	@Override
	public QueueConnection createQueueConnection(String userName,
			String password) throws JMSException {
		String connectionName = getConnectionName(userName, password);
		QueueConnection connection = getSharedConnection(connectionName);
		if (connection != null) {
			return connection;
		}
		connection = super.createQueueConnection(userName, password);
		logger.info(String.format("createQueueConnection(%s,%s) with name:%s", userName, password, connectionName));
		sharedConnectionMap.put(connectionName, connection);
		return connection;
	}
	@Override
	public Connection createConnection() throws JMSException {
		String connectionName = getConnectionName(null, null);
		Connection connection = getSharedConnection(connectionName);
		if (connection != null) {
			return connection;
		}
		connection = super.createConnection();
		logger.info(String.format("createConnection() with name:%s", connectionName));
		sharedConnectionMap.put(connectionName, connection);
		return connection;
	}
	@Override
	public Connection createConnection(String userName, String password)
			throws JMSException {
		String connectionName = getConnectionName(userName, password);
		Connection connection = getSharedConnection(connectionName);
		if (connection != null) {
			return connection;
		}
		connection = super.createConnection(userName, password);
		logger.info(String.format("createConnection(%s,%s) with name:%s", userName, password, connectionName));
		sharedConnectionMap.put(connectionName, connection);
		return connection;
	}
	
}
