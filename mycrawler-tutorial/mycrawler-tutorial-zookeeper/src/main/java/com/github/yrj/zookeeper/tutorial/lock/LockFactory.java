
package com.github.yrj.zookeeper.tutorial.lock;


public abstract class LockFactory {
	
	private static LockFactory instance;

	public synchronized static LockFactory getInstance() {
		if(instance == null){
			instance = new CuratorLockFactoryImpl();
		}
		return instance;
	} 
	public abstract Lock getXLock(String lockName);
 
	public abstract Lock getXLock(LockNamePrefix lockNamePrefix , String id);
}
