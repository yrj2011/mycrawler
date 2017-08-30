package com.taikang.ribbon.web;

public  class WalleResponse {
	private Object response;
	private int code;
	private String codeMsg;
	
	public void setStatus(WalleStatus status) {
		this.code = status.getCode();
		this.codeMsg =status.getMsg();
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getCodeMsg() {
		return codeMsg;
	}
	public void setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
	}
	
}
