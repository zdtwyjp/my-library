package com.app.upload;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 文件操作公共接口
 * 
 * @author YangJunping
 */
public interface UploadService {
    /**
     * 保存文件
     * 
     * @param path
     *            文件的路径
     * @param fileName
     *            文件名
     * @param mediaFile
     *            文件二进制数组
     */
    void saveFile(String path, String fileName, byte[] mediaFile) throws Exception;

    /**
     * 
     * @Title: changeFileName
     * @Description: 修改文件名
     * @param fileUrl
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-6-14上午11:38:55
     */
    void changeFileName(String fileUrl);
    
    
    void changeAndroidFileName(String fileUrl);

    /**
     * 删除文件
     * 
     * @param path
     *            文件的路径
     * @param fileName
     *            文件名
     * @param stream
     *            文件流
     */
    void deleteFile(String path, String fileName) throws Exception;

    void deleteFileByAppointFileName(String fileName) throws Exception;

    /**
     * 
     * @Title: deleteFileOrDir
     * @Description: 删除目录
     * @param dir
     * @throws Exception
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-5-17下午1:13:36
     */
    public boolean deleteFileOrDir(String dir) throws Exception;

    /**
     * 获得存储文件的根目录
     * 
     * @return
     */
    String getPath();

    /**
     * 设置存储文件的根目录
     * 
     * @param path
     */
    void setPath(String path);

    /**
     * 编码
     * 
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    String encode(String str) throws Exception;

    /**
     * 解码
     * 
     * @param str
     * @return
     * @throws IOException
     */
    String decode(String str) throws Exception;
}