/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved. This software is the confidential and proprietary information of XZJC, Inc. You shall
 * not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement you entered into
 * with XZJC.
 */
package org.j2se.string;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

/**
 * <p>
 * Title: StringEscapeUtilsTest.java
 * </p>
 * <p>
 * Description:StringEscapeUtilsTest
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
 * @date 2013年11月28日上午9:17:34
 * @version 1.0
 */
public class StringEscapeUtilsTest {
	@Test
	public void testHtmlTag() {
		String str = "<script>alert(3);中国</script>";
		str = StringEscapeUtils.escapeHtml4(str);
		System.out.println("html > " + str);
	}
	
	@Test
	public void testJavaTag() {
		String str = "中国";
		str = StringEscapeUtils.escapeJava(str);
		System.out.println("java > " + str);
	}
	
}
