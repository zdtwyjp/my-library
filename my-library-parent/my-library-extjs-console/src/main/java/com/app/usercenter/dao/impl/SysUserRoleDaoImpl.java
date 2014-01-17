package com.app.usercenter.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.usercenter.dao.SysUserRoleDao;
import com.app.usercenter.domain.SysUserRole;
import com.sys.common.dao.impl.HibernateDaoImpl;

@Repository("sysUserRoleDao")
public class SysUserRoleDaoImpl extends HibernateDaoImpl<SysUserRole, Long> implements SysUserRoleDao {

    @Override
    public List<SysUserRole> getRolesByManagerId(Long id) {
	String rolesHql = "from SysUserRole srole where srole.user.smanId=?";
	return super.find(rolesHql, id);
    }

    @Override
    public void deleteSysUserRoleByManagerId(Long id) {
	String hql = "delete from SysUserRole sur where sur.user.smanId = ? ";
	super.executeUpdate(hql, id);
    }

}
