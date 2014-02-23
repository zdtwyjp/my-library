/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.util;

import java.io.UnsupportedEncodingException;

/**
 * 
 * <p>
 * Title: StringUtil.java
 * </p>
 * <p>
 * Description:字符串工具类
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
 * @date 2012-4-12下午2:07:58
 * @version 1.0
 */
public class StringUtil {

    /**
     * 
     * @Title: isNotNull
     * @Description: 字符不为空
     * @param value
     * @return
     * @return boolean
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午2:08:46
     */
    public static boolean isNotNull(String value) {
	return value != null && !"".equals(value.trim()) && !"null".equals(value.toLowerCase());
    }

    /**
     * 
     * @Title: getStringForTruncation
     * @Description: 截断字符串
     * @param str
     * @param length
     * @return
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午3:34:51
     */
    public static String getStringForTruncation(String str, int length) {
	if (StringUtil.isNotNull(str)) {
	    while (getStringRealLength(str) > length) {
		str = str.substring(0, str.length() - 1);
	    }
	}
	return str;
    }

    /**
     * 
     * @Title: getStringRealLength
     * @Description: 获取UTF-8格式数据库下,输入中英文混合字符串的真实长度(中文字符占3位)
     * @param str
     * @return
     * @return int
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午3:34:30
     */
    public static int getStringRealLength(String str) {
	int result = 0;
	if (StringUtil.isNotNull(str)) {
	    int blen = str.getBytes().length; // 中文占2位时的总长度
	    int len = str.length(); // 中文占1位时的总长度
	    if (blen > len) {
		result = blen + (blen - len); // (blen - len)即为中文字符数
	    } else {
		result = len;
	    }
	}
	return result;
    }

    /**
     * 返回固定长度(真实长度)的字符串，超长的以...结尾。 (主要用于页面显示)
     * 
     * @author dongchangsha
     * @date 2010-10-15
     * @param str
     * @param length
     * @return
     */
    public static String getStringForFixedRealLength(String str, int length) {
	if (getStringRealLength(str) <= length) {
	    return str;
	}
	return getStringForTruncation(str, length - 2) + "...";
    }

    /**
     * 截取中英文混合字符串
     * 
     * @param text
     * @param length
     * @param endWith
     * @return
     * @author YangJunping
     * @date 2010-10-26
     */
    public static String subString(String text, int length, String endWith) {
	int textLength = text.length();
	int byteLength = 0;
	StringBuffer returnStr = new StringBuffer();
	for (int i = 0; i < textLength && byteLength < length * 2; i++) {
	    String str_i = text.substring(i, i + 1);
	    if (str_i.getBytes().length == 1) {// 英文
		byteLength++;
	    } else {// 中文
		byteLength += 2;
	    }
	    returnStr.append(str_i);
	}
	try {
	    if (byteLength < text.getBytes("GBK").length) {// getBytes("GBK")每个汉字长2，getBytes("UTF-8")每个汉字长度为3
		returnStr.append(endWith);
	    }
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	return returnStr.toString();
    }

    /**
     * 
     * @Title: getWhereOrAnd
     * @Description: 获取and or where
     * @param hasWhere
     * @return
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-5-2下午9:41:52
     */
    public static String getWhereOrAnd(boolean hasWhere) {
	if (hasWhere) {
	    return " and ";
	} else {
	    return " where ";
	}
    }

    /**
     * 
     * @Title: returnStr
     * @Description: 返回字符串，当为NULL时返回空串
     * @param str
     * @return
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-5-5下午12:08:24
     */
    public static String returnStr(String str) {
	if (isNotNull(str)) {
	    return str;
	} else {
	    return "";
	}
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
     * @date 2012-6-12下午4:21:44
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

    /**
     * 
    * @Title: nullToBlank
    * @Description: nullToBlank
    * @param str
    * @return
    * @return String
    * @throws
    * @author: YangJunping
    * @date 2012-8-29上午10:57:54
     */
    public static String nullToBlank(String str) {
	if (null == str) {
	    return "";
	}
	return str;
    }
}
