package com.app.usercenter.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.usercenter.domain.SysCatalog;
import com.app.usercenter.domain.SysFunction;
import com.app.usercenter.domain.SysRole;
import com.app.usercenter.service.SysCatalogService;
import com.app.usercenter.service.SysFunctionService;
import com.app.usercenter.service.SysRoleFunctionService;
import com.app.usercenter.service.SysRoleService;
import com.app.util.ExtUtil;
import com.sys.common.core.Wrapper;
import com.sys.ext.PageExtNative;

@Controller
@RequestMapping(value = "/console/data/usercenter/roleFunManage")
public class RoleFunctionManageController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysCatalogService sysCatalogService;

    @Resource
    private SysFunctionService sysFunctionService;

    @Resource
    private SysRoleFunctionService sysRoleFunctionService;

    @RequestMapping(value = "/initPage")
    public ModelAndView initPage(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	String roleId = request.getParameter("roleId");
	request.setAttribute("roleId", roleId);
	SysRole sr = this.sysRoleService.findById(Long.parseLong(roleId));
	request.setAttribute("roleName", sr.getSrolName());
	return new ModelAndView("usercenter/roleFunctionManage");
    }
    
    /**
     * 
    * @Title: createCatalogTree
    * @Description: 获取目录树，不含功能点
    * @param request
    * @param response
    * @param page
    * @return
    * @throws Exception
    * @return ModelAndView
    * @throws
    * @author: YangJunping
    * @date 2012-6-7下午3:08:23
     */
    @RequestMapping(value = "/createCatalogTree")
    public ModelAndView createCatalogTree(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	StringBuilder sb = new StringBuilder();
	String id = request.getParameter("nodeId");
	List<SysCatalog> catagories = sysCatalogService.listAll();
	java.util.Collections.sort(catagories);
	sb.append("[");
	if (id == null || id.equals("") || id.equals("0")) {
	    for (SysCatalog sc : catagories) {
		if (sc.getParentCatalog() == null)
		    sb.append(createTreeJsonStr(sc.getScatName(), sc.getScatId(), false));
	    }
	}
	if (id != null && !id.equals("")) {
	    List<SysCatalog> childrenCatalogs = this.sysCatalogService.getSysCatalogByParentCatalogId(Long.parseLong(id));
	    if (childrenCatalogs != null && childrenCatalogs.size() > 0) {
		for (SysCatalog sc : childrenCatalogs) {
		    sb.append(createTreeJsonStr(sc.getScatName(), sc.getScatId(), false));
		}
	    }
	}
	sb.deleteCharAt(sb.length() - 1);
	sb.append("]");
	request.setAttribute("id", id);
	response.getWriter().write(sb.toString());
	response.getWriter().flush();
	System.out.println(sb.toString());
	return null;
    }

    /**
     * 构造树
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createTree")
    public ModelAndView createTree(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	StringBuilder sb = new StringBuilder();
	String id = request.getParameter("nodeId");
	List<SysCatalog> catagories = sysCatalogService.listAll();
	java.util.Collections.sort(catagories);
	sb.append("[");
	if (id == null || id.equals("") || id.equals("0")) {
	    for (SysCatalog sc : catagories) {
		if (sc.getParentCatalog() == null)
		    sb.append(createTreeJsonStr(sc.getScatName(), sc.getScatId(), false));
	    }
	}
	if (id != null && !id.equals("")) {
	    List<SysCatalog> childrenCatalogs = this.sysCatalogService.getSysCatalogByParentCatalogId(Long.parseLong(id));
	    if (childrenCatalogs != null && childrenCatalogs.size() > 0) {
		for (SysCatalog sc : childrenCatalogs) {
		    sb.append(createTreeJsonStr(sc.getScatName(), sc.getScatId(), false));
		}
	    }
	    List<SysFunction> funcs = this.sysFunctionService.getSysFunctionByParentCatalogId(Long.parseLong(id));
	    if (funcs != null && funcs.size() > 0) {
		for (SysFunction sf : funcs) {
		    if(sf.getSfunIsHidden()){
			sb.append(createTreeJsonStr(sf.getSfunName(), sf.getSfunId(), true));
		    }
		}
	    }
	}
	sb.deleteCharAt(sb.length() - 1);
	sb.append("]");
	request.setAttribute("id", id);
	response.getWriter().write(sb.toString());
	response.getWriter().flush();
	System.out.println(sb.toString());
	return null;
    }

    private String createTreeJsonStr(String text, Long id, boolean isLeaf) {
	StringBuilder sb = new StringBuilder();
	sb.append("{ text: '").append(text).append("', leaf: ").append(isLeaf).append(", id: ").append(id).append("},");
	return sb.toString();
    }

    /**
     * 获得对应的catalog下面的functions
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllFunctionsByCatalog")
    public ModelAndView getAllFunctionsByCatalog(HttpServletRequest request, HttpServletResponse response) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	String id = request.getParameter("id");
	Wrapper wrapper = this.sysFunctionService.getAllFunctionsByCataLogId(Long.parseLong(id), null);
	@SuppressWarnings("rawtypes")
	List list = (List) wrapper.getResult();
	String result = ExtUtil.createJsonTable(list, new String[] { "sfunId", "sfunName", "sfunDesc", "sfunUrl", "sfunIndex", "sfunCode",
		"sfunIsHidden" }, wrapper.getMessage());
	response.getWriter().write(result);
	response.getWriter().flush();
	return null;
    }

    /**
     * 获得对应的角色下面的functions
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllFunctionsByRole")
    public ModelAndView getAllFunctionsByRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	String id = request.getParameter("roleId");
	Wrapper wrapper = this.sysFunctionService.getAllFunctionsByRoleId(Long.parseLong(id), null);
	@SuppressWarnings("rawtypes")
	List list = (List) wrapper.getResult();
	String result = ExtUtil.createJsonTable(list, new String[] { "sfunId", "sfunName", "sfunDesc", "sfunUrl", "sfunIndex", "sfunCode",
		"sfunIsHidden" }, wrapper.getMessage());
	response.getWriter().write(result);
	response.getWriter().flush();
	return null;
    }

    /**
     * 给角色增加权限
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addFunctionsToRole")
    public ModelAndView addFunctionsToRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	String roleId = request.getParameter("roleId");
	String[] functionsId = request.getParameter("functionsId").split(",");
	Long[] funcsId = null;
	if (roleId != null && !roleId.equals("") && functionsId != null && functionsId.length > 0) {
	    funcsId = new Long[functionsId.length];
	    for (int i = 0; i < functionsId.length; i++) {
		funcsId[i] = Long.parseLong(functionsId[i]);
	    }
	}
	this.sysRoleFunctionService.saveFunctionsToRole(funcsId, Long.parseLong(roleId));
	response.getWriter().write("添加权限成功");
	response.getWriter().flush();
	return null;
    }

    /**
     * 给角色删除权限
     * 
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteFunctionsFromRole")
    public ModelAndView deleteFunctionsFromRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	String roleId = request.getParameter("roleId");
	String[] functionsId = request.getParameter("functionsId").split(",");
	Long[] funcsId = null;
	if (roleId != null && !roleId.equals("") && functionsId != null && functionsId.length > 0) {
	    funcsId = new Long[functionsId.length];
	    for (int i = 0; i < functionsId.length; i++) {
		funcsId[i] = Long.parseLong(functionsId[i]);
	    }
	}
	this.sysRoleFunctionService.deleteFunctionsFromRole(funcsId, Long.parseLong(roleId));
	response.getWriter().write("删除权限成功");
	response.getWriter().flush();
	return null;
    }
}