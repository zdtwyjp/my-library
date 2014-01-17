package com.sys.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.app.usercenter.domain.SysCatalog;
import com.app.usercenter.domain.SysFunction;
import com.app.usercenter.domain.SysManager;
import com.app.usercenter.domain.SysRole;
import com.app.usercenter.domain.SysRoleFunction;
import com.app.usercenter.service.SysCatalogService;
import com.app.usercenter.service.SysRoleService;
import com.app.util.StringUtil;

/**
 * <p>
 * Title: CurrentUserSession.java
 * </p>
 * <p>
 * Description:UserSession类
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
 * @date 2012-4-12下午2:23:14
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "serial"})
public class CurrentUserSession implements Serializable {
	private static Logger logger = Logger.getLogger(CurrentUserSession.class);

	private Long id;

	/** 当前用户 */
	private SysManager currentUser;

	/** 顾问 */
//	private Consultant consultant;

	/** 当前用户所拥有的角色集合 */
	private List<SysRole> currentUserRoles;

	/** 当前用户所拥有的功能对象树 */
	private List<SysCatalog> accessableSysCatalogs = new ArrayList<SysCatalog>();

	/** 当前用户所拥有功能点集合 */
	private List<SysFunction> functions = new ArrayList<SysFunction>();

	/** 当前用户所拥有功能点，以功能点Id为索引 */
	private Map<Long, SysFunction> functionIndex = new HashMap<Long, SysFunction>();

	/** 当前用户所拥有的功能点id */
	private Set<Long> functionIds = new HashSet<Long>();

	/** 当前用户所拥有的功能点所对应的所有URL集合 */
	private List<String> functionURLs = new ArrayList<String>();

	/** 是否拥有网站后台管理员权限 */
	private boolean manager;

	/** 是否调用过刷新角色功能方法 */
	private boolean hasRefleshRoleFunction = false;

	/** 保存用户有权限功能树的json */
	private Map<Long, String> jsonFunctionTreeMap = new HashMap<Long, String>();

	public CurrentUserSession() {
		hasRefleshRoleFunction = false;
		id = new Long(System.currentTimeMillis());
	}

	public CurrentUserSession(SysManager currentUser) {
		hasRefleshRoleFunction = false;
		this.currentUser = currentUser;
		id = new Long(System.currentTimeMillis());
	}

	public Long getId() {
		return id;
	}

	public SysManager getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(SysManager user) {
		this.currentUser = user;
	}

	public boolean isManager() {
		return manager;
	}

