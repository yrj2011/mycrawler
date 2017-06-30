package com.mycrawler.common.utils;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SettingProperties {
    private static final Logger logger = LoggerFactory.getLogger(SettingProperties.class);
    
    private static volatile boolean loaded = false;

    public static SettingProperties instance = new SettingProperties();

    public static boolean isLoaded() {
		return loaded;
	}

	private Properties props;
	
	public static void onPropertyChange(String propName, String propValue){
		instance.props.put(propName, propValue);
	}

	public static SettingProperties getInstance(){
		if (instance == null) {
			instance = new SettingProperties();
		}
		return instance;
	}
	
    private SettingProperties() {
        try {
            props = PropertiesLoader.load("setting.properties", true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception happens in SettingProperties()", e);
            System.exit(-1);
        }
    }

    public boolean getLoadExtendsFromDB() {
    	 boolean loadExtendedConfigFromDB = !Boolean.FALSE.toString().equalsIgnoreCase(props.getProperty("LOAD_EXTENDED_CONFIG_FROM_DB"));
         return loadExtendedConfigFromDB;
    }

    public String getModuleID() {
        return this.props.getProperty("MODULE_ID");
    }
    
    public String getDBConnDriver() {
    	return this.props.getProperty("DB_CONN_DRIVER");
    }

    public String getDBConnURL() {
        return this.props.getProperty("DB_CONN_URL");
    }

    public String getDBConnUser() {
        return this.props.getProperty("DB_CONN_USER");
    }

    public String getDBConnPwd() {
        return this.props.getProperty("DB_CONN_PWD");
    }

    public String getMQConnectionURL() {
        return this.props.getProperty("MESSAGE_QUEUE_CONNECTION_URL");
    }

    public String getMQUsername() {
        return this.props.getProperty("MESSAGE_QUEUE_USERNAME");
    }

    public String getMQPassword() {
        return this.props.getProperty("MESSAGE_QUEUE_PASSWORD");
    }

    public int getDBConnFailRetryInterval() {
        return Integer.parseInt(this.props.getProperty("DATABASE_CONNECTION_FAILURE_RETRY_INTERVAL"));
    }

    public int getDBConnFailRetryTimes() {
        return Integer.parseInt(this.props.getProperty("DATABASE_CONNECTION_FAILURE_RETRY_TIMES"));
    }

    public String getZooKeeperConnURL() {
        return this.props.getProperty("ZOOKEEPER_CONN_URL","localhost:2181");
    }

    public int getZooKeeperConnTimeout() {
        return Integer.parseInt(this.props.getProperty("ZOOKEEPER_CONN_TIMEOUT","500000"));
    }
    
    public String getWalleLogDir() {
        return this.props.getProperty("WALLE_LOG_DIR");
    }

    public String getWalleTestDir() {
        return this.props.getProperty("WALLE_TEST_DIR");
    }

    public String getWalleManagerPath() {
        return this.props.getProperty("WALLE_MANAGER_BUILD_PATH");
    }

    public String getWalleRemoteDriveUnitConfigurePath() {
        return this.props.getProperty("REMOTE_DRIVEUNIT_CONFIG");
    }

    public String getEthernetIP() {
        return this.props.getProperty("EthernetIP");
    }

    public String getAgentPort() {
        return this.props.getProperty("AGENT_PORT");
    }

    public String getDriveUnitManagerPort() {
        return this.props.getProperty("DRIVEUNIT_MANAGER_PORT");
    }

    public String getDriveUnitBuildDir() {
        return this.props.getProperty("DRIVEUNIT_BUILD_BASE_DIR");
    }

    public String getDriveunitWorkingModeFileName() {
        return this.props.getProperty("workingModeFileName");
    }

    public int getBucketWeight() {
        return Integer.parseInt(this.props.getProperty("bucket_weight"));
    }

    public int getDriveunitPodLength() {
        return Integer.parseInt(this.props.getProperty("driveunit_pod_length"));
    }

    public int getRemoteMethodInvocationTimeout() {
        int remoteMethodInvocationTimeout = 0;
        try {
            remoteMethodInvocationTimeout = Integer.parseInt(this.props.getProperty("REMOTE_METHOD_INVOCATION_TIMEOUT"));
        } catch (Exception e) {
            remoteMethodInvocationTimeout = 10000;
        }
        return remoteMethodInvocationTimeout;
    }

    public int getDasServerPort() {
        int serverPort = 0;
        try {
            serverPort = Integer.parseInt(this.props.getProperty("DAS_SERVER_PORT"));
        } catch (Exception e) {
            serverPort = 8090;
        }
        return serverPort;
    }
    
    public String getDasServerIP() {
        return this.props.getProperty("DAS_SERVER_IP");
    }

    public String getLocalMessageQueueConnectionUrl() {
        return "vm://localhost?broker.persistent=false";
    }

    public String getBuildFolderName() {
        return this.props.getProperty("BUILD_FOLDER");
    }

    public String getWMSServerPath() {
        return this.props.getProperty("WMS_SERVER_PATH");
    }

    public int getInterfaceServerPort() {
        int serverPort = 0;
        try {
            serverPort = Integer.parseInt(this.props.getProperty("INTERFACE_PORT"));
        } catch (Exception e) {
            serverPort = 8010;
        }
        return serverPort;
    }
    
   
    
    public int getEventServerPort() {
        int serverPort = 0;
        try {
            serverPort = Integer.parseInt(this.props.getProperty("EVENT_PORT"));
        } catch (Exception e) {
            serverPort = 8020;
        }
        return serverPort;
    }
}
