/**
 * Copyright (c) 2012 FXPGY, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FXPGY, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FXPGY.
 */
package com.app.usercenter.dao;

import java.util.List;
import java.util.Map;

import com.app.usercenter.domain.SysCatalog;
import com.app.usercenter.domain.SysRole;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.HibernateDao;
import com.sys.ext.PageExtNative;

/**
 * 
 * <p>
 * Title: SysCatalogDao.java
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
 * @date 2012-4-11下午2:05:09
 * @version 1.0
 */
public interface SysCatalogDao extends HibernateDao<SysCatalog, Long> {
    /**
     * 
     * @Title: getSysCatalogByParentCatalogId
     * @Description: 根据父级目录ID，查询功能列表
     * @param parentCatalogId
     * @return
     * @return List<SysCatalog>
     * @throws
     * @author: YangJunping
     * @date 2012-4-11下午2:12:31
     */
    public List<SysCatalog> getSysCatalogByParentCatalogId(Long parentCatalogId);

    /**
     * 
     * @Title: getAccessableSysCatalogs
     * @Description:根据角色集合，获得角色集中的角色有权限操作的系统功能树
     * @param roles
     * @return
     * @return List<Object>
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午5:27:53
     */
    public List<SysCatalog> getAccessableSysCatalogs(List<SysRole> roles);

    /**
     * 
     * @Title: getSysCatalogsBySearchCondition
     * @Description: 获取功能目录列表
     * @param conditionMap
     * @param page
     * @return
     * @return Wrapper
     * @throws
     * @author: YangJunping
     * @date 2012-6-5下午4:55:56
     */
    public Wrapper getSysCatalogsBySearchCondition(Map<String, Object> conditionMap, PageExtNative page);

    /**
     * 
     * @Title: getCatalogByCondition
     * @Description:根据条件查询对应的目录
     * @param name
     * @param parentId
     * @return
     * @return SysCatalog
     * @throws
     * @author: YangJunping
     * @date 2012-6-8下午3:41:39
     */
    public SysCatalog getCatalogByCondition(String name, Long parentId);

    /**
     * 
     * @Title: getSysCatalog
     * @Description: 获取所有的目录
     * @return
     * @return List<SysCatalog>
     * @throws
     * @author: YangJunping
     * @date 2012-6-11下午3:35:09
     */
    public List<SysCatalog> getSysCatalog();
}
