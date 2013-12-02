/**
 * Copyright (c) 2013 XZJC, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * XZJC, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with XZJC.
 */
package org.j2se.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * <p>
 * Title: SplitTest.java
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
 * @date 2013年11月29日下午5:00:02
 * @version 1.0
 * 
 */
public class SplitTest {
	@Test
	public void test1(){
		String condition = "bt like '%dd%' and nr like '%gg%'";
		Map<String, Object> map = parseCondition(condition);
		String con = (String)map.get("condition");
		Object[] objs = (Object[])map.get("params");
		System.out.println("con > " + con);
		for(Object o : objs) {
			System.out.println("valu >" + o);
		}
	}
	
	
	private Map<String, Object> parseCondition(String condition){
		Map<String, Object> map = new HashMap<String, Object>();
//		if(StringUtil.isEmpty(condition)){
//			return map;
//		}
		List<Object> list = new ArrayList<Object>();
		String result = "";
		String[] cnoditionArr = condition.split("and");
		int i = 0;
		for(String con : cnoditionArr) {
			String keywords = getKeyworksFromCondition(condition);
			String[] arr = con.split(keywords);
			if(arr.length == 2){
				if(i != 0){
					result += " and ";
				}
				result += arr[0] + " " + keywords + " ? ";
				list.add(arr[1]);
			}
			i++;
		}
		map.put("condition", result);
		map.put("params", list.toArray());
		return map;
	}
	
	private String getKeyworksFromCondition(String condition){
		String result = "";
		if(condition.contains("=")){
			result = "=";
		}else if(condition.contains("!=")){
			result = "!=";
		}else if(condition.contains(">")){
			result = ">";
		}else if(condition.contains("<")){
			result = "<";
		}else if(condition.contains(">=")){
			result = ">=";
		}else if(condition.contains("<=")){
			result = "<=";
		}else if(condition.contains("like")){
			result = "like";
		}
		return result;
	}
}
