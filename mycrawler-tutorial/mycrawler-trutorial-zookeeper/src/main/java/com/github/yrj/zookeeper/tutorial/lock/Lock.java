
package com.github.yrj.zookeeper.tutorial.lock;


public interface Lock {
	
	boolean acquire();

	boolean acquire(long timeout);

	void release();
}
