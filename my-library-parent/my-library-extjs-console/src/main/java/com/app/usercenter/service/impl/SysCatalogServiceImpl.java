/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.usercenter.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.usercenter.dao.SysCatalogDao;
import com.app.usercenter.domain.SysCatalog;
import com.app.usercenter.domain.SysRole;
import com.app.usercenter.service.SysCatalogService;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.impl.HibernateDaoImpl;
import com.sys.common.service.impl.BaseServiceImpl;
import com.sys.ext.PageExtNative;

/**
 * 
 * <p>
 * Title: SysCatalogServiceImpl.java
 * </p>
 * <p>
 * Description:系统目录
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
 * @date 2012-4-11下午2:07:15
 * @version 1.0
 */
@Service("sysCatalogService")
public class SysCatalogServiceImpl extends BaseServiceImpl<SysCatalog, Long> implements SysCatalogService {

    private SysCatalogDao sysCatalogDao;

    @Resource(name = "sysCatalogDao")
    @Override
    public void setHibernateDao(HibernateDaoImpl<SysCatalog, Long> hibernateDao) {
	super.hibernateDao = hibernateDao;
	this.sysCatalogDao = (SysCatalogDao) hibernateDao;
    }

    @Override
    public List<SysCatalog> getSysCatalogByParentCatalogId(Long parentCatalogId) {
	return sysCatalogDao.getSysCatalogByParentCatalogId(parentCatalogId);
    }

    @Override
    public List<SysCatalog> getAccessableSysCatalogs(List<SysRole> roles) {
	return sysCatalogDao.getAccessableSysCatalogs(roles);
    }

    @Override
    public Wrapper getSysCatalogsBySearchCondition(Map<String, Object> conditionMap, PageExtNative page) {
	return sysCatalogDao.getSysCatalogsBySearchCondition(conditionMap, page);
    }

    @Override
    public SysCatalog getCatalogByCondition(String name, Long parentId) {
	return sysCatalogDao.getCatalogByCondition(name, parentId);
    }

    @Override
    public List<SysCatalog> getSysCatalog() {
	return sysCatalogDao.getSysCatalog();
    }

}
