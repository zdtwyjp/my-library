package com.sys.core;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * <p>
 * Title: ConstantsObj.java
 * </p>
 * <p>
 * Description:常量类
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
 * @date 2012-4-12下午2:20:27
 * @version 1.0
 */
public final class ConstantsObj implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 佣金单位
	 */
	public static final String PRICICE_UNIT = "元";

	/** 货币格式化 */
	public static final String FORMATE_MONEY = "#.##";

	/** 列表默认每页显示条数 */
	public static final int DEFAULT_PAGE_SIZE = 20;

	/** 判断字符串是否包含字母的正则表达式 */
	public static final String FIND_IS_CONTAIN_LETTER_REGEX = "^.*[a-zA-Z].*$";

	/** 日期格式化串 */
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	/** 日期格式化 */
	public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(
			DATE_PATTERN);

	/** 日期时间格式化串 */
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

	/** 日期时间格式化 */
	public static final SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(
			DATE_TIME_PATTERN);

	/** 验证电子邮箱的正则表达式(允许空串) */
	public static final String FIND_IS_E_MAIL_REGEX = "/^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$|^\\s{0}$/";

	final public static String noAuthor = "<span style='color:red'>无</span>";

	/** 列表分页显示文字 */
	public static final String PAGING_TOOLBAR_DISPLAY_MSG = "当前显示第&nbsp;{0}&nbsp;到&nbsp;{1}&nbsp;条,共&nbsp;{2}&nbsp;条";

	/** 限制Ext页面TAG标签的长度 */
	public static final int TAG_LENGTH = 4;

	/** 短信下发接口用户名与密码 */
	// public static final String SMS_USERNAME = "R_Xg_YFZX";
	// public static final String SMS_PWD = "gj6wgp";
	// public static final String SMS_USERNAME = "R_shili";
	// public static final String SMS_PWD = "123123";
	public static final String SMS_USERNAME = "R_XG_YFW";

	public static final String SMS_PWD = "123123";

	/** 临时文件存放文件名 */
	public static final String TEMPDATA_NAME = "tempdata";

	/** 上传的图片存放文件名 */
	public static final String PHOTOES = "photoes";

	/** 版本存放路径 */
	public static final String VERSIONDATA = "versionData";

	/** 手机：Android */
	public static final String PHONE_ANDROID = "android";

	/** 手机：IPhone */
	public static final String PHONE_IPHONE = "iphone";

	/** Ipad */
	public static final String IPAD = "ipad";

	/** 设置悬赏的图片数 */
	public static final int OFFER_NUMBER = 10;

	/** 本地推荐限制上限 */
	public static final int LOCAL_PROJECT_NUMBER = 20;

	/** 高佣金推荐获取上限 */
	public static final int HIGHCOMMISSION_NUMBER = 20;

	/** 商业地产推荐上限 */
	public static final int COMMERCIALPROPERTYRECOMMEND_NUMBER = 20;

	/** 旅游地产 */
	public static final int TRAVEL_NUMBER = 20;

	/** 即将开始 */
	public static final int TRANSACTIONSTATUS_NO_BEGIN_NUMBER = 20;

	/** 预约排行展示条数 */
	public static final int APPOINT_SHOW_NUMBER = 10;

	/** 首页公告展示条数 */
	public static final int ANNOUNCEMENT_SHOW_NUMBER = 6;

	/** APP公告展示条数 */
	public static final int APP_ANNOUNCEMENT_SHOW_NUMBER = 10;

	/** 用户头像存放文件名 */
	public static final String PHONTO_USER = "users";

	/** APPSession过期时间 */
	public static final int APP_SESSION_MAXINACTIVEINTERVAL = 518400;

	/** 项目评论每页展示条数 */
	public static final int PROJECT_SHOW_PAGESIZE = 5;

	/**
	 * 图片尺寸大小限制
	 */
	/** 开发商图片 */
	public static final int PHOTO_DEVELOP_SIZE_WIDTH = 278;

	public static final int PHOTO_DEVELOP_SIZE_HEIGHT = 64;

	/** 项目首页图片： */
	public static final int PHOTO_HOMEPAGE_SIZE_WIDTH = 210;

	public static final int PHOTO_HOMEPAGE_SIZE_HEIGHT = 128;

	public static final int PHOTO_HOMEPAGE_SIZE_PHONE_WIDTH = 95;

	public static final int PHOTO_HOMEPAGE_SIZE_PHONE_HEIGHT = 55;

	/** 项目详情图片： */
	public static final int PHOTO_DETAIL_SIZE_WIDTH = 695;

	public static final int PHOTO_DETAIL_SIZE_HEIGHT = 480;

	public static final int PHOTO_DETAIL_SIZE_PHONE_WIDTH = 306;

	public static final int PHOTO_DETAIL_SIZE_PHONE_HEIGHT = 154;

	/** 户型缩略图 */
	public static final int PHOTO_HOUSE_TYPE_WIDTH = 194;

	public static final int PHOTO_HOUSE_TYPE_HEIGHT = 194;

	/** 项目悬赏图片： */
	public static final int PHOTO_OFFER_SIZE_WIDTH = 747;

	public static final int PHOTO_OFFER_SIZE_HEIGHT = 264;

	public static final int PHOTO_OFFER_SIZE_PHONE_WIDTH = 320;

	public static final int PHOTO_OFFER_SIZE_PHONE_HEIGHT = 160;

	public static final int PHOTO_PAD_OFFER_SIZE_WIDTH = 670;

	public static final int PHOTO_PAD_OFFER_SIZE_HEIGHT = 344;

	/** 用户头像 */
	public static final int PHOTO_USER_PHOTO_WIDTH = 128;

	public static final int PHOTO_USER_PHOTO_HEIGHT = 128;

	/** 变更记录提示信息 */
	public static final String ORDER_STATE_RECORD_VISIT = "该客户已看房！";

	public static final String ORDER_STATE_RECORD_ORDER = "该客户已订房！";

	public static final String ORDER_STATE_RECORD_SIGN = "该客户已签约！";

	public static final String ORDER_STATE_RECORD_PAY = "该客户已付款！";

	public static final String ORDER_STATE_RECORD_CAN_MAID = "该客户已可结佣！";

	public static final String ORDER_STATE_RECORD_END = "该客户已完成！";

	public static final String ORDER_STATE_RECORD_CANCEL = "该客户已失效！";

	/**
	 * 项目列表类型定义
	 */
	public static final int PROJECJT_REWARDING = 1;// 悬赏项目

	public static final int PROJEECT_FOCUSING = 2;// 推荐项目

	public static final int PROJECT_FAVORITE = 3;// 关注项目

	/**
	 * APP接口请求类型
	 */
	public static final String REQUESTTYPE_PHONE = "phone";

	public static final String REQUESTTYPE_PAD = "pad";
}