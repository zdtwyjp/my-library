package com.sys.core;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * 
 * <p>
 * Title: PasswordAuthenticator.java
 * </p>
 * <p>
 * Description:密码验证类
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
 * @date 2012-4-11上午10:04:23
 * @version 1.0
 */

public class PasswordAuthenticator {
    /** 随机数生成器*/ 
    private static SecureRandom random = new SecureRandom();

    private PasswordAuthenticator() {

    }

    /** 产生下一个随机数*/ 
    private static synchronized byte[] nextBytes(int byteSize) {
	byte[] salt = new byte[byteSize];
	random.nextBytes(salt);
	return salt;
    }

    /**
     * 将密码加密
     * 
     * @param password
     *            密码
     * @return
     * @throws Exception
     */
    public static String createPassword(String password) throws Exception {
	byte[] salt = nextBytes(12);

	// Get a MessageDigest object
	MessageDigest md = MessageDigest.getInstance("MD5");
	md.update(salt);
	md.update(password.getBytes());
	byte[] digest = md.digest();
	byte[] result = new byte[digest.length + 12];
	System.arraycopy(salt, 0, result, 0, 12);
	System.arraycopy(digest, 0, result, 12, digest.length);

	return new BASE64Encoder().encode(result);
    }

    /**
     * 验证密码
     * 
     * @param password
     *            密码
     * @param digestWithSalt
     *            存储的加密数据
     * 
     * @return
     * @throws Exception
     */
    public static boolean authenticatePassword(String password, String digestWithSalt) throws Exception {
	BASE64Decoder baseDecoder = new BASE64Decoder();
	byte[] hashedPasswordWithSalt = baseDecoder.decodeBuffer(digestWithSalt);
	boolean result = false;
	if (hashedPasswordWithSalt.length > 12) {
	    byte[] salt = new byte[12];
	    System.arraycopy(hashedPasswordWithSalt, 0, salt, 0, 12);

	    // Get a message digest and digest the salt and
	    // the password that was entered.
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(salt);
	    md.update(password.getBytes());
	    byte[] digest = md.digest();

	    // Get the byte array of the hashed password in the file
	    byte[] digestInSave = new byte[hashedPasswordWithSalt.length - 12];
	    System.arraycopy(hashedPasswordWithSalt, 12, digestInSave, 0, hashedPasswordWithSalt.length - 12);

	    // Now we have both arrays, we need to compare them.

	    if (Arrays.equals(digest, digestInSave)) {
		result = true;

	    }
	}
	return result;
    }

    /**
     * 
    * @Title: sha1
    * @Description: 将原来SHA1加密算法，移到密码处理器里面
    * @param str
    * @return
    * @return String
    * @throws
    * @author: YangJunping
    * @date 2012-4-12下午3:15:08
     */
    public static String sha1(String str) {
	String result = null;
	try {
	    java.security.MessageDigest alga = java.security.MessageDigest.getInstance("SHA-1");
	    alga.update(str.getBytes());
	    byte[] digesta = alga.digest();

	    result = byte2hex(digesta);
	} catch (java.security.NoSuchAlgorithmException ex) {
	    System.out.println("非法摘要算法");
	}
	return result;
    }

    /**
     * 
    * @Title: byte2hex
    * @Description: 二行制转字符串
    * @param b
    * @return
    * @return String
    * @throws
    * @author: YangJunping
    * @date 2012-4-12下午3:15:24
     */
    private static String byte2hex(byte[] b) {
	String hs = "";
	String stmp = "";
	for (int n = 0; n < b.length; n++) {
	    stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
	    if (stmp.length() == 1)
		hs = hs + "0" + stmp;
	    else
		hs = hs + stmp;
	}
	return hs.toLowerCase();
    }
    
    public static void main(String[] args) {
	String pwd = "123456";
	try {
	    String pwd2 = createPassword(pwd);
	    System.out.println(pwd2);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
