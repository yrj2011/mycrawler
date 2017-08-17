package com.taikang.ribbon.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import feign.RetryableException;
import feign.Retryer;

@Configuration
public class MyFeignConfiguration {

	@Bean
	public Retryer getRetryer(@Autowired RetryTemplate retryTemplate){
		FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
		fixedBackOffPolicy.setBackOffPeriod(1000l);
		retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(30);

		retryTemplate.setRetryPolicy(retryPolicy);
		return new Retryer(){

			@Override
			public void continueOrPropagate(RetryableException e) {
				
				throw e;
				
			}

			@Override
			public Retryer clone() {
				
				return this;
			}
			
		};
		
	}
}
