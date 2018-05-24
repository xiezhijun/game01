package com.xzj.g01.dispatch.bo;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.springframework.context.ApplicationContext;

import com.xzj.g01.base.bo.server.ServerInfo;
import com.xzj.g01.base.bo.server.ServerType;
import com.xzj.g01.base.data.ServerInfoData;
import com.xzj.g01.base.proto.base.BaseProtoBuffer.StrProto;
import com.xzj.g01.dispatch.net.DispatchClientMsgHandler;
import com.xzj.g01.dispatch.net.codec.DispatchChannelPipelineFactory;

/**
 * dispatcher 的客户端，用于与dispatcher服务器通信
 * @author zane
 * @Time 2018-5-7 上午11:05:16
 */
public class DispatcherClientInitor {
	
	private static final Logger logger = Logger.getLogger(DispatcherClientInitor.class);
	
	private ApplicationContext ac;
	
	private ClientBootstrap clientBootstrap;
	
	public DispatcherClientInitor(ApplicationContext ac) {
		this.ac = ac;
		initParam();
	}
	
	private void initParam(){
		ClientBootstrap clientBootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newFixedThreadPool(1), Executors.newFixedThreadPool(1), 2));
		
		DispatchClientMsgHandler handler = ac.getBean(DispatchClientMsgHandler.class);
		
		clientBootstrap.setPipelineFactory(new DispatchChannelPipelineFactory(handler));
		clientBootstrap.setOption("tcpNoDelay", true);
		clientBootstrap.setOption("recevieBufferSize", 1024*512);
		this.clientBootstrap = clientBootstrap;
	}
	
	/**
	 * 
	 * @param type 大厅、房间、登录等
	 */
	public void initClientConnect(DispatchType type) {
		try{
			ServerInfoData serverInfoData = ac.getBean(ServerInfoData.class);
			ServerInfo dispatcher = serverInfoData.getServerInfoByType(ServerType.dispatch);
			String hostname = dispatcher.getNetIp();
			int port = dispatcher.getPort();
			
			InetSocketAddress address = new InetSocketAddress(hostname, port);
			ChannelFuture future = clientBootstrap.connect(address);
			
			try{
				future.await(3000);
			}catch (InterruptedException e) {
				// TODO: handle exception
			}
			
			if(future.isSuccess()){
				DispatcherClient dispatcherClient = new DispatcherClient(type, future.getChannel());
				ac.getBean(DispatchContext.class).addClient(dispatcherClient);
				future.getChannel().getPipeline().getContext(ac.getBean(DispatchClientMsgHandler.class)).setAttachment(dispatcherClient);
				dispatcherClient.send("dispatchLoginService.login", StrProto.newBuilder().setParam(DispatchType.login.name()));
			}
		}catch (Exception e) {
			logger.info("error"+e);
		}
	}
}
