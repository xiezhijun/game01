package com.xzj.g01.base.dao.redis.base;

import com.ebo.storage.redis.RedisStorageKey;

public class RedisKeyUtil {
	
	public static byte[] getByteArray(int areaId, long userId, StorageKey keyFlag) {
		return getByteArray(userId | (areaId * 1l << 32), keyFlag); //32位的areaId
	}
	
	public static byte[] getByteArray(long userId, StorageKey keyFlag){
		return RedisStorageKey.getByteArray(userId, (short)keyFlag.code);
	}
	
	public static byte[] getByteArray(String platFormId, StorageKey keyFlag){
		return RedisStorageKey.getByteArray(platFormId, (short)keyFlag.code);
	}
	
	public static byte[] getByteArray(String platFormId, int areaId, long roleId, long time, StorageKey keyFlag){
		return RedisStorageKey.getByteArray(platFormId, areaId, roleId, time, (short)keyFlag.code);
	}
}
