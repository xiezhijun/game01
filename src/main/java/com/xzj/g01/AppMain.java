package com.xzj.g01;

import java.sql.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xzj.g01.base.bo.server.ServerInfo;
import com.xzj.g01.base.data.ServerInfoData;
import com.xzj.g01.base.scheduler.MyScheduler;
import com.xzj.g01.base.scheduler.RefreshServerInfoJob;
import com.xzj.g01.dispatch.bootstrap.DispatchBootstrap;
import com.xzj.g01.login.global.LoginBootstrap;
import com.xzj.game_base.nettybase.codec.ProtoFactory;

/**
 * 启动类
 * @author zane
 * @Time 2018-5-7 下午4:11:28
 */
public class AppMain {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		//加载proto池
		ProtoFactory.init("com.xzj.g01.base.proto");
		
		ServerInfoData serverInfoData = ac.getBean(ServerInfoData.class);
		serverInfoData.init();
		
		//开启服务器
		ServerInfo selfServerInfo = serverInfoData.getSelfServerInfo();
		/*if(selfServerInfo.getServerType()==ServerType.all.getCode()){
			DispatchBootstrap dispatchBootstrap = ac.getBean(DispatchBootstrap.class);
			dispatchBootstrap.start(ac);
			LoginBootstrap loginBootstrap = ac.getBean(LoginBootstrap.class);
			loginBootstrap.start(ac);
		}else if(selfServerInfo.getServerType()==ServerType.login.getCode()){
			LoginBootstrap loginBootstrap = ac.getBean(LoginBootstrap.class);
			loginBootstrap.start(ac);
		}else if(selfServerInfo.getServerType()==ServerType.dispatch.getCode()){
			DispatchBootstrap dispatchBootstrap = ac.getBean(DispatchBootstrap.class);
			dispatchBootstrap.start(ac);
		}*/
		DispatchBootstrap dispatchBootstrap = ac.getBean(DispatchBootstrap.class);
		dispatchBootstrap.start(ac);
		LoginBootstrap loginBootstrap = ac.getBean(LoginBootstrap.class);
		loginBootstrap.start(ac);
		
		
		//开启定时调度的任务
		Date currentTime = new Date(System.currentTimeMillis());
		RefreshServerInfoJob refreshServerInfoJob = ac.getBean(RefreshServerInfoJob.class);
		MyScheduler.schedule(refreshServerInfoJob , currentTime, RefreshServerInfoJob.refreshTime);
		
//		ServerInfoDao serverInfoDao = ac.getBean(ServerInfoDao.class);
//		selfServerInfo.setServerState(1);
//		serverInfoDao.updateServerInfo(selfServerInfo);
	}
}
