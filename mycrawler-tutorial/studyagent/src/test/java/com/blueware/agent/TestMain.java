package com.blueware.agent;

@ExclusiveTime 
public class TestMain {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("hello world");
		Thread.sleep(1000l);
		System.out.println("end");
	}
}
