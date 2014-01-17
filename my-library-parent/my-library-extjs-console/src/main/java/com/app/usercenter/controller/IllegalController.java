package com.app.usercenter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 非法访问跳转控制器
 * 
 * @author YangJunping
 * @date 2012-4-13下午1:22:50
 */
@Controller
public class IllegalController {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/illegal")
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
	StringBuffer message = new StringBuffer("无效的非法操作，<br \\>请点击&nbsp;<a href=\"");
	message.append(request.getContextPath());
	message.append("/console/index.htm\">这里</a>&nbsp;返回主页。");
	Map model = new HashMap();
	model.put("message", message.toString());
	return new ModelAndView("main/error", model);
    }

}