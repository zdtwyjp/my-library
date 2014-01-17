package com.sys.common.dao;

import java.io.Serializable;

/**
 * <p>
 * Title: HibernateDao.java
 * </p>
 * <p>
 * Description:Hibernate独有的方法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company: fxpgy
 * </p>
 * <p>
 * team: fxpgy team
 * </p>
 * <p>
 * 
 * @author YangJunping
 *         </p>
 * @date 2012-4-7下午8:12:59
 * @version 1.0
 * @param <T>
 * @param <ID>
 */
public interface HibernateDao<T, ID extends Serializable> extends
		BaseDao<T, ID> {
	/**
	 * 加载ID指定的对象
	 * 
	 * @param id
	 *            对象ID
	 * @return 如果找到对应的对象，则返回该对象。
	 */
	T load(ID id);

	/**
	 * 根据实体状态，选择保存或者更新
	 * 
	 * @Title: saveOrUpdate
	 * @Description: 根据实体状态，选择保存或者更新
	 * @param t
	 *            持久化的实体
	 * @return T 实体
	 * @throws
	 */
	T saveOrUpdate(T t);

	/**
	 * 把缓冲区内的全部对象清除，但不包括操作中的对象
	 */
	void clear();

	/**
	 * 立刻执行延迟的操作
	 */
	void flush();

	/**
	 * 把指定的缓存对象进行清除
	 * 
	 * @param t
	 *            要清除的缓存对象
	 */
	void evict(T t);

	/**
	 * 在上下文中合并同一个实体
	 * 
	 * @param t
	 *            要合并的实体
	 */
	void merge(T t);

	/**
	 * 在上下文中刷新一个实体
	 * 
	 * @param t
	 *            要刷新的实体
	 */
	void refresh(T t);
}
