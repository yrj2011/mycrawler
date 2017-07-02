package com.mycrawler.tutorial.zookeeper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.mycrawler.tutorial.zookeeper.lock.CuratorLockFactoryImpl;
import com.mycrawler.tutorial.zookeeper.lock.Lock;

public class ZkLockTest {
	
	@Test
	public void testLock() throws InterruptedException{
		final Lock  lock = CuratorLockFactoryImpl.getInstance().getXLock("A");
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(3);
		Runnable runner = () ->{
			try {
				System.out.println(Thread.currentThread().getName()+" acquir lock");
				boolean isLock = lock.acquire(60000);
				if(isLock){
					System.out.println(Thread.currentThread().getName()+" get lock");
				}else{
					System.out.println(Thread.currentThread().getName()+" un get lock");
				}
			} finally {
				lock.release();
				System.out.println(Thread.currentThread().getName()+" realease lock");
			};
		};
		
		for (int i = 0; i < 10; i++) {
			cachedThreadPool.execute(runner);
		}
		Thread.sleep(10000000);
	}
}
