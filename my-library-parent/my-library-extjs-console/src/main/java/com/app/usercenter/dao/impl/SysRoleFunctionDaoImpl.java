/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.usercenter.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.usercenter.dao.SysRoleFunctionDao;
import com.app.usercenter.domain.SysRoleFunction;
import com.sys.common.dao.impl.HibernateDaoImpl;

/**
 * 
 * <p>
 * Title: SysRoleFunctionDaoImpl.java
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
 * @date 2012-4-11下午2:58:17
 * @version 1.0
 */
@Repository("sysRoleFunctionDao")
public class SysRoleFunctionDaoImpl extends HibernateDaoImpl<SysRoleFunction, Long> implements SysRoleFunctionDao {

    @Override
    public List<SysRoleFunction> getSysRoleFunctionByRoleId(Long funId, Long roleId) {
	String hql = "select srf from SysRoleFunction srf where " + "srf.sysFunction.sfunId=" + funId + " " + "and srf.sysRole.srolId="
		+ roleId;
	return super.find(hql);
    }
}
