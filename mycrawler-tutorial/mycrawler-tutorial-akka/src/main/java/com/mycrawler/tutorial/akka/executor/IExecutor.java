package com.mycrawler.tutorial.akka.executor;

public interface IExecutor {
	public boolean execute();
	public void undo();
}
