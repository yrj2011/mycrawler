package com.mycrawler.tutorial.akka.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Transaction implements ITransaction {
	public static Lock STR_LOCK = new ReentrantLock();
	private List<IExecutor> executors = new ArrayList<>();
	public void init1(){
		executors.clear();
		executors.add(new Executor1());
		executors.add(new ExecutorA());
	}
	
	public void init2(){
		executors.clear();
		executors.add(new Executor1());
		executors.add(new ExecutorB());
	}
	@Override
	public boolean execute() {
		for (IExecutor iExecutor : executors) {
			try {
				 if(iExecutor instanceof Executor1){
					   STR_LOCK.lock();
					   System.out.println(Thread.currentThread().getName()+" get lock");
				 }
				 iExecutor.execute();
			} finally {
				 if(iExecutor instanceof Executor1){
					   System.out.println(Thread.currentThread().getName()+" un lock");
					   STR_LOCK.unlock();
				 }
			}
		  
			
		}
		return false;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
