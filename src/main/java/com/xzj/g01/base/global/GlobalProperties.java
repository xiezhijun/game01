package com.xzj.g01.base.global;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 读取全局参数配置文件
 * @author zane
 * @Time 2018-5-9 上午10:31:19
 */
public class GlobalProperties {
	private static String file = "config/global.properties";
	private static Map<String, String> properties = new HashMap<String, String>();
	
	static{
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
		if(in!=null){
			try{
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
				String line ;
				while ((line=bufferedReader.readLine())!=null) {
					if(line.trim().length()>0 && !line.startsWith("#")){
						String[] temp = line.split("=");
						String key = temp[0];
						String value = temp[1];
						properties.put(key, value);
					}
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取配置参数
	 * @param key
	 * @return
	 */
	public static String getProp(String key) {
		return properties.get(key);
	}
}
