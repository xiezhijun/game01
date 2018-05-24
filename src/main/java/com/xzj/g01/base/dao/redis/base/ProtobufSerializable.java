package com.xzj.g01.base.dao.redis.base;

import com.google.protobuf.GeneratedMessage;

/**
 * protobuf序列化接口
 * @author zane
 * @Time 2018-5-23 下午3:32:31
 */
public interface ProtobufSerializable {
	
	void copyFrom(GeneratedMessage message);
	
	GeneratedMessage copyTo();
	
	void parseFrom(byte[] bytes);

	byte[] toByteArray();
}
