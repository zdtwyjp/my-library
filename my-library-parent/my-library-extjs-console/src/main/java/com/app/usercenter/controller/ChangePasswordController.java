package com.app.usercenter.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.usercenter.domain.SysManager;
import com.app.usercenter.service.SysManagerService;
import com.sys.core.CurrentUserSession;
import com.sys.core.PasswordAuthenticator;

/**
 * 
 * 
 * <p>
 * Title: ChangePasswordController.java
 * </p>
 * <p>
 * Description:密码修改控制器
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
 * @date 2012-4-12上午11:04:12
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/console/data/changePassword")
public class ChangePasswordController {

    @Resource
    private SysManagerService sysManagerService;

    @RequestMapping(value = "/initializePage")
    public ModelAndView initializePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
	return new ModelAndView("usercenter/changepwd");
    }

    @RequestMapping(value = "/changepwd")
    public void changepwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	String pwdStart = ServletRequestUtils.getStringParameter(request, "pwdStart", "");
	String pwdNew = ServletRequestUtils.getStringParameter(request, "pwdNew", "");
	CurrentUserSession userSession = (CurrentUserSession) CurrentUserSession.findUserSession(request);
	SysManager sysUser = userSession.getCurrentUser();
	try {
	    if (!PasswordAuthenticator.authenticatePassword(pwdStart, sysUser.getSmanPwd())) {
		sysUser = null;
	    }
	} catch (Exception e) {
	    response.getWriter().write("{failure:true,errors:'密码验证失败'}");
	    response.getWriter().flush();
	    sysUser = null;
	    return;
	}
	if (sysUser == null) {
	    response.getWriter().write("{failure:true,errors:'密码验证失败'}");
	    response.getWriter().flush();
	    return;
	}
	sysUser.setSmanPwd(PasswordAuthenticator.createPassword(pwdNew));
	sysManagerService.saveOrUpdate(sysUser);
	response.getWriter().write("{success:true,msg:'密码修改成功'}");
	response.getWriter().flush();
    }

}
