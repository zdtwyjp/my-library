package com.app.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片处理工具类：<br>
 * 一、对图片进行等比例缩放。<br>
 * 二、指定宽度和高度对图片进行剪切。
 * 
 * @author yangJunping
 * @date 2010-11-18
 */
public class ImageScaleTool {
    /**
     * 指定宽度和高度截取图片.<br>
     * 原理：<br>
     * 将图片按宽度和高度等比例缩放，然后再在截取出来的图片中实现图片剪切功能。
     * 
     * @param filepath
     *            原图路径
     * @param w
     *            指定宽度
     * @param h
     *            指定高度
     * @param createThumbFile
     *            缩略图存放路径
     * @throws Exception
     */
    public static void createThumbPic(String filepath, int w, int h, String createThumbFile) throws Exception {
	// 读入文件
	File file = new File(filepath);
	// 构造Image对象
	BufferedImage src = ImageIO.read(file);
	int old_w = src.getWidth();
	int old_h = src.getHeight();
	int new_w = 0;
	int new_h = 0;
	double ratio = 0.0;
	if (old_w > old_h) {
	    ratio = (new Integer(h)).doubleValue() / old_h;
	    new_h = h;
	    new_w = (int) (old_w * ratio);
	} else {
	    ratio = (new Integer(w)).doubleValue() / old_w;
	    new_w = w;
	    new_h = (int) (old_h * ratio);
	}
	// 固定宽度和高度缩放图片
	BufferedImage tag = zoomImage(src, new_w, new_h, null);
	// 剪切图片
	BufferedImage newTag = scaleImage(tag, new_w, new_h, w, h);
	// 生成图片
	createThumbImage(createThumbFile, newTag);
    }

    /**
     * 指定宽度和高度截取图片.<br>
     * 原理：<br>
     * 将图片按宽度和高度等比例缩放，然后再在截取出来的图片中实现图片剪切功能。
     * 
     * @param pic
     *            原图字节数组
     * @param w
     *            指定宽度
     * @param h
     *            指定高度
     * @return 剪切图字节数组
     * @throws Exception
     */
    public static byte[] createThumbPic(byte[] pic, int w, int h) throws Exception {
	byte[] thumb = null;
	try {
	    // 构造Image对象
	    ByteArrayInputStream in = new ByteArrayInputStream(pic);
	    BufferedImage src = ImageIO.read(in);
	    int old_w = src.getWidth();
	    int old_h = src.getHeight();
	    int new_w = 0;
	    int new_h = 0;
	    double ratio = 0.0;
	    if (old_w < w && old_h < h)
		return pic;
	    if (old_w > old_h) {
		ratio = (new Integer(h)).doubleValue() / old_h;
		new_h = h;
		new_w = (int) (old_w * ratio);
	    } else {
		ratio = (new Integer(w)).doubleValue() / old_w;
		new_w = w;
		new_h = (int) (old_h * ratio);
	    }
	    // 固定宽度和高度缩放图片
	    BufferedImage tag = zoomImage(src, new_w, new_h, null);
	    // 剪切图片
	    BufferedImage newTag = scaleImage(tag, new_w, new_h, w, h);
	    // 生成字节数组
	    thumb = createThumbImageByte(newTag);
	} catch (Exception e) {
	    thumb = pic;
	    e.printStackTrace();
	}
	return thumb;
    }

    /**
     * 按宽度对图片进行缩放
     * 
     * @param imagePath
     *            原图片路径
     * @param w
     *            指定缩放的宽度
     * @param thumbImaagePath
     *            缩放图片存放路径
     * @throws Exception
     */
    public static void createThumbPicByWidth(String imagePath, int w, String thumbImaagePath) throws Exception {
	// 构造Image对象
	BufferedImage src = ImageIO.read(new File(imagePath));
	// 按宽度对图片进行缩放并生成图片
	createThumbImage(thumbImaagePath, zoomImage(src, w, 0, "w"));
    }

