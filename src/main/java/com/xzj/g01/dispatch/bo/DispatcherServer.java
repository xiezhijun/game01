package com.xzj.g01.dispatch.bo;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.google.protobuf.Message.Builder;
import com.xzj.game_base.synroom.executor.SynRoomPlayer;

/**
 * 连接客户端
 * @author zane
 * @Time 2018-5-9 下午4:42:07
 */
public class DispatcherServer extends SynRoomPlayer{
	
	private DispatchType dispatchType;
	
	private Channel channel;
	
	private static final Logger logger = Logger.getLogger(DispatcherServer.class);
	
	public DispatcherServer(ChannelHandlerContext ctx){
		super(ctx.getChannel().getId(), ctx);
		this.channel = ctx.getChannel();
	}
	
	public void response(String serviceAndMethodStr, Builder builder){
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
