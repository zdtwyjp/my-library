/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved. This software is the confidential and proprietary information of XZJC, Inc. You shall
 * not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered into
 * with XZJC.
 */
package org.j2se.classloader.encryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * Title: MyClassLoader.java
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
 * @date 2014年4月15日上午11:11:18
 * @version 1.0
 */
public class MyClassLoader {
	public static void main(String[] args) throws Exception {
		String srcPath = args[0];
		String destDir = args[1]; // 存放加密过的class的目标目录
		FileInputStream fis = new FileInputStream(srcPath);
		String fileName = srcPath.substring(srcPath.lastIndexOf("\\") + 1);
		String destPath = "D:\\" + fileName;
		FileOutputStream fos = new FileOutputStream(destPath);
		cypher(fis, fos);
		fis.close();
		fos.close();
	}

	// 加密方法
	private static void cypher(InputStream ips, OutputStream ops)
			throws Exception {
		int b = -1;
		while((b = ips.read()) != -1) {
			ops.write(b ^ 0xff); // 0与1调换
		}
	}
}
