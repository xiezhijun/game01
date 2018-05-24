package com.xzj.g01.base.dao.mapper;

import java.util.List;

import com.xzj.g01.base.bo.server.ServerInfo;

public interface ServerInfoMapper {
	
	public List<ServerInfo> getAllServerInfos();
	
	public void updateServerInfo(ServerInfo newServerInfo);
}
