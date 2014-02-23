package com.app.usercenter.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.usercenter.dao.SysUserRoleDao;
import com.app.usercenter.domain.SysUserRole;
import com.app.usercenter.service.SysUserRoleService;
import com.sys.common.dao.impl.HibernateDaoImpl;
import com.sys.common.service.impl.BaseServiceImpl;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole, Long> implements SysUserRoleService {
    private SysUserRoleDao sysUserRoleDao;

    @Resource(name = "sysUserRoleDao")
    @Override
    public void setHibernateDao(HibernateDaoImpl<SysUserRole, Long> hibernateDao) {
	this.hibernateDao = hibernateDao;
	this.sysUserRoleDao = (SysUserRoleDao) hibernateDao;
    }

}
