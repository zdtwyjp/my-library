package com.sys.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.fxpgy.lfd.core.IdGenerator")
	private Long sn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getSn() {
		return sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass().getPackage() != obj.getClass().getPackage()) {
			return false;
		}
		final BaseEntity other = (BaseEntity)obj;
		if(sn == 0) {
			if(other.getSn() != 0) {
				return false;
			}
		}else if(sn != other.getSn()) {
			return false;
		}
		return true;
	}
}