    /**
     * 按宽度对图片进行缩放
     * 
     * @param pic
     *            图片字节数组
     * @param w
     *            指定缩放的宽度
     * @param thumbImaagePath
     *            缩放图片存放路径
     * @throws Exception
     */
    public static void createThumbPicByWidth(byte[] pic, int w, String thumbImaagePath) throws Exception {
	// 构造Image对象
	BufferedImage src = ImageIO.read(new ByteArrayInputStream(pic));
	// 按宽度对图片进行缩放并生成图片
	createThumbImage(thumbImaagePath, zoomImage(src, w, 0, "w"));
    }

    /**
     * 按宽度对图片进行缩放，并返回新图字节数组
     * 
     * @param pic
     *            原图
     * @param w
     *            指定缩放的宽度
     * @return 新图字节数组
     * @throws Exception
     */
    public static byte[] createThumbPicByteByWidth(byte[] pic, int w) throws Exception {
	// 构造Image对象
	BufferedImage src = ImageIO.read(new ByteArrayInputStream(pic));
	// 按宽度对图片进行缩放并生成图片
	return createThumbImageByte(zoomImage(src, w, 0, "w"));
    }

    /**
     * 按高度对图片进行缩放
     * 
     * @param imagePath
     *            原图片路径
     * @param h
     *            指定缩放高度
     * @param thumbImaagePath
     *            缩放图片存放路径
     * @throws Exception
     */
    public static void createThumbPicByHeight(String imagePath, int h, String thumbImaagePath) throws Exception {
	// 构造Image对象
	BufferedImage src = ImageIO.read(new File(imagePath));
	// 按宽度对图片进行缩放并生成图片
	createThumbImage(thumbImaagePath, zoomImage(src, 0, h, "h"));
    }

    /**
     * 按高度对图片进行缩放
     * 
     * @param pic
     *            图片字节数组
     * @param h
     *            指定缩放高度
     * @param thumbImaagePath
     *            缩放图片存放路径
     * @throws Exception
     */
    public static void createThumbPicByHeight(byte[] pic, int h, String thumbImaagePath) throws Exception {
	// 构造Image对象
	BufferedImage src = ImageIO.read(new ByteArrayInputStream(pic));
	// 按宽度对图片进行缩放并生成图片
	createThumbImage(thumbImaagePath, zoomImage(src, 0, h, "h"));
    }

    /**
     * 按高度或者宽度自动对图片进行缩放。 当图片大小高宽都小于指定的高宽时不做任务处理。
     * 
     * @param imagePath
     *            原图片路径
     * @param w
     *            指定宽度
     * @param h
     *            指定高度
     * @param thumbImaagePath
     *            缩放图片存放路径
     * @throws Exception
     */
    public static void createThumbPicAutoByWidthOrHeight(byte[] pic, int w, int h, String thumbImaagePath) throws Exception {
	// 构造Image对象
	BufferedImage src = ImageIO.read(new ByteArrayInputStream(pic));
	if (src != null) {
	    int old_w = src.getWidth();
	    int old_h = src.getHeight();
	    if (old_w < w && old_h < h) {
		BufferedImage tag = new BufferedImage(old_w, old_h, src.getType());
		// Image.SCALE_SMOOTH 选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
		tag.getGraphics().drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
		// 生成图片
		createThumbImage(thumbImaagePath, tag);
	    } else {
		if (old_w > old_h) {
		    // 按宽度对图片进行缩放并生成图片
		    createThumbImage(thumbImaagePath, zoomImage(src, w, 0, "w"));
		} else {
		    // 按高度对图片进行缩放并生成图片
		    createThumbImage(thumbImaagePath, zoomImage(src, 0, h, "h"));
		}
	    }

	}
    }

