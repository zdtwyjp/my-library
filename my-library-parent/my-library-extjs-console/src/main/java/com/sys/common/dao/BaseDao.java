package com.sys.common.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.sys.common.core.Wrapper;
import com.sys.common.page.Pagination;

/**
 * <p>
 * Title: BaseDao.java
 * </p>
 * <p>
 * Description:基础dao
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
 * @author: YangJunping
 *          </p>
 * @date 2012-4-7下午6:41:58
 * @version 1.0
 * @param <T>
 * @param <ID>
 */
public interface BaseDao<T, ID> {
	/**
	 * 根据对象ID来查询对象。
	 * 
	 * @param id
	 *            对象ID。
	 * @param lock
	 *            锁。
	 * @return 如果找到对应的对象，则返回该对象。如果不能找到，则返回null。
	 */
	T findById(ID id, boolean lock);

	/**
	 * 根据对象ID来查询对象。
	 * 
	 * @param id
	 *            对象ID。
	 * @return 如果找到对应的对象，则返回该对象。如果不能找到，则返回null。
	 */
	T findById(ID id);

	/**
	 * 查询所有的指定对象。
	 * 
	 * @return 结果集
	 */
	List<T> listAll();

	/**
	 * <p>
	 * Title: listAll
	 * </p>
	 * <p>
	 * Description: 特定过id查询所有指定对象
	 * </p>
	 * 
	 * @param id列表
	 * @return List<T>
	 */
	List<T> listAll(List<ID> ids);

	/**
	 * 持久化指定的对象。
	 * 
	 * @param T
	 *            将要持久化的对象。
	 * @return 持久化以后的对象。
	 */
	T save(T t);

	/**
	 * 在数据库中删除指定的对象。该对象必须具有对象ID。
	 * 
	 * @param T
	 *            将要被删除的对象。
	 */
	void delete(T t);

	/**
	 * 更新给定的对象。
	 * 
	 * @param T
	 *            含有将要被更新内容的对象。
	 * @return 更新后的对象。
	 */
	T update(T t);

	/**
	 * @Title: getIDToken
	 * @Description: 获取ID字段,若实体表的ID名称不是“id”，请覆盖此方法
	 * @return String
	 * @throws
	 */
	String getIDToken();

	/**
	 * 保存T对象到数据库.
	 * 
	 * @param TObjs
	 *            待保存的T对象列表,如果为NULL,无效果.
	 * @return 实例化的对象
	 */
	Collection<T> saveAll(Collection<T> TObjs);

	/**
	 * @Title: find
	 * @Description: 通过分页对象和查询语句进行查询
	 * @param pagination
	 *            分页对象
	 * @param queryString
	 *            查询语句
	 * @return List 查询结果
	 */
	List<T> findByPagination(Pagination pagination, String queryString);

	/**
	 * @Title: find
	 * @Description: 普通查询
	 * @param queryString
	 * @return
	 * @return List<T>
	 * @throws
	 * @date 2012-4-10
	 * @author YangJunping
	 */
	List<T> find(String queryString);

	/**
	 * @Title: find
	 * @Description: 根据一个参数查询
	 * @param queryString
	 * @param value
	 * @return
	 * @return List<T>
	 * @throws
	 * @date 2012-4-10
	 * @author YangJunping
	 */
	List<T> find(String queryString, Object value);

	/**
	 * @Title: find
	 * @Description: TODO
	 * @param queryString
	 * @param value
	 * @return
	 * @return T
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-11上午9:55:11
	 */
	T findObject(String queryString, Object value);

	/**
	 * @Title: find
	 * @Description: TODO
	 * @param queryString
	 * @param value
	 * @return
	 * @return T
	 * @throws
	 * @author: YangJunping
	 * @date 2012-5-11上午11:53:15
	 */
	T findObject(String queryString, Object[] value);

	/**
	 * @Title: find
	 * @Description: 根据多个参数查询
	 * @param queryString
	 * @param values
	 * @return
	 * @return List<T>
	 * @throws
	 * @date 2012-4-10
	 * @author YangJunping
	 */
	List<T> find(String queryString, Object[] values);

	/**
	 * @Title: find
	 * @Description: 通过分页对象和查询语句进行查询
	 * @param pagination
	 *            分页对象
	 * @param queryString
	 *            查询语句
	 * @param value
	 *            参数
	 * @return List 查询结果
	 */
	List<T> findByPagination(Pagination pagination, String queryString,
			Object value);

	/**
	 * @Title: find
	 * @Description: 通过分页对象和查询语句以及计数语句进行查询
	 * @param pagination
	 *            分页对象
	 * @param queryString
	 *            查询语句
	 * @param queryCountString
	 *            计数语句
	 * @param values
	 *            参数
	 * @return List 查询结果
	 */
	List<T> findByPagination(Pagination pagination, String queryString,
			String queryCountString, Object[] values);

