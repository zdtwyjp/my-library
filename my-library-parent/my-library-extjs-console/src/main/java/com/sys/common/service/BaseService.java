package com.sys.common.service;

import java.util.List;

/**
 * <p>
 * Title: BaseService.java
 * </p>
 * <p>
 * Description:Service基类
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
 * @date 2012-4-8上午4:17:35
 * @version 1.0
 */
public interface BaseService<T, ID extends java.io.Serializable> {
	/**
	 * 根据对象ID来查询对象。
	 * 
	 * @param id
	 *            对象ID。
	 * @return 如果找到对应的对象，则返回该对象。如果不能找到，则返回null。
	 */
	public abstract T findById(ID id);

	/**
	 * 查询所有的指定对象。
	 * 
	 * @return 结果集
	 */
	public abstract List<T> listAll();

	/**
	 * 持久化指定的对象。
	 * 
	 * @param T
	 *            将要持久化的对象。
	 * @return 持久化以后的对象。
	 */
	public abstract T save(T t);

	/**
	 * 在数据库中删除指定的对象。该对象必须具有对象ID。
	 * 
	 * @param T
	 *            将要被删除的对象。
	 */
	public abstract void delete(T t);

	/**
	 * 更新给定的对象。
	 * 
	 * @param T
	 *            含有将要被更新内容的对象。
	 * @return 更新后的对象。
	 */
	public abstract T update(T t);

	/**
	 * 保存或更新给定的对象。
	 * 
	 * @param T
	 *            含有将要被更新内容的对象。
	 * @return 更新后的对象。
	 */
	public abstract T saveOrUpdate(T t);
}
