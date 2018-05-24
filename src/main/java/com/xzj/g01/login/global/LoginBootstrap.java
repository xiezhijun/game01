package com.xzj.g01.login.global;

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
import com.xzj.g01.base.global.GlobalProperties;
import com.xzj.g01.base.net.BaseChannelPipelineFactory;
import com.xzj.g01.dispatch.bo.DispatchType;
import com.xzj.g01.dispatch.bo.DispatcherClientInitor;
import com.xzj.g01.login.net.LoginMsgHandler;

@Component
public class LoginBootstrap {
	
	private ApplicationContext ac;
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	private static final Logger logger = Logger.getLogger(LoginBootstrap.class);
	
	public void start(ApplicationContext ac) {
		this.ac = ac;
		startSocket();
		initDispatch();
	}
	
	private void startSocket() {
		try{
			ServerBootstrap serverBootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
							Executors.newFixedThreadPool(1), Executors.newFixedThreadPool(1)));
			
			serverBootstrap.setPipelineFactory(new BaseChannelPipelineFactory(ac.getBean(LoginMsgHandler.class)));
			serverBootstrap.bind(new InetSocketAddress(serverInfoData.getServerInfoByType(ServerType.login).getPort()));
			
			logger.info("login server start ...");
		}catch (Exception e) {
			logger.error("start login server meet an error ..."+ e);
		}
	}
	
	/**
	 * 与dispatcher建立连接
	 */
	private void initDispatch(){
		DispatcherClientInitor clientInitor = new DispatcherClientInitor(ac);
		int connect_num = Integer.parseInt(GlobalProperties.getProp("login.connectDispatcher.number"));
		for(int i=0;i<connect_num;i++){
			clientInitor.initClientConnect(DispatchType.login);
		}
	}
}
