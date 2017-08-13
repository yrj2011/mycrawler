package com.mycrawler.tutorial.akka.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycrawler.common.utils.CollectionUtils;
import com.mycrawler.common.utils.StringUtils;

public class TransactionManager {
	private static Logger logger = LoggerFactory.getLogger(TransactionManager.class);
	private TransactionManager(){};
	public static TransactionManager instance = new TransactionManager();
	private Map<String,Lock> lockMap = new HashMap<>();
	private Map<String,List<Object>> lockObjectsMap = new HashMap<>();
	
	/**
	 * 
	* @Title: tryLock 
	* @Description:  
	* @throws
	 */
	public void tryLock(Object lockTarget){
		try {
			String lockKey = getLockKey(lockTarget);
			if(StringUtils.isEmpty(lockKey))
				return ;
			 if(lockMap.get(lockKey) == null){
				 Lock lock  = new ReentrantLock(true);
				 lockMap.put(lockKey, lock);
			 }
			 if(lockObjectsMap.get(lockKey) == null){
				 List<Object> objects = new ArrayList<>();
				 lockObjectsMap.put(lockKey, objects);
			 }
			 if(!lockObjectsMap.get(lockKey).contains(lockTarget)){
				 lockObjectsMap.get(lockKey).add(lockTarget);
			 }
			 Lock lock = lockMap.get(lockKey);
			 if(lock != null){
				 long getLockTime = System.currentTimeMillis();
			    lockMap.get(lockKey).tryLock(5,TimeUnit.SECONDS);
			    logger.debug(lockKey +" get lock waste time " + (System.currentTimeMillis()- getLockTime));
			 }
		} catch (Exception e) {
			logger.error("",e);
		}
	
		return ;
	}

	private String getLockKey(Object lockTarget) {
		if(lockTarget instanceof WalleSynchronization){
			 WalleSynchronization sync = (WalleSynchronization) lockTarget; 
			 String lockKey = sync.getLockKey();
			 if(StringUtils.isEmpty(lockKey))
				 return null ;
			 String lockKey2 = lockTarget.getClass().getName()+"_" + lockKey;
			 return lockKey2;
		}
		return null;
	}
	
	public void unlock(Object lockTarget){
		try {
			String lockKey = getLockKey(lockTarget);
			if(StringUtils.isEmpty(lockKey))
				return ;
			Lock lock = lockMap.get(lockKey);
			if(lock == null)
				return ;
			lock.unlock();
			 if(lockObjectsMap.get(lockKey).contains(lockTarget)){
				 lockObjectsMap.get(lockKey).remove(lockTarget);
			 }
			 if(CollectionUtils.isEmpty(lockObjectsMap.get(lockKey))){
				 lockMap.remove(lockKey);
			 }
		} catch (Exception e) {
			logger.error("",e);
		}
		
		
	}
}
