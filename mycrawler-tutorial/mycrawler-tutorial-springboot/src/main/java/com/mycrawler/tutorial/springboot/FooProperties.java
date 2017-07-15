package com.mycrawler.tutorial.springboot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="foo")
public class FooProperties {
   private String name;
   private String sex;
   @Value("myname")
   private String myNa;
   private final List<String> list = new ArrayList<>();

    public List<String> getList() {
        return this.list;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}