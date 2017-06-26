package com.mycrawler.common.exception;

/**
 * 
* @ClassName: RepeatRequestException
* @Description: 
* @author yangrenjiang
* @date 2017年6月26日 下午11:22:30
*
 */
public class RepeatRequestException extends RuntimeException{
	
	private static final long serialVersionUID = -73182036301322673L;
	
	public RepeatRequestException() {
		super();
	}
	
	public RepeatRequestException(String message) {
		super(message);
	}

	public RepeatRequestException(Throwable e) {
		super(e);
	}
	
	public RepeatRequestException(String message , Throwable e) {
		super(message , e);
	}
	
}
