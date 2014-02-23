package com.app.usercenter.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * <p>
 * Title: SysCatalog.java
 * </p>
 * <p>
 * Description:系统模块实体
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
 * @date 2012-4-12下午2:46:26
 * @version 1.0
 */
public class SysCatalog implements Serializable, Comparable<SysCatalog> {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long scatId;

    /** 模块名称 */
    private String scatName;

    /** 模块描述 */
    private String scatDesc;

    /** 模块编号,排序用 */
    private Short scatCode;

    /** 父模块ID */
    private SysCatalog parentCatalog;

    /** 不由Hibernate自动加载，而是由程序来填写，在根据用户角色集合获得用户有权限操作的菜单时使用 */
    private List<SysFunction> sysFunctions = new ArrayList<SysFunction>();

    /** 不由Hibernate自动加载，而是由程序来填写,在根据用户角色集合获得用户有权限操作的菜单时使用 */
    private List<SysCatalog> childrenCatalogs = new ArrayList<SysCatalog>();

    public SysCatalog() {
    }

    public Long getScatId() {
	return this.scatId;
    }

    public void setScatId(Long scatId) {
	this.scatId = scatId;
    }

    public String getScatName() {
	return this.scatName;
    }

    public void setScatName(String scatName) {
	this.scatName = scatName;
    }

    public String getScatDesc() {
	return this.scatDesc;
    }

    public void setScatDesc(String scatDesc) {
	this.scatDesc = scatDesc;
    }

    public SysCatalog getParentCatalog() {
	return this.parentCatalog;
    }

    public void setParentCatalog(SysCatalog sysCatalog) {
	this.parentCatalog = sysCatalog;
    }

    public List<SysFunction> getSysFunctions() {
	return sysFunctions;
    }

    public void setSysFunctions(List<SysFunction> sysFunctions) {
	this.sysFunctions = sysFunctions;
    }

    public String toString() {
	return this.getScatId() + "";
    }

    public Short getScatCode() {
	return scatCode;
    }

    public void setScatCode(Short scatCode) {
	this.scatCode = scatCode;
    }

    public List<SysCatalog> getChildrenCatalogs() {
	return childrenCatalogs;
    }

    public void setChildrenCatalogs(List<SysCatalog> childrenCatalogs) {
	this.childrenCatalogs = childrenCatalogs;
    }

    public boolean equals(Object obj) {
	if (obj instanceof SysCatalog) {
	    return this.getScatId().longValue() == ((SysCatalog) obj).getScatId().longValue();
	}
	return false;
    }

    @Override
    public int compareTo(SysCatalog o) {
	if (o.getScatCode().intValue() <= this.getScatCode().intValue())
	    return 1;
	return 0;
    }
}