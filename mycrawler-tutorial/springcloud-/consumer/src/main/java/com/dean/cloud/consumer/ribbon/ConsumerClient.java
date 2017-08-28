package com.dean.cloud.consumer.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*@FeignClient("PRODUCER")*/
@Service
public class ConsumerClient {
	@Autowired
	private RestTemplate restTemplate;

	public String sayName(String name) {
		return restTemplate.getForObject("http://biz/say/"+name, String.class);
	}
}
