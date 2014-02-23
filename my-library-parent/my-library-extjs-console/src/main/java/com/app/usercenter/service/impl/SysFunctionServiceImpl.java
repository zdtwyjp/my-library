/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.usercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.usercenter.dao.SysFunctionDao;
import com.app.usercenter.domain.SysFunction;
import com.app.usercenter.service.SysFunctionService;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.impl.HibernateDaoImpl;
import com.sys.common.service.impl.BaseServiceImpl;
import com.sys.ext.PageExtNative;

/**
 * 
 * <p>
 * Title: SysFunctionServiceImpl.java
 * </p>
 * <p>
 * Description:系统功能点
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
 * @date 2012-4-11下午2:21:01
 * @version 1.0
 */
@Service("sysFunctionService")
public class SysFunctionServiceImpl extends BaseServiceImpl<SysFunction, Long> implements SysFunctionService {

    private SysFunctionDao sysFunctionDao;

    @Resource(name = "sysFunctionDao")
    @Override
    public void setHibernateDao(HibernateDaoImpl<SysFunction, Long> hibernateDao) {
	super.hibernateDao = hibernateDao;
	this.sysFunctionDao = (SysFunctionDao) hibernateDao;
    }

    @Override
    public List<SysFunction> getSysFunctionByParentCatalogId(Long parentCatalogId) {
	return sysFunctionDao.getSysFunctionByParentCatalogId(parentCatalogId);
    }

    @Override
    public Wrapper getAllFunctionsByCataLogId(Long id, PageExtNative page) {
	return sysFunctionDao.getAllFunctionsByCataLogId(id, page);
    }

    @Override
    public Wrapper getAllFunctionsByRoleId(Long id, PageExtNative page) {
	return sysFunctionDao.getAllFunctionsByRoleId(id, page);
    }

    @Override
    public SysFunction getFunctionByCondition(String name, Long parentId) {
	return sysFunctionDao.getFunctionByCondition(name, parentId);
    }

}
