package com.app.core;

import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.usercenter.domain.SysManager;
import com.sys.core.CurrentUserSession;

public class ServiceUtil {
    /**
     * 
     * @Title: getCurrentUser
     * @Description: 获得当前登录的用户对象
     * @param request
     * @return
     * @return SysManager
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午3:22:16
     */
    public static SysManager getCurrentUser(HttpServletRequest request) {
	SysManager user = null;
	CurrentUserSession userSession = CurrentUserSession.findUserSession(request);
	if (userSession != null) {
	    user = userSession.getCurrentUser();
	}
	return user;
    }

    
    

    /**
     * 
     * @Title: getCurrentUserName
     * @Description: 获取当前登录用户名
     * @param request
     * @return
     * @return String
     * @throws
     * @author: YangJunping
     * @date 2012-4-16下午2:40:33
     */
    public static String getCurrentUserName(HttpServletRequest request) {
	SysManager user = null;
	CurrentUserSession userSession = CurrentUserSession.findUserSession(request);
	if (userSession != null) {
	    user = userSession.getCurrentUser();
	    return user.getSmanName();
	}
	return "";
    }

    /**
     * 
     * @Title: deleteCookie
     * @Description: 删除Cookie
     * @param cookieName
     * @param cookiePath
     * @param response
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午3:33:18
     */
    public static void deleteCookie(String cookieName, String cookiePath, HttpServletResponse response) {
	Cookie cookie = new Cookie(cookieName, "");
	cookie.setPath(cookiePath);
	cookie.setMaxAge(-1);
	response.addCookie(cookie);
    }
    /**
     * 
    * @Title: generateSwiftNumber
    * @Description: TODO
    * @param prefix
    * @return String
    * @throws
    * @author: NieBin
    * @date 2012-5-15上午09:59:05
     */
    public static String generateSwiftNumber(String prefix){
		StringBuffer result = new StringBuffer(prefix);
		Random random = new Random();
		for(int i = 0 ;i < 5 ; i++){
			result.append(random.nextInt(10));
		}
		return result.toString();
    }

}