	/**
	 * @param pagination
	 * @param queryString
	 * @param queryCountString
	 * @param values
	 * @return
	 */
	public List<Object[]> findByPaginationBySQL(Pagination pagination,
			String queryString, String queryCountString, Object[] values);

	/**
	 * @Title: find
	 * @Description: 通过分页对象和查询语句进行查询
	 * @param pagination
	 *            分页对象
	 * @param queryString
	 *            查询语句
	 * @param values
	 *            参数
	 * @throws DataAccessException
	 *             数据访问异常
	 * @return List 查询结果
	 */
	List<T> findByPagination(Pagination pagination, String queryString,
			Object[] values);

	/**
	 * @Title: findSingle
	 * @Description: 获取单项值
	 * @param queryCountString
	 * @param values
	 * @return
	 * @return Object
	 * @throws
	 * @author: NieBin
	 * @date 2012-5-23上午11:51:52
	 */
	public Object findSingle(String queryCountString, Object[] values);

	/**
	 * @Title: find
	 * @Description: 查询条数
	 * @param queryCountString
	 *            查询语句
	 * @return
	 * @return Long
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-8上午3:59:35
	 */
	Long findCount(String queryCountString);

	/**
	 * @Title: find
	 * @Description: 查询条数
	 * @param queryCountString
	 *            查询语句
	 * @param values
	 *            参数
	 * @return
	 * @throws DataAccessException
	 * @return int 查询结果
	 */
	Long findCount(String queryCountString, Object[] values);

	/**
	 * @Title: executeUpdate
	 * @Description: 执行修改语句，不含参数
	 * @param updateHql
	 * @return
	 * @return int
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-13上午10:19:05
	 */
	int executeUpdate(String updateHql);

	/**
	 * @Title: executeUpdate
	 * @Description: 执行修改语句，含一个参数
	 * @param updateHql
	 * @param value
	 * @return
	 * @return int
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-13上午10:19:17
	 */
	int executeUpdate(String updateHql, Object value);

	/**
	 * @Title: executeUpdate
	 * @Description: 执行修改，含多个参数
	 * @param updateHql
	 * @param values
	 * @return
	 * @return int
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-13上午10:20:03
	 */
	int executeUpdate(String updateHql, Object[] values);

	/**
	 * @Title: findWrapper
	 * @Description: 不含条件的分页查询
	 * @param queryHql
	 * @param queryCountHql
	 * @param start
	 * @param limit
	 * @return
	 * @return Wrapper
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-13上午10:51:20
	 */
	Wrapper findWrapper(String queryHql, String queryCountHql, int start,
			int limit);

	/**
	 * @Title: findWrapper
	 * @Description: 含一个条件的分页查询
	 * @param queryHql
	 * @param queryCountHql
	 * @param value
	 * @param start
	 * @param limit
	 * @return
	 * @return Wrapper
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-13上午10:52:48
	 */
	Wrapper findWrapper(String queryHql, String queryCountHql, Object value,
			int start, int limit);

	/**
	 * @Title: findWrapper
	 * @Description: 含多个条件的分页查询
	 * @param queryHql
	 * @param queryCountHql
	 * @param values
	 * @param start
	 * @param limit
	 * @return
	 * @return Wrapper
	 * @throws
	 * @author: YangJunping
	 * @date 2012-4-13上午10:53:13
	 */
	Wrapper findWrapper(String queryHql, String queryCountHql, Object[] values,
			int start, int limit);

	/**
	 * @Title: findWrapperBySql
	 * @Description: 执行查询SQL
	 * @param querySql
	 *            查询SQL
	 * @param queryCountSql
	 *            查询总数SQL
	 * @param values
	 *            参数
	 * @param start
	 * @param limit
	 * @return
	 * @return Wrapper
	 * @throws
	 * @author: YangJunping
	 * @date 2012-8-27下午2:08:16
	 */
	Wrapper findWrapperBySql(String querySql, String queryCountSql,
			Object[] values, int start, int limit);

	/**
	 * @Title: findWrapperBySql
	 * @Description: 执行查询SQL
	 * @param querySql
	 *            查询SQL
	 * @param queryCountSql
	 *            查询总数SQL
	 * @param values
	 *            参数
	 * @param start
	 * @param limit
	 * @return
	 * @return Wrapper
	 * @throws
	 * @author: YangJunping
	 * @date 2012-8-27下午2:08:16
	 */
	Wrapper findWrapperBySql(String querySql, String queryCountSql,
			String sumSql, Object[] values, int start, int limit);
}
