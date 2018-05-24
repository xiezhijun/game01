package com.xzj.g01.dispatch.bootstrap;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.xzj.g01.base.bo.server.ServerType;
import com.xzj.g01.base.data.ServerInfoData;
import com.xzj.g01.dispatch.net.DispatchMsgHandler;
import com.xzj.g01.dispatch.net.codec.DispatchChannelPipelineFactory;

@Component
public class DispatchBootstrap {
	
	private static final Logger logger = Logger.getLogger(DispatchBootstrap.class);
	
	private ApplicationContext ac;
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	public void start(ApplicationContext ac) {
		this.ac = ac;
		startSocket();
	}
	
	private void startSocket(){
		try{
			ServerBootstrap serverBootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
							Executors.newFixedThreadPool(5), Executors.newFixedThreadPool(5)));
			
			serverBootstrap.setPipelineFactory(new DispatchChannelPipelineFactory(ac.getBean(DispatchMsgHandler.class)));
			serverBootstrap.bind(new InetSocketAddress(serverInfoData.getServerInfoByType(ServerType.dispatch).getPort()));
			
			logger.info("dispatch server start ...");
		}catch (Exception e) {
			logger.error("start dispatch server meet an error ..."+ e);
		}
	}
}
