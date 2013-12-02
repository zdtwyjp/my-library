package org.j2se.authorization;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 授权码验证类
 * 
 * @author YangJunping
 */
public class DecryptorFile {
	private static final String IP = "Ip";

	private static final String MAC = "Mac";

	private static final String DATE = "Date";

	private DecryptorFile() {}

	/**
	 * 二进制转为十六进制
	 * 
	 * @author YangJunping
	 */
	public static String parseByte2HexStr(byte[] buf) {
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
	 * 十六进制转为二进制
	 * 
	 * @author YangJunping
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
	 * 加载Key文件
	 * 
	 * @author YangJunping
	 */
	private static Properties loadKeyFile(String keyFile) throws Exception {
		Properties pro = null;
		BufferedReader reader = null;
		String encryptResultStr = "";
		try {
			reader = new BufferedReader(new FileReader(keyFile));
			String temp = null;
			while((temp = reader.readLine()) != null) {
				encryptResultStr += temp;
			}
			reader.close();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(reader != null) {
				try {
					reader.close();
				}catch(IOException e1) {
				}
			}
		}
		byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(Constants.ENCRPT_PASSWORD.getBytes()));
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] awl = cipher.doFinal(decryptFrom);
		pro = new Properties();
		InputStream ins = new ByteArrayInputStream(awl);
		pro.load(ins);
		ins.close();
		return pro;
	}

	/**
	 * 验证key是否合法
	 * 
	 * @author YangJunping
	 */
	public static void validateKey(String keyFile) throws Exception {
		if(keyFile == null) {
			throw new IllegalArgumentException("Key 文件为空！");
		}
		String ip = null;
		String mac = null;
		String limitDate = null;
		Properties pro = null;
		try {
			/** 加载key */
			pro = loadKeyFile(keyFile);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(Constants.LICENSE_MESSAGE);
		}
		if(pro != null) {
			ip = pro.getProperty(IP);
			mac = pro.getProperty(MAC).toLowerCase();
			limitDate = pro.getProperty(DATE);
			Constants.setStrLimitDate(limitDate);
		}
		if(ip == null || mac == null || limitDate == null) {
			throw new RuntimeException(Constants.LICENSE_MESSAGE);
		}
		/** 验证IP*/
//		String currentIp = NetworkInfo.getIPAddress();
//		if(!ip.equals(currentIp)){
//			throw new RuntimeException(Constants.LICENSE_MESSAGE);
//		}
		List<String> ips = NetworkInfo.getIPAddressList();
		if(ips == null || ips.size() == 0){
			throw new RuntimeException(Constants.LICENSE_MESSAGE);
		}else {
			boolean isTrue = false;
			for (String tmp : ips) {
				if(ip.equals(tmp)){
					isTrue = true;
					break;
				}
			}
			if(!isTrue) {
				throw new RuntimeException(Constants.LICENSE_MESSAGE);
			}
		}
		/** 验证Mac*/
		List<String> strMacs = NetworkInfo.getMacAddress();
		if(strMacs == null || strMacs.size() == 0) {
			throw new RuntimeException(Constants.LICENSE_MESSAGE);
		}else {
			boolean isTrue = false;
			for(int i = 0, j = strMacs.size(); i < j; i++) {
				if(mac.equals(strMacs.get(i).toLowerCase())) {
					isTrue = true;
					break;
				}
			}
			if(!isTrue) {
				throw new RuntimeException(Constants.LICENSE_MESSAGE);
			}
		}
		/** 验证日期 */
		validateLimitDate(limitDate);
	}

	/**
	 * 验证授权码是否过期
	 * 
	 * @author YangJunping
	 */
	private static void validateLimitDate(String limitDate) {
		if(limitDate == null || limitDate.trim().length() == 0) {
			throw new RuntimeException(Constants.LICENSE_OUT_DATE_MESSAGE);
		}
		DateFormat format = new SimpleDateFormat(Constants.DATE_PATTERN);
		try {
			Date date = format.parse(limitDate);
			if(date.compareTo(new Date()) < 0) {
				throw new RuntimeException(Constants.LICENSE_OUT_DATE_MESSAGE);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}