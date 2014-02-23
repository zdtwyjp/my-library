package com.sys.core;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * 
 * 
 * <p>
 * Title: IdGenerator.java
 * </p>
 * <p>
 * Description:ID生成器
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
 * @date 2012-4-12下午3:13:12
 * @version 1.0
 */
public class IdGenerator implements IdentifierGenerator {
    private static final Logger logger = Logger.getLogger(IdGenerator.class);

    /** 存储当前主键 */
    private static long key;

    /** 主键前缀,获取当前主机IP的最后一节 */
    private static int keyPre;

    public IdGenerator() {
	initKey();
    }

    /**
     * 
     * @Title: initKey
     * @Description: 获取当前主机IP的最后一节(如192.168.0.112中的112),左移48位后存入变量lastTransIP中
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午3:13:35
     */
    private static void initKey() {
	try {
	    // 否则,取当前服务器IP后面3位ID前缀
	    if (keyPre == 0) {
		InetAddress ia = InetAddress.getLocalHost();
		String strIP = ia.getHostAddress();
		int lastPoint = strIP.lastIndexOf(".");
		keyPre = Integer.parseInt(strIP.substring(lastPoint + 1));
	    }
	    makeKey(keyPre);
	} catch (UnknownHostException e) {
	    logger.error("No Host!");
	}
    }

    /**
     * 
     * @Title: makeKey
     * @Description: 生成key
     * @param seed
     * @return void
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午3:13:45
     */
    private synchronized static void makeKey(int seed) {
	long idPre = Long.parseLong(seed + "0000000000000");
	Date date = new Date();
	long longTime = date.getTime();
	key = longTime + idPre;
    }

    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
	Long result = null;
	if (object instanceof Persistent) {
	    Persistent p = (Persistent) object;
	    if (p.getId() != null) {
		result = p.getId();
	    }
	}
	if (result == null) {
	    result = new Long(key++);
	}
	return result;
    }

    /**
     * 
     * @Title: generateId
     * @Description: 获取ID值
     * @return
     * @return Long
     * @throws
     * @author: YangJunping
     * @date 2012-4-12下午3:13:55
     */
    public static Long generateId() {
	if (key == 0) {
	    initKey();
	}
	return new Long(key++);
    }

    public static void main(String[] args) {
	System.out.println(IdGenerator.generateId());
	System.out.println(IdGenerator.generateId());
	System.out.println(IdGenerator.generateId());
	System.out.println(IdGenerator.generateId());
	System.out.println(IdGenerator.generateId());
    }
}