package com.taikang.hystrix.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class Computer {

	/**
	 * 通过添加@HystrixCommand注解可以为该方法开启熔断器功能 
	 * fallbackMethod属性用于指定当该方法内发生异常时执行的替代方法
	 * 		注意:fallbackMethod指定的方法必须和原方法的参数和返回值相同,且在同一个类中,否则会报错
	 * ignoreExceptions属性用于指定需要排除的异常,比如排除NullPointerException,那么当方法内
	 * 		抛出NullPointerException时,不会触发fallbackMethod,而是直接将异常向上抛
	 * 
	 * @param flag
	 * @return
	 */
	// @HystrixCommand(fallbackMethod="screen",ignoreExceptions =
	// NullPointerException.class)
	@HystrixCommand(fallbackMethod = "screen")
	public String keyboard(String flag) {
		System.out.println(new Date());
		System.out.println("**********************");
		if ("error".equals(flag)) {
			throw new NullPointerException("nullpointer");
		}
		return "print a word 'hello'";
	}

	public String screen(String flag) {
		return "show a word 'hello'";
	}
}
