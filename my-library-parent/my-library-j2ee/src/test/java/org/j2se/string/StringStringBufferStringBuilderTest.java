/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * XZJC, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with XZJC.
 */
package org.j2se.string;

import org.junit.Test;

/**
 * 
 * <p>
 * Title: StringStringBufferStringBuilderTest.java
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
 * @date 2014年3月5日上午10:32:59
 * @version 1.0
 * 
 */
public class StringStringBufferStringBuilderTest {
	
	@Test
	public void testString(){
		String str = "abc";
		str += str;
		System.out.println(str);
	}
	
	@Test
	public void testStringBuffer(){
		StringBuffer sb = new StringBuffer();
		sb.append("abc");
		System.out.println(sb.toString());
	}
	
	@Test
	public void testStringBuilder(){
		StringBuilder sb = new StringBuilder();
		sb.append("abc");
		System.out.println(sb.toString());
	}
}
