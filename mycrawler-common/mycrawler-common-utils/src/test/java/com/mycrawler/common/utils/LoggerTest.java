package com.mycrawler.common.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class LoggerTest {
	
	//private static Logger logger = LoggerFactory.getLogger(LoggerTest.class);
	private static Logger logger = LogManager.getLogger();
	@Before
	public void before(){
		//LoggerUtils.init();
	}
	
	@Test
	public void test(){
		logger.debug("hello world!");
		// System.out.println("hello world!");
	}
}
