/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.ipparse;

/**
 * 
 * <p>
 * Title: IpSeekerUtil.java
 * </p>
 * <p>
 * Description:TODO
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
 * @date 2012-4-18下午5:38:29
 * @version 1.0
 */
public class IpSeekerUtil {

    private static IPSeeker ipSeeker = null;

    static {
	String path = IpSeekerUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	if (path.indexOf("WEB-INF") > 0) {
	    path = path.substring(1, path.indexOf("/WEB-INF/"));
	}
	path += "/WEB-INF/classes/config/data";
	// 指定纯真数据库的文件名，所在文件夹
	ipSeeker = new IPSeeker("QQWry.Dat", path);
    }

    /**
     * 
     * @Title: getCountry
     * @Description: 通过IP获取城市名称
     * @param ip
     * @return
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-4-18下午5:50:41
     */
    public static String getCountry(String ip) {
	if (ip == null || ip.trim().length() < 1) {
	    throw new IllegalArgumentException("IP参数不能为空！");
	}
	return ipSeeker.getIPLocation(ip).getCountry();
    }

}
