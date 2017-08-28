package com.dean.eureka.product.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWeb {
	@RequestMapping("/say/{name}")
	public String serviceInstancesByApplicationName(
			@PathVariable String name) {
		return "Hello 1"+name ;
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info() {
		return "success";
	}
}
