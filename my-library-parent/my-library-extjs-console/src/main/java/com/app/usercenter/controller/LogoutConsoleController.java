package com.app.usercenter.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.core.ServiceUtil;
import com.app.usercenter.domain.SysManager;
import com.sys.core.CurrentUserSession;

/**
 * 
 * 
 * <p>
 * Title: LogoutConsoleController.java
 * </p>
 * <p>
 * Description:退出登录
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
 * @date 2012-4-12上午10:54:27
 * @version 1.0
 */
@Controller
public class LogoutConsoleController {

    @RequestMapping(value = "/console/logout.htm")
    public ModelAndView managerLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
	// 用户注销,清空session
	CurrentUserSession userSession = CurrentUserSession.findUserSession(request);
	// 删除管理员
	SysManager userinfo = userSession.getCurrentUser();
	if (userSession != null) {
	    userSession.setCurrentUser(null);
	}
	request.getSession().setAttribute("userSession", null);
	String cookiePath = request.getContextPath() + "/lfd/";
	Cookie cookie = new Cookie("activeUserID", "");
	cookie.setMaxAge(0);
	cookie.setPath(cookiePath);
	response.addCookie(cookie);

	cookie = new Cookie("activeSessionID", "");
	cookie.setPath(cookiePath);
	cookie.setMaxAge(0);
	response.addCookie(cookie);

	// 删除Cookie
	ServiceUtil.deleteCookie("userName", "/", response);
	ServiceUtil.deleteCookie("userId", "/", response);

	response.sendRedirect(request.getContextPath() + "/console/index.htm");

	return null;
    }

}
