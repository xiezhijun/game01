package com.xzj.g01.base.bo.server;

public enum ServerType {
	all(0), dispatch(1), login(2), hall(3);
	
	private int code;
	
	private ServerType(int code){
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
}
