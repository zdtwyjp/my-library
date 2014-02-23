package com.app.usercenter.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.exception.ErrorMessageException;
import com.app.usercenter.domain.SysRole;
import com.app.usercenter.service.SysRoleService;
import com.app.util.ExtUtil;
import com.sys.common.core.Wrapper;
import com.sys.ext.PageExtNative;

@Controller
@RequestMapping(value = "/console/data/usercenter/roleManage")
public class RoleManageController {

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
    @RequestMapping(value = "/roleManagement")
    public ModelAndView roleManagement(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String type = request.getParameter("type");
	return new ModelAndView("/usercenter/" + (type == null ? "roleManagement" : type));
    }

    /**
     * 根据条件查询所有的角色并分页
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/initAllRoles")
    public ModelAndView initAllRoles(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	String condition = ServletRequestUtils.getStringParameter(request, "condition");
	Wrapper wrapper = this.sysRoleService.getAllRolesBySearchCondition(condition, page);
	@SuppressWarnings("rawtypes")
	List list = (List) wrapper.getResult();
	String result = ExtUtil.createJsonTable(list, new String[] { "srolId", "srolName", "srolCode", "srolDesc", "srolIsSysRole" },
		wrapper.getMessage());
	String s = result.replaceAll("\"srolIsSysRole\":true", "\"srolIsSysRole\":\"是\"").replaceAll("\"srolIsSysRole\":false",
		"\"srolIsSysRole\":\"不是\"");
	response.getWriter().write(s);
	response.getWriter().flush();
	return null;
    }

    /**
     * 添加新的角色
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addRole")
    public ModelAndView addRole(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	SysRole role = new SysRole();
	role.setSrolDesc(request.getParameter("srolDesc"));
	role.setSrolName(request.getParameter("srolName"));
	role.setSrolCode(request.getParameter("srolCode"));
	role.setSrolIsSysRole(false);
	role.setUpdateTime(new Date());
	String msg = "";
	try {
	    this.sysRoleService.save(role);
	    msg = "{success: true, msg:'增加角色成功!!'}";
	} catch (ErrorMessageException e) {
	    msg = "{success: false, msg: '" + e.getMessage() + "'}";
	}
	response.getWriter().write(msg);
	response.getWriter().flush();
	return null;
    }

    /**
     * 更新角色信息
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyRole")
    public ModelAndView modifyRole(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	Long roleId = Long.parseLong(request.getParameter("roleId"));
	SysRole role = this.sysRoleService.findById(roleId);
	role.setSrolDesc(request.getParameter("srolDesc"));
	role.setSrolName(request.getParameter("srolName"));
	role.setSrolCode(request.getParameter("srolCode"));
	role.setUpdateTime(new Date());
	String msg = "";
	try {
	    this.sysRoleService.update(role);
	    msg = "{success: true, msg: '更新角色成功!!'}";
	} catch (ErrorMessageException e) {
	    msg = "{success: false, msg: '" + e.getMessage() + "'}";
	}
	response.getWriter().write(msg);
	response.getWriter().flush();
	return null;
    }

    /**
     * 删除角色
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteRole")
    public ModelAndView deleteRole(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	Long roleId = Long.parseLong(request.getParameter("roleId"));
	SysRole role = this.sysRoleService.findById(roleId);
	String msg = "";
	try {
	    this.sysRoleService.delete(role);
	    msg = "删除角色成功";
	} catch (ErrorMessageException e) {
	    msg = e.getMessage();
	}
	response.getWriter().write(msg);
	response.getWriter().flush();
	return null;
    }
}