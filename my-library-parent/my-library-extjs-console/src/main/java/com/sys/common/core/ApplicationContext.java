package com.sys.common.core;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

public class ApplicationContext implements ServletContextAware {
	/** 应用上下文 */
	private static ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext context) {
		servletContext = context;
	}

	/**
	 * @Title: getServletContext
	 * @Description: 获取servletContext
	 * @return ServletContext
	 * @throws
	 */
	public static ServletContext getServletContext() {
		return servletContext;
	}
}
