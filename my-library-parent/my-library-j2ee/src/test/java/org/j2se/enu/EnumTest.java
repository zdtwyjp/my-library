/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved. This software is the confidential and proprietary information of XZJC, Inc. You shall
 * not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered into
 * with XZJC.
 */
package org.j2se.enu;

/**
 * <p>
 * Title: EnumTest.java
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
 * @date 2013年12月3日上午9:25:47
 * @version 1.0
 */
public class EnumTest {
	public static void tell(Season s) {
		switch(s) {
		case Spring:
			System.out.println(s + "春天");
			break;
		case Summer:
			System.out.println(s + "夏天");
			break;
		case Fall:
			System.out.println(s + "秋天");
			break;
		case Winter:
			System.out.println(s + "冬天");
			break;
		}
	}

	public static void main(String[] args) {
		for(Season s : Season.values()) {
			System.out.println(s);
		}
		tell(Season.Fall);
	}
}

enum Season {
	Spring, Summer, Fall, Winter;
}
