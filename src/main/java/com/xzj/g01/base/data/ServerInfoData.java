package com.xzj.g01.base.data;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xzj.g01.base.bo.server.ServerInfo;
import com.xzj.g01.base.bo.server.ServerType;
import com.xzj.g01.base.dao.sql.ServerInfoDao;

@Component
public class ServerInfoData {
	
	//所有服务器缓存
	//key: serverId
	private Map<Integer, ServerInfo> serverInfos = new ConcurrentHashMap<Integer,ServerInfo>();
	
	@Autowired
	private ServerInfoDao serverInfoDao;
	
	private ServerInfo selfServer;
	
	private static ServerInfoData instance;
	
	private Logger logger = Logger.getLogger(ServerInfoData.class);
	
	private ServerInfoData(){
		instance = this;
	}
	
	public static ServerInfoData getInstance(){
		return instance;
	}
	
	public void init(){
		refreshServerInfo();
		loadSelfServerInfo();
		logger.info("init serverInfoData success !");
	}
	
	public void refreshServerInfo(){
		serverInfos.clear();
		for(ServerInfo info : serverInfoDao.getAllServerInfos()) {
			serverInfos.put(info.getServerId(), info);
		}
	}
	
	public ServerInfo getServerInfoByType(ServerType type) {
		for(ServerInfo info: serverInfos.values()) {
			if(info.getServerType()==type.getCode()){
				return info;
			}
		}
		return null;
	}
	
	public ServerInfo getServerInfoByServerId(int serverId) {
		return serverInfos.get(serverId);
	}
	
	public ServerInfo getSelfServerInfo(){
		return selfServer;
	}
	
	/**
	 * 加载本服务器
	 */
	public void loadSelfServerInfo(){
		try {
            boolean hadInit = false;
            Enumeration<NetworkInterface> localInterfaces = NetworkInterface.getNetworkInterfaces();   
            a1:while (localInterfaces.hasMoreElements()) {   
                NetworkInterface ni = localInterfaces.nextElement();   
                Enumeration<InetAddress> ipAddresses = ni.getInetAddresses();   
                while (ipAddresses.hasMoreElements()) {   
                    InetAddress adderss = ipAddresses.nextElement();
                    String ip = adderss.getHostAddress();
                    for(ServerInfo serverInfo : serverInfos.values()) {
                        if(serverInfo.getNetIp().equals(ip)) {
                            hadInit = true;
                            this.selfServer = serverInfo;
                            break a1;
                        }
                    }
                }
            }
            if(!hadInit) {
                new RuntimeException("find areaId fail!").printStackTrace();
                System.exit(-1);
            }
        } catch (Exception e) {
            new RuntimeException("find areaId fail!").printStackTrace();
            System.exit(-1);
        }
	}
}
