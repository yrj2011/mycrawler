package com.mycrawler.das.impl;

import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import com.mycrawler.common.domain.IPProxy;
import com.mycrawler.common.utils.LoggerUtils;
import com.mycrawler.das.IIPProxyService;

public class ServiceTest {
	private static Logger logger = LoggerFactory.getLogger(ServiceTest.class);
	private IIPProxyService ipService;
	private ApplicationContext serverCtx;
	@Before
	public void before(){
		try {
			LoggerUtils.init();
			serverCtx = new ClassPathXmlApplicationContext("spring/DasSpringConfig.xml");
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	@Test
	public void test(){
		try {
			ipService = serverCtx.getBean(IIPProxyService.class);
			IPProxy ip = new IPProxy();
			ip.setAvailable(true);
			ip.setIp("127.0.0.1");
			ip.setPort(8080);
			ip.setType("http");
			ipService.insert(ip);
			System.out.println(ip);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
