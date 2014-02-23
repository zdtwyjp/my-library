package com.app.usercenter.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * 
 * <p>
 * Title: SysUserRole.java
 * </p>
 * <p>
 * Description:系统用户角色实体
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
 * @date 2012-4-12下午3:11:24
 * @version 1.0
 */
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    /** ID */
    private Long suroId;
    /** 管理员 */
    private SysManager user;
    private Long sysUserId;

    /** 角色 */
    private SysRole role;

    public SysUserRole() {
    }

    public Long getSuroId() {
	return this.suroId;
    }

    public void setSuroId(Long suroId) {
	this.suroId = suroId;
    }

    public SysRole getRole() {
	return this.role;
    }

    public void setRole(SysRole sysRole) {
	this.role = sysRole;
    }

    public SysManager getUser() {
	return user;
    }

    public void setUser(SysManager user) {
	this.user = user;
    }

    public Long getSysUserId() {
	return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
	this.sysUserId = sysUserId;
    }

    @Override
    public String toString() {
	return new ToStringBuilder(this).append("suroId", getSuroId()).toString();
    }
}
