package com.xzj.g01.dispatch.net;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ebo.synframework.nettybase.dispatcher.ReqCmd;
import com.xzj.g01.dispatch.bo.DispatchContext;
import com.xzj.g01.dispatch.bo.DispatcherClient;

@Component
public class DispatchClientMsgHandler extends SimpleChannelUpstreamHandler implements ApplicationContextAware{
	
	private ApplicationContext ac;
	
	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		this.ac = ac;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		//客户端收到消息后立即返回给调用者
		DispatcherClient client = (DispatcherClient) ctx.getAttachment();
		ReqCmd cmd = (ReqCmd) e.getMessage();
		client.setMsgAndNotify(cmd.getMessage());
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		DispatcherClient client = (DispatcherClient) ctx.getAttachment();
		ac.getBean(DispatchContext.class).removeClient(client);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		super.exceptionCaught(ctx, e);
	}
}
