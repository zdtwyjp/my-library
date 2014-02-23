package com.app.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.app.exception.ErrorMessageException;
import com.sys.core.ConstantsObj;

/**
 * 
 * 
 * <p>
 * Title: CommonUtil.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company: fxpgy
 * </p>
 * <p>
 * team: fxpgy Team
 * </p>
 * <p>
 * 
 * @author: YangJunping
 *          </p>
 * @date 2012-4-12下午2:04:11
 * @version 1.0
 */

public class ExtUtil {
    private static final Logger logger = Logger.getLogger(ExtUtil.class);

    /**
     * 在下拉选项框中添加自定义选项
     * 
     * @param jsondata
     *            json格式字符串
     * @return
     * @author YangJunping
     * @date 2012-4-12
     */
    public static String createStoreSelectHasNull(String jsondata, String value, String text) {
	String result = "[['" + value + "','" + text + "']";
	if (jsondata.length() > 2) {
	    result += ",";
	    result += jsondata.substring(1);
	}
	return result;
    }

    /**
     * 创建TABLE JSON报文
     * 
     * @author YangJunping
     * @date 2012-4-12
     * @version 1.0
     * @param list
     *            List 重构对象以后的参数对象列表
     * @param strings
     *            String[] 需要构造的JSON报文KEY
     * @param total
     *            String 页面总条数，可以为空
     * @return String 构造好的JSON报文字符串
     */
    public static String createJsonTable(List list, String[] keys, String totalPages) {
	try {
	    if (keys == null || keys.length == 0) {
		throw new ErrorMessageException("需要构造的JSON报文KEY为空！");
	    }
	    List<Map> dealList = new ArrayList<Map>();
	    for (int i = 0, size = list.size(); i < size; i++) {
		Object[] row = (Object[]) list.get(i);
		if (row.length != keys.length) {
		    throw new ErrorMessageException("数据对象中的列数量与需要构造的JSON报文KEY数量不相等！");
		}
		Map<String, Object> obj2Map = new HashMap<String, Object>();
		for (int j = 0; j < row.length; j++) {
		    if (row[j] instanceof Date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date) row[j]);
			if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0
				&& calendar.get(Calendar.SECOND) == 0) {
			    row[j] = ConstantsObj.FORMAT_DATE.format(row[j]);
			} else {
			    row[j] = ConstantsObj.FORMAT_DATE_TIME.format(row[j]);
			}
		    }
		    obj2Map.put(keys[j], row[j]);
		}
		dealList.add(obj2Map);
	    }
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("totalProperty", totalPages);
	    result.put("root", dealList);
	    String json = JSONObject.fromObject(result).toString();
	    if (logger.isInfoEnabled()) {
		logger.info(json);
	    }
	    return json;
	} catch (Exception e) {
	    e.printStackTrace();
	    return "";
	}
    }

    /**
     * json - > Object
     * 
     * @param jsonString
     * @param clazz
     * @return
     * @author YangJunping
     * @date 2012-4-12
     */
    public static Object jsonToObject(String jsonString, Class clazz) {
	JSONObject jsonObject = null;
	try {
	    jsonObject = JSONObject.fromObject(jsonString);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return JSONObject.toBean(jsonObject, clazz);
    }

    public static String createOptionsList(List list) {
	if (list.size() == 0)
	    return "[]";
	StringBuilder result = new StringBuilder();
	result.append("[");
	for (int i = 0; i < list.size(); i++) {
	    Object[] obj = (Object[]) list.get(i);
	    result.append("['").append(obj[0]).append("','").append(obj[1]).append("']");
	    if (i != list.size() - 1)
		result.append(",");
	}
	result.append("]");
	return result.toString();
    }

    /**
     * 
     * @Title: hangleTextArea
     * @Description: 处理TextArea的换行符
     * @param in
     * @return
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午2:19:24
     */
    public static String hangleTextArea(String in) {
	StringBuffer out = new StringBuffer();
	for (int i = 0; in != null && i < in.length(); i++) {
	    char c = in.charAt(i);
	    if (c == '\'')
		out.append("&#039;");
	    else if (c == '\"')
		out.append("&#034;");
	    else if (c == '<')
		out.append("&lt;");
	    else if (c == '>')
		out.append("&gt;");
	    else if (c == '&')
		out.append("&amp;");
	    else if (c == ' ')
		out.append("&nbsp;");
	    else if (c == '\n')
		out.append("<br/>");
	    else
		out.append(c);
	}
	return out.toString();
    }

}
