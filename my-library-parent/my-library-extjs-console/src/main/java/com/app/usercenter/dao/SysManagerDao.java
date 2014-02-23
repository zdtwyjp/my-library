package com.app.usercenter.dao;

import com.app.usercenter.domain.SysManager;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.HibernateDao;
import com.sys.ext.PageExtNative;

public interface SysManagerDao extends HibernateDao<SysManager, Long> {

    /**
     * 
     * @Title: getSysManagerByLoginName
     * @Description: 通过用户名获取系统管理员
     * @param loginName
     * @return
     * @return SysManager
     * @throws
     * @author: YangJunping
     * @date 2012-4-11上午9:52:54
     */
    public SysManager getSysManagerByLoginName(String loginName);

    /**
     * 
     * @Title: getAllManagersBySearchCondition
     * @Description: 根据条件得到所有的管理员，并分页
     * @param condition
     * @param page
     * @return
     * @return Wrapper
     * @throws
     * @author: YangJunping
     * @date 2012-4-11上午10:35:03
     */
    Wrapper getAllManagersBySearchCondition(String condition, PageExtNative page);
}
