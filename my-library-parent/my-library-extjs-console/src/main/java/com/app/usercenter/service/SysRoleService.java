/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.usercenter.service;

import java.util.List;

import com.app.usercenter.domain.SysRole;
import com.sys.common.core.Wrapper;
import com.sys.common.service.BaseService;
import com.sys.ext.PageExtNative;

/**
 * 
 * <p>
 * Title: SysRoleService.java
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
 * @date 2012-4-10下午3:24:54
 * @version 1.0
 */
public interface SysRoleService extends BaseService<SysRole, Long> {

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
     *            管理员ID
     * @return
     * @return List<Object>
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午4:21:37
     */
    public List<SysRole> getSysRoles(Long smanId);

}
