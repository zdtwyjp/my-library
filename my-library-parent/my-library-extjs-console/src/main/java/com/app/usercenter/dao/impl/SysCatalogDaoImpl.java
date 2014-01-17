/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.usercenter.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.app.usercenter.dao.SysCatalogDao;
import com.app.usercenter.domain.SysCatalog;
import com.app.usercenter.domain.SysFunction;
import com.app.usercenter.domain.SysRole;
import com.app.usercenter.domain.SysRoleFunction;
import com.app.util.StringUtil;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.impl.HibernateDaoImpl;
import com.sys.ext.PageExtNative;

/**
 * 
 * <p>
 * Title: SysCatalogDaoImpl.java
 * </p>
 * <p>
 * Description:TODO
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
 * @date 2012-4-11下午2:05:31
 * @version 1.0
 */
@Repository("sysCatalogDao")
public class SysCatalogDaoImpl extends HibernateDaoImpl<SysCatalog, Long> implements SysCatalogDao {

    @Override
    public List<SysCatalog> getSysCatalogByParentCatalogId(Long parentCatalogId) {
	String hql = "from SysCatalog sc where sc.parentCatalog.scatId = ? ";
	return super.find(hql, parentCatalogId);
    }

    @Override
    public List<SysCatalog> getAccessableSysCatalogs(List<SysRole> roles) {
	if (roles.size() == 0) {
	    return null;
	}
	StringBuffer roleIds = new StringBuffer();
	/** */
	List<SysCatalog> categories = new ArrayList<SysCatalog>();
	/** 用于存放用户拥有的功能权限 */
	List<SysFunction> sysFunctionList = new ArrayList<SysFunction>();
	/** 第一步：获得该用户所拥有的所有功能权限 */
	for (int i = 0; i < roles.size(); i++) {
	    SysRole role = roles.get(i);
	    roleIds.append(role.getSrolId());
	    if (i < roles.size() - 1)
		roleIds.append(",");
	    Set<SysRoleFunction> sysRoleFunctions = role.getSysRoleFunctions();
	    for (SysRoleFunction sysRoleFunction : sysRoleFunctions) {
		sysFunctionList.add(sysRoleFunction.getSysFunction());
	    }
	}
	return getRootCategories(sysFunctionList);
    }

    /**
     * 
     * @Title: getRootCategories
     * @Description: 根据功能点获取对应的顶级目录
     * @param sysFunctionList
     * @return
     * @return List<SysCatalog>
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午5:37:51
     */
    private List<SysCatalog> getRootCategories(List<SysFunction> sysFunctionList) {
	if (sysFunctionList == null) {
	    return new ArrayList<SysCatalog>();
	}
	List<SysCatalog> categories;
	StringBuffer sysFunctionIds = new StringBuffer();
	// 用于存所有被访问到的SysCatalog，因为在建树时，SysCatalog一个对象可能会被反复访问到，
	CataLogMap temp = new CataLogMap();
	// 加一个缓存可以减少数据库访问量，同时方便建树
	// 用来存放最顶层的SysCatalog，在这些顶层的SysCatalog中包含有它们的子SysCatalog，
	Map<Long, SysCatalog> rootCatalog = new HashMap<Long, SysCatalog>();
	List<SysFunction> noRepeatSysFunctionList = new ArrayList<SysFunction>();
	for (int i = 0, size = sysFunctionList.size(); i < size; i++) {
	    SysFunction element = sysFunctionList.get(i);
	    boolean hasRepeat = false;
	    for (int j = 0, size2 = noRepeatSysFunctionList.size(); j < size2; j++) {
		SysFunction function = noRepeatSysFunctionList.get(j);
		if (element.getSfunId().equals(function.getSfunId())) {
		    hasRepeat = true;
		    break;
		}
	    }
	    if (!hasRepeat) {
		noRepeatSysFunctionList.add(element);
	    }
	}
	/** 第一步完 */
	if (noRepeatSysFunctionList.size() == 0) {
	    // 没有功能
	    return null;
	}
	/** 第二步 将用户拥有的SysFunction的ID组装成串，便于数据库查询 */
	for (int i = 0; i < noRepeatSysFunctionList.size(); i++) {
	    SysFunction function = noRepeatSysFunctionList.get(i);
	    sysFunctionIds.append(function.getSfunId());
	    if (i < noRepeatSysFunctionList.size() - 1)
		sysFunctionIds.append(",");
	}
	/** 第三步 获取用户可以访问的SysCatalog */
	categories = getAccessableSysCatalogs(sysFunctionIds.toString());
	for (int i = 0; i < categories.size(); i++) {
	    SysCatalog catalog = (SysCatalog) categories.get(i);
	    // 构件SysCatalog的访问树，这一步很重要
	    // 这个方法会返回顶层SysCatalog，这些顶层SysCatalog中还包含有它们的子SysCatalog，所以SysCatalog是以树结构存储在MAP中的
	    SysCatalog root = getRootCatalog(catalog, temp, noRepeatSysFunctionList);
	    if (root != null) {
		rootCatalog.put(root.getScatId(), root);
	    }
	}
	categories = new ArrayList<SysCatalog>();
	categories.addAll(rootCatalog.values());
	java.util.Collections.sort(categories);
	return categories;
    }

