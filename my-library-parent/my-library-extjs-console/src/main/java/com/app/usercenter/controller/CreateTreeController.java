package com.app.usercenter.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.core.ServiceUtil;
import com.app.usercenter.domain.SysManager;
import com.sys.core.CurrentUserSession;

@Controller
public class CreateTreeController {
    private static Logger logger = Logger.getLogger(CreateTreeController.class);

    @RequestMapping(value = "/console/createTree")
    public void childCategory(HttpServletRequest request, HttpServletResponse response) {
	SysManager sysUserinfo = ServiceUtil.getCurrentUser(request);
	if (sysUserinfo != null) {
	    long catalogId = ServletRequestUtils.getLongParameter(request, "id", 0);
	    CurrentUserSession userSession = CurrentUserSession.findUserSession(request);
	    String json = userSession.getJsonFunctionTree(catalogId);

	    if (logger.isDebugEnabled())
		logger.debug(json);

	    response.setContentType("txt/plain;charset=utf-8");
	    PrintWriter writer = null;
	    try {
		writer = response.getWriter();
		writer.write(json);
		writer.flush();
		writer.close();
	    } catch (IOException e) {
		logger.error("获得输出对象失败！", e);
	    }
	}
    }
}
