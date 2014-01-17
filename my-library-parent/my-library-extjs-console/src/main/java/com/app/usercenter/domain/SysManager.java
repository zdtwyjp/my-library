package com.app.usercenter.domain;

import java.util.Date;

/**
 * 
 * 
 * <p>
 * Title: SysManager.java
 * </p>
 * <p>
 * Description:系统管理员实体
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
 * @date 2012-4-12下午3:00:38
 * @version 1.0
 */
public class SysManager implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long smanId;

    /** 管理员名称 */
    private String smanName;

    /** 管理员登录名称 */
    private String smanLoginName;

    /** 管理员密码 */
    private String smanPwd;

    /** 管理员邮箱 */
    private String smanEmail;

    /** 管理员电话 */
    private String smanTel;

    /** 备注 */
    private String smanRemark;

    /** 帐号是否停用 */
    private Boolean smanIsStop;

    /** 创建时间 */
    private Date createTime;

    /** 登录次数 */
    private int loginedCount;

    /** 处理完订单数 */
    private int handledOrders;

    /** 处理中的订单数量 */
    private int handlingOrders;
    
    /** 最后登录时间*/
    private Date loginedTime;
    
    /** 更新时间*/
    private Date updateTime;

    
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public SysManager() {
    }
    
    public Date getLoginedTime() {
        return loginedTime;
    }


    public void setLoginedTime(Date loginedTime) {
        this.loginedTime = loginedTime;
    }


    public Date getCreateTime() {
	return createTime;
    }

    public void setCreateTime(Date createTime) {
	this.createTime = createTime;
    }

    public int getLoginedCount() {
	return loginedCount;
    }

    public void setLoginedCount(int loginedCount) {
	this.loginedCount = loginedCount;
    }

    public int getHandledOrders() {
	return handledOrders;
    }

    public void setHandledOrders(int handledOrders) {
	this.handledOrders = handledOrders;
    }

    public int getHandlingOrders() {
	return handlingOrders;
    }

    public void setHandlingOrders(int handlingOrders) {
	this.handlingOrders = handlingOrders;
    }

    public Long getSmanId() {
	return this.smanId;
    }

    public void setSmanId(Long smanId) {
	this.smanId = smanId;
    }

    public String getSmanName() {
	return this.smanName;
    }

    public void setSmanName(String smanName) {
	this.smanName = smanName;
    }

    public String getSmanLoginName() {
	return this.smanLoginName;
    }

    public void setSmanLoginName(String smanLoginName) {
	this.smanLoginName = smanLoginName;
    }

    public String getSmanPwd() {
	return this.smanPwd;
    }

    public void setSmanPwd(String smanPwd) {
	this.smanPwd = smanPwd;
    }

    public String getSmanEmail() {
	return this.smanEmail;
    }

    public void setSmanEmail(String smanEmail) {
	this.smanEmail = smanEmail;
    }

    public String getSmanTel() {
	return this.smanTel;
    }

    public void setSmanTel(String smanTel) {
	this.smanTel = smanTel;
    }

    public String getSmanRemark() {
	return this.smanRemark;
    }

    public void setSmanRemark(String smanRemark) {
	this.smanRemark = smanRemark;
    }

    public String getSmanNameBy10() {
	if (this.smanName.length() < 8)
	    return this.smanName;
	return this.smanName.substring(0, 8) + "...";
    }

    public String getSubSuinLoginName() {
	if (this.smanLoginName.length() < 15)
	    return this.smanLoginName;
	return this.smanLoginName.substring(0, 15) + "...";
    }

    public Boolean getSmanIsStop() {
	return smanIsStop;
    }

    public void setSmanIsStop(Boolean smanIsStop) {
	this.smanIsStop = smanIsStop;
    }
}