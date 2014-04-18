/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * XZJC, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with XZJC.
 */
package org.j2se.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * Title: For.java
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
 * @date 2014年4月16日上午9:57:01
 * @version 1.0
 * 
 */
public class For {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	public static void testFor(){
		List list = new ArrayList();
		out:
		for(int i = 0; i < list.size(); i++) {
			
			in:
			for(int j = 0; j < list.size(); j++) {
				continue out;
			}
		}
		
	}
}
