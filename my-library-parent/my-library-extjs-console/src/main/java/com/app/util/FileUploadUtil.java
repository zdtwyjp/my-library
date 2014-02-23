package com.app.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * 
 * <p>
 * Title: UploadUtil.java
 * </p>
 * <p>
 * Description:文件上传工具类
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
 * @date 2012-5-17上午9:23:08
 * @version 1.0
 */
public class FileUploadUtil {

    public FileUploadUtil() {
    }

    /**
     * 
     * @Title: encode
     * @Description: 编码
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-5-17上午9:23:38
     */
    public static String encode(String str) throws UnsupportedEncodingException {
	if (str == null)
	    return "";
	Base64 base64 = new Base64();
	str = java.net.URLEncoder.encode(str, "utf-8");
	return new String(base64.encode(str.getBytes()));
    }

    /**
     * 
     * @Title: decode
     * @Description: 解码
     * @param str
     * @return
     * @throws IOException
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-5-17上午9:23:57
     */
    public static String decode(String str) throws IOException {
	if (str == null)
	    return "";
	Base64 base64 = new Base64();
	str = new String(base64.decode(str.getBytes()));
	return java.net.URLDecoder.decode(str, "utf-8");
    }

    /**
     * 
     * @Title: getEncodeUrl
     * @Description: 生成编码后的URL
     * @param pathAndLocalFileName
     *            路径
     * @param viewFileName
     *            浏览文件名
     * @param isOnLine
     *            是否在线打开
     * @return
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-5-17上午9:37:48
     */
    public static String getEncodeUrl(String pathAndLocalFileName, String viewFileName, boolean isOnLine) {
	try {
	    return "FileDownload?downloadPath=" + encode(pathAndLocalFileName) + "&fileName=" + encode(viewFileName) + "&isOnLine="
		    + isOnLine;
	} catch (Exception e) {
	    return "";
	}
    }

    /**
     * 
    * @Title: getAndroidEncodeUrl
    * @Description: Android安装
    * @param pathAndLocalFileName
    * @param viewFileName
    * @param isOnLine
    * @return
    * @return String
    * @throws
    * @author: YangJunping
    * @date 2012-6-15下午5:29:53
     */
    public static String getAndroidEncodeUrl(String pathAndLocalFileName, String viewFileName) {
	try {
	    return "AndroidFileDownloadServlet?downloadPath=" + encode(pathAndLocalFileName) + "&fileName=" + encode(viewFileName) ;
	} catch (Exception e) {
	    return "";
	}
    }

    /**
     * 
     * @Title: getDownloadPathFromEncodeUrl
     * @Description: 获取下载路径
     * @param fileUrl
     * @return
     * @throws UnsupportedEncodingException
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-5-17上午11:59:02
     */
    public static String getDownloadPathFromEncodeUrl(String fileUrl) {
	if (fileUrl == null) {
	    return "";
	}
	fileUrl = fileUrl.replace("FileDownload?downloadPath=", "");
	String[] uris = fileUrl.split("&fileName=");
	String downloadPath = uris[0];
	// 解码
	Base64 base64 = new Base64();
	downloadPath = new String(base64.decode(downloadPath.getBytes()));
	try {
	    downloadPath = java.net.URLDecoder.decode(downloadPath, "utf-8");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	return downloadPath;
    }
    
    public static String getAndroidDownloadPathFromEncodeUrl(String fileUrl) {
	if (fileUrl == null) {
	    return "";
	}
	fileUrl = fileUrl.replace("AndroidFileDownloadServlet?downloadPath=", "");
	String[] uris = fileUrl.split("&fileName=");
	String downloadPath = uris[0];
	// 解码
	Base64 base64 = new Base64();
	downloadPath = new String(base64.decode(downloadPath.getBytes()));
	try {
	    downloadPath = java.net.URLDecoder.decode(downloadPath, "utf-8");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	return downloadPath;
    }

    /**
     * 
     * @Title: getFileNameFromEncodeUrl
     * @Description: 获取文件名
     * @param fileUrl
     * @return
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-6-14上午11:41:13
     */
    public static String getFileNameFromEncodeUrl(String fileUrl) {
	if (fileUrl == null) {
	    return "";
	}

	fileUrl = fileUrl.replace("FileDownload?downloadPath=", "");
	String[] uris = fileUrl.split("&fileName=");
	if (uris.length > 1) {
	    String temp = uris[1];
	    String[] tempfilename = temp.split("&isOnLine=");
	    String fileName = tempfilename[0];
	    // 解码
	    Base64 base64 = new Base64();
	    fileName = new String(base64.decode(fileName.getBytes()));
	    try {
		fileName = java.net.URLDecoder.decode(fileName, "utf-8");
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }
	    return fileName;
	}
	return "";
    }
    
    public static String getAndroidFileNameFromEncodeUrl(String fileUrl) {
  	if (fileUrl == null) {
  	    return "";
  	}

  	fileUrl = fileUrl.replace("AndroidFileDownloadServlet?downloadPath=", "");
  	String[] uris = fileUrl.split("&fileName=");
  	if (uris.length > 1) {
  	    String temp = uris[1];
  	    String[] tempfilename = temp.split("&isOnLine=");
  	    String fileName = tempfilename[0];
  	    // 解码
  	    Base64 base64 = new Base64();
  	    fileName = new String(base64.decode(fileName.getBytes()));
  	    try {
  		fileName = java.net.URLDecoder.decode(fileName, "utf-8");
  	    } catch (UnsupportedEncodingException e) {
  		e.printStackTrace();
  	    }
  	    return fileName;
  	}
  	return "";
      }

    /**
     * 
     * @Title: getPathNotFileNameFromEncodeUrl
     * @Description: 获取文件路径不含文件名
     * @param fileUrl
     * @return
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-6-14上午11:59:25
     */
    public static String getPathNotFileNameFromEncodeUrl(String fileUrl) {
	String downloadPath = getDownloadPathFromEncodeUrl(fileUrl);
	String path = "";
	if (downloadPath != null && downloadPath.trim().length() > 0) {
	    String[] temp = downloadPath.split("/");
	    path = temp[0];
	} else {
	    throw new RuntimeException("下载路径为空！");
	}
	return path;
    }
    
    public static String getAndroidPathNotFileNameFromEncodeUrl(String fileUrl) {
	String downloadPath = getAndroidDownloadPathFromEncodeUrl(fileUrl);
	String path = "";
	if (downloadPath != null && downloadPath.trim().length() > 0) {
	    String[] temp = downloadPath.split("/");
	    path = temp[0];
	} else {
	    throw new RuntimeException("下载路径为空！");
	}
	return path;
    }

    /**
     * 返回文件后缀名，以"."开始 。例 文件名"pic.jpg",返回的后缀名"jpg"。
     * 
     * @param fileName
     * @return
     */
    public static String getExtName(String fileName) {
	String extName = "";
	if (fileName != null) {
	    extName = fileName.substring(fileName.lastIndexOf('.') + 1);
	}
	return extName;
    }

    public static void main(String[] args) {
	System.out
		.println(getPathNotFileNameFromEncodeUrl("FileDownload?downloadPath=dmVyc2lvbkRhdGElMkZwaG9uZV9hbmRyb2lkLmFwaw==&fileName=cGhvbmVfYW5kcm9pZC5hcGs=&isOnLine=false"));
    }
}