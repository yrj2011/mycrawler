
package com.mycrawler.tutorial.zookeeper.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.mycrawler.common.utils.SettingProperties;


public class CuratorLockFactoryImpl extends LockFactory {

	private CuratorFramework client;

	private static final String X_LOCK_PATH = "/locks/x_lock/";

	public CuratorLockFactoryImpl() {
		this.client = CuratorFrameworkFactory.builder().connectString(SettingProperties.instance.getZooKeeperConnURL())
		.connectionTimeoutMs(SettingProperties.instance.getZooKeeperConnTimeout())
		.sessionTimeoutMs(30000)
		.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();
	}

	public Lock getXLock(String lockName) {
		return new CuratorXLock(client, X_LOCK_PATH + lockName);
	}

	@Override
	public Lock getXLock(LockNamePrefix lockNamePrefix, String id) {
		String lockName  = String.format("%s_%s", lockNamePrefix.name(), id);
		return getXLock(lockName);
	}
}
