/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.usercenter.service;

import com.app.usercenter.domain.SysRoleFunction;
import com.sys.common.service.BaseService;

/**
 * 
 * <p>
 * Title: SysRoleFunctionService.java
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
 * @date 2012-4-11下午2:59:35
 * @version 1.0
 */
public interface SysRoleFunctionService extends BaseService<SysRoleFunction, Long> {

    /**
     * 
     * @Title: saveFunctionsToRole
     * @Description: 给一个角色添加权限
     * @param funcsId
     * @param roleId
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-4-11下午2:47:15
     */
    public void saveFunctionsToRole(Long[] funcsId, Long roleId);

    /**
     * 
     * @Title: deleteFunctionsFromRole
     * @Description: 给角色删除权限
     * @param funcsId
     * @param roleId
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-4-11下午2:47:22
     */
    public void deleteFunctionsFromRole(Long[] funcsId, Long roleId);
}
