/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * XZJC, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with XZJC.
 */
package org.j2se.grammar.reflect;

import java.lang.reflect.Method;

/**
 * 
 * <p>
 * Title: GetClass.java
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
 * @date 2014年4月16日上午10:30:07
 * @version 1.0
 * 
 */
public class GetClass {
	public static void main(String[] args) throws ClassNotFoundException {
		GetClass t = new GetClass();
		Class cl = t.getClass();
		Method[] me = cl.getMethods();
		for(Method method : me) {
//			method.invoke(obj, args);
		}
//		GetClass.class;
		Class.forName("");
		
	}
}
