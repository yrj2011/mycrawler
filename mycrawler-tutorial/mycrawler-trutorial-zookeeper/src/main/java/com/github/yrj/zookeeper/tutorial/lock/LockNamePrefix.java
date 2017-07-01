package com.github.yrj.zookeeper.tutorial.lock;

public enum LockNamePrefix {
	
	;
	private final String code;

    private final String message;

    private LockNamePrefix(String code, String message) {
		this.code = code;
		this.message = message;
    }

    public String getMessage() {
    	return message;
    }

    public String getCode() {
    	return code;
    }

    public String toString() {
    	return String.format("%s: %s", this.code, this.message);
    }
}
