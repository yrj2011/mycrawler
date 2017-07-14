package com.mycrawler.tutorial.akka.executor;

public class Executor1 implements IExecutor {

	@Override
	public boolean execute() {
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+":executor1:"+i);
		}
		return true;
	}

	@Override
	public void undo() {

	}

}
