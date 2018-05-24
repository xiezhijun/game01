package com.xzj.g01.base.dao.redis.base;

public enum StorageKey {
	
	global_user_key(1)
	;
	
	public final int code;
	
	private StorageKey(int code) {
		this.code = code;
	}
}
