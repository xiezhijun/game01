package com.xzj.g01.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * String工具类
 * @author Zane
 * @Time 2018年8月15日 上午10:21:30
 */
public class StringHelper {
	
	/**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
	public static String filterEmoji(String source) {
		if (source == null || source.length() == 0) {
			return "";
		}
		Pattern emoji = Pattern.compile(
				"[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]|[\ue000-\uefff]",
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

		Matcher emojiMatcher = emoji.matcher(source);
		if (emojiMatcher.find()) {
			String no_emoji = emojiMatcher.replaceAll("*");
			return no_emoji;
		}
		return source;

	}
    
    /**
     * 检测是否有emoji字符
     * @param source
     * @return 一旦含有就抛出
     */
	public static boolean containsEmoji(String source) {
		if (source == null || source.length() == 0) {
			return false;
		}
		//
		// 过滤表情字符
		Pattern emoji = Pattern.compile(
				"[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]|[\ue000-\uefff]",
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

		Matcher emojiMatcher = emoji.matcher(source);
		if (emojiMatcher.find()) {
			return true;
		}
		return false;

	}
     
}