    /**
     * 
     * @Title: getAccessableSysCatalogs
     * @Description: 根据sysFunctionIds返回有访问权限的SysCatalog
     * @param sysFunctionIds
     * @return
     * @return List
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午5:46:37
     */
    private List<SysCatalog> getAccessableSysCatalogs(String sysFunctionIds) {
	if (!StringUtil.isNotNull(sysFunctionIds)) {
	    return null;
	}
	String hql = "select distinct catalog from SysFunction fun,SysCatalog catalog where fun.parentCatalog = catalog and fun.sfunId in ("
		+ sysFunctionIds + ")";

	return super.find(hql);
    }

    /***************************************************************************************************************************************
     * 该方法的算法思路是获得一个SysCatalog，如果该SysCatalog为最顶层SysCatalog则在权限赋值完成后返回
     * 如果该SysCatalog有父SysCatalog
     * ，则进入递归体访问其它的所有上层SysCatalog，并对这些SysCatalog赋予相应的访问权限，
     * 同时将这些SysCatalog都添加到SysCatalog的对象树中，当访问到最顶层SysCatalog时返回该对象树
     * 注意：SysCatalog对象本身拥有childrenCatalogs属性，所以对象树是建立在SysCatalog对象中的
     **************************************************************************************************************************************/
    private SysCatalog getRootCatalog(SysCatalog catalog, CataLogMap catalogMap, List functionList) {
	if (catalog == null)
	    return null;
	// 判断当前SysCatalog是否在缓存中，如果不在则加入之
	if (!catalogMap.isExists(catalog)) {
	    // 在加入缓存前先将该SysCatalog的权限加载到SysCatalog中
	    // 应该需要排序
	    catalog = setSysFunctionToCataLog(catalog, functionList);
	    // 放入缓存
	    catalogMap.addCataLog(catalog);
	}
	// 有父类Catalog
	if (catalog.getParentCatalog() != null) {
	    SysCatalog parent = catalog.getParentCatalog();
	    // 将以前处理过的相同的对象进行数据同步，以确保处理的正确
	    if (catalogMap.isExists(parent))
	    /*
	     * 如果catalogMap中已经有该对象了则直接返回已经缓存在该MAP中的对象，
	     * 因为这个对象以前处理过，已经和其他对象建立了树的关系并且已经赋予了权限， 这样可以方便前台建立导航和检查权限
	     */
	    {
		parent = catalogMap.getSysCatalog(parent);
	    } else {
		// 如果catalogMap中没有该对象，则在该对象中加入SysFunction以方便前台检查权限
		parent = setSysFunctionToCataLog(parent, functionList);
	    }
	    Set set = new HashSet();
	    set.addAll(parent.getChildrenCatalogs());
	    set.add(catalog);// Set集合不允许重复的数据，这样可以剔除重复的数据
	    Object[] obj = set.toArray();
	    List list = new ArrayList();
	    for (int i = 0; i < obj.length; i++) {
		list.add(obj[i]);
	    }
	    Collections.sort(list, new Comparator<SysCatalog>() {
		public int compare(SysCatalog sysCatalog1, SysCatalog sysCatalog2) {
		    return sysCatalog1.getScatCode() - sysCatalog2.getScatCode();
		}
	    });
	    parent.getChildrenCatalogs().clear();
	    parent.getChildrenCatalogs().addAll(list);
	    catalogMap.addCataLog(parent);
	    if (parent.getParentCatalog() == null)
		return parent;
	    return getRootCatalog(parent, catalogMap, functionList);
	}
	return catalog;
    }

