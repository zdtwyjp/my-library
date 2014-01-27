/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * XZJC, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with XZJC.
 */
package org.j2se.property;
/**
 * 
 * <p>
 * Title: UserDirTest.java
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
 * @date 2014年1月26日下午2:58:50
 * @version 1.0
 * 
 */
public class UserDirTest {
	public static void main(String[] args) {
		String str = System.getProperty("user.dir");
		System.out.println(str);
	}
}
