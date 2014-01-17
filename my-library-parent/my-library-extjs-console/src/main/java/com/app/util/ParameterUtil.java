package com.app.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

/**
 * @author: kang
 * @date: 2012-4-17 下午9:05:27
 */
public class ParameterUtil {
	/**
	 * App 会话Session管理 Map
	 */
	private static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();

	/**
	 * App 会话action管理 Map(针对添加关注判断用户连续操作)
	 */
	private static Map<Long, Long> actionMap = new HashMap<Long, Long>();

	/**
	 * 项目搜索关键词索引
	 */
	private static JSONArray searchWords;

	public static void addNewSession(String sessionId, HttpSession session) {
		sessionMap.put(sessionId, session);
	}

	public static void removeSession(String sessionId) {
		sessionMap.remove(sessionId);
	}

	public static Map<String, HttpSession> getSessionMap() {
		return sessionMap;
	}

	public static void addNewAction(Long sn1, Long sn2) {
		actionMap.put(sn1, sn2);
	}

	public static void removeAction(Long sn) {
		actionMap.remove(sn);
	}

	public static Map<Long, Long> getActionMap() {
		return actionMap;
	}

	public static JSONArray getSearchWords() {
		return searchWords;
	}

	public static void setSearchWords(JSONArray searchWords) {
		ParameterUtil.searchWords = searchWords;
	}

	/**
	 * 将Map转换成URL参数串
	 * 
	 * @param params
	 * @return
	 */
	public static String convertToURLParamString(Map<String, Object> params) {
		Set<String> keys = params.keySet();
		Iterator<String> it = keys.iterator();
		StringBuilder sb = null;
		while (it.hasNext()) {
			String key = it.next();
			Object val = params.get(key);
			if (val != null && !"".equals(val.toString().trim())) {
				if (sb != null) {
					sb.append("&");
				} else {
					sb = new StringBuilder();
				}
				// if(val != null && "keywords".equals(key)){
				// try {
				// val = URLEncoder.encode(val.toString(),"UTF-8");
				// } catch (UnsupportedEncodingException e) {
				// e.printStackTrace();
				// }
				// }
				sb.append(key).append("=").append(val == null ? "" : val);
			}
		}

		return (sb == null) ? "" : sb.toString();
	}

	/**
	 * 删除URL参数
	 * 
	 * @param url
	 * @param key
	 * @param flag
	 * @return
	 */
	public static String removeURLParam(String url, String key, boolean flag) {
		int index = url.indexOf("?");
		if (index == -1) {
			if (flag) {
				return url + "?type=1";
			}
			return url;
		}
		int beginIndex = url.indexOf(key + "=", index);
		if (beginIndex == -1) {
			return url;
		}
		int endIndex = url.indexOf("&", beginIndex);
		String resURL = "";
		if (endIndex == -1) {
			resURL = url.substring(0, beginIndex - 1);
		} else {
			resURL = url.substring(0, beginIndex) + url.substring(endIndex + 1);
		}
		if (flag && resURL.indexOf('?') == -1) {
			return resURL + "?type=1";
		}
		return resURL;
	}

	/**
	 * 参数替换
	 * 
	 * @param url
	 * @param key
	 * @param val
	 * @return
	 */
	public static String replaceURLParam(String url, String key, String val) {
		if (url == null) {
			return null;
		}
		int index = url.indexOf(key + "=");
		if (index == -1) {
			return url;
		}

		int endIndex = url.indexOf("&", index);

		String resURL = "";
		String param = key + "=" + val;
		if (endIndex == -1) {
			resURL = url.substring(0, index) + param;
		} else {
			resURL = url.substring(0, index) + param + url.substring(endIndex);
		}

		return resURL;
	}

	public static void main(String[] args) {
		System.out.println(removeURLParam(
				"/lfd/plateform/project/search.htm?a=1&b=2&c=4", "a", true));
		System.out.println(removeURLParam(
				"/lfd/plateform/project/search.htm?a=1&b=2&c=4", "b", true));
		System.out.println(removeURLParam(
				"/lfd/plateform/project/search.htm?a=1&b=2&c=4", "c", true));
		System.out.println(removeURLParam(
				"/lfd/plateform/project/search.htm?a=1", "a", true));
		System.out.println(removeURLParam(
				"/lfd/plateform/project/search.htm?a=1", "a", false));
		System.out.println(replaceURLParam(
				"/lfd/plateform/project/search.htm?keywords=重庆&local_city=22",
				"keywords", "%E9%87%8D%E5%BA%86"));
	}
}