    /** 将用户权限设置到对应的Catalog中 */
    private SysCatalog setSysFunctionToCataLog(SysCatalog catalog, List functionList) {
	if (catalog == null)
	    return null;
	Long scatId = catalog.getScatId();
	List functions = new ArrayList();
	for (Iterator iter = functionList.iterator(); iter.hasNext();) {
	    SysFunction element = (SysFunction) iter.next();
	    if (element.getParentCatalog().getScatId().equals(scatId)) {
		Boolean flag = false;
		for (Iterator iterator = functions.iterator(); iterator.hasNext();) {
		    SysFunction function = (SysFunction) iterator.next();
		    if (function.getSfunId().equals(element.getSfunId()))
			flag = true;
		}
		if (flag == false)
		    functions.add(element);
	    }
	}
	Set temp = new HashSet(functions);
	functions = new ArrayList(temp);
	Collections.sort(functions, new Comparator<SysFunction>() {
	    public int compare(SysFunction sysFunction1, SysFunction sysFunction2) {
		return sysFunction1.getSfunIndex() - sysFunction2.getSfunIndex();
	    }
	});
	catalog.setSysFunctions(functions);
	return catalog;
    }

    @SuppressWarnings("unchecked")
    class CataLogMap {
	private Map<Long, SysCatalog> catalogMap = new HashMap<Long, SysCatalog>();

	public void addCataLog(SysCatalog catalog) {
	    if (catalog == null) {
		return;
	    }
	    catalogMap.put(catalog.getScatId(), catalog);
	}

	public SysCatalog getSysCatalog(SysCatalog catalog) {
	    if (catalog == null) {
		return null;
	    }
	    if (catalogMap.get(catalog.getScatId()) == null) {
		return catalog;
	    }
	    return catalogMap.get(catalog.getScatId());
	}

	public SysCatalog remove(SysCatalog catalog) {
	    if (catalog == null) {
		return null;
	    }
	    return catalogMap.remove(catalog.getScatId());
	}

	public boolean isExists(SysCatalog catalog) {
	    if (catalog == null) {
		return false;
	    }
	    return catalogMap.remove(catalog.getScatId()) != null;
	}
    }

    @Override
    public Wrapper getSysCatalogsBySearchCondition(Map<String, Object> conditionMap, PageExtNative page) {
	String hqlCount = "select count(*) from SysCatalog syc ";
	String hql = "from SysCatalog syc ";
	boolean hasCondition = false;
	boolean hasOrder = false;
	String conditionHql = "";
	String orderHql = "";
	boolean hasWhere = false;
	List<Object> conditionList = new ArrayList<Object>();
	// 名称
	Object searchConditionObj = conditionMap.get("searchCondition");
	if (searchConditionObj != null) {
	    String searchCondition = (String) searchConditionObj;
	    if (StringUtil.isNotNull(searchCondition)) {
		conditionHql += StringUtil.getWhereOrAnd(hasWhere) + " syc.scatName like ? or syc.parentCatalog.scatName like ? ";
		conditionList.add("%" + searchCondition + "%");
		conditionList.add("%" + searchCondition + "%");
		hasCondition = true;
		hasWhere = true;
	    }
	}
	if (page != null && StringUtil.isNotNull(page.getSort()) && StringUtil.isNotNull(page.getDir())) {
	    orderHql = " order by syc." + page.getSort() + " " + page.getDir();
	    hasOrder = true;
	}
	if (hasCondition) {
	    hqlCount += conditionHql;
	    hql += conditionHql;
	}
	if (hasOrder) {
	    hqlCount += orderHql;
	    hql += orderHql;
	}
	return super.findWrapper(hql, hqlCount, conditionList.toArray(), page.getStart(), page.getLimit());
    }

    @Override
    public SysCatalog getCatalogByCondition(String name, Long parentId) {
	List<Object> list = new ArrayList<Object>();
	String hql = "from SysCatalog sc where sc.scatName = ? ";
	list.add(name);
	if (parentId > 0) {
	    hql += "and sc.parentCatalog.scatId = ? ";
	    list.add(parentId);
	}else{
	    hql += "and sc.parentCatalog.scatId is null ";
	}
	return super.findObject(hql, list.toArray());
    }

    @Override
    public List<SysCatalog> getSysCatalog() {
	String hql = "from SysCatalog order by scatCode ASC";
	return this.find(hql);
    }
}
