package com.app.ws.util;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * WebService工具类
 * 
 * @author: YangJunping
 * @createTime: 2012-3-31下午2:50:34
 * @version: 1.0
 * @lastVersion: 1.0
 * @updateTime: 2012-3-31下午2:50:34
 * @updateAuthor: YangJunping
 * @changesSum: TODO
 */
public class WebServiceUtil {
    /** 日志 */
    private Logger logger = Logger.getLogger(getClass());

    private static ApplicationContext context = null;
    static {
	try {
	    // 加载客户端的配置定义
	    context = new ClassPathXmlApplicationContext("config/springFile/webService.xml");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    
}
