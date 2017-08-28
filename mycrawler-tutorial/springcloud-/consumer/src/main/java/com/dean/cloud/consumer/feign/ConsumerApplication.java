package com.dean.cloud.consumer.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.Request;
import feign.Retryer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
		//new SpringApplicationBuilder(ConsumerApplication.class).web(true).run(args);
	}
	/*@Bean
	Request.Options feignOptions() {
		return new Request.Options(*//**connectTimeoutMillis**//*1 * 1000, *//** readTimeoutMillis **//*1 * 1000);
	}

	@Bean
	Retryer feignRetryer() {
		return Retryer.Default;
	}*/
}
