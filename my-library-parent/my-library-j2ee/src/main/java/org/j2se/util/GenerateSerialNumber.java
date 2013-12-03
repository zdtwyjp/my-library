/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved. This software is the confidential and proprietary information of XZJC, Inc. You shall
 * not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered into
 * with XZJC.
 */
package org.j2se.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * <p>
 * Title: GenerateSerialNumber.java
 * </p>
 * <p>
 * Description:TODO
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: xzjc
 * </p>
 * <p>
 * team: XzjcTeam
 * </p>
 * <p>
 * 
 * @author: Junping.Yang
 *          </p>
 * @date 2013年12月2日下午5:18:50
 * @version 1.0
 */
public class GenerateSerialNumber {
	public static String getSerialNumber() {
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStr = ft.format(new Date());
		timeStr = timeStr.substring(4);
		String preStr = "11";
		String random = Math.round(Math.random() * 100) + "";
		return preStr + timeStr + random;
	}

	public static void main(String[] args) {
		/** 日期时间 */
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStr = ft.format(new Date());
		System.out.println(timeStr);
		timeStr = timeStr.substring(4);
		String preStr = "11";
		String random = Math.round(Math.random() * 100) + "";
		System.out.println(random);
		System.out.println(getSerialNumber());
	}
}
