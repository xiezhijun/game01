package com.xzj.g01.base.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.xzj.g01.base.util.DocumentBuilder;

/**
 * 读取xml文件，序列化对象到内存
 * @author zane
 * @Time 2018-5-10 下午5:32:37
 */
public class ItemData {
	
	private static Map<Integer, ItemProperty> items = new HashMap<Integer, ItemProperty>();
	
	public static void init() {
		String fileUrl = "static/xml/Item.xml";
		try {
			List<Element> elements = DocumentBuilder.build(fileUrl);
			for(Element element : elements) {
				int id = Integer.parseInt(element.elementText("id"));
				String name = element.elementText("name");
				ItemProperty itemProperty = new ItemProperty(id, name);
				items.put(id, itemProperty);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
}
