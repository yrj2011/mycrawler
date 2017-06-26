package com.mycrawler.common.dao;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mycrawler.common.domain.IPProxy;
import com.mycrawler.common.utils.LoggerUtils;

public class IPPrxoyTest {
	private static Logger logger = LoggerFactory.getLogger(MyBatisTest.class);
	private IPProxyMapper ipmapper;
	private ApplicationContext serverCtx;
	@Before
	public void before(){
		try {
			LoggerUtils.init();
			serverCtx = new ClassPathXmlApplicationContext("spring/SpringConfig.xml");
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	@Test
	public void test(){
		try {
			ipmapper = serverCtx.getBean(IPProxyMapper.class);
			IPProxy ip = new IPProxy();
			ip.setAvailable(true);
			ip.setIp("127.0.0.1");
			ip.setPort(8080);
			ip.setType("http");
			ipmapper.insert(ip);
			System.out.println(ip);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
