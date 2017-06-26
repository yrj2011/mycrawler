package com.mycrawler.common.dao;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mycrawler.common.utils.LoggerUtils;

public class MyBatisTest {
	private static Logger logger = LoggerFactory.getLogger(MyBatisTest.class);
	@Before
	public void before(){
		try {
			LoggerUtils.init();
			ApplicationContext serverCtx = new ClassPathXmlApplicationContext("spring/SpringConfig.xml");
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	@Test
	public void test(){
		logger.debug("hello world!");
	}
}
