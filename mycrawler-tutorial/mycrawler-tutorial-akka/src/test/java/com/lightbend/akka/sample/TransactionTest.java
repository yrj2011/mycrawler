package com.lightbend.akka.sample;

import com.mycrawler.tutorial.akka.executor.Transaction;

public class TransactionTest {
	public static void main(String[] args) {
		final Transaction t1 = new Transaction();
		t1.init1();
		final Transaction t2 = new Transaction();
		t2.init2();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				t1.execute();
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				t2.execute();
			}
		}).start();
	}
}
