package com.taikang.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taikang.hystrix.service.Computer;

@RestController
public class HystrixController {

	@Autowired
	private Computer computer;

	@Autowired
	private DiscoveryClient client;

	/**
	 * 该方法为了测试Hystrix circuit功能
	 * 通过前台传递的falg标记来决定computer中的keyboard方法中是否发生异常
	 * 当falg为error时发生异常
	 * 循环调用keyboard方法22次,
	 * 如果异常执行,那么,将会满足Hystrix的默认配置,5秒内发生20次错误使得circuit打开
	 * 通过查看控制台是否有keyboard方法打印的日志来判断keyboard方法是否执行,
	 * 从而判断keyboard方法的circuit是否处于打开状态
	 * @param flag
	 * @return
	 */
	@RequestMapping("/helloSpringBoot")
	public String hello(String flag) {
		ServiceInstance localServiceInstance = client.getLocalServiceInstance();
		String serviceId = localServiceInstance.getServiceId();
		int port = localServiceInstance.getPort();
		
		for (int i = 0; i < 22; i++) {
			computer.keyboard(flag);
		}
		
		return serviceId+port+" :  "+computer.keyboard(flag);
	}
}
