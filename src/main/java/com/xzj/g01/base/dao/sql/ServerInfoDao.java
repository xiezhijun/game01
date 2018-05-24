package com.xzj.g01.base.dao.sql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xzj.g01.base.bo.server.ServerInfo;
import com.xzj.g01.base.dao.mapper.ServerInfoMapper;

@Repository
public class ServerInfoDao{

	@Autowired
	private ServerInfoMapper serverInfoMapper;
	
	
	public List<ServerInfo> getAllServerInfos() {
		return serverInfoMapper.getAllServerInfos();
	}
	
	public void updateServerInfo(ServerInfo newServerInfo){
		serverInfoMapper.updateServerInfo(newServerInfo);
	}

}
