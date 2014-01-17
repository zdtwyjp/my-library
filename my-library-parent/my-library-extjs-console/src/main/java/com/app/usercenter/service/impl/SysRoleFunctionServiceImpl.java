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
import com.app.usercenter.dao.SysRoleDao;
import com.app.usercenter.dao.SysRoleFunctionDao;
import com.app.usercenter.domain.SysRoleFunction;
import com.app.usercenter.service.SysRoleFunctionService;
import com.sys.common.dao.impl.HibernateDaoImpl;
import com.sys.common.service.impl.BaseServiceImpl;

/**
 * 
 * <p>
 * Title: SysRoleFunctionServiceImpl.java
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
 * @date 2012-4-11下午3:00:06
 * @version 1.0
 */
@Service("sysRoleFunctionService")
public class SysRoleFunctionServiceImpl extends BaseServiceImpl<SysRoleFunction, Long> implements SysRoleFunctionService {

    private SysRoleFunctionDao sysRoleFunctionDao;

    @Resource
    private SysFunctionDao sysFunctionDao;

    @Resource
    private SysRoleDao sysRoleDao;

    @Resource(name = "sysRoleFunctionDao")
    @Override
    public void setHibernateDao(HibernateDaoImpl<SysRoleFunction, Long> hibernateDao) {
	super.hibernateDao = hibernateDao;
	this.sysRoleFunctionDao = (SysRoleFunctionDao) hibernateDao;
    }

    @Override
    public void saveFunctionsToRole(Long[] funcsId, Long roleId) {
	for (int i = 0; i < funcsId.length; i++) {
	    List sysRoles = sysRoleFunctionDao.getSysRoleFunctionByRoleId(funcsId[i], roleId);
	    if (sysRoles != null && sysRoles.size() != 0) {
		continue;
	    }
	    SysRoleFunction srf = new SysRoleFunction();
	    srf.setSysFunction(this.sysFunctionDao.findById(funcsId[i]));
	    srf.setSysRole(this.sysRoleDao.findById(roleId));
	    this.sysRoleFunctionDao.save(srf);
	}
    }

    @Override
    public void deleteFunctionsFromRole(Long[] funcsId, Long roleId) {
	for (int i = 0; i < funcsId.length; i++) {
	    List<SysRoleFunction> srf = sysRoleFunctionDao.getSysRoleFunctionByRoleId(funcsId[i], roleId);
	    if (srf != null && srf.size() > 0) {
		this.sysRoleFunctionDao.delete(srf.get(0));
	    }
	}
    }
}
