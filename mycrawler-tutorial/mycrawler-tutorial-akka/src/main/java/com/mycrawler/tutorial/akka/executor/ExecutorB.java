package com.mycrawler.tutorial.akka.executor;

public class ExecutorB implements IExecutor {

	@Override
	public boolean execute() {
		for (int i = 0; i < 10; i++) {
			System.out.println(Thread.currentThread().getName()+":executorB:"+i);
		}
		return true;
	}

	@Override
	public void undo() {

	}

}
