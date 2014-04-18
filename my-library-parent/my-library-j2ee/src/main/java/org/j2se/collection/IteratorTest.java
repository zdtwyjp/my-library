/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * XZJC, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with XZJC.
 */
package org.j2se.collection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * <p>
 * Title: Iterator.java
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
 * @date 2014年4月16日上午10:42:13
 * @version 1.0
 * 
 */
public class IteratorTest {
	public static void main(String[] args) throws FileNotFoundException {
		List list = new ArrayList();
		Iterator it = list.listIterator();
		InputStream is = new FileInputStream(new File(""));
		Reader rs = new BufferedReader(new FileReader(""));
		Stack s = null;
		Vector v = null;
		HashMap tx;
		ConcurrentMap<String, String> d;
		ConcurrentHashMap t;
	}
}
