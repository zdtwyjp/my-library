package com.app.usercenter.dao;

import java.util.List;

import com.app.usercenter.domain.SysUserRole;
import com.sys.common.dao.HibernateDao;

public interface SysUserRoleDao extends HibernateDao<SysUserRole, Long> {

    /**
     * 
     * @Title: getRolesByManagerId
     * @Description:
     * @param id
     * @return
     * @return List
     * @throws
     * @date 2012-4-10
     * @author YangJunping
     */
    public List<SysUserRole> getRolesByManagerId(Long id);

    /**
     * 
     * @Title: deleteSysUserRoleByManagerId
     * @Description: 根据管理员ID删除用户角色权限
     * @param id
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-4-11上午11:18:32
     */
    public void deleteSysUserRoleByManagerId(Long id);
}
