package com.xzj.g01.base.bo.server;

public enum ServerState {
	/**正常*/
	normal(0),
	/**维护中*/
	maintain(1),
	/**关闭*/
	close(2);
	
	private int code;
	private ServerState(int code){
		this.code = code;
	}
	
	public int getCode(){
		return this.code;
	}
}
