package com.mycrawler.common.exception;

/**
 * ��治��Exception
 * 
 * @author kim.cheng
 * 
 */
public class OutOfStockException extends RuntimeException {
	private static final long serialVersionUID = -7450853908666526002L;

	public OutOfStockException() {
		super();
	}

	public OutOfStockException(String message) {
		super(message);
	}

	public OutOfStockException(Exception e) {
		super(e);
	}
	
	public OutOfStockException(String message , Exception e) {
		super(message , e);
	}
}
