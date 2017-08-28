package com.dean.cloud.consumer.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
	@Autowired
	private ConsumerClient consumerClient;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
    public String test(@RequestParam String str) {
        String str1 = consumerClient.sayName(str);
        return str1;
    }

}
