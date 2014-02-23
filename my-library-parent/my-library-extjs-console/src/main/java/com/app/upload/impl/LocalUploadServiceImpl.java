package com.app.upload.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.app.upload.UploadService;
import com.app.util.FileUploadUtil;
import com.app.util.StringUtil;

//@Service("uploadService")
public class LocalUploadServiceImpl implements UploadService {
    private static final Logger logger = Logger.getLogger(LocalUploadServiceImpl.class);

    private String path;

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    @Override
    public void saveFile(String path, String fileName, byte[] mediaFile) throws Exception {
	File filePath = new File(this.path + path); // 构建目录
	if (!filePath.exists()) {
	    filePath.mkdirs();
	}
	File file = new File(filePath, fileName);
	if (logger.isDebugEnabled()) {
	    logger.debug("-------------- 文件开始上传-------------------------------");
	}
	FileOutputStream output = new FileOutputStream(file);
	output.write(mediaFile);
	output.flush();
	output.close();
	if (logger.isDebugEnabled()) {
	    logger.debug("-------------- 文件上传完毕-------------------------------");
	}
    }

    @Override
    public void deleteFile(String path, String fileName) throws Exception {
	File file = new File(this.path + path, fileName);
	if (file.exists()) {
	    if (file.delete()) {
		if (logger.isDebugEnabled()) {
		    logger.debug("文件删除成功！");
		}
	    }
	} else {
	    if (logger.isDebugEnabled()) {
		logger.debug("文件不存在！");
	    }
	}
    }

    public boolean deleteFileOrDir(String path) {
	boolean result = false;
	File file = new File(this.path + path);
	if (file.isFile()) {
	    result = file.delete();
	} else {
	    result = deleteFolder(file);
	}
	return result;
    }

    /**
     * 删除指定文件夹及其的全部内容
     * 
     * @param path
     *            目录对象
     * @return true--删除成功 ,false--删除失败
     * @author liulm
     */
    private boolean deleteFolder(File path) {
	boolean result = true;
	if (path != null && path.isDirectory()) {
	    File[] fileList = path.listFiles();
	    for (int i = 0; i < fileList.length; i++) {
		if (fileList[i].isDirectory()) {
		    deleteFolder(fileList[i]);
		}
		fileList[i].delete();
	    }
	    // 因为目录不为空时，不能删除，所以只需要返回外层删除结果。
	    result = path.delete();
	} else {
	    result = false;
	}
	return result;
    }

    /**
     * 编码
     * 
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public String encode(String str) throws UnsupportedEncodingException {
	if (str == null)
	    return "";
	Base64 base64 = new Base64();
	str = java.net.URLEncoder.encode(str, "utf-8");
	return new String(base64.encode(str.getBytes()));
    }

    /**
     * 解码
     * 
     * @param str
     * @return
     * @throws IOException
     */
    public String decode(String str) throws IOException {
	if (str == null)
	    return "";
	Base64 base64 = new Base64();
	str = new String(base64.decode(str.getBytes()));
	return java.net.URLDecoder.decode(str, "utf-8");
    }

    @Override
    public void deleteFileByAppointFileName(String fileUrl) throws Exception {
	if (!StringUtil.isNotNull(fileUrl)) {
	    return;
	}
	String downloadPath = FileUploadUtil.getDownloadPathFromEncodeUrl(fileUrl);
	File file = new File(this.path + downloadPath);
	if (file.exists()) {
	    if (file.delete()) {
		if (logger.isDebugEnabled()) {
		    logger.debug("文件删除成功！");
		}
	    }
	} else {
	    if (logger.isDebugEnabled()) {
		logger.debug("文件不存在！");
	    }
	}
    }

    @Override
    public void changeFileName(String fileUrl) {
	if (!StringUtil.isNotNull(fileUrl)) {
	    return;
	}
	String fileName = "temp_" + FileUploadUtil.getFileNameFromEncodeUrl(fileUrl);
	String downloadPath = FileUploadUtil.getDownloadPathFromEncodeUrl(fileUrl);
	File file = new File(this.path + downloadPath);
	if (file.exists()) {
	    FileInputStream fis = null;
	    FileOutputStream fos = null;
	    try {
		String root = FileUploadUtil.getPathNotFileNameFromEncodeUrl(fileUrl);
		fis = new FileInputStream(file);
		fos = new FileOutputStream(this.path + root + "/" + fileName);
		byte[] buff = new byte[1024];
		int readed = -1;
		while ((readed = fis.read(buff)) > 0) {
		    fos.write(buff, 0, readed);
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		try {
		    fis.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		try {
		    fos.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	    /** 删除旧文件 */
	    if (file.delete()) {
		if (logger.isDebugEnabled()) {
		    logger.debug("删除成功！");
		}
	    }

	}

    }

    @Override
    public void changeAndroidFileName(String fileUrl) {
	if (!StringUtil.isNotNull(fileUrl)) {
	    return;
	}
	String fileName = "temp_" + FileUploadUtil.getAndroidFileNameFromEncodeUrl(fileUrl);
	String downloadPath = FileUploadUtil.getAndroidDownloadPathFromEncodeUrl(fileUrl);
	File file = new File(this.path + downloadPath);
	if (file.exists()) {
	    FileInputStream fis = null;
	    FileOutputStream fos = null;
	    try {
		String root = FileUploadUtil.getAndroidPathNotFileNameFromEncodeUrl(fileUrl);
		fis = new FileInputStream(file);
		fos = new FileOutputStream(this.path + root + "/" + fileName);
		byte[] buff = new byte[1024];
		int readed = -1;
		while ((readed = fis.read(buff)) > 0) {
		    fos.write(buff, 0, readed);
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		try {
		    fis.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		try {
		    fos.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	    /** 删除旧文件 */
	    if (file.delete()) {
		if (logger.isDebugEnabled()) {
		    logger.debug("删除成功！");
		}
	    }

	}

    }
}