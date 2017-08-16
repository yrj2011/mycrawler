package com.itmuch.cloud.study.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.itmuch.cloud.study.interceptor.UserSecurityInterceptor;

@Component
public class WebAppConfig extends WebMvcConfigurerAdapter{    
    
    /** 
     * 配置拦截器 
     * @param registry 
     */  
    public void addInterceptors(InterceptorRegistry registry) {  
           registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/user/**");  
   }  
}  