    /**
     * 按高度或者宽度自动对图片进行缩放。 当图片大小高宽都小于指定的高宽时不做任务处理。
     * 
     * @param imagePath
     *            原图片路径
     * @param w
     *            指定宽度
     * @param h
     *            指定高度
     * @param thumbImaagePath
     *            缩放图片存放路径
     * @throws Exception
     */
    public static void createThumbPicAutoByWidthOrHeight(String imagePath, int w, int h, String thumbImaagePath) throws Exception {
	// 构造Image对象
	BufferedImage src = null;
	try {
	    src = ImageIO.read(new File(imagePath));
	} catch (Exception e) {
	    e.printStackTrace();
	    return;
	}
	
	if (src != null) {
	    int old_w = src.getWidth();
	    int old_h = src.getHeight();
	    if (old_w < w && old_h < h) {
		BufferedImage tag = new BufferedImage(old_w, old_h, src.getType());
		// Image.SCALE_SMOOTH 选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
		tag.getGraphics().drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
		// 生成图片
		createThumbImage(thumbImaagePath, tag);
	    } else {
		if (old_w > old_h) {
		    // 按宽度对图片进行缩放并生成图片
		    createThumbImage(thumbImaagePath, zoomImage(src, w, 0, "w"));
		} else {
		    // 按高度对图片进行缩放并生成图片
		    createThumbImage(thumbImaagePath, zoomImage(src, 0, h, "h"));
		}
	    }

	}
    }

    /**
     * 按高度或者宽度自动对图片进行缩放。 当图片大小高宽都小于指定的高宽时不做任务处理。
     * 
     * @param imagePath
     *            原图片路径
     * @param w
     *            指定宽度
     * @param h
     *            指定高度
     * @param thumbImaagePath
     *            缩放图片存放路径
     * @throws Exception
     */
    public static byte[] createThumbPicAutoByWidthOrHeight(byte[] pic, int w, int h) throws Exception {
	// 构造Image对象
	BufferedImage src = ImageIO.read(new ByteArrayInputStream(pic));
	if (src != null) {
	    int old_w = src.getWidth();
	    int old_h = src.getHeight();
	    if (old_w < w && old_h < h) {
		BufferedImage tag = new BufferedImage(old_w, old_h, src.getType());
		// Image.SCALE_SMOOTH 选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
		tag.getGraphics().drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
		// 生成图片
		return createThumbImageByte(tag);
	    } else {
		if (old_w > old_h) {
		    // 按宽度对图片进行缩放并生成图片
		    return createThumbImageByte(zoomImage(src, w, 0, "w"));
		} else {
		    // 按高度对图片进行缩放并生成图片
		    return createThumbImageByte(zoomImage(src, 0, h, "h"));
		}
	    }
	}
	return null;
    }
    
    public static byte[] createThumbPicAutoByWidthOrHeight2(byte[] pic, int w, int h) throws Exception {
	// 构造Image对象
	BufferedImage src = ImageIO.read(new ByteArrayInputStream(pic));
	if (src != null) {
	    int old_w = src.getWidth();
	    int old_h = src.getHeight();
	    if (old_w < w && old_h < h) {
		BufferedImage tag = new BufferedImage(old_w, old_h, src.getType());
		// Image.SCALE_SMOOTH 选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
		tag.getGraphics().drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
		// 生成图片
		return createThumbImageByte2(tag);
	    } else {
		if (old_w > old_h) {
		    // 按宽度对图片进行缩放并生成图片
		    return createThumbImageByte2(zoomImage(src, w, 0, "w"));
		} else {
		    // 按高度对图片进行缩放并生成图片
		    return createThumbImageByte2(zoomImage(src, 0, h, "h"));
		}
	    }
	}
	return null;
    }

    /**
     * 按高度对图片进行缩放，并返回新图片的字节数组
     * 
     * @param pic
     *            原图
     * @param h
     *            指定的缩放高度
     * @return 新图字节数组
     * @throws Exception
     */
    public static byte[] createThumbPicByteByHeight(byte[] pic, int h) throws Exception {
	BufferedImage src = ImageIO.read(new ByteArrayInputStream(pic));
	return createThumbImageByte(zoomImage(src, 0, h, "h"));
    }

