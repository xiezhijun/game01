package com.xzj.g01.login.net;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class LoginMsgHandler extends SimpleChannelUpstreamHandler implements ApplicationContextAware{
	
	private ApplicationContext ac;
	
	public LoginMsgHandler(){
		
	}
	
	public LoginMsgHandler(ApplicationContext ac) {
		this.ac = ac;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.messageReceived(ctx, e);
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		//ctx.setAttachment(player)
		super.channelConnected(ctx, e);
	}
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ac = applicationContext;
	}
}
