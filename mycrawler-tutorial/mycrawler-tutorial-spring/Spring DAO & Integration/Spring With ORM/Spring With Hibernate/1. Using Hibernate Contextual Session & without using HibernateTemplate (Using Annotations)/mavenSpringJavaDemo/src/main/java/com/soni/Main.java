package com.soni;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.soni.dao.UserDao;

public class Main {
	private static ApplicationContext context;
	
	public static void main(String[] args) {
		context=new ClassPathXmlApplicationContext("spring.xml"); 
		UserDao userDao = (UserDao)context.getBean("userDaoImpl");
		
		userDao.displayData();
	}
}