    /**
     * 按指定的宽度和高度生成图片
     * 
     * @param imagePath
     *            原图片路径
     * @param w
     *            指定图片的宽度
     * @param h
     *            指定图片的高度
     * @param thumbImaagePath
     *            新图存放路径
     * @throws Exception
     */
    public static void createThumbPicByAppointWidthAndHeight(String imagePath, int w, int h, String thumbImaagePath) throws Exception {
	// 构造Image对象
	BufferedImage src = ImageIO.read(new File(imagePath));
	// 按宽度对图片进行缩放并生成图片
	createThumbImage(thumbImaagePath, zoomImage(src, w, h, null));
    }

    /**
     * 按指定的宽度和高度生成图片
     * 
     * @param pic
     *            原图片
     * @param w
     *            指定图片的宽度
     * @param h
     *            指定图片的高度
     * @param thumbImaagePath
     *            新图存放路径
     * @throws Exception
     */
    public static void createThumbPicByAppointWidthAndHeight(byte[] pic, int w, int h, String thumbImaagePath) throws Exception {
	// 构造Image对象
	BufferedImage src = ImageIO.read(new ByteArrayInputStream(pic));
	// 按宽度对图片进行缩放并生成图片
	createThumbImage(thumbImaagePath, zoomImage(src, w, h, null));
    }

    /**
     * 按指定的宽度和高度生成图片,并返回图片字节数组
     * 
     * @param pic
     *            原图片
     * @param w
     *            指定图片的宽度
     * @param h
     *            指定图片的高度
     * @return
     * @throws Exception
     */
    public static byte[] createThumbPicByAppointWidthAndHeight(byte[] pic, int w, int h) throws Exception {
	// 构造Image对象
	BufferedImage src = ImageIO.read(new ByteArrayInputStream(pic));
	// 按宽度对图片进行缩放并生成图片
	return createThumbImageByte(zoomImage(src, w, h, null));
    }

    /**
     * 图片缩放处理
     * 
     * @param src
     *            图片
     * @param w
     * @param h
     * @param type
     *            : w:表示按宽度缩放；h:表示按高度缩放；否则按指定的宽度和高度生成图片
     * @return
     */
    private static BufferedImage zoomImage(BufferedImage src, int w, int h, String type) {
	int old_w = src.getWidth();
	int old_h = src.getHeight();
	int new_w = 0;
	int new_h = 0;
	double ratio = 0.0;
	if ("w".equals(type)) {
	    ratio = (new Integer(w)).doubleValue() / old_w;
	    new_w = w;
	    new_h = (int) (old_h * ratio);
	} else if ("h".equals(type)) {
	    ratio = (new Integer(h)).doubleValue() / old_h;
	    new_h = h;
	    new_w = (int) (old_w * ratio);
	} else {
	    new_w = w;
	    new_h = h;
	}
	// 缩放图片
	BufferedImage tag = new BufferedImage(new_w, new_h, src.getType());
	// Image.SCALE_SMOOTH 选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
	tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
	return tag;
    }

