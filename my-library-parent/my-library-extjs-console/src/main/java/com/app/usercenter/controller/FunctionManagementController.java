package com.app.usercenter.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.exception.ErrorMessageException;
import com.app.usercenter.domain.SysCatalog;
import com.app.usercenter.domain.SysFunction;
import com.app.usercenter.service.SysCatalogService;
import com.app.usercenter.service.SysFunctionService;
import com.app.util.ExtUtil;
import com.app.util.StringUtil;
import com.sys.common.core.Wrapper;
import com.sys.ext.PageExtNative;

/**
 * 
 * 
 * <p>
 * Title: AdminManageController.java
 * </p>
 * <p>
 * Description:功能点管理控制器
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
 * @date 2012-6-5下午4:35:58
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/console/data/usercenter/adminManage")
public class FunctionManagementController {

    @Resource
    private SysCatalogService sysCatalogService;

    @Resource
    private SysFunctionService sysFunctionService;

    /**
     * 
     * @Title: indexFunctionManagement
     * @Description: 功能点
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @return ModelAndView
     * @throws
     * @author: YangJunping
     * @date 2012-6-5下午4:37:08
     */
    @RequestMapping(value = "/indexFunctionManagement")
    public ModelAndView indexFunctionManagement(HttpServletRequest request, HttpServletResponse response) throws Exception {
	return new ModelAndView("/usercenter/indexFunctionManagement");
    }

    /**
     * 
     * @Title: initFunctionManagement
     * @Description: 初始化功能目录
     * @param request
     * @param response
     * @param page
     * @throws Exception
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-6-5下午4:52:44
     */
    @RequestMapping(value = "/initFunctionManagement")
    public void initFunctionManagement(HttpServletRequest request, HttpServletResponse response, PageExtNative page) throws Exception {
	response.setContentType("text/html; charset=UTF-8");
	String searchCondition = ServletRequestUtils.getStringParameter(request, "searchCondition", "");
	Map<String, Object> conditionMap = new HashMap<String, Object>();
	if (StringUtil.isNotNull(searchCondition)) {
	    conditionMap.put("searchCondition", searchCondition);
	}
	Wrapper wrapper = this.sysCatalogService.getSysCatalogsBySearchCondition(conditionMap, page);
	List<SysCatalog> list = (List<SysCatalog>) wrapper.getResult();
	List<Object[]> tempList = new ArrayList<Object[]>();
	for (int i = 0; i < list.size(); i++) {
	    SysCatalog sysCatalog = list.get(i);
	    Object[] obj = new Object[5];
	    obj[0] = sysCatalog.getScatId();
	    obj[1] = sysCatalog.getScatName();
	    obj[2] = sysCatalog.getScatDesc();
	    obj[3] = sysCatalog.getScatCode();
	    if (sysCatalog.getParentCatalog() != null) {
		obj[4] = sysCatalog.getParentCatalog().getScatName();
	    } else {
		obj[4] = "";
	    }
	    tempList.add(obj);
	}
	String result = ExtUtil.createJsonTable(tempList, new String[] { "scatId", "scatName", "scatDesc", "scatCode", "parentCatalog" },
		wrapper.getMessage());
	response.getWriter().write(result);
	response.getWriter().flush();
    }

    /**
     * 
     * @Title: initCatalogCombox
     * @Description: 初始化目录下拉框
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @return ModelAndView
     * @throws
     * @author: YangJunping
     * @date 2012-6-6下午4:17:14
     */
    @RequestMapping(value = "/initCatalogCombox")
    public void initCatalogCombox(HttpServletRequest request, HttpServletResponse response) throws Exception {
	List<SysCatalog> list = this.sysCatalogService.getSysCatalog();
	StringBuffer result = new StringBuffer("[['-1','一级目录'],");
	for (SysCatalog cat : list) {
	    if (cat.getParentCatalog() == null) {
		result.append("['").append(cat.getScatId()).append("','").append(cat.getScatName()).append("']");
		result.append(",");
		for (SysCatalog sysCatalog : list) {
		    if (sysCatalog.getParentCatalog() != null && cat.getScatId().equals(sysCatalog.getParentCatalog().getScatId())) {
			result.append("['").append(sysCatalog.getScatId()).append("','")
				.append(cat.getScatName() + "_" + sysCatalog.getScatName()).append("']");
			result.append(",");
		    }
		}
	    }
	}
	String temp = result.toString();
	temp = temp.substring(0, temp.length() - 1);
	temp += "]";
	response.setContentType("text/plain");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(temp);
    }

    @RequestMapping(value = "/initCatalogJsonCombox")
    public void initCatalogJsonCombox(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String root = "/0";
	List<SysCatalog> list = this.sysCatalogService.getSysCatalog();
	StringBuffer result = new StringBuffer("[{scatId:'-1',scatName:'一级目录',path:'" + root + "'},");
	for (SysCatalog cat : list) {
	    if (cat.getParentCatalog() == null) {
		String tempPath = root + "/" + cat.getScatId();
		result.append("{scatId:'").append(cat.getScatId()).append("',scatName:'").append(cat.getScatName()).append("',path:'")
			.append(tempPath).append("'}");
		result.append(",");
		result.append(getJsonString(list, cat, null, tempPath));
	    }
	}
	String temp = result.toString();
	temp = temp.substring(0, temp.length() - 1);
	temp += "]";
	response.setContentType("text/plain");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(temp);
    }

    private String getJsonString(List<SysCatalog> list, SysCatalog cat, String parentCatName, String parentPath) {
	StringBuffer result = new StringBuffer();
	for (SysCatalog sysCatalog : list) {
	    if (sysCatalog.getParentCatalog() != null && cat.getScatId().equals(sysCatalog.getParentCatalog().getScatId())) {
		if (!StringUtil.isNotNull(parentCatName)) {
		    parentCatName = cat.getScatName();
		}
		String tempName = parentCatName + "_" + sysCatalog.getScatName();
		String tempPath = parentPath + "/" + sysCatalog.getScatId();
		result.append("{scatId:'").append(sysCatalog.getScatId()).append("',scatName:'").append(tempName).append("',path:'")
			.append(tempPath).append("'}");
		result.append(",");
		result.append(getJsonString(list, sysCatalog, tempName, tempPath));
	    }
	}
	return result.toString();
    }

    /**
     * 
     * @Title: initCatalog
     * @Description: 初始化目录
     * @param request
     * @param response
     * @throws Exception
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-6-8下午3:17:39
     */
    @RequestMapping(value = "/initCatalog")
    public void initCatalog(HttpServletRequest request, HttpServletResponse response) throws Exception {
	response.setContentType("text/plain;charset=utf-8");
	PrintWriter out = response.getWriter();
	JSONObject json = new JSONObject();
	try {

	    Long catId = ServletRequestUtils.getLongParameter(request, "catId", -1L);
	    SysCatalog cat = this.sysCatalogService.findById(catId);
	    if (cat == null) {
		throw new ErrorMessageException("未找到对应的目录！");
	    }
	    json.put("success", true);
	    json.put("name_Add", cat.getScatName());
	    json.put("desc_Add", cat.getScatDesc());
	    json.put("serialNumber_add", cat.getScatCode());
	    if (cat.getParentCatalog() != null) {
		json.put("parentCatalogId", cat.getParentCatalog().getScatId());
	    } else {
		json.put("parentCatalogId", "-1");
	    }
	} catch (ErrorMessageException e) {
	    e.printStackTrace();
	    json.put("success", false);
	    json.put("msg", e.getMessage());
	}
	out.write(json.toString());
	out.flush();
    }

    /**
     * 
     * @Title: saveCatalog
     * @Description: 保存目录
     * @param request
     * @param response
     * @throws Exception
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-6-7上午10:52:33
     */
    @RequestMapping(value = "/saveCatalog")
    public void saveCatalog(HttpServletRequest request, HttpServletResponse response) throws Exception {
	response.setContentType("text/plain;charset=utf-8");
	PrintWriter out = response.getWriter();
	try {
	    String name_Add = ServletRequestUtils.getStringParameter(request, "name_Add", "");
	    String desc_Add = ServletRequestUtils.getStringParameter(request, "desc_Add", "");
	    String serialNumber = ServletRequestUtils.getStringParameter(request, "serialNumber", "100");
	    Long parentCatalogId = ServletRequestUtils.getLongParameter(request, "parentCatalogId", -1L);
	    if (!StringUtil.isNotNull(name_Add))
		throw new ErrorMessageException("名称不能为空！");
	    if (name_Add.length() > 15)
		throw new ErrorMessageException("名称不能超过15个字符！");
	    if (desc_Add.trim().length() > 256)
		throw new ErrorMessageException("描述不能超过256个字符！");
	    String isEditing = ServletRequestUtils.getStringParameter(request, "isEditing", "false");
	    // 验证是否存在
	    SysCatalog tempCat = this.sysCatalogService.getCatalogByCondition(name_Add, parentCatalogId);

	    SysCatalog cat = null;
	    if ("false".equals(isEditing)) {
		cat = new SysCatalog();
		if (tempCat != null) {
		    throw new ErrorMessageException("该目录名称已经存在！");
		}
	    } else {
		Long selectCatalogId = ServletRequestUtils.getLongParameter(request, "selectCatalogId", -1L);
		cat = this.sysCatalogService.findById(selectCatalogId);
		if (cat == null) {
		    throw new ErrorMessageException("未找到对应的目录！");
		}
		if (tempCat != null && !tempCat.getScatId().equals(cat.getScatId())) {
		    throw new ErrorMessageException("该目录名称已经存在！");
		}
	    }
	    if (parentCatalogId > 0) {
		SysCatalog parentCat = this.sysCatalogService.findById(parentCatalogId);
		if (parentCat == null)
		    throw new ErrorMessageException("目录不存在！");
		cat.setParentCatalog(parentCat);
	    }

	    cat.setScatDesc(desc_Add);
	    cat.setScatName(name_Add);
	    cat.setScatCode(Short.valueOf(serialNumber));
	    this.sysCatalogService.saveOrUpdate(cat);
	    if ("false".equals(isEditing)) {
		out.write("{success:true,msg:'新增成功！'}");
	    } else {
		out.write("{success:true,msg:'编辑成功！'}");
	    }
	} catch (ErrorMessageException e) {
	    out.write("{success:false,msg:'" + e.getMessage() + "'}");
	}
	out.flush();
    }

    /**
     * 
     * @Title: delCatalog
     * @Description: 删除目录
     * @param request
     * @param response
     * @throws Exception
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-6-7上午10:53:32
     */
    @RequestMapping(value = "/delCatalog")
    public void delCatalog(HttpServletRequest request, HttpServletResponse response) throws Exception {
	response.setContentType("text/plain;charset=utf-8");
	PrintWriter out = response.getWriter();
	try {

	    Long selectCatalogId = ServletRequestUtils.getLongParameter(request, "selectCatalogId", -1L);
	    SysCatalog cat = this.sysCatalogService.findById(selectCatalogId);
	    if (cat == null) {
		throw new ErrorMessageException("未找到对应的目录！");
	    }
	    this.sysCatalogService.delete(cat);
	    out.write("{success:true,msg:'删除成功！'}");
	} catch (ErrorMessageException e) {
	    e.printStackTrace();
	    out.write("{success:false,msg:'" + e.getMessage() + "'}");
	}
	out.flush();
    }

    /**
     * 
     * @Title: saveFunction
     * @Description: 保存功能点
     * @param request
     * @param response
     * @throws Exception
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-6-11下午4:30:09
     */
    @RequestMapping(value = "/saveFunction")
    public void saveFunction(HttpServletRequest request, HttpServletResponse response) throws Exception {
	response.setContentType("text/plain;charset=utf-8");
	PrintWriter out = response.getWriter();
	try {
	    String funName_Add = ServletRequestUtils.getStringParameter(request, "funName_Add", "");
	    String funDes_Add = ServletRequestUtils.getStringParameter(request, "funDes_Add", "");
	    String funUrl_Add = ServletRequestUtils.getStringParameter(request, "funUrl_Add", "");
	    String funSerial_add = ServletRequestUtils.getStringParameter(request, "funSerial_add", "100");
	    String isHidden = ServletRequestUtils.getStringParameter(request, "isHidden", "1");
	    Long parentCatalogId = ServletRequestUtils.getLongParameter(request, "parentCatalogId", -1L);
	    String isFunEditing = ServletRequestUtils.getStringParameter(request, "isFunEditing", "false");
	    // 验证是否存在
	    SysFunction tempFun = this.sysFunctionService.getFunctionByCondition(funName_Add, parentCatalogId);

	    SysFunction fun = null;
	    if ("false".equals(isFunEditing)) {
		fun = new SysFunction();
		if (tempFun != null) {
		    throw new ErrorMessageException("该功能点已经存在！");
		}
	    } else {
		Long selectFunId = ServletRequestUtils.getLongParameter(request, "selectFunId", -1L);
		fun = this.sysFunctionService.findById(selectFunId);
		if (fun == null) {
		    throw new ErrorMessageException("未找到对应的功能点！");
		}
		if (tempFun != null && !tempFun.getSfunId().equals(fun.getSfunId())) {
		    throw new ErrorMessageException("该功能点已经存在！");
		}
	    }
	    SysCatalog parentCat = this.sysCatalogService.findById(parentCatalogId);
	    if (parentCat == null)
		throw new ErrorMessageException("目录不存在！");

	    fun.setParentCatalog(parentCat);
	    fun.setSfunName(funName_Add);
	    fun.setSfunDesc(funDes_Add);
	    fun.setSfunUrl(funUrl_Add);
	    fun.setSfunIndex(Short.valueOf(funSerial_add));
	    if ("1".equals(isHidden)) {
		fun.setSfunIsHidden(true);
	    } else {
		fun.setSfunIsHidden(false);
	    }
	    this.sysFunctionService.saveOrUpdate(fun);
	    if ("false".equals(isFunEditing)) {
		// 给系统管理员授权

		out.write("{success:true,msg:'新增成功！'}");
	    } else {
		out.write("{success:true,msg:'编辑成功！'}");
	    }
	} catch (ErrorMessageException e) {
	    out.write("{success:false,msg:'" + e.getMessage() + "'}");
	}
	out.flush();
    }

    /**
     * 
     * @Title: initFun
     * @Description: 初始化功能点表单
     * @param request
     * @param response
     * @throws Exception
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-6-11下午5:05:01
     */
    @RequestMapping(value = "/initFun")
    public void initFun(HttpServletRequest request, HttpServletResponse response) throws Exception {
	response.setContentType("text/plain;charset=utf-8");
	PrintWriter out = response.getWriter();
	JSONObject json = new JSONObject();
	try {

	    Long funId = ServletRequestUtils.getLongParameter(request, "funId", -1L);
	    SysFunction fun = this.sysFunctionService.findById(funId);
	    if (fun == null) {
		throw new ErrorMessageException("未找到对应的功能点！");
	    }

	    json.put("success", true);
	    json.put("funName_Add", fun.getSfunName());
	    json.put("funDes_Add", fun.getSfunDesc());
	    json.put("funUrl_Add", fun.getSfunUrl());
	    json.put("funSerial_add", fun.getSfunIndex());
	    json.put("isHidden", fun.getSfunIsHidden());
	    json.put("parentCatalogId", fun.getParentCatalog().getScatId());

	} catch (ErrorMessageException e) {
	    e.printStackTrace();
	    json.put("success", false);
	    json.put("msg", e.getMessage());
	}
	out.write(json.toString());
	out.flush();
    }
    
    
    /**
     * 
    * @Title: delFun
    * @Description: 删除功能点
    * @param request
    * @param response
    * @throws Exception
    * @return void
    * @throws
    * @author: YangJunping
    * @date 2012-6-11下午5:19:01
     */
    @RequestMapping(value = "/delFun")
    public void delFun(HttpServletRequest request, HttpServletResponse response) throws Exception {
	response.setContentType("text/plain;charset=utf-8");
	PrintWriter out = response.getWriter();
	try {

	    Long selectFunId = ServletRequestUtils.getLongParameter(request, "selectFunId", -1L);
	    SysFunction fun = this.sysFunctionService.findById(selectFunId);
	    if (fun == null) {
		throw new ErrorMessageException("未找到对应的功能点！");
	    }
	    this.sysFunctionService.delete(fun);
	    out.write("{success:true,msg:'删除成功！'}");
	} catch (ErrorMessageException e) {
	    e.printStackTrace();
	    out.write("{success:false,msg:'" + e.getMessage() + "'}");
	}
	out.flush();
    }
}
