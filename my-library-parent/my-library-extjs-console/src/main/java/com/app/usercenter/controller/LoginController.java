package com.app.usercenter.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.usercenter.domain.SysManager;
import com.app.usercenter.service.SysCatalogService;
import com.app.usercenter.service.SysManagerService;
import com.app.usercenter.service.SysRoleService;
import com.sys.core.CurrentUserSession;

/**
 * 
 * 
 * <p>
 * Title: LoginController.java
 * </p>
 * <p>
 * Description:登录验证
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
 * @date 2012-4-13上午9:45:09
 * @version 1.0
 */
@Controller
public class LoginController{
	
	@Resource
	private SysManagerService sysManagerService;
	
	@Resource
	private SysRoleService sysRoleService;
	
	@Resource
	private SysCatalogService sysCatalogService;

	/**
	 * 管理员登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/managerLogin")
	public ModelAndView managerLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		CurrentUserSession userSession = (CurrentUserSession)CurrentUserSession.findUserSession(request);
		userSession.setCurrentUser(null);
		String loginName = ServletRequestUtils.getStringParameter(request, "userName");
		String pwd = ServletRequestUtils.getStringParameter(request, "pwd");
		SysManager sysManager = sysManagerService.managerLogin(loginName, pwd, request.getRemoteHost());
//		Enumeration<String> paramNamesEnum = request.getParameterNames();
//		String paramName = null;
//		String paramValue = null;
//		int i = 1;
//		while(paramNamesEnum.hasMoreElements()) {
//			paramName = paramNamesEnum.nextElement();
//			paramValue = request.getParameter(paramName);
//			i++;
//		}
		if(sysManager == null){
		    return new ModelAndView("usercenter/err");
		}
		int loginCount = sysManager.getLoginedCount();
		if(loginCount > 0){
		    loginCount++;
		    sysManager.setLoginedCount(loginCount);
		}else{
		    sysManager.setLoginedCount(1);
		}
		sysManager.setLoginedTime(new Date());
		this.sysManagerService.update(sysManager);
		
		userSession.setCurrentUser(sysManager);
		request.getSession().setAttribute("userSession", userSession);
		userSession.refreshRoleFunctions(sysRoleService, sysCatalogService);
		response.sendRedirect("console/index.htm");
		return null;
	}
}