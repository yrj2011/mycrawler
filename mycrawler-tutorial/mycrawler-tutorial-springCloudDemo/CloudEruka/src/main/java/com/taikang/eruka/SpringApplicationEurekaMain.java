package com.taikang.eruka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringApplicationEurekaMain {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringApplicationEurekaMain.class);
		//SpringApplication.run(SpringApplicationMain.class, args);
		app.setAdditionalProfiles("profile1");
		app.run(args);
	}
}
