/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * XZJC, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with XZJC.
 */
package org.j2se.grammar.extend;
/**
 * 
 * <p>
 * Title: Extends.java
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
 * @date 2014年4月16日上午10:17:58
 * @version 1.0
 * 
 */
public class Extends extends Father{
	public Extends() {
		System.out.println("Child");
	}
	public static void main(String[] args) {
		Father  f = new Father();
		Extends d = new Extends();
		Class ft = null;
	}
}

class Father{
	public Father(){
		System.out.println("Father");
	}
}
