package com.mycrawler.tutorial.zookeeper;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.AsyncCallback.VoidCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.Op;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ZkManager implements Watcher, Closeable {
	
	private static final Logger logger = LoggerFactory.getLogger(ZkManager.class);

	public enum MasterStates {RUNNING, ELECTED, NOTELECTED};
	
    private volatile MasterStates state = MasterStates.RUNNING;
    
    public MasterStates getState() {
        return state;
    }
    private ZooKeeper zk;
    private String hostPort;
    private String serverId;
    private int timeout = 10000;
    private volatile boolean connected = false;
    private volatile boolean expired = false;
    protected Collection<String> stations;
    protected Map<String, Long> stationHashcode;
    
    public ZkManager(String hostPort, String serverId, int timeout, Collection<String> stations) {
    	this.hostPort = hostPort;
    	this.serverId = serverId + "_" + (new Date().getTime()) + "_" + (new Random(new Date().getTime()).nextInt(10000));
    	this.timeout = timeout;
    	this.stations = stations;
    	this.stationHashcode = new HashMap<>();
    }
    
    
    public void startZK() throws IOException {
        zk = new ZooKeeper(hostPort, timeout, this);
    }

    public void stopZK() throws InterruptedException, IOException {
        zk.close();
    }
    
	@Override
	public void process(WatchedEvent e) {
		logger.info("Processing event: " + e.toString());
        if(e.getType() == Event.EventType.None){
            switch (e.getState()) {
            case SyncConnected:
                connected = true;
                break;
            case Disconnected:
                connected = false;
                logger.error("session disconnected");
                break;
            case Expired:
                expired = true;
                connected = false;
                logger.error("Session expiration", new RuntimeException());
                System.exit(-1);
                break;
            default:
                break;
            }
        }
	}
	
	public void init() {
		
	}

	private void createParentPersistent(String path, byte[] data) {
		createPersistent(path, data);
	}

	private void createPersistent(String path, byte[] data) {
		zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, createPersistentCallback, data);
	}

	private StringCallback createPersistentCallback = new StringCallback() {
		@Override
		public void processResult(int rc, String path, Object ctx, String name) {
			switch (Code.get(rc)) { 
			case CONNECTIONLOSS:
				createPersistent(path, (byte[]) ctx);
				break;
			case OK:
				logger.info("Parent created: " + path);
				break;
			case NODEEXISTS:
				logger.warn("Parent already registered: " + path);
				break;
			default:
				logger.error("Retry : Something went wrong: ", KeeperException.create(Code.get(rc), path));
				createPersistent(path, (byte[]) ctx);
				break;
			}
		}
	};
	
	private void create3LayersPersistent(String parentPath, Collection<String> children, Collection<String> gradeChildren) {
		List<Collection<String>> sublayers = new ArrayList<>(2);
		sublayers.add(children);
		sublayers.add(gradeChildren);
		
		zk.create(parentPath, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, create3LayersCallback, sublayers);
	}

	private StringCallback create3LayersCallback = new StringCallback() {
		@Override
		public void processResult(int rc, String path, Object ctx, String name) {
			Collection<String> children = ((List<Collection<String>>)ctx).get(0);
			Collection<String> gradeChildren = ((List<Collection<String>>)ctx).get(1);
			switch (Code.get(rc)) { 
			case CONNECTIONLOSS:
				create3LayersPersistent(path, children, gradeChildren);
				break;
			case OK:
				logger.info("Parent created: " + path);
				for (String child : children) {
					create2LayersPersistent(path + "/" + child, gradeChildren);
				}
				break;
			case NODEEXISTS:
				logger.warn("Parent already registered: " + path);
				for (String child : children) {
					create2LayersPersistent(path + "/" + child, gradeChildren);
				}
				break;
			case NONODE:
				logger.warn("nonode retry");
				create3LayersPersistent(path, children, gradeChildren);
				break;
			default:
				logger.error("Something went wrong: ", KeeperException.create(Code.get(rc), path));
			}
		}
	};

	private void create2LayersPersistent(String parentPath, Collection<String> children) {
		zk.create(parentPath, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, create2LayersCallback, children);
	}

	private StringCallback create2LayersCallback = new StringCallback() {
		@Override
		public void processResult(int rc, String path, Object ctx, String name) {
			Collection<String> children = (Collection<String>)ctx;
			switch (Code.get(rc)) { 
			case CONNECTIONLOSS:
				create2LayersPersistent(path, children);
				break;
			case OK:
				logger.info("Parent created: " + path);
				for (String child : children) {
					createParentPersistent(path + "/" + child, new byte[0]);
				}
				break;
			case NODEEXISTS:
				logger.warn("Parent already registered: " + path);
				for (String child : children) {
					createParentPersistent(path + "/" + child, new byte[0]);
				}
				break;
			case NONODE:
				logger.warn("nonode retry");
				create2LayersPersistent(path, children);
				break;
			default:
				logger.error("Something went wrong: ", KeeperException.create(Code.get(rc), path));
			}
		}
	};
	public boolean isConnected() {
		return connected;
	}
	
	public boolean isExpired() {
		return expired;
	}
	
        
    
    
    @Override
    public void close() throws IOException {
        if(zk != null) {
            try{
                zk.close();
            } catch (InterruptedException e) {
            	logger.warn( "Interrupted while closing ZooKeeper session.", e );
            }
        }
    }

    private void deletenode(String path) {
    	zk.delete(path, -1, nodeDeleteCallback, null);
    }
    VoidCallback nodeDeleteCallback = new VoidCallback() {
		
		@Override
		public void processResult(int rc, String path, Object ctx) {
			switch(Code.get(rc)) {
			case CONNECTIONLOSS:
				deletenode(path);
				break;
			case OK:
				logger.info("path correctly deleted: " + path); 
				break;
			default:
				logger.error("Failed to delete node data" + KeeperException.create(Code.get(rc), path) + " call callback function !!!");
			} 

		}
	};
	
	private void deletePath(String path) {
        zk.getChildren(path, null, deletePathGetChildrenCallback, null);
	}

    ChildrenCallback deletePathGetChildrenCallback = new ChildrenCallback() {
        @Override
		public void processResult(int rc, String path, Object ctx, List<String> children) {
            switch (Code.get(rc)) { 
            case CONNECTIONLOSS:
            	deletePath(path);
                break;
            case OK:
            	logger.info("Succesfully got a list of workers: " + children.size() + " workers path : " + path);
            	for (String child : children) {
            		deletenode(path + "/" + child); 
            	}
                break;
            default:
            	logger.error("getChildren failed", KeeperException.create(Code.get(rc), path));
            }
            logger.info("get children callback " + path + " " + Code.get(rc));
        }
    };

	public void orderComplete(String str ) {

		List<Op> transaction = new ArrayList<>(2);
		transaction.add(Op.delete("A" + "/" + "I" + "/" + "B" + "/"+ str, -1));
		transaction.add(Op.delete("A" + "/" + "I" + "/" + "C" + "/"+ str, -1));
		ZkUtils.doTransaction(zk, transaction);
	}
	
	public void doTransaction(List<Op> transactions) {
		ZkUtils.doTransaction(zk, transactions);
	}
	
	
	public String getData(String path) {
		byte[] bytes = null;
		try {
			bytes = zk.getData(path, false, null);
		} catch (KeeperException e) {
			logger.error("", e);
		} catch (InterruptedException e) {
			logger.error("", e);
		}
		if (bytes != null) {
			return new String(bytes);
		}
		return null;
	}
	

	public ZooKeeper getZk() {
		return zk;
	}
	
	
}
