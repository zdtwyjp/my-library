package com.app.usercenter.dao;

import java.util.List;

import com.app.usercenter.domain.SysRole;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.HibernateDao;
import com.sys.ext.PageExtNative;

public interface SysRoleDao extends HibernateDao<SysRole, Long> {

    /**
     * 
     * @Title: getAllRoles
     * @Description: 获取角色
     * @param page
     * @return
     * @return Wrapper
     * @throws
     * @author: YangJunping
     * @date 2012-4-10下午3:33:23
     */
    public Wrapper getAllRoles(PageExtNative page);

    /**
     * 
     * @Title: getRoleByRoleName
     * @Description: 根据角色名称获取角色
     * @param roleName
     * @return
     * @return List
     * @throws
     * @author: YangJunping
     * @date 2012-4-11上午10:53:05
     */
    public List getRoleByRoleName(String roleName);

    /**
     * 
     * @Title: getAllRolesBySearchCondition
     * @Description: 根据条件获取所有角色
     * @param condition
     * @param page
     * @return
     * @return Wrapper
     * @throws
     * @author: YangJunping
     * @date 2012-4-11下午1:29:23
     */
    public Wrapper getAllRolesBySearchCondition(String condition, PageExtNative page);

    /**
     * 
     * @Title: getUserRoles
     * @Description: 根据用户获取用户角色
     * @param smanId
     * @return
     * @return List<SysRole>
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午4:23:53
     */
    public List<SysRole> getUserRoles(Long smanId);

}
