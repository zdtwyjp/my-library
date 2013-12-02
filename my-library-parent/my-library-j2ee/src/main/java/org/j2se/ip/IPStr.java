package org.j2se.ip;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPStr {
	/**
	 * @Title: main
	 * @Description: TODO
	 * @param args
	 * @return void
	 * @throws
	 * @author: YangJunping
	 */
	public static void main(String[] args) {
		String mac = "[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}";
		String ip = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
//		Pattern pattern = Pattern.compile(ip);
//		Matcher matcher = pattern.matcher("127.330.0.2"); //����֤127.400.600.2Ϊ��
//		System.out.println(matcher.matches());
		
//		System.out.println(validate(ip,"10.2.10.1"));
		
		mac = "^([0-9a-fA-F]{2})(([0-9a-fA-F]{2}){5})$";
		System.out.println(validate(mac,"78-E3-B5-8F-36-CF"));
		
		String patternMac="^[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}$";
        Pattern pa= Pattern.compile(patternMac);
        System.out.println(pa.matcher("23:34:3e:5f:33:3d").find());
	}
	
	
	public static boolean validate(String regex, String str){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}
