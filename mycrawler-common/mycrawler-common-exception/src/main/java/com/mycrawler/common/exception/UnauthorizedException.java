package com.mycrawler.common.exception;

/**
 * 
* @ClassName: UnauthorizedException
* @Description: 
* @author yangrenjiang
* @date 2017年6月26日 下午11:22:43
*
 */
public class UnauthorizedException extends RuntimeException{
	
	private static final long serialVersionUID = -73182036301322673L;
	
	public UnauthorizedException() {
		super();
	}
	
	public UnauthorizedException(String message) {
		super(message);
	}

	public UnauthorizedException(Throwable e) {
		super(e);
	}
	
	public UnauthorizedException(String message , Throwable e) {
		super(message , e);
	}
	
}
