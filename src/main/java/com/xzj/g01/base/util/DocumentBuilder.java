package com.xzj.g01.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 用于读取xml静态文件
 * @author zane
 * @Time 2018-5-10 下午5:32:15
 */
public class DocumentBuilder {
	
	private static Logger logger = Logger.getLogger(DocumentBuilder.class);
	
	private static boolean loadUnderClassPath = true;
	
	@SuppressWarnings("unchecked")
	public static List<Element> build(String src) throws DocumentException {
		
		try {
			SAXReader reader = new SAXReader();
			InputStream input;
			if(loadUnderClassPath)
				input = Thread.currentThread().getContextClassLoader().getResourceAsStream(src);
			else 
				input = openFileOutsideClassPath(src);
			Document doc = reader.read(input);
			return doc.getRootElement().elements();
		} catch (DocumentException e) {
			logger.error("build document from src : " + src + "fail!");
			throw e;
		}
	}
	
	/**
	 * load from outside classpath/../../../xml/
	 */
	public static InputStream openFileOutsideClassPath(String src){
		String path = Thread.currentThread().getContextClassLoader().getResource("").toExternalForm();
		String[] strs = path.split("/");
		StringBuilder sb = new StringBuilder();
		for(int i=0, j=strs.length-3; i<j; i++) sb.append(strs[i]).append("/");
		sb.append("xml/").append(src);
		System.out.println(sb);
		InputStream input = null;
		try {
			input = new URL(sb.toString()).openStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
	
	public static void loadUnderClassPath() {
		loadUnderClassPath = true;
	}
	
	public static void loadFromOtherPath() {
		loadUnderClassPath = false;
	}
}
