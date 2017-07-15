package com.itmuch.cloud.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这边的@RefreshScope注解不能少，否则即使调用/refresh，配置也不会刷新
 * @author eacdy
 */
@RestController
@RefreshScope
public class ConfigClientController {
	public ConfigClientController(){
		System.out.println();
	}
  //@Value("${profile}")
  private String profile;
  
  @Autowired
  private JpushCenterProperties jcp;
  @Autowired
  private Hello hello;
  @Value("${hello.a}")
  private String a;
  
  private String b;
  @GetMapping("/hello")
  public String hello() {
    return this.profile;
  }
  
  
}
