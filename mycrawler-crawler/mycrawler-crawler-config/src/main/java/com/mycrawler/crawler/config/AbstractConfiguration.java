package com.mycrawler.crawler.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycrawler.common.utils.FileHelper;
import com.mycrawler.common.utils.PropertiesLoader;

public abstract class AbstractConfiguration {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractConfiguration.class); 
    public static final String DEFAULT_CONFIG_FILE = "config.json";
    public static final String DEFAULT_CONFIG_DIR = AbstractConfiguration.class.getResource("/").getPath() + "/";

    protected String config;

    protected AbstractConfiguration() {
        this(DEFAULT_CONFIG_DIR + DEFAULT_CONFIG_FILE);
        
    }

    protected AbstractConfiguration(String path) {
    	try {
        	File log4j2File = PropertiesLoader.loadFile("proxyip_config.json","crawler", true);
        	config = FileHelper.getRawText(log4j2File.getAbsolutePath());
            resolve();
		} catch (Exception e) {
			logger.error("",e);
		}
        
    }

    abstract protected void resolve();

    public String getConfig() {
        return config;
    }
}