//	public Consultant getConsultant() {
//		return consultant;
//	}
//
//	public void setConsultant(Consultant consultant) {
//		this.consultant = consultant;
//	}

	public List<SysRole> getCurrentUserRoles() {
		return currentUserRoles;
	}

	public void setCurrentUserRoles(List<SysRole> currentUserRoles) {
		this.currentUserRoles = currentUserRoles;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public List<SysFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(List<SysFunction> functions) {
		this.functions = functions;
	}

	public boolean isHasRefleshRoleFunction() {
		return hasRefleshRoleFunction;
	}

	public List<SysCatalog> getAccessableSysCatalogs() {
		return accessableSysCatalogs;
	}

	public void setAccessableSysCatalogs(List<SysCatalog> accessableSysCatalogs) {
		this.accessableSysCatalogs = accessableSysCatalogs;
	}

	public void setHasRefleshRoleFunction(boolean isRefleshRoleFunction) {
		this.hasRefleshRoleFunction = isRefleshRoleFunction;
	}

	public Map<Long, SysFunction> getFunctionIndex() {
		return functionIndex;
	}

	public List<String> getFunctionURLs() {
		return functionURLs;
	}

	public void setFunctionURLs(List<String> functionURLs) {
		this.functionURLs = functionURLs;
	}

	public void setFunctionIndex(Map<Long, SysFunction> functionIndex) {
		this.functionIndex = functionIndex;
	}

	/**
	 * @Title: addFunction
	 * @Description: 添加功能点
	 * @param function
	 * @return void
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-12下午2:39:33
	 */
	private void addFunction(SysFunction function) {
		if(function == null)
			return;
		if(!functions.contains(function)) {
			for(int i = 0; i < functions.size(); i++) {
				if(((SysFunction)functions.get(i)).getSfunId().equals(
						function.getSfunId())) {
					return;
				}
			}
			functionIds.add(function.getSfunId());
			functionIndex.put(function.getSfunId(), function);
			functions.add(function);
			// 添加该用户拥有的功能点包括的URL集合,
			addFunctionUrl(function.getSfunUrl());
		}
	}

	/**
	 * @Title: addFunctionUrl
	 * @Description: 添加功能点URL到相应的URL集合中
	 * @param url
	 * @return void
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-12下午2:41:42
	 */
	private void addFunctionUrl(String url) {
		if(!functionURLs.contains(url)) {
			functionURLs.add(url);
		}
	}

	/**
	 * @Title: makeFunctions
	 * @Description: 根据角色集合，构造角色集所拥有的功能集合
	 * @param roles
	 * @return void
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-12下午2:43:51
	 */
	private void makeFunctions(List<SysRole> roles) {
		functions.clear();
		functionURLs.clear();
		functionIds.clear();
		functionIndex.clear();
		for(SysRole sysRole : roles) {
			Set<SysRoleFunction> roleFunctions = sysRole.getSysRoleFunctions();
			for(SysRoleFunction sysRoleFunction : roleFunctions) {
				addFunction(sysRoleFunction.getSysFunction());
			}
		}
		// 按功能的index排序
		try {
			Collections.sort(functions, new Comparator<SysFunction>(){
				@Override
				public int compare(SysFunction f1, SysFunction f2) {
					return (f1.getSfunIndex().shortValue() - f2.getSfunIndex()
							.shortValue());
				}
			});
		}catch(ConcurrentModificationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: findUserSession
	 * @Description: 获取UserSession
	 * @param request
	 * @return
	 * @return CurrentUserSession
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-12下午3:20:41
	 */
	public static CurrentUserSession findUserSession(HttpServletRequest request) {
		CurrentUserSession userSession = (CurrentUserSession)request
				.getSession().getAttribute("userSession");
		if(userSession == null) {
			userSession = new CurrentUserSession(null);
			request.getSession().setAttribute("userSession", userSession);
		}
		return userSession;
	}

	/**
	 * 刷新当前登录用户的角色集合和系统功能树集合及功能点集合
	 * 
	 * @param schoolUser
	 * @param userSession
	 */
	public void refreshRoleFunctions(SysRoleService sysRoleService,
			SysCatalogService sysCatalogService) {
		hasRefleshRoleFunction = true;
		/** 得到角色 */
		List<SysRole> roles = sysRoleService.getSysRoles(currentUser
				.getSmanId());
		if(logger.isInfoEnabled()) {
			logger.info("角色列表---->:" + roles);
		}
		if(roles == null) {
			roles = new ArrayList<SysRole>();
		}
		/** 初始化当前用户角色 */
		setCurrentUserRoles(roles);
		/** 生成功能列表 */
		makeFunctions(roles);
		/** 根据角色集合，获得角色集有权限操作的系统功能树 */
		List catalogs = sysCatalogService
				.getAccessableSysCatalogs(this.currentUserRoles);
		setAccessableSysCatalogs(catalogs);
		// 刷新功能点JSON
		refreshJSONRoleFunctions();
		// 设置该用户是否拥有后台管理员权限
		setManager(false);
		// 获得该用户在后台管理方面的权限.
		List sysCatalogs = getAccessableSysCatalogs();
		if(sysCatalogs != null && sysCatalogs.size() > 0) {
			// 如果该用户拥有后台管理方面的权限,则设置他是管理员.
			setManager(true);
		}
	}

	/**
	 * @Title: isHasFunction
	 * @Description: 判断用户是否有该功能权限
	 * @param funId
	 * @return
	 * @return boolean
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-12下午3:57:01
	 */
	public boolean isHasFunction(Long funId) {
		boolean result = false;
		if(functionIds != null && functionIds.size() > 0) {
			result = functionIds.contains(funId);
		}
		return result;
	}

	/**
	 * 根据SysCatalog的ID值,从 accessableSysCatalogs中获得指定的SysCatalog对象
	 * 
	 * @param id
	 * @return
	 */
	public SysCatalog getSysCatalogsById(String id) {
		SysCatalog sysCatalog = null;
		if(StringUtil.isNotNull(id)) {
			long idValue = Long.parseLong(id);
			for(SysCatalog catalog : accessableSysCatalogs) {
				if(catalog.getScatId().longValue() == idValue) {
					sysCatalog = catalog;
					break;
				}
			}
		}
		return sysCatalog;
	}

	/**
	 * @Title: getFunctionUrl
	 * @Description: 根据id获得功能点链接，当无功能点时返回null，否则返回链接值。
	 * @param funcId
	 * @return
	 * @return String
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-12下午4:00:24
	 */
	public String getFunctionUrl(Long funcId) {
		SysFunction function = functionIndex.get(funcId);
		if(function != null)
			return function.getSfunUrl();
		return null;
	}

	public Map<Long, String> getJsonFunctionTreeMap() {
		return jsonFunctionTreeMap;
	}

	/**
	 * @Title: getJsonFunctionTree
	 * @Description: 通过顶级分类获得该分类下的树
	 * @param catalogId
	 * @return
	 * @return String
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-12下午4:02:24
	 */
	public String getJsonFunctionTree(Long catalogId) {
		String json = null;
		if(catalogId != null) {
			json = jsonFunctionTreeMap.get(catalogId);
		}
		if(json == null)
			json = "";
		return json;
	}

	/**
	 * @Title: refreshJSONRoleFunctions
	 * @Description: 刷新用户有权限功能树的json一 该方法访问accessableSysCatalogs生成功能菜单JSON
	 * @return void
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-12下午4:02:49
	 */
	private void refreshJSONRoleFunctions() {
		jsonFunctionTreeMap.clear();
		StringBuilder json = new StringBuilder("");
		List<SysCatalog> accessableSysCatalogsList = getAccessableSysCatalogs();
		if(accessableSysCatalogsList != null) {
			for(SysCatalog sysCatalog : accessableSysCatalogsList) {
				json.setLength(0);
				json.append("[");
				List<SysCatalog> childrenCatalogs = sysCatalog
						.getChildrenCatalogs();
				// 构造分类目录
				for(int j = 0; j < childrenCatalogs.size(); j++) {
					SysCatalog subSysCatalog = (SysCatalog)childrenCatalogs
							.get(j);
					if(j > 0)
						json.append(",");
					createFuncJson(json, subSysCatalog);
				}
				json.append("]");
				if(logger.isDebugEnabled())
					logger.debug(json.toString());
				jsonFunctionTreeMap
						.put(sysCatalog.getScatId(), json.toString());
			}
		}
	}

	/**
	 * @Title: createFuncJson
	 * @Description: 递归生成功能树json
	 * @param json
	 * @param sysCatalog
	 * @return void
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-12下午4:17:47
	 */
	private void createFuncJson(StringBuilder json, SysCatalog sysCatalog) {
		json.append("{id:'cat").append(sysCatalog.getScatId()).append("'")
				.append(",text:'").append(sysCatalog.getScatName()).append("'");
		json.append(",children:[");
		List<SysCatalog> childrenCatalogs = sysCatalog.getChildrenCatalogs();
		// 构造分类目录
		if(childrenCatalogs != null && childrenCatalogs.size() > 0) {
			for(int j = 0; j < childrenCatalogs.size(); j++) {
				SysCatalog subSysCatalog = (SysCatalog)childrenCatalogs.get(j);
				if(j > 0)
					json.append(",");
				createFuncJson(json, subSysCatalog);
			}
		}
		List<SysFunction> sysFuncList = sysCatalog.getSysFunctions();
		// 构造功能列表
		if(sysFuncList != null && sysFuncList.size() > 0) {
			// 如果前面有分类需要增加逗号分隔
			if(childrenCatalogs != null && childrenCatalogs.size() > 0) {
				json.append(",");
			}
			for(int k = 0; k < sysFuncList.size(); k++) {
				SysFunction func = (SysFunction)sysFuncList.get(k);
				if(k > 0)
					json.append(",");
				json.append("{id:'fun").append(func.getSfunId()).append("'")
						.append(",text:'").append(func.getSfunName())
						.append("'").append(",leaf:true,url:'")
						.append(func.getSfunUrl()).append("'}");
			}
		}
		json.append("]}");
	}
}