package com.app.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.app.util.FileUploadUtil;

/**
 * 
 * 
 * <p>
 * Title: FileDownloadServlet.java
 * </p>
 * <p>
 * Description:文件下载Servlet
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
 * @date 2012-5-17上午9:25:15
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FileDownloadServlet extends HttpServlet {
    private static String downLoadPath = null;

    /** 日志 */
    private static final Logger logger = Logger.getLogger(FileDownloadServlet.class);

    @Override
    public void init() throws ServletException {
	downLoadPath = getInitParameter("downLoadPath");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	if (logger.isDebugEnabled()) {
	    logger.debug("downloadPath=" + FileUploadUtil.decode(request.getParameter("downloadPath")) + "\r\nfileName="
		    + FileUploadUtil.decode(request.getParameter("fileName")));
	}
	downloadFile(request, response, FileUploadUtil.decode(request.getParameter("downloadPath")),
		FileUploadUtil.decode(request.getParameter("fileName")));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
    }

    /**
     * 
    * @Title: downloadFile
    * @Description: 下载文件
    * @param request
    * @param response
    * @param literaturePath
    * @param flieName
    * @return void
    * @throws
    * @author: YangJunping
    * @date 2012-5-17上午9:30:15
     */
    private void downloadFile(HttpServletRequest request, HttpServletResponse response, String literaturePath, String flieName) {
	File f = new File(downLoadPath + literaturePath);
	if (f.exists()) {
	    /** 是否在线显示*/
	    String isOnLineStr = request.getParameter("isOnLine");
	    if (isOnLineStr == null || "".equals(isOnLineStr)){
		isOnLineStr = "false";
	    }
	    boolean isOnLine = Boolean.parseBoolean(isOnLineStr);
	    try {
		// 处理不同浏览器对文件名的解析问题
		String agent = (String) request.getHeader("USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
		    // FF
		    flieName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(flieName.getBytes("UTF-8")))) + "?=";
		} else {
		    // IE
		    flieName = new String(flieName.getBytes("GBK"), "ISO-8859-1");
		}
	    } catch (UnsupportedEncodingException e1) {
		e1.printStackTrace();
	    }
	    if (isOnLine) { // 在线打开方式
		URL u;
		try {
		    u = new URL("file:///" + literaturePath);
		    response.setContentType(u.openConnection().getContentType());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		response.setHeader("Content-Disposition", "inline; filename=" + flieName);
		response.setHeader("accept-encoding", "gzip,deflate"); 
	    } else { // 纯下载方式
		response.setContentType("application/x-msdownload; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + flieName);
	    }
	    byte[] buf = new byte[4096];
	    int len = 0;
	    BufferedInputStream br = null;
	    OutputStream out = null;
	    try {
		br = new BufferedInputStream(new FileInputStream(f));
		out = response.getOutputStream();
		while ((len = br.read(buf)) > 0) {
		    out.write(buf, 0, len);
		}
		out.flush();
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		if (br != null) {
		    try {
			br.close();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		    br = null;
		}
		if (out != null) {
		    try {
			out.close();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		    out = null;
		}
	    }
	} else {
	    logger.error("指定文件(" + downLoadPath + literaturePath + ")不存在！");
	}
    }
}