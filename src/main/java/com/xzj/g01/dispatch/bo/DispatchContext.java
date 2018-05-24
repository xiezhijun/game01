package com.xzj.g01.dispatch.bo;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * 管理所有dispatcher客户端连接
 * 其他模块要与dispatcher通信，从这里获取链接
 * 链接用完后及时返回到clients中
 * @author zane
 * @Time 2018-5-7 上午11:06:06
 */
@Component
public class DispatchContext {
	
	// K:dispatchType
	private Map<Integer, LinkedBlockingQueue<DispatcherClient>> clients = new ConcurrentHashMap<Integer, LinkedBlockingQueue<DispatcherClient>>();
	
	private static final Logger logger = Logger.getLogger(DispatchContext.class);
	
	public DispatcherClient borrowClient(DispatchType type){
		if(clients.size()==0){
			logger.error("borrow client but the clients is null ");
			return null;
		}
		LinkedBlockingQueue<DispatcherClient> data = clients.get(type.getCode());
		if(data==null || data.size()==0) {
			logger.error("borrow client but the clients is null ");
			return null;
		}
		return data.remove();
	}
	
	public void addClient(DispatcherClient client){
		LinkedBlockingQueue<DispatcherClient> data =clients.get(client.getDispatchType().getCode());
		if(data==null){
			data = new LinkedBlockingQueue<DispatcherClient>();
			clients.put(client.getDispatchType().getCode(), data);
		}
		data.add(client);
	}
	
	public void removeClient(DispatcherClient client) {
		LinkedBlockingQueue<DispatcherClient> data =clients.get(client.getDispatchType().getCode());
		if(data!=null){
			data.remove(client);
		}
	}
}
