package com.mycrawler.das.impl;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mycrawler.common.domain.User;
import com.mycrawler.common.utils.LoggerUtils;
import com.mycrawler.das.IUserService;

public class ServiceTest {
	private static Logger logger = LoggerFactory.getLogger(ServiceTest.class);
	private IUserService userService;
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
			userService = serverCtx.getBean(IUserService.class);
			User user = new User();
			user.setName("A");
			user.setId(UUID.randomUUID().toString());
			userService.insert(user);
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
