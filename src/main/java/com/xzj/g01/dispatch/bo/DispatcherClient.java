package com.xzj.g01.dispatch.bo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

/**
 * 连接dispatcher的客户端，用于其他模块与dispatcher通信
 * @author zane
 * @Time 2018-5-7 上午11:49:11
 */
public class DispatcherClient {
	
	private DispatchType dispatchType;
	
	private Channel channel;
	
	private Message message;
	
	private final Lock lock = new ReentrantLock();
	
	private final Condition condition = lock.newCondition();
	
	private static final Logger logger = Logger.getLogger(DispatcherClient.class);
	
	public DispatcherClient(DispatchType type, Channel channel){
		this.dispatchType = type;
		this.channel = channel;
	}
	
	/**
	 * 发送消息至dispatcher，并等待返回结果
	 * @param msg
	 * @return
	 */
	public Message sendMsgAndWaitRes(String serviceAndMethodStr,Builder builder) {
		send(serviceAndMethodStr, builder);
		boolean isWaitEnd = false;
		try{
			lock.lock();
			//最多等待3秒，3秒内若setMsgAndNotify，则被唤醒，并返回message
			isWaitEnd = condition.await(3, TimeUnit.SECONDS);
		}catch (Exception e) {
			logger.error("send message error: "+ e.getMessage());
		}finally {
			lock.unlock();
		}
		if(!isWaitEnd) {
			logger.error("get resp fail! " + message.toString());
		}
		return message;
	}
	
	/**
	 * 当客户端收到dispatcher服务器返回的消息时
	 * 唤醒等待，并设置返回消息，返回给调用者
	 * @param message 服务器返回的消息
	 */
	public void setMsgAndNotify(Message message) {
		this.message = message;
		lock.lock();
		try {
			condition.signalAll();
		} catch (Exception e) {
			
		}finally{
			lock.unlock();
		}
	}
	
	public void send(String serviceAndMethodStr, Builder builder){
		Object[] objects = new Object[]{serviceAndMethodStr, 0, builder};
		send(objects);
	}
	
	public void send(Object[] objects){
		channel.write(objects);
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public DispatchType getDispatchType() {
		return dispatchType;
	}

	public void setDispatchType(DispatchType dispatchType) {
		this.dispatchType = dispatchType;
	}
	
	
}
