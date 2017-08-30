package com.taikang.ribbon.web;

/**
 * 
 * 工作站任务DTO
 * @author yrj
 *
 */
public class TaskDTO {
	
	private int taskID;

	private String state;

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
