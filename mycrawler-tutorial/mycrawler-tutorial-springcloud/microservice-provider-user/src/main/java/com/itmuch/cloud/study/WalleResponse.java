package com.itmuch.cloud.study;

public  class WalleResponse {
	private WalleStatus status;
	private Object response;
	private int code;
	private String codeMsg;
	
	public void setStatus(WalleStatus status) {
		this.status = status;
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
