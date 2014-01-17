package com.app.usercenter.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.app.util.StringUtil;

/**
 * 
 * 
 * <p>
 * Title: SysFunction.java
 * </p>
 * <p>
 * Description:功能点实体
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
 * @date 2012-4-12下午2:58:14
 * @version 1.0
 */
public class SysFunction implements Serializable, Comparable<SysFunction> {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long sfunId;

    /** 功能点名称 */
    private String sfunName;

    /** 功能描述 */
    private String sfunDesc;

    /** 功能点地址URL */
    private String sfunUrl;

    /** 功能点所属模块 */
    private SysCatalog parentCatalog;

    /** 排序列*/
    private Short sfunIndex;

    /** 功能点编码 */
    private String sfunCode;

    /** 是否隐藏该功能点 */
    private Boolean sfunIsHidden;

    public SysFunction() {
    }

    public Long getSfunId() {
	return this.sfunId;
    }

    public void setSfunId(Long sfunId) {
	this.sfunId = sfunId;
    }

    public String getSfunName() {
	return this.sfunName;
    }

    public void setSfunName(String sfunName) {
	this.sfunName = sfunName;
    }

    public String getSfunDesc() {
	return this.sfunDesc;
    }

    public void setSfunDesc(String sfunDesc) {
	this.sfunDesc = sfunDesc;
    }

    public String getSfunUrl() {
	return this.sfunUrl;
    }

    public void setSfunUrl(String sfunUrl) {
	this.sfunUrl = sfunUrl;
    }

    public Short getSfunIndex() {
	return sfunIndex;
    }

    public void setSfunIndex(Short sfunIndex) {
	this.sfunIndex = sfunIndex;
    }

    public SysCatalog getParentCatalog() {
	return parentCatalog;
    }

    public void setParentCatalog(SysCatalog parentCatalog) {
	this.parentCatalog = parentCatalog;
    }

    public String getSfunCode() {
	return sfunCode;
    }

    public void setSfunCode(String sfunCode) {
	this.sfunCode = sfunCode;
    }

    public Boolean getSfunIsHidden() {
	return sfunIsHidden;
    }

    public void setSfunIsHidden(Boolean sfunIsHidden) {
	this.sfunIsHidden = sfunIsHidden;
    }

    @Override
    public String toString() {
	return new ToStringBuilder(this).append("sfunId", getSfunId()).toString();
    }

    @Override
    public int compareTo(SysFunction o) {
	if (!StringUtil.isNotNull(this.getSfunCode()))
	    return -1;
	if (!StringUtil.isNotNull(o.getSfunCode()))
	    return -1;
	return Integer.parseInt(this.getSfunCode()) > Integer.parseInt(o.getSfunCode()) ? 1 : -1;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof SysFunction) {
	    return this.getSfunId().longValue() == ((SysFunction) obj).getSfunId().longValue();
	}
	return false;
    }

}