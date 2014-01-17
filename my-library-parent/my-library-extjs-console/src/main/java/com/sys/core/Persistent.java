package com.sys.core;

import java.io.Serializable;

/**
 * 
 * 
 * <p>
 * Title: Persistent.java
 * </p>
 * <p>
 * Description:所有Hibernate映射对象的基类,用于ID生成器
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
 * @date 2012-4-12下午3:15:45
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Persistent implements Serializable {
    private Long id;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	return ((Persistent) obj).id == id;
    }
}