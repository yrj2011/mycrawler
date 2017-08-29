package com.blueware.agent;


public class TestMain {
	
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("hello world");
		A.ad("arg hello","1");
		Thread.sleep(1000l);
		System.out.println("end");
	}
}
