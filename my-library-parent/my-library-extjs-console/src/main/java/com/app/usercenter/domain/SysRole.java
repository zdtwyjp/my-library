package com.app.usercenter.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * 
 * <p>
 * Title: SysRole.java
 * </p>
 * <p>
 * Description:系统角色实体
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
 * @date 2012-4-12下午3:05:11
 * @version 1.0
 */
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    private Long srolId;

    /** 角色名称 */
    private String srolName;

    /** 角色编码 */
    private String srolCode;

    /** 角色描述 */
    private String srolDesc;

    /** 角色功能点 */
    private Set<SysRoleFunction> sysRoleFunctions;

    /** 是否系统角色 */
    private Boolean srolIsSysRole;
    
    /** 更新时间*/
    private Date updateTime;

    
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public SysRole() {
    }

    public Long getSrolId() {
	return this.srolId;
    }

    public void setSrolId(Long srolId) {
	this.srolId = srolId;
    }

    public String getSrolName() {
	return this.srolName;
    }

    public void setSrolName(String srolName) {
	this.srolName = srolName;
    }

    public String getSrolCode() {
	return this.srolCode;
    }

    public void setSrolCode(String srolCode) {
	this.srolCode = srolCode;
    }

    public String getSrolDesc() {
	return this.srolDesc;
    }

    public void setSrolDesc(String srolDesc) {
	this.srolDesc = srolDesc;
    }

    public Set<SysRoleFunction> getSysRoleFunctions() {
	return sysRoleFunctions;
    }

    public void setSysRoleFunctions(Set<SysRoleFunction> sysRoleFunctions) {
	this.sysRoleFunctions = sysRoleFunctions;
    }

    public String toString() {
	return new ToStringBuilder(this).append("srolId", getSrolId()).toString();
    }

    public Boolean getSrolIsSysRole() {
	return srolIsSysRole;
    }

    public void setSrolIsSysRole(Boolean srolIsSysRole) {
	this.srolIsSysRole = srolIsSysRole;
    }

    @Override
    public boolean equals(Object obj) {
	SysRole role = (SysRole) obj;
	return this.srolId.longValue() == role.getSrolId().longValue();
    }

}
