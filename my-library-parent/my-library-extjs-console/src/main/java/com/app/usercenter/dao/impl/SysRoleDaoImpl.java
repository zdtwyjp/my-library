/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.usercenter.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.usercenter.dao.SysRoleDao;
import com.app.usercenter.domain.SysRole;
import com.app.util.StringUtil;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.impl.HibernateDaoImpl;
import com.sys.ext.PageExtNative;

/**
 * 
 * <p>
 * Title: SysRoleDaoImpl.java
 * </p>
 * <p>
 * Description:系统角色DAO
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
 * @date 2012-4-10下午2:55:42
 * @version 1.0
 */
@Repository("sysRoleDao")
public class SysRoleDaoImpl extends HibernateDaoImpl<SysRole, Long> implements SysRoleDao {

    @Override
    public Wrapper getAllRoles(PageExtNative page) {
	String hqlCount = "select count(*) from SysRole sr ";
	String hql = "select sr.srolId,sr.srolName from SysRole sr ";

	if (page != null) {
	    if (StringUtil.isNotNull(page.getSort()) && StringUtil.isNotNull(page.getDir())) {
		hqlCount += " order by sr." + page.getSort() + " " + page.getDir();
		hql += " order by sr." + page.getSort() + " " + page.getDir();
	    }
	}
	List list = find(hql);
	List listCount = find(hqlCount);
	
	return new Wrapper(list, listCount.get(0).toString());
    }

    @Override
    public List getRoleByRoleName(String roleName) {
	List managerRoles = null;
	// 根据各个用户的id查出对应的角色名字
	String sysUserRoles = "select sysRole.srolName from SysRole sysRole where " + "sysRole.srolId in "
		+ "(select sur.role.srolId from SysUserRole sur, SysManager manager "
		+ "where manager.smanId=? and sur.user.smanId=manager.smanId)";
	return super.find(sysUserRoles, Long.parseLong(roleName));
    }

    @Override
    public Wrapper getAllRolesBySearchCondition(String condition, PageExtNative page) {
	Object[] values = null;
	String hqlCount = "select count(*) from SysRole role ";
	String hql = "select role.srolId, role.srolName, role.srolCode, role.srolDesc, " + "role.srolIsSysRole from SysRole role";
	String suffixHql = " where role.srolName like ?";
	if (condition != null && !condition.trim().equals("")) {
	    hqlCount += suffixHql;
	    hql += suffixHql;
	    values = new Object[]{"%" + condition.trim() + "%"};
	}
	if (page != null) {
	    if (StringUtil.isNotNull(page.getSort()) && StringUtil.isNotNull(page.getDir())) {
		hqlCount += " order by role." + page.getSort() + " " + page.getDir();
		hql += " order by role." + page.getSort() + " " + page.getDir();
	    }
	}
	return super.findWrapper(hql, hqlCount, values, page.getStart(), page.getLimit());
    }

    @Override
    public List<SysRole> getUserRoles(Long smanId) {
	String hql = "select ur.role from SysUserRole ur where ur.user.smanId = ? ";
	return super.find(hql, smanId);
    }

}