    /**
     * 图片剪切处理
     * 
     * @param src
     *            图片
     * @param x
     *            横坐标
     * @param y
     *            纵坐标
     * @param w
     *            宽度
     * @param h
     *            高度
     * @return 剪切后的图片
     */
    private static BufferedImage scaleImage(BufferedImage src, int new_w, int new_h, int w, int h) {
	// 获得截取时需要指定的坐标
	int[] point = getScalePoint(w, h, new_w, new_h);
	int x = 0;
	int y = 0;
	try {
	    x = point[0];
	    y = point[1];
	} catch (Exception e) {
	    x = 0;
	    y = 0;
	}
	// 按指定 x、y、w 和 h 参数从源 Image 提取绝对矩形区域来构造 CropImageFilter
	CropImageFilter cropFilter = new CropImageFilter(x, y, w, h);
	// 使用指定的图像生成器创建一幅图像
	Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(src.getSource(), cropFilter));
	BufferedImage newTag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	// 生成图片
	newTag.getGraphics().drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
	return newTag;
    }

    /**
     * 生成图片
     * 
     * @param thumbImagePath
     *            图片存放路径
     * @param tag
     *            图片
     * @throws Exception
     */
    private static void createThumbImage(String thumbImagePath, BufferedImage tag) throws Exception {
	String dir = getPrePath(thumbImagePath);
	File filePath = new File(dir); // 构建目录
	if (!filePath.exists()) {
	    filePath.mkdir();
	}
	FileOutputStream out = new FileOutputStream(thumbImagePath);
	// 近JPEG编码
	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	JPEGEncodeParam jep = encoder.getDefaultJPEGEncodeParam(tag);
	// 指定图片生成的质量： quality取值在 1.0 到 0.0 之间Some guidelines: 0.75 high quality
	// 0.5 medium quality 0.25 low quality
	jep.setQuality(1f, false);
	encoder.setJPEGEncodeParam(jep);
	encoder.encode(tag, jep);
	out.close();
    }

    public static String getPrePath(String path) {
	String preName = "";
	if (path != null) {
	    preName = path.substring(0, path.lastIndexOf('/'));
	}
	return preName;
    }

    /**
     * 构造图片字节数组
     * 
     * @param tag
     *            图片
     * @throws Exception
     */
    private static byte[] createThumbImageByte(BufferedImage tag) throws Exception {
	byte[] thumb = null;
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	// 近JPEG编码
	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	JPEGEncodeParam jep = encoder.getDefaultJPEGEncodeParam(tag);
	// 指定图片生成的质量： quality取值在 1.0 到 0.0 之间Some guidelines: 0.75 high quality
	// 0.5 medium quality 0.25 low quality
	jep.setQuality(1f, false);
	encoder.setJPEGEncodeParam(jep);
	encoder.encode(tag, jep);
	thumb = out.toByteArray();
	out.close();
	return thumb;
    }
    
    private static byte[] createThumbImageByte2(BufferedImage tag) throws Exception {
	byte[] thumb = null;
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	// 近JPEG编码
	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	JPEGEncodeParam jep = encoder.getDefaultJPEGEncodeParam(tag);
	// 指定图片生成的质量： quality取值在 1.0 到 0.0 之间Some guidelines: 0.75 high quality
	// 0.5 medium quality 0.25 low quality
	jep.setQuality(0.8f, false);
	encoder.setJPEGEncodeParam(jep);
	encoder.encode(tag, jep);
	thumb = out.toByteArray();
	out.close();
	return thumb;
    }

    /**
     * @param new_w
     *            指定的宽度
     * @param new_h
     *            指定的高度
     * @param old_w
     *            缩放后图片宽度
     * @param old_h
     *            缩放后图片高度
     * @return
     */
    private static int[] getScalePoint(int new_w, int new_h, int old_w, int old_h) {
	int[] point = new int[2];
	if (old_w < new_w && old_h < new_h) {
	    point[0] = 0;
	    point[1] = 0;
	} else if (old_w > old_h) {
	    point[0] = (old_w - new_w) / 2;
	    point[1] = 0;
	} else {
	    point[0] = 0;
	    point[1] = (old_h - new_h) / 2;
	}
	return point;
    }

    public static void main(String[] args) throws Exception {
	// createThumbPicByWidth("d:/1.jpg", 300, "d:/11.jpg");
	// createThumbPicAutoByWidthOrHeight("d:/1.jpg", 800, 800, "d:/12.jpg");
//	createThumbPicAutoByWidthOrHeight("d:/1.jpg", 300, 200, "d:/12.jpg");
	createThumbPicAutoByWidthOrHeight("d:/1.jpg", 95, 55, "d:/13.jpg");
    }
}