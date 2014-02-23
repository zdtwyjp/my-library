/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.usercenter.dao;

import java.util.List;

import com.app.usercenter.domain.SysRoleFunction;
import com.sys.common.dao.HibernateDao;

/**
 * 
 * <p>
 * Title: SysRoleFunctionDao.java
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
 * @date 2012-4-11下午2:57:52
 * @version 1.0
 */
public interface SysRoleFunctionDao extends HibernateDao<SysRoleFunction, Long> {

    /**
     * 
     * @Title: getSysRoleFunctionByRoleId
     * @Description: 通过角色ID获取角色功能点
     * @param roleId
     * @return
     * @return List
     * @throws
     * @author: YangJunping
     * @date 2012-4-11下午2:53:54
     */
    public List<SysRoleFunction> getSysRoleFunctionByRoleId(Long funId, Long roleId);
}
