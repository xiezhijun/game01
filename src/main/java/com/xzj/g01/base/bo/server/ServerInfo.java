package com.xzj.g01.base.bo.server;

public class ServerInfo {
	
	private Integer id;
	
	private Integer serverId;
	
	private Integer serverType;
	
	private String innerIp;
	
	private String netIp;
	
	private Integer port;
	
	private Integer serverState;//服务器状态：0正常，1维护中，2关闭

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public Integer getServerType() {
		return serverType;
	}

	public void setServerType(Integer serverType) {
		this.serverType = serverType;
	}

	public String getInnerIp() {
		return innerIp;
	}

	public void setInnerIp(String innerIp) {
		this.innerIp = innerIp;
	}

	public String getNetIp() {
		return netIp;
	}

	public void setNetIp(String netIp) {
		this.netIp = netIp;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getServerState() {
		return serverState;
	}

	public void setServerState(Integer serverState) {
		this.serverState = serverState;
	}
	
	
}
