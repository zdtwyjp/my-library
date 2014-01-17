package com.sys.common.service.impl;

import com.app.util.JobInit;
import com.sys.common.service.InitParameterService;

/**
 * <p>
 * Title: InitParameterServiceImpl.java
 * </p>
 * <p>
 * Description:初始化参数Service
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
 * @date 2012-7-2上午11:14:31
 * @version 1.0
 */
public class InitParameterServiceImpl implements InitParameterService {
	private String prefixPath;

	public void init() {
		JobInit jobInit = new JobInit();
		jobInit.init();
		JobInit.doWork();
	}

	public String getPrefixPath() {
		return prefixPath;
	}

	public void setPrefixPath(String prefixPath) {
		this.prefixPath = prefixPath;
	}
}
