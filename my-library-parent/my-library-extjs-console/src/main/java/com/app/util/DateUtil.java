package com.app.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间、字符串转换为timestamp的类
 * 
 * @author shi ying
 * 
 */
public class DateUtil {

    /**
     * 时间格式规范
     */
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间格式规范TWE
     */
    public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    /**
     * 定义的一个Date，用来接收转换过后的Date
     */
    public static Date date = null;

    /**
     * 由字符串转换为Date
     * 
     * @return date This is Date
     * @param timeStr
     *            要进行转换为Date的字符串
     * @throws BusinessException
     *             业务异常
     */
    public static Date strToDate(String timeStr) throws Exception {
	try {
	    date = sdf.parse(timeStr);
	} catch (ParseException e) {
	    try {
		date = sdf1.parse(timeStr);
	    } catch (ParseException e2) {
		throw new Exception("字符串转换时间异常！");
	    }
	}
	return date;
    }

    /**
     * 由时间转换为Date
     * 
     * @return date This is Date
     * @param time
     *            要进行转换为Date的时间
     * @throws BusinessException
     *             业务异常
     */
    public static Date timestampToDate(Timestamp time) throws Exception {
	String dateStr = sdf.format(time);
	try {
	    date = sdf.parse(dateStr);
	} catch (ParseException e) {
	    try {
		date = sdf1.parse(dateStr);
	    } catch (ParseException e2) {
		throw new Exception("时间转换异常！");
	    }
	}
	return date;
    }

    /**
     * 定义的一个Timestamp，接受转换后的Timestamp
     */
    public static Timestamp time = null;

    /**
     * 由字符串转换为Timestamp
     * 
     * @return time This is Timestamp
     * @param timeStr
     *            要进行转换为Timestamp的字符串
     */
    @SuppressWarnings("deprecation")
    public static Timestamp strToTimestamp(String timeStr) {
	Date date = new Date(timeStr);
	String dateStr = sdf.format(date);
	time = Timestamp.valueOf(dateStr);
	return time;
    }

    /**
     * 由时间转换为Timestamp
     * 
     * @return time This is Timestamp
     * @param dateStr
     *            要进行转换为Timestamp的时间
     */
    public static Timestamp dateToTimestamp(Date dateStr) {
	String timeStr = sdf.format(dateStr);
	time = Timestamp.valueOf(timeStr);
	return time;
    }

    public static String dateToString(Date dateStr) {
	String timeStr = sdf.format(dateStr);
	// time = Timestamp.valueOf(timeStr);
	return timeStr;
    }

    /**
     * 获取当前Date
     */
    public static Date getCurrDate() {
	return new Date();
    }

    /**
     * 
     * @Title: compareDate
     * @Description: 比较两个日期大小
     * @param date1
     * @param date2
     * @return
     * @return int 0相等，>0大于，<0小于
     * @throws
     * @author: YangJunping
     * @date 2012-8-30上午8:59:16
     */
    public static int compareDate(String date1, String date2) {
	java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
	java.util.Calendar c1 = java.util.Calendar.getInstance();
	java.util.Calendar c2 = java.util.Calendar.getInstance();
	try {
	    c1.setTime(df.parse(date1));
	    c2.setTime(df.parse(date2));
	} catch (java.text.ParseException e) {
	    System.err.println("格式不正确");
	}
	int result = c1.compareTo(c2);
	return result;
    }

    public static void main(String[] args) {
	String s1 = "2008-11-25 09:12:09";
	String s2 = "2008-01-29 09:12:11";
	System.out.println(compareDate(s1, s2));
    }
}
