/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved. This software is the confidential and proprietary information of XZJC, Inc. You shall
 * not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered into
 * with XZJC.
 */
package org.j2se.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: ListAddObjectArray.java
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
 * @date 2013年11月29日下午2:18:36
 * @version 1.0
 */
public class ListAddObjectArray {
	public static void main(String[] args) {
		List<Object> list = new ArrayList<Object>();
		Object[] objs = new Object[]{1,2,3};
		for(Object obj : objs) {
			list.add(obj);
		}
		list.add(new Object[]{1,2,3,4});
		System.out.println(list.size());
	}
}
