package com.sys.common.util;

import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>
 * Title: AESEncryptUtil.java
 * </p>
 * <p>
 * Description:AES加密解密工具
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company: fxpgy
 * </p>
 * <p>
 * team: fxpgy Team
 * </p>
 * <p>
 * 
 * @author: YangJunping
 *          </p>
 * @date 2012-4-7下午8:26:11
 * @version 1.0
 */
public class AESEncryptUtil {
	/**
	 * 由于JDK默认只支持128位,以下内容注释, 如果要启动256位密钥, 则需要更新local_policy.jar,US_export_policy.jar
	 */
	// public enum KeySize {
	// LENGTH128(128), LENGTH192(192), LENGTH256(256);
	// private final int length;
	//
	// private KeySize(int length) {
	// this.length = length;
	// }
	//
	// public int length() {
	// return length;
	// }
	// }
	/**
	 * @Title: encrypt
	 * @Description: 加密
	 * @param content
	 *            待加密内容
	 * @param password
	 *            加密密码
	 * @return
	 * @throws Exception
	 * @throws
	 * @author YangJunping
	 * @date 2012-4-7下午8:26:49
	 */
	public static byte[] encrypt(String content, String password)
			throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(password.getBytes()));
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		byte[] byteContent = content.getBytes("utf-8");
		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(byteContent);
		return result; // 加密
	}

	/**
	 * @Title: decrypt
	 * @Description: 解密
	 * @param content
	 *            待解密内容
	 * @param password
	 *            加密密码
	 * @return 解密后的内容
	 * @throws Exception
	 * @author YangJunping
	 * @date 2012-4-7下午8:27:14
	 */
	public static byte[] decrypt(byte[] content, String password)
			throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(password.getBytes()));
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码�?
		cipher.init(Cipher.DECRYPT_MODE, key);// 初始�?
		byte[] result = cipher.doFinal(content);
		return result; // 加密
	}

	/**
	 * @Title: parseByte2HexStr
	 * @Description: 将二进制转换成16进制
	 * @param buf
	 * @return
	 * @return String
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-7下午8:27:39
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if(hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * @Title: parseHexStr2Byte
	 * @Description: 将16进制转换为二进制
	 * @param hexStr
	 * @return
	 * @return byte[]
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-7下午8:27:52
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if(hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for(int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte)(high * 16 + low);
		}
		return result;
	}

	/**
	 * @Title: encryptToString
	 * @Description: 加密并得到字符串
	 * @param content
	 *            待加密的内容
	 * @param password
	 *            密码
	 * @return 加密后的字符串
	 * @throws Exception
	 * @author YangJunping
	 * @date 2012-4-7下午8:28:54
	 */
	public static String encryptToString(String content, String password)
			throws Exception {
		byte[] encryptResult = encrypt(content, password);
		String encryptResultStr = parseByte2HexStr(encryptResult);
		return encryptResultStr;
	}

	/**
	 * @Title: decrypt
	 * @Description: 对字符串解密
	 * @param content
	 *            待解密的内容
	 * @param password
	 *            密码
	 * @return 解密后的串
	 * @throws Exception
	 * @author YangJunping
	 * @date 2012-4-7下午8:28:59
	 */
	public static String decrypt(String content, String password)
			throws Exception {
		byte[] decryptFrom = parseHexStr2Byte(content);
		byte[] decryptResult = decrypt(decryptFrom, password);
		return new String(decryptResult);
	}

	public static void main(String[] args) throws Exception {
		System.out.println("请输入需加密内容:");
		Scanner in = new Scanner(System.in);
		String content = in.nextLine();
		String password = null;
		boolean twoInputEq = false;
		while(!twoInputEq) {
			System.out.println("请输入密钥:");
			String password1 = in.nextLine();
			System.out.println("请再次输入密钥:");
			String password2 = in.nextLine();
			twoInputEq = password1.equals(password2);
			password = password1;
		}
		in.close();
		System.out.println("-----------------------------");
		String encryptStr = encryptToString(content, password);
		System.out.println("第二次加密后" + encryptStr);
		String result = decrypt(encryptStr, password);
		System.out.println("第二次解密后" + result);
	}
}
