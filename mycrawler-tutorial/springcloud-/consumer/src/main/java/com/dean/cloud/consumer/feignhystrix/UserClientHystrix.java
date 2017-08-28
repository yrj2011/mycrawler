package com.dean.cloud.consumer.feignhystrix;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.netflix.discovery.shared.transport.decorator.RetryableEurekaHttpClient;

import feign.Retryer;
@Service
public class UserClientHystrix implements ConsumerClient{

	@Override
	public String sayName(@PathVariable("name") String name) {
		return "UserClientHystrix getuserinfostr() is fallback 服务不可用。。";
	}
	
}
