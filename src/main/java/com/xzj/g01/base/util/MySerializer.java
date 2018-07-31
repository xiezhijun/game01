package com.xzj.g01.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/**
 * 序列化工具类
 * @author Zane
 * @Time 2018年7月31日 上午9:56:02
 */
public class MySerializer {
	
	private static final Logger logger = Logger.getLogger(MySerializer.class);
	
	/***
	 * 将对象序列化为byte[]
	 * @param object： 实现了Serializable接口的Java类
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			logger.error("【serialize object to bytes meet an error : 】",e);
		}
		return null;
	}

	/**
	 * 反序列化byte[]为Java对象
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			logger.error("【unserialize bytes to object meet an error : 】",e);
		}
		return null;
	}
}
