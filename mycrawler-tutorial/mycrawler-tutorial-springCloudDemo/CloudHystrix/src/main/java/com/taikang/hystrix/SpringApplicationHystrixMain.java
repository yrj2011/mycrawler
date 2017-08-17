package com.taikang.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
@EnableEurekaClient
public class SpringApplicationHystrixMain {
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringApplicationHystrixMain.class);
		//app.setAdditionalProfiles("profile2");
		app.run(args);
	}
	
}
