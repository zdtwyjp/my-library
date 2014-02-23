package com.app.usercenter.service;

import java.util.List;
import java.util.Map;

import com.app.usercenter.domain.SysManager;
import com.app.usercenter.domain.SysRole;
import com.sys.common.core.Wrapper;
import com.sys.common.service.BaseService;
import com.sys.ext.PageExtNative;

public interface SysManagerService extends BaseService<SysManager, Long> {

    /**
     * 管理员登录
     * 
     * @param 参数描述
     *            .
     * @return SysManager
     * @auther YangJunping
     * @throws 抛出的异常
     * @createTime 2012-4-10
     */
    public SysManager managerLogin(String userName, String password, String remoteHost);

    /**
     * 根据id获取管理员
     * 
     * @param id
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Map<String, List> getManagerInfoById(Long id);

    /**
     * 
     * @Title: saveManager
     * @Description: 保存管理员
     * @param manager
     * @param roles
     * @return
     * @return SysManager
     * @throws
     * @author: YangJunping
     * @date 2012-4-11上午9:51:09
     */
    public SysManager saveManager(SysManager manager, SysRole[] roles);

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

    /**
     * 更新管理员基本信息
     * 
     * @param manager
     */
    void updateManagerInfo(SysManager manager, SysRole[] roles);

    /**
     * 修改管理员状态
     * 
     * @param managerId
     */
    void updateSysManagerStatus(Long managerId);
}
