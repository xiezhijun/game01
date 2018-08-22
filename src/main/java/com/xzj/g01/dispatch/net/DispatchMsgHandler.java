package com.xzj.g01.dispatch.net;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.google.protobuf.Message;
import com.xzj.g01.base.proto.base.BaseProtoBuffer.StrProto;
import com.xzj.g01.dispatch.bo.DispatcherServer;
import com.xzj.game_base.nettybase.dispatcher.CmdDispatcher;
import com.xzj.game_base.nettybase.dispatcher.ReqCmd;


/**
 * Dispatcher 服务器处理
 * @author zane
 * @Time 2018-5-7 上午10:59:54
 */
@Component
public class DispatchMsgHandler extends SimpleChannelUpstreamHandler implements ApplicationContextAware{
	
	private ApplicationContext ac;
	
	@Autowired
	private CmdDispatcher cmdDispatcher;
	
	private static final Logger logger = Logger.getLogger(DispatchMsgHandler.class);
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		DispatcherServer dispatcherServer = (DispatcherServer) ctx.getAttachment();
		ReqCmd cmd = (ReqCmd) e.getMessage();
		if("dispatchLoginService.login".equals(cmd.getServiceAndMethodStr())){
			try {
				Object result = cmdDispatcher.dispatch(cmd, dispatcherServer);
				if(result instanceof Void || result==null) {
					return;
				}
				dispatcherServer.response(cmd.getServiceAndMethodStr(), (Message.Builder)result);
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}else{
			handleMsg(cmd, e.getChannel(), dispatcherServer);
		}
	}
	
	//处理消息
	private void handleMsg(ReqCmd cmd, Channel channel, DispatcherServer dispatcherServer) {
		//测试，实际根据cmdIndex或者是serviceAndMethodStr进行处理
		Message message = cmd.getMessage();
		if(message instanceof StrProto) {
			StrProto temProto = (StrProto) message;
			String value = temProto.getParam();
			dispatcherServer.response(cmd.getServiceAndMethodStr(), StrProto.newBuilder().setParam(value));
		}
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		logger.info("client connnected to dispatcher"+e.getChannel());
		DispatcherServer dispatcherServer = new DispatcherServer(ctx);
		ctx.setAttachment(dispatcherServer);
	}


	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		this.ac = ac;
	}
}
