/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved. This software is the confidential and proprietary information of XZJC, Inc. You shall
 * not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered into
 * with XZJC.
 */
package org.j2se.classloader.encryption;

/**
 * <p>
 * Title: ClassLoaderTest.java
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
 * @date 2014年4月15日上午11:13:52
 * @version 1.0
 */
public class ClassLoaderTest {
	public static void main(String[] args) throws Exception {
		System.out.println(new ClassLoaderAttachment().toString());
		// 输出结果为：hello,itcast
	}
}
