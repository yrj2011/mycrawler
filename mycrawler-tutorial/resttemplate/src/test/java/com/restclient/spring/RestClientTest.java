package com.restclient.spring;

import com.restclient.spring.client.RestClient;

public class RestClientTest {

	
	public static void main(String[] args) {
		User user = new User();
		user.setUsername("yrj");
		User user2 = RestClient.post(user, "http://localhost:8000/user", User.class);
		System.out.println(user2);
	}
}
