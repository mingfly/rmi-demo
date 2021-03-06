package com.reed.rmi.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * rpc client listener to get rpc url from zookeeper
 * 
 * @author reed
 * 
 */
@Component
public class ClientZkListener extends BaseZkListener {

	/** log */
	private Logger logger = LoggerFactory.getLogger(ClientZkListener.class);

	/**
	 * after context loading to connect zk to get rpc node , listen zk changing
	 * and refresh rpc client beans
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent ev) {
		if (super.isUseZk()) {
			logger.debug(">>>>>>>>>>>>>>>>>>zookeeper connecting.....");
			if (super.getRpcZkWatcher() == null) {
				try {
					RpcZkWatcher rpcZkWatcher = new RpcZkWatcher(
							super.getZkClaster(), null);
					super.setRpcZkWatcher(rpcZkWatcher);
					rpcZkWatcher.startMaster();
					logger.info(">>>>>>>>>>>>>>>>>>zookeeper has connected.....");
				} catch (Exception e) {
					logger.error("client watcher start failed=====>"
							+ e.getMessage());
				}
			}
		}
	}

}