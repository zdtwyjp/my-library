/**
 * 
 */
package com.app.util;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author: kang
 * @date: 2012-4-17 下午5:03:37
 */
public class VerificationCodeUtil {
	
	private static Random rd=new Random();
	
	//邀请码生成的数量
	public static int inviteCodeCount=3;
	
    private static String[] beforeShuffle = new String[] { "2", "3", "4", "5",
	    "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
	    "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V",
	    "W", "X", "Y", "Z" };

    /**
     * 生成指定位数的验证码
     * @param size
     */
    public static String generate(int size) {
	List<String> list = Arrays.asList(beforeShuffle);
	Collections.shuffle(list);
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < list.size(); i++) {
	    sb.append(list.get(i));
	}
	String afterShuffle = sb.toString();
	String result = afterShuffle.substring(5, size + 5);
	return result;
    }
    
   /**
    * 
    *@return 返回随机创建的一个邀请码 
    * 
    * */
    public static String createInviteCode(){
    	
    	return (rd.nextInt(99900000)+100000)+"";
    	
    }
    
    
    //test
    public static void main(String[] args) {
//		for(int i=0;i<100;i++){
//		    System.out.println(generate(10));
//		}
    	System.out.println(createInviteCode());
    }
}
