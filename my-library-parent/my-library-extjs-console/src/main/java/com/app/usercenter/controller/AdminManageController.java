package com.app.usercenter.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.exception.ErrorMessageException;
import com.app.usercenter.domain.SysManager;
import com.app.usercenter.domain.SysRole;
import com.app.usercenter.domain.SysUserRole;
import com.app.usercenter.service.SysManagerService;
import com.app.usercenter.service.SysRoleService;
import com.app.util.ExtUtil;
import com.sys.common.core.Wrapper;
import com.sys.core.PasswordAuthenticator;
import com.sys.ext.PageExtNative;

/**
 * 
 * 
 * <p>
 * Title: AdminManageController.java
 * </p>
 * <p>
 * Description:后台管理员管理控制器
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
 * @date 2012-4-13上午9:29:13
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/console/data/usercenter/adminManage")
public class AdminManageController {
    @Resource
    private SysManagerService sysManagerService;

    @Resource
    private SysRoleService sysRoleService;
    

    /**
     * 页面初始化方法，根据type的不同转向不同的页面 主要是做页面的转向工作
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/adminManagement")
    public ModelAndView adminManagement(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String type = ServletRequestUtils.getStringParameter(request, "type");
	Long userId = ServletRequestUtils.getLongParameter(request, "userId");
	if (type != null && type.equals("editManager") && userId != null) {
	    Map<String, List> resultMap = sysManagerService.getManagerInfoById(userId);
	    List<SysUserRole> managerRoles = (List<SysUserRole>) resultMap.get("managerRoles");
	    Map<Long, String> map = new HashMap<Long, String>();
	    for (int i = 0; i < managerRoles.size(); i++) {
		map.put(managerRoles.get(i).getRole().getSrolId(), "role");
	    }
	    List<SysRole> roles = sysRoleService.listAll();
	    StringBuilder sb = new StringBuilder();
	    // 构造Ext checkbox
	    for (int i = 0; i < roles.size(); i++) {
		SysRole s = (SysRole) roles.get(i);
		sb.append("{ boxLabel: '").append(s.getSrolName()).append("'").append(",name: 'srolIds', id: '").append(s.getSrolId())
			.append("'").append(",inputValue: '").append(s.getSrolId()).append("'").append(",xtype: 'checkbox' ");
		if (map.containsKey(s.getSrolId()))
		    sb.append(",checked: true ");
		sb.append("},");
	    }
	    sb.deleteCharAt(sb.length() - 1);
	    request.setAttribute("resultMap", resultMap);
	    request.setAttribute("result", sb.toString());
	}
	if (type != null && type.equals("addManager")) {
	    makeChckBoxes(request, response, null);
	}
	return new ModelAndView("/usercenter/" + (request.getParameter("type") == null ? "adminManagement" : request.getParameter("type")));
    }

    /**
     * 
     * @Title: makeChckBoxes
     * @Description: 构造页面上角色组的checkbox显示字符串
     * @param request
     * @param response
     * @param page
     * @return
     * @return List
     * @throws
     * @author: YangJunping
     * @date 2012-4-13上午9:35:27
     */
    private List makeChckBoxes(HttpServletRequest request, HttpServletResponse response, PageExtNative page) {
	Wrapper wrapper = sysRoleService.getAllRoles(page);
	List list = (List) wrapper.getResult();
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < list.size(); i++) {
	    Object[] j = (Object[]) list.get(i);
	    sb.append("{ boxLabel: '").append(j[1]).append("'").append(",name: 'srolIds', id: '").append(j[0]).append("'")
		    .append(",inputValue: '").append(j[0]).append("'").append(",xtype: 'checkbox' },");
	}
	sb.deleteCharAt(sb.length() - 1);
	request.setAttribute("result", sb.toString());
	return list;
    }

    /**
     * 根据条件查询所有的管理员并分页
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/initAllManagers")
    public ModelAndView initAllManagers(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	String confition = ServletRequestUtils.getStringParameter(request, "condition");
	Wrapper wrapper = this.sysManagerService.getAllManagersBySearchCondition(confition, page);
	List list = (List) wrapper.getResult();
	String result = ExtUtil.createJsonTable(list, new String[] { "smanId", "smanName", "smanLoginName", "smanEmail", "smanTel",
		"smanRemark", "smanIsStop", "createTime","loginedTime","loginedCount","handledOrders","handlingOrders","roles" }, wrapper.getMessage());
	response.getWriter().write(result);
	response.getWriter().flush();
	return null;
    }

    /**
     * 
     * @Title: addManager
     * @Description: 添加管理员
     * @param request
     * @param response
     * @param page
     * @throws Exception
     * @author: YangJunping
     * @date 2012-4-11上午9:48:54
     */
    @RequestMapping(value = "/addManager")
    public void addManager(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	SysManager manager = new SysManager();
	constructManagerByRequest(request, manager);
	SysRole[] roles = getSysRoles(request, "srolIds");
	String msg = "";
	try {
	    sysManagerService.saveManager(manager, roles);
	    msg = "{success: true, msg: '添加成功!!!'}";
	} catch (ErrorMessageException e) {
	    msg = "{success: false, msg:'" + e.getMessage() + "'}";
	}
	response.getWriter().write(msg);
	response.getWriter().flush();
    }

    // 填充sysManager各个属性
    private SysManager constructManagerByRequest(HttpServletRequest request, SysManager manager) throws Exception {
	manager.setSmanEmail(ServletRequestUtils.getStringParameter(request, "smanEmail"));
	manager.setSmanLoginName(ServletRequestUtils.getStringParameter(request, "smanLoginName"));
	manager.setSmanName(ServletRequestUtils.getStringParameter(request, "smanName"));
	manager.setSmanTel(ServletRequestUtils.getStringParameter(request, "smanTel"));
	manager.setSmanRemark(ServletRequestUtils.getStringParameter(request, "smanRemark"));
	manager.setSmanIsStop(false);
	manager.setHandledOrders(0);
	manager.setHandlingOrders(0);
	manager.setLoginedCount(0);
	Date date = new Date();
	manager.setCreateTime(date);
	manager.setUpdateTime(date);
	if (ServletRequestUtils.getStringParameter(request, "smanPwd1") != null) {
	    manager.setSmanPwd(PasswordAuthenticator.createPassword(ServletRequestUtils.getStringParameter(request, "smanPwd1")));
	}
	return manager;
    }

    // 根据客户端获取权限组
    private SysRole[] getSysRoles(HttpServletRequest request, String param) {
	SysRole[] roles = null;
	String[] ids = request.getParameterValues(param);
	if (ids != null) {
	    roles = new SysRole[ids.length];
	    for (int i = 0; i < ids.length; i++) {
		SysRole role = (SysRole) sysRoleService.findById(Long.parseLong(ids[i]));
		roles[i] = role;
	    }
	}
	return roles;
    }

    /**
     * 更新管理员基本信息
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyManagerInfo")
    public void modifyManagerInfo(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	SysManager manager = (SysManager) (((List<SysManager>) sysManagerService.getManagerInfoById(
		ServletRequestUtils.getLongParameter(request, "smanId")).get("manager")).get(0));
	constructManagerByRequest(request, manager);
	SysRole[] roles = getSysRoles(request, "srolIds");
	String msg = "";
	try {
	    this.sysManagerService.updateManagerInfo(manager, roles);
	    msg = "{success: true, msg: '更新成功!!!'}";
	} catch (ErrorMessageException e) {
	    msg = "{success: false, msg:'" + e.getMessage() + "'}";
	}
	response.getWriter().write(msg);
	response.getWriter().flush();
    }

    /**
     * 停用/启用管理员
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyAdminStatus")
    public void modifyAdminStatus(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	this.sysManagerService.updateSysManagerStatus(ServletRequestUtils.getLongParameter(request, "userId"));
	response.getWriter().write("修改管理员状态成功!!!");
	response.getWriter().flush();
    }

    /**
     * 修改管理员密码
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyManagerPwd")
    public ModelAndView modifyManagerPwd(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	SysManager manager = this.sysManagerService.findById(ServletRequestUtils.getLongParameter(request, "userId"));
	manager.setSmanPwd(PasswordAuthenticator.createPassword(ServletRequestUtils.getStringParameter(request, "newPassword", "")));
	manager.setUpdateTime(new Date());
	this.sysManagerService.update(manager);
	response.getWriter().write("{success: true, msg: '密码更改成功!!!'}");
	response.getWriter().flush();
	return null;
    }

}
