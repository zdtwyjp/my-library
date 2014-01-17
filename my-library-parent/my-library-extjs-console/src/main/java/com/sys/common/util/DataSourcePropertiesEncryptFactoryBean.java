package com.sys.common.util;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * <p>
 * Title: DataSourcePropertiesEncryptFactoryBean.java
 * </p>
 * <p>
 * Description:数据源用户名密码加密Bean
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
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
 * @date 2012-4-7下午8:33:27
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class DataSourcePropertiesEncryptFactoryBean implements FactoryBean {
	Log log = LogFactory.getLog(getClass());

	private Properties properties;

	@Override
	public Object getObject() throws Exception {
		return properties;
	}

	@Override
	public Class getObjectType() {
		return java.util.Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setProperties(Properties inProperties) {
		this.properties = inProperties;
		String originalUsername = properties.getProperty("user");
		String originalPassword = properties.getProperty("password");
		String originalPd = properties.getProperty("pd");
		try {
			if(originalUsername != null) {
				String newUsername = AESEncryptUtil.decrypt(originalUsername,
						originalPd);
				properties.put("user", newUsername);
			}
			if(originalPassword != null) {
				String newPassword = AESEncryptUtil.decrypt(originalPassword,
						originalPd);
				properties.put("password", newPassword);
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("数据库密码解释失败!", e);
		}
	}
}
