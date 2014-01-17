package com.app.usercenter.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * 
 * <p>
 * Title: SysRoleFunction.java
 * </p>
 * <p>
 * Description:角色功能点实体
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
 * @date 2012-4-12下午3:08:37
 * @version 1.0
 */
public class SysRoleFunction implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    private Long srfuId;

    /** 角色ID */
    private Long srfuSrolId;

    /** 功能点ID */
    private Long srfuSfunId;

    /** 功能点 */
    private SysFunction sysFunction;

    /** 角色 */
    private SysRole sysRole;

    public Long getSrfuId() {
	return this.srfuId;
    }

    public void setSrfuId(Long srfuId) {
	this.srfuId = srfuId;
    }

    public Long getSrfuSrolId() {
	return this.srfuSrolId;
    }

    public void setSrfuSrolId(Long srfuSrolId) {
	this.srfuSrolId = srfuSrolId;
    }

    public SysFunction getSysFunction() {
	return this.sysFunction;
    }

    public void setSysFunction(SysFunction sysFunction) {
	this.sysFunction = sysFunction;
    }

    public Long getSrfuSfunId() {
	return srfuSfunId;
    }

    public void setSrfuSfunId(Long srfuSfunId) {
	this.srfuSfunId = srfuSfunId;
    }

    public SysRole getSysRole() {
	return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
	this.sysRole = sysRole;
    }

    @Override
    public String toString() {
	return new ToStringBuilder(this).append("srfuId", getSrfuId()).toString();
    }

}