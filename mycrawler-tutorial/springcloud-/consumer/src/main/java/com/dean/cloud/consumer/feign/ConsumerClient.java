package com.dean.cloud.consumer.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("biz")
public interface ConsumerClient {
	@RequestMapping(method = RequestMethod.GET, value = "/say/{name}")
	public String sayName(@PathVariable("name") String name);
}
