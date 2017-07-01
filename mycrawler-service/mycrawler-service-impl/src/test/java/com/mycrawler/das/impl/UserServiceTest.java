package com.mycrawler.das.impl;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baomidou.mybatisplus.plugins.Page;
import com.mycrawler.common.domain.User;
import com.mycrawler.common.utils.LoggerUtils;
import com.mycrawler.das.IUserService;

public class UserServiceTest {
	private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
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
			user.setName(System.currentTimeMillis()%100+"");
			user.setId(UUID.randomUUID().toString());
			user.setState(System.currentTimeMillis()%100+"");
			user.setAge(Integer.parseInt(System.currentTimeMillis()%100+""));
			userService.insert(user);
			
			 Page<User> page = userService.selectPage(new Page<>(0, 10));
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
