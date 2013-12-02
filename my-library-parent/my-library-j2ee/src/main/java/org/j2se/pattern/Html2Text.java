// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space
// Source File Name: Html2Text.java
package org.j2se.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Html2Text {
	public Html2Text() {}

	/**
	 * 将传入的string格式化为没有html标签的string
	 * 
	 * @param inputString
	 * @return
	 */
	public static String html2TextFormat(String inputString) {
		String htmlStr = inputString;
		String textStr = "";
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			String regEx_html = "<[^>]+>";
			Pattern p_script = Pattern.compile(regEx_script, 2);
			Matcher m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll("");
			Pattern p_style = Pattern.compile(regEx_style, 2);
			Matcher m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll("");
			Pattern p_html = Pattern.compile(regEx_html, 2);
			Matcher m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll("");
			textStr = htmlStr;
		}catch(Exception e) {
			System.err.println((new StringBuilder("Html2Text: ")).append(
					e.getMessage()).toString());
		}
		return textStr;
	}
	
	public static void main(String[] args) {
		String str = "<script>testset</script><html>中国国家</html>";
		str = html2TextFormat(str);
		System.out.println(str);
	}
}
