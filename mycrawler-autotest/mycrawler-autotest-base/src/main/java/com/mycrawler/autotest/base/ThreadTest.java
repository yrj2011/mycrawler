package com.mycrawler.autotest.base;

import org.junit.Test;

public class ThreadTest {
	
	
	public void testInterrupt() throws InterruptedException{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println("thread is run");
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					};
				}
				
			}
		});
		thread.start();
		Thread.sleep(100);
		thread.interrupt();
	}
	
	@Test
	public void testYield() throws InterruptedException{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName()+ " is run 1");
					try {
						Thread.sleep(50);
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
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName()+ " is run");
					try {
						Thread.sleep(50);
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
