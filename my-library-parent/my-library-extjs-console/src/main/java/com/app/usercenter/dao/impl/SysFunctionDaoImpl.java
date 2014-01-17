/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.usercenter.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.app.usercenter.dao.SysFunctionDao;
import com.app.usercenter.domain.SysFunction;
import com.app.util.StringUtil;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.impl.HibernateDaoImpl;
import com.sys.ext.PageExtNative;

/**
 * 
 * <p>
 * Title: SysFunctionDaoImpl.java
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
 * @date 2012-4-11下午2:19:33
 * @version 1.0
 */
@Repository("sysFunctionDao")
public class SysFunctionDaoImpl extends HibernateDaoImpl<SysFunction, Long> implements SysFunctionDao {

    @Override
    public List<SysFunction> getSysFunctionByParentCatalogId(Long parentCatalogId) {
	String hql = "from SysFunction sf where sf.parentCatalog.scatId = ? ";
	return super.find(hql, parentCatalogId);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Wrapper getAllFunctionsByCataLogId(Long id, PageExtNative page) {
	String hqlCount = "select count(*) from SysFunction sf ";
	String hql = "select sf.sfunId,sf.sfunName,sf.sfunDesc,sf.sfunUrl,"
		+ "sf.sfunIndex,sf.sfunCode,sf.sfunIsHidden from SysFunction sf where sf.parentCatalog.scatId=" + id
		+ " or sf.parentCatalog.parentCatalog.scatId=" + id;
	List listCount = super.getSession().createQuery(hqlCount).list();
	Query q = super.getSession().createQuery(hql);
	if (page != null && page.getLimit() != 0) {
	    q.setFirstResult(page.getStart());
	    q.setMaxResults(page.getLimit());
	}
	List list = q.list();
	if (list == null || list.size() == 0) {
	    list = super.getSession().createQuery(hql.substring(0, hql.indexOf("where")) + "	where sf.sfunId=" + id).list();
	}
	return new Wrapper(list, listCount.get(0).toString());
    }

    @Override
    public Wrapper getAllFunctionsByRoleId(Long id, PageExtNative page) {
	String hqlCount = "select count(*) from SysRoleFunction srf " + "where srf.sysRole.srolId=" + id;
	String hql = "select distinct sf.sfunId,sf.sfunName,sf.sfunDesc,sf.sfunUrl,"
		+ "sf.sfunIndex,sf.sfunCode,sf.sfunIsHidden from SysFunction sf, SysRoleFunction srf" + " where srf.sysRole.srolId=" + id
		+ " and srf.sysFunction.sfunId=sf.sfunId";
	if (page != null) {
	    if (StringUtil.isNotNull(page.getSort()) && StringUtil.isNotNull(page.getDir())) {
		hqlCount += " order by srf." + page.getSort() + " " + page.getDir();
		hql += " order by srf." + page.getSort() + " " + page.getDir();
	    }
	}
	List list = super.find(hql);
	List listCount = super.find(hqlCount);
	return new Wrapper(list, listCount.get(0).toString());
    }

    @Override
    public SysFunction getFunctionByCondition(String name, Long parentId) {
	String hql = "from SysFunction sf where sf.sfunName = ? and sf.parentCatalog.scatId = ? ";
	return super.findObject(hql, new Object[]{name,parentId});
    }

}
