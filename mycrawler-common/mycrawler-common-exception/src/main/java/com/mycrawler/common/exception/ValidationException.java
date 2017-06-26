package com.mycrawler.common.exception;

import java.util.Map;

/**
 * 
* @ClassName: ValidationException
* @Description: 
* @author yangrenjiang
* @date 2017年6月26日 下午11:22:54
*
 */
public class ValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errors;
	
	public ValidationException(String message){
		super(message);
	}
	
	public ValidationException(String message, Map<String, String> errors) {
		super(message);
		this.errors = errors;
	}

	public Map<String, String> getErrors() {
		return errors;
	}
}
