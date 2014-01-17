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

import com.app.usercenter.dao.SysRoleDao;
import com.app.usercenter.domain.SysRole;
import com.app.usercenter.service.SysRoleService;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.impl.HibernateDaoImpl;
import com.sys.common.service.impl.BaseServiceImpl;
import com.sys.ext.PageExtNative;

/**
 * 
 * <p>
 * Title: SysRoleServiceImpl.java
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
 * @date 2012-4-10下午3:25:22
 * @version 1.0
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, Long> implements SysRoleService {

    private SysRoleDao sysRoleDao;

    @Resource(name = "sysRoleDao")
    @Override
    public void setHibernateDao(HibernateDaoImpl<SysRole, Long> hibernateDao) {
	this.hibernateDao = hibernateDao;
	this.sysRoleDao = (SysRoleDao) hibernateDao;
    }

    @Override
    public Wrapper getAllRoles(PageExtNative page) {
	return sysRoleDao.getAllRoles(page);
    }

    @Override
    public Wrapper getAllRolesBySearchCondition(String condition, PageExtNative page) {
	return sysRoleDao.getAllRolesBySearchCondition(condition, page);
    }

    @Override
    public List<SysRole> getSysRoles(Long smanId) {
	return sysRoleDao.getUserRoles(smanId);
    }

}
