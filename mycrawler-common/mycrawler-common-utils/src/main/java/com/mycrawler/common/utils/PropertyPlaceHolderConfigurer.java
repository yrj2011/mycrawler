package com.mycrawler.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
 * 
* @ClassName: PropertyPlaceHolderConfigurer
* @Description: spring 配置文件处理
* @author yangrenjiang
* @date 2017年6月25日 下午11:11:00
*
 */
public class PropertyPlaceHolderConfigurer extends PropertyPlaceholderConfigurer {

	private boolean enableDevMode = true;

	private List<String> propertyFiles = new ArrayList<String>();
	
	public boolean isEnableDevMode() {
		return enableDevMode;
	}

	public void setEnableDevMode(boolean enableDevMode) {
		this.enableDevMode = enableDevMode;
	}
	
	public List<String> getPropertyFiles() {
		return propertyFiles;
	}

	public void setPropertyFiles(List<String> propertyFiles) throws Exception {
		this.propertyFiles = propertyFiles;
		
		if (propertyFiles == null) {
			throw new RuntimeException("'propertyFiles' is not set");
		}
		
		Properties mergedProperties = new Properties();
		for (String propertyFile : this.propertyFiles) {
			Properties prop = null;
			try{
				prop = PropertiesLoader.load(propertyFile, this.enableDevMode);
			}catch (RuntimeException e) {
				logger.error("read "+propertyFile+" failed!",e);
			}
			if(null != prop) {
				mergedProperties.putAll(prop);
			}
		}
		
		this.setProperties(mergedProperties);
	}
}
