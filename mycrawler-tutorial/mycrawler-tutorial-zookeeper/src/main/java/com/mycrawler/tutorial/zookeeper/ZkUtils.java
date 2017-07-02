package com.mycrawler.tutorial.zookeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Op;
import org.apache.zookeeper.OpResult;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.AsyncCallback.VoidCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.OpResult.ErrorResult;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkUtils {

	private static Logger logger = LoggerFactory.getLogger(ZkUtils.class);
	
	//public method
	public static void create(ZooKeeper zk, String path, byte[] data) {
		logger.debug(String.format("create path %s data %s", path, new String(data)));
		byte[][] dataparam = new byte[1][];
		dataparam[0] = data;
		List<String> pathlst = new ArrayList<>(1);
		pathlst.add(path);
		
		recursiveCreateSubFunc(new RecursiveParameter(zk, pathlst, dataparam));
	}
	
	public static void syncDelete(ZooKeeper zk, String path) {
		try {
			logger.info("zk delete " + path);
			zk.delete(path, -1);
		} catch (InterruptedException | KeeperException e) {
			logger.error("path " + path, e);
		}
	}

	public static void recursiveDelete(ZooKeeper zk, List<String> pathLst) {
		logger.debug("recursive delete:");
		for (String path : pathLst) {
			logger.debug("path " + path);
		}
		recursiveDeleteSubFunc(new RecursiveParameter(zk, pathLst));
	}
	
	public static void recursiveCreate(ZooKeeper zk, List<String> pathLst) {
		logger.debug("recursive create:");
		for (String path : pathLst) {
			logger.debug("path " + path);
		}
		recursiveCreateSubFunc(new RecursiveParameter(zk, pathLst));
	}
	
	public static void recursiveCreate(ZooKeeper zk, List<String> pathLst, byte[][] datalst) {
		logger.debug("recursive create:");
		for (int i = 0; i < pathLst.size(); i ++) {
			logger.debug("path " + pathLst.get(i) + " data " + new String(datalst[i]));
		}
		recursiveCreateSubFunc(new RecursiveParameter(zk, pathLst, datalst));
	}
	
	public static void doTransaction(ZooKeeper zk, List<Op> transactionOps) {
		
		//iterator for speedup
		Iterator<Op> iter = transactionOps.iterator();
		Map<Integer, List<String>> actions = new HashMap<>();
		while (iter.hasNext()) {
			Op op = iter.next();
			String path = op.getPath();
			int action = op.getType();
			
			if (!actions.containsKey(action)) {
				actions.put(action, new ArrayList<String>());
			}
			if (actions.get(action).contains(path)) {
				iter.remove();
			}
			else {
				actions.get(action).add(path);
			}
		}
		iter = transactionOps.iterator();
		while (iter.hasNext()) {
			Op op = iter.next();
			if (op.getType() == ZooDefs.OpCode.delete) {
				String path = op.getPath();
				try {
					if (path == null || zk.exists(path, false) == null) {
						logger.debug(path + " not exist");
						iter.remove();
					}
					else {
						logger.debug(path + "\tpass");
					}
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if (op.getType() == ZooDefs.OpCode.create) {
				String path = op.getPath();
				try {
					if (zk.exists(path, false) != null) {
						logger.debug(path + " already exist");
						iter.remove();
					}
					else {
						logger.debug(path + "\tpass");
					}
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

      	boolean success = false;
    	while (!success) {
    		try {
    			List<OpResult> results = zk.multi(transactionOps);
    			success = true;
    			for (OpResult result : results) {
    				logger.info("op result " + result.getType());
    				if (result.getType() == ZooDefs.OpCode.error) {
    					success = false;
    				}
    			}
    		} catch (InterruptedException e) {
    			logger.error("", e);
    			e.printStackTrace();
    			success = false;
    		} catch (KeeperException e) {
    			logger.error("keeper exception return directly");
    			List<OpResult> results = e.getResults();
    			for (int i = 0; i < results.size() ; i ++) {
    				OpResult result = results.get(i);
    				logger.debug(result.getType() + "\t" + transactionOps.get(i).getPath());
    				if (result instanceof ErrorResult) {
    					ErrorResult error = (ErrorResult)result;
    					logger.debug(error.getErr() + "\t" + Code.get(error.getErr()));
    					if (Code.get(error.getErr()) == Code.NONODE) {
    						transactionOps.remove(i);
    						break;
    					}
    					else if (Code.get(error.getErr()) == Code.NODEEXISTS) {
    						transactionOps.remove(i);
    						break;
    					}
    				}
    			}
    			success = false;
    		}
    	}
	}


	//private method
	private static void recursiveDeleteSubFunc(RecursiveParameter parmeter) {
		
		ZooKeeper zk = parmeter.zk;
		List<String> pathLst = parmeter.pathlst;

		if (pathLst == null || pathLst.size() == 0) {
			return;
		}
		
		String firstOne = pathLst.get(0);
		zk.delete(firstOne, -1, nodeDeleteCallback, parmeter);
	}
	
    private static VoidCallback nodeDeleteCallback = new VoidCallback() {
		
		@Override
		public void processResult(int rc, String path, Object ctx) {

			RecursiveParameter parmeter = (RecursiveParameter)ctx;

			switch(Code.get(rc)) {
			case CONNECTIONLOSS:
				recursiveDeleteSubFunc(parmeter);
				break;
			case OK:
				logger.info("path correctly deleted: " + path); 
				if (parmeter.pathlst.size() == 0) {
					break;
				}
				parmeter.pathlst.remove(0);
				recursiveDeleteSubFunc(parmeter);
				break;
			case NONODE:
				logger.info("no node to deleted: " + path); 
				parmeter.pathlst.remove(0);
				recursiveDeleteSubFunc(parmeter);
				break;
			default:
				logger.error("Failed to delete node data" + KeeperException.create(Code.get(rc), path) + " call callback function !!![try again]");
				recursiveDeleteSubFunc(parmeter);
				break;
			} 

		}
	};
	
	public static void createEphemeral(ZooKeeper zk, String path, byte[] data) {
		try {
			zk.create(path, data,Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean checkIsExists(ZooKeeper zk, String path) {
		try {
			Stat stat = zk.exists(path, null);
			if(stat == null) {
				return false;
			}
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	private static void recursiveCreateSubFunc(RecursiveParameter parmeter) {
		
		ZooKeeper zk = parmeter.zk;
		List<String> pathLst = parmeter.pathlst;

		if (pathLst == null || pathLst.size() == 0) {
			return;
		}
		
		String firstOne = pathLst.get(0);
		byte[] data = parmeter.getData(pathLst.size());

		zk.create(firstOne, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, createPersistentCallback, parmeter);
	}

	private static StringCallback createPersistentCallback = new StringCallback() {
		@Override
		public void processResult(int rc, String path, Object ctx, String name) {
			
			RecursiveParameter parmeter = (RecursiveParameter)ctx;

			switch (Code.get(rc)) { 
			case CONNECTIONLOSS:
				recursiveCreateSubFunc(parmeter);
				break;
			case OK:
				logger.info("Parent created: " + path);
				parmeter.pathlst.remove(0);
				recursiveCreateSubFunc(parmeter);
				break;
			case NODEEXISTS:
				logger.warn("Parent already registered: " + path);
				parmeter.pathlst.remove(0);
				recursiveCreateSubFunc(parmeter);
				break;
			default:
				logger.error("Retry : Something went wrong: ", KeeperException.create(Code.get(rc), path));
				recursiveDeleteSubFunc(parmeter);
				break;
			}
		}
	};

	
}

class RecursiveParameter {

	ZooKeeper zk;
	List<String> pathlst;
	byte[][] datalst;
	
	public RecursiveParameter(ZooKeeper zk, List<String> pathLst) {
		this.zk = zk;
		this.pathlst = pathLst;
		this.datalst = null;
	}

	public RecursiveParameter(ZooKeeper zk, List<String> pathLst, byte[][] datalst) {
		this.zk = zk;
		this.pathlst = pathLst;
		this.datalst = datalst;
	}
	
	public byte[] getData(int currentPathSize) {
		if (datalst == null) {
			return null;
		}
		
		int totalLen = datalst.length;
		return datalst[totalLen - currentPathSize];
	}
}
