package com.taikang.ribbon.web;

public enum WalleStatus {
	FAIL(0,"请求失败"),
	
	SUCCESS(1000,"请求成功"),
	
	TASKNOTEXIST(1001,"任务不存在");
	
	private int code;
	private String msg;
	WalleStatus(int code , String message){
		this.code=code;
		this.msg=message;
	}
	public int getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	
}
