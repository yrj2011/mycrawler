package com.github.yrj.autotest.base;

import org.junit.Test;

public class ThreadTest {
	
	
	public void testInterrupt() throws InterruptedException{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					System.out.println("thread is run");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					};
				}
				
			}
		});
		thread.start();
		Thread.sleep(10000);
		thread.interrupt();
	}
	
	@Test
	public void testYield() throws InterruptedException{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					System.out.println(Thread.currentThread().getName()+ " is run 1");
					try {
						Thread.sleep(500);
						Thread.yield();
						System.out.println(Thread.currentThread().getName()+ " is run 2");
					} catch (InterruptedException e) {
						e.printStackTrace();
					};
				}
				
			}
		});
		thread.start();
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					System.out.println(Thread.currentThread().getName()+ " is run");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					};
				}
				
			}
		});
		thread2.start();
		thread.join();
		System.out.println();
		
	}
}
