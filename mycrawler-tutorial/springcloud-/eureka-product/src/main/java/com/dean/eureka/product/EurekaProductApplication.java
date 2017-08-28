package com.dean.eureka.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaProductApplication.class, args);
	}
}
