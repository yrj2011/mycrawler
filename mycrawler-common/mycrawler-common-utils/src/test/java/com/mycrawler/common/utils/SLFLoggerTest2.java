package com.mycrawler.common.utils;


import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLFLoggerTest2 {
	
	private static Logger logger = LoggerFactory.getLogger(LoggerTest.class);
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
