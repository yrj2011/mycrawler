package com.mycrawler.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQProperties {
    private static final Logger logger = LoggerFactory.getLogger(ActiveMQProperties.class);
    
    public static ActiveMQProperties instance = new ActiveMQProperties();
    
    private static final String PACKAGE = "activemq.shareconn.";
    
    private static final String QUEUE_NAME_PATTERN_PREFIX = PACKAGE + "queueNamePattern.";
    
    private static final String APP_NAME_PATTERN_PREFIX = PACKAGE + "appNamePattern.";
    
    private static final String CONNECTION_NAME_PREFIX = PACKAGE + "connectionName.";
    
    private static final String SWITCH_PREFIX = PACKAGE + "switch.";
    
    private static final String UNKNOW = "UNKNOW";
	private static final String APP_NAME = "appName";
    
    private String appName;
    
    private List<ShareConnsSetting> shareConnsConfiguration = new ArrayList<ShareConnsSetting>();
    
    private boolean isShareConnsRemoteOn = false;
    
    private boolean isShareConnsLocalOn = false;
    
    private boolean isShareConnsFeatrueOn = false;
    
    private class ShareConnsSetting {
    	private Pattern pattern;
    	private String value;
    	public ShareConnsSetting(Pattern pattern, String value) {
    		this.pattern = pattern;
    		this.value = value;
    	}
    	public boolean matches(String name) {
    		return pattern.matcher(name).matches();
    	}
    	public String getValue() {
    		return this.value;
    	}
    }
    
    private ActiveMQProperties() {
    	appName = System.getProperty(APP_NAME, UNKNOW);
    }
    
    private void addShareConnsConfiguration(String name, String value) {
    	String realName = getShareConnsConfigurationName(name);
    	String realValue = getShareConnsConfigurationValue(value);
    	Pattern pattern = Pattern.compile(realName);
    	shareConnsConfiguration.add(new ShareConnsSetting(pattern, realValue));
    	logger.info(String.format("addConnPoolConfiguration|pattern:%s|value:%s", realName, realValue));
    }
    
    private void setShareConnsSwitches(String name, String value) {
    	String realName = getSwitchConfigurationName(name);
    	String realValue = getSwitchConfigurationValue(value);
    	Pattern pattern = Pattern.compile(realName);
    	if (pattern.matcher(appName).matches()) {
    		if (ALL_ON.equalsIgnoreCase(realValue)) {
    			this.isShareConnsFeatrueOn = true;
    			this.isShareConnsLocalOn = true;
    			this.isShareConnsRemoteOn = true;
			} else if (REMOTE_ON.equalsIgnoreCase(realValue)) {
				this.isShareConnsFeatrueOn = true;
				this.isShareConnsRemoteOn = true;
				if (LOCAL_ON.equalsIgnoreCase(realValue)) {
					this.isShareConnsLocalOn = true;
				}
			} else if (LOCAL_ON.equalsIgnoreCase(realValue)) {
				this.isShareConnsFeatrueOn = true;
				this.isShareConnsLocalOn = true;
			}
			
		}
    	logger.info(String.format("appName:%s|isShareConnsFeatrueOn:%s|isShareConnsLocalOn:%s|isShareConnsRemoteOn:%s|pattern:%s|value:%s", 
    			appName, this.isShareConnsFeatrueOn, this.isShareConnsLocalOn, this.isShareConnsRemoteOn, realName, realValue));
    }
    
    private String getShareConnsConfigurationName(String name) {
    	return name.replaceFirst(QUEUE_NAME_PATTERN_PREFIX, "").trim();
    }
    
    private String getSwitchConfigurationName(String name) {
    	return name.replaceFirst(APP_NAME_PATTERN_PREFIX, "").trim();
    }
    
    private String getShareConnsConfigurationValue(String name) {
    	return name.replaceFirst(CONNECTION_NAME_PREFIX, "");
    }
    
    private String getSwitchConfigurationValue(String name) {
    	return name.replaceFirst(SWITCH_PREFIX, "");
    }
    
    private boolean isShareConnsConfiguration(String name, String value) {
    	return StringUtils.startsWithIgnoreCase(name, QUEUE_NAME_PATTERN_PREFIX)
    			&& name.length() > QUEUE_NAME_PATTERN_PREFIX.length()
    			&& StringUtils.startsWithIgnoreCase(value, CONNECTION_NAME_PREFIX)
    			&& value.length() > CONNECTION_NAME_PREFIX.length();
    }
    
    private boolean isSwitchConfiguration(String name, String value) {
    	return StringUtils.startsWithIgnoreCase(name, APP_NAME_PATTERN_PREFIX)
    			&& name.length() > APP_NAME_PATTERN_PREFIX.length()
    			&& StringUtils.startsWithIgnoreCase(value, SWITCH_PREFIX)
    			&& value.length() > SWITCH_PREFIX.length();
    }
    
	private static final String ALL_ON = "ALL_ON";
	private static final String REMOTE_ON = "REMOTE_ON";
	private static final String LOCAL_ON = "LOCAL_ON";
	private static final String ALL_OFF = "ALL_OFF";
	
	public boolean isShareConnectionsOn(boolean isInnerQueue) {
		return (isInnerQueue && this.isShareConnsLocalOn || !isInnerQueue && isShareConnsRemoteOn); 
    }
	
	public boolean isShareConnectionsFeatrueOn() {
		return this.isShareConnsFeatrueOn;
    }
	
	private static final String DEFAULT = "DEFAULT";
	
	public String getConnnectionName(String name) {
		for (ShareConnsSetting setting : shareConnsConfiguration) {
			if (setting.matches(name)) {
				return setting.getValue();
			}
		}
		return DEFAULT;
    }
	
}
