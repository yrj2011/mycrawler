
package com.mycrawler.tutorial.zookeeper.lock;


public interface Lock {
	
	boolean acquire();

	boolean acquire(long timeout);

	void release();
}
