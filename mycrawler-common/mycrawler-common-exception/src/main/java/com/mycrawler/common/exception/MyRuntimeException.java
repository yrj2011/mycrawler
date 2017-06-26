package com.mycrawler.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRuntimeException extends RuntimeException{
	
	private static final long serialVersionUID = -73182036301322673L;
	private static Logger logger = LoggerFactory.getLogger(MyRuntimeException.class);
	
 
	private Integer type;
	
	private Object obj;
	public MyRuntimeException() {
		super();
		logger.error("", this);
	}
	
	public MyRuntimeException(String str) {
		super(str);
		logger.error("", this);
	}

	public MyRuntimeException(Exception e) {
		e.printStackTrace();
		logger.error(e.toString(), this);
	}

	public MyRuntimeException(Integer type, Object obj) {
		this.obj = obj;
		this.type = type;
		logger.error(type + "==" + com.mycrawler.common.utils.StringUtils.toString(obj), this);
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
