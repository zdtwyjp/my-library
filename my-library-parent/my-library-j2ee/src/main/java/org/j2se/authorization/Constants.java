package org.j2se.authorization;

/**
 * 授权码常量类
 * @author YangJunping
 */
public class Constants {
	
	/** Encrpt password*/
	public static final String ENCRPT_PASSWORD = "xzjc";

	/** 日期格式化串 */
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	/** 授权码异常提示信息*/
	public static final String LICENSE_MESSAGE = "Sorry,your license is invalid.Please contact with provider(028-xxxxxx)!";

	/** 授权码异常提示信息*/
	protected static final String LICENSE_OUT_DATE_MESSAGE = "Sorry,your software has been out of probationary period.Please contact with provider(028-xxxx)!";

	/** 授权码有效时间*/
	private static String strLimitDate = "";

	public static String getStrLimitDate() {
		return strLimitDate;
	}

	public static void setStrLimitDate(String strLimitDate) {
		Constants.strLimitDate = strLimitDate;
	}
	
	/** 授权码key文件*/
	private static String keyFile = "";

	public static String getKeyFile() {
		return keyFile;
	}

	public static void setKeyFile(String keyFile) {
		Constants.keyFile = keyFile;
	}
	
	/**
	 * 验证key
	 * @author YangJunping
	 */
	public static void validateKeyFile() {
		try {
			DecryptorFile.validateKey(keyFile);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}
}
