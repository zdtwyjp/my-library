/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.usercenter.dao;

import java.util.List;

import com.app.usercenter.domain.SysFunction;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.HibernateDao;
import com.sys.ext.PageExtNative;

/**
 * 
 * <p>
 * Title: SysFunctionDao.java
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
 * @date 2012-4-11下午2:18:56
 * @version 1.0
 */
public interface SysFunctionDao extends HibernateDao<SysFunction, Long> {
    /**
     * 
     * @Title: getSysFunctionByParentCatalogId
     * @Description: 根据父级目录ID获取功能点
     * @param parentCatalogId
     * @return
     * @return List<SysFunction>
     * @throws
     * @author: YangJunping
     * @date 2012-4-11下午2:23:38
     */
    public List<SysFunction> getSysFunctionByParentCatalogId(Long parentCatalogId);

    /**
     * 
     * @Title: getAllFunctionsByCataLogId
     * @Description: 跟据catalogid获取所有的function
     * @param id
     * @param page
     * @return
     * @return Wrapper
     * @throws
     * @author: YangJunping
     * @date 2012-4-11下午2:31:21
     */
    public Wrapper getAllFunctionsByCataLogId(Long id, PageExtNative page);

    /**
     * 
     * @Title: getAllFunctionsByRoleId
     * @Description: 跟据roleid获取所有的function
     * @param id
     * @param page
     * @return
     * @return Wrapper
     * @throws
     * @author: YangJunping
     * @date 2012-4-11下午2:41:03
     */
    public Wrapper getAllFunctionsByRoleId(Long id, PageExtNative page);
    
    /**
     * 
    * @Title: getCatalogByCondition
    * @Description:根据条件查询对应的功能点
    * @param name
    * @param parentId
    * @return
    * @return SysCatalog
    * @throws
    * @author: YangJunping
    * @date 2012-6-8下午3:41:39
     */
    public SysFunction getFunctionByCondition(String name,Long parentId);

}
