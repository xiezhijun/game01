package com.xzj.g01.dispatch.bo;

public enum DispatchType {
	/**测试用*/
	nulled(0), 
	login(1);
	
	private int code;
	
	private DispatchType(int code) {
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
}
