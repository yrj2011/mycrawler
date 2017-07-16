package com.lance.hello.web;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

//需要注解，并扫描，在程序启动的时候就自动加载  
@Component  
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {  

  @Override  
  public void onApplicationEvent(ContextRefreshedEvent event) {  
      ApplicationContext applicationContext = event.getApplicationContext();  
      WebApplicationContext webApplicationContext = (WebApplicationContext)applicationContext;  
      ServletContext servletContext = webApplicationContext.getServletContext();  
   String path0 = servletContext.getRealPath("/");  
  System.out.println("path0:" + path0);  
  }  
}  