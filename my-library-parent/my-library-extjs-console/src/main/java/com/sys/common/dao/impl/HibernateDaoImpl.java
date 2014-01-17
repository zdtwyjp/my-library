package com.sys.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.sys.common.core.Wrapper;
import com.sys.common.dao.BaseDao;
import com.sys.common.dao.HibernateDao;
import com.sys.common.page.Pagination;

/**
 * <p>
 * Title: HibernateDaoImpl.java
 * </p>
 * <p>
 * Description:Hibernate的dao实现
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
 * @date 2012-4-7下午8:41:08
 * @version 1.0
 * @param <T>
 * @param <ID>
 */
public abstract class HibernateDaoImpl<T, ID extends Serializable> implements
		HibernateDao<T, ID>, BaseDao<T, ID> {
	private Class<T> persistentClass;

	protected static final String DEFAULT_INDEIRECTLY_DELETE_TOKEN = "disused";

	/**
	 * 得到当前对象的Class对象
	 * 
	 * @return 当前对象的Class对象
	 */
	public Class<T> getPersistentClass() {
		return this.persistentClass;
	}

	public String getIDToken() {
		return "id";
	}

	/**
	 * 默认构造函数，用于获取范型T的带有类型化信息的Class对象
	 */
	@SuppressWarnings("unchecked")
	public HibernateDaoImpl() {
		this.persistentClass = (Class)((ParameterizedType)this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	@Override
	public T findById(ID id) {
		return (T)this.getSession().get(this.getPersistentClass(), id);
	}

	@Override
	public T save(T t) {
		this.getSession().save(t);
		return t;
	}

	@Override
	public List<T> listAll() {
		String hql = "from " + getPersistentClass().getSimpleName();
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	@Override
	public List<T> listAll(List<ID> ids) {
		List<T> totalList = new ArrayList<T>();
		List<ID> subIdList = null;
		String idString = null;
		String hql = null;
		Query query = null;
		List<T> subObjectList = null;
		while(ids != null && ids.size() > 0) {
			if(ids.size() > 1000) {
				subIdList = ids.subList(0, 1000);
				ids = ids.subList(1000, ids.size());
			}else {
				subIdList = ids;
				ids = null;
			}
			idString = StringUtils.join(subIdList.iterator(), ",");
			hql = "from " + getPersistentClass().getSimpleName()
					+ " obj where obj." + getIDToken() + " in ( " + idString
					+ " )";
			query = this.getSession().createQuery(hql);
			subObjectList = query.list();
			totalList.addAll(subObjectList);
		}
		return totalList;
	}

	@Override
	public T saveOrUpdate(T t) {
		this.getSession().saveOrUpdate(t);
		return t;
	}

	@Override
	public T update(T t) {
		this.getSession().update(t);
		return t;
	}

	@Override
	public void delete(T t) {
		this.getSession().delete(t);
	}

	@Override
	public T findById(ID id, boolean lock) {
		T T;
		if(lock)
			T = (T)this.getSession().load(getPersistentClass(), id,
					LockMode.UPGRADE);
		else
			T = findById(id);
		return T;
	}

	@Override
	public T load(ID id) {
		T T = (T)this.getSession().load(getPersistentClass(), id);
		return T;
	}

	@Override
	public void clear() {
		this.getSession().clear();
	}

	@Override
	public void flush() {
		this.getSession().flush();
	}

	@Override
	public void evict(T t) {
		this.getSession().evict(t);
	}

	@Override
	public void merge(T t) {
		this.getSession().merge(t);
	}

	@Override
	public void refresh(T t) {
		this.getSession().refresh(t);
	}

	@Override
	public Collection<T> saveAll(Collection<T> TObjs) {
		for(T t : TObjs) {
			save(t);
		}
		return TObjs;
	}

	public Class getGenericType(int index) {
		Type genType = getClass().getGenericSuperclass();
		if(!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		if(index >= params.length || index < 0) {
			throw new RuntimeException("Index outof bounds");
		}
		if(!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class)params[index];
	}

	@Override
	public List<T> findByPagination(Pagination pagination, String queryString) {
		return findByPagination(pagination, queryString, (Object[])null);
	}

	@Override
	public List<T> findByPagination(Pagination pagination, String queryString,
			Object value) {
		return findByPagination(pagination, queryString, new Object[] {value});
	}

	private String makeCountSql(String queryString) {
		StringTokenizer token = new StringTokenizer(queryString);
		StringBuffer result = new StringBuffer("select count(*)");
		int step = 0;
		while(token.hasMoreTokens()) {
			String s = token.nextToken();
			if(step == 0) {
				if(s.equalsIgnoreCase("from")) {
					result.append(' ');
					result.append(s);
					step = 1;
				}
			}else if(step == 1) {
				result.append(' ');
				result.append(s);
				step = 2;
			}else if(step == 2) {
				if(!s.equalsIgnoreCase("left") && !s.equalsIgnoreCase("right")
						&& !s.equalsIgnoreCase("inner")
						&& !s.equalsIgnoreCase("join")) {
					// Table alse
					result.append(' ');
					result.append(s);
				}
				step = 3;
			}else if(step == 3) {
				if(s.equalsIgnoreCase("where")) {
					result.append(' ');
					result.append(s);
					step = 4;
				}
			}else if(step == 4) {
				if(!s.equalsIgnoreCase("order")) {
					result.append(' ');
					result.append(s);
				}else {
					break;
				}
			}
		}
		return result.toString();
	}

	@Override
	public List<T> findByPagination(Pagination pagination, String queryString,
			String queryCountString, Object[] values) {
		Query queryCount = this.getSession().createQuery(queryCountString);
		if(values != null) {
			for(int i = 0; i < values.length; i++) {
				queryCount.setParameter(i, values[i]);
			}
		}
		List<T> list = queryCount.list();
		int amount = 0;
		if(!list.isEmpty()) {
			amount = ((Long)list.get(0)).intValue();
		}
		/** 设置刻录总数到分页对象中 */
		pagination.setTotalResults(amount);
		if(amount > 0) {
			/** 得到记录列表 */
			Query query = this.getSession().createQuery(queryString);
			if(pagination.getPageResults() > 0) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(pagination.getPageResults());
			}
			if(values != null) {
				for(int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}
			return query.list();
		}
		return Collections.emptyList();
	}

	@Override
	public List<Object[]> findByPaginationBySQL(Pagination pagination,
			String queryString, String queryCountString, Object[] values) {
		Query queryCount = this.getSession().createSQLQuery(queryCountString);
		if(values != null) {
			for(int i = 0; i < values.length; i++) {
				queryCount.setParameter(i, values[i]);
			}
		}
		List<T> list = queryCount.list();
		int amount = 0;
		if(!list.isEmpty()) {
			amount = ((BigInteger)list.get(0)).intValue();
		}
		/** 设置刻录总数到分页对象中 */
		pagination.setTotalResults(amount);
		if(amount > 0) {
			/** 得到记录列表 */
			Query query = this.getSession().createSQLQuery(queryString);
			if(pagination.getPageResults() > 0) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(pagination.getPageResults());
			}
			if(values != null) {
				for(int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}
			return query.list();
		}
		return Collections.emptyList();
	}

	public List<T> findByPagination(Pagination pagination, String queryString,
			Object[] values) {
		String countHql = makeCountSql(queryString);
		return findByPagination(pagination, queryString, countHql, values);
	}

	@Override
	public Object findSingle(String queryCountString, Object[] values) {
		Query query = this.getSession().createQuery(queryCountString);
		if(values != null) {
			for(int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.uniqueResult();
	}

	@Override
	public Long findCount(String queryCountString) {
		return this.findCount(queryCountString, null);
	}

	@Override
	public Long findCount(String queryCountString, Object[] values) {
		Query query = this.getSession().createQuery(queryCountString);
		if(values != null) {
			for(int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (Long)query.uniqueResult();
	}

	@Override
	public List<T> find(String queryString) {
		return this.find(queryString, null);
	}

	@Override
	public List<T> find(String queryString, Object value) {
		return this.find(queryString, new Object[] {value});
	}

	@Override
	public T findObject(String queryString, Object value) {
		List<T> list = find(queryString, value);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public T findObject(String queryString, Object[] value) {
		List<T> list = find(queryString, value);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<T> find(String queryString, Object[] values) {
		Query query = this.getSession().createQuery(queryString);
		if(values != null) {
			for(int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	@Override
	public int executeUpdate(String updateHql) {
		return this.executeUpdate(updateHql, null);
	}

	@Override
	public int executeUpdate(String updateHql, Object value) {
		return this.executeUpdate(updateHql, new Object[] {value});
	}

	@Override
	public int executeUpdate(String updateHql, Object[] values) {
		Query query = this.getSession().createQuery(updateHql);
		if(values != null) {
			for(int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public Wrapper findWrapper(String queryHql, String queryCountHql,
			int start, int limit) {
		return findWrapper(queryHql, queryCountHql, null, start, limit);
	}

	@Override
	public Wrapper findWrapper(String queryHql, String queryCountHql,
			Object value, int start, int limit) {
		return findWrapper(queryHql, queryCountHql, new Object[] {value},
				start, limit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Wrapper findWrapper(String queryHql, String queryCountHql,
			Object[] values, int start, int limit) {
		Query queryCount = this.getSession().createQuery(queryCountHql);
		if(values != null) {
			for(int i = 0; i < values.length; i++) {
				queryCount.setParameter(i, values[i]);
			}
		}
		List<T> list = queryCount.list();
		int amount = 0;
		if(!list.isEmpty()) {
			amount = ((Long)list.get(0)).intValue();
		}
		if(amount > 0) {
			/** 得到记录列表 */
			Query query = this.getSession().createQuery(queryHql);
			if(limit != 0) {
				query.setFirstResult(start);
				query.setMaxResults(limit);
			}
			if(values != null) {
				for(int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}
			return new Wrapper(query.list(), amount + "");
		}
		return new Wrapper(Collections.emptyList(), "0");
	}

	@Override
	public Wrapper findWrapperBySql(String querySql, String queryCountSql,
			Object[] values, int start, int limit) {
		SQLQuery queryCount = this.getSession().createSQLQuery(queryCountSql);
		if(values != null) {
			for(int i = 0; i < values.length; i++) {
				queryCount.setParameter(i, values[i]);
			}
		}
		List list = queryCount.list();
		int amount = 0;
		if(!list.isEmpty()) {
			amount = Integer.parseInt(list.get(0).toString());
		}
		if(amount > 0) {
			SQLQuery query = this.getSession().createSQLQuery(querySql);
			if(limit != 0) {
				query.setFirstResult(start);
				query.setMaxResults(limit);
			}
			if(values != null) {
				for(int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}
			return new Wrapper(query.list(), amount + "");
		}
		return new Wrapper(Collections.emptyList(), "0");
	}

	@Override
	public Wrapper findWrapperBySql(String querySql, String queryCountSql,
			String sumSql, Object[] values, int start, int limit) {
		// 执行countsql
		SQLQuery queryCount = this.getSession().createSQLQuery(queryCountSql);
		if(values != null) {
			for(int i = 0; i < values.length; i++) {
				queryCount.setParameter(i, values[i]);
			}
		}
		List list = queryCount.list();
		int amount = 0;
		if(!list.isEmpty()) {
			amount = Integer.parseInt(list.get(0).toString());
		}
		if(amount > 0) {
			// 执行sql
			SQLQuery query = this.getSession().createSQLQuery(querySql);
			if(limit != 0) {
				query.setFirstResult(start);
				query.setMaxResults(limit);
			}
			if(values != null) {
				for(int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}
			// 执行sumsql
			SQLQuery sumQuery = this.getSession().createSQLQuery(sumSql);
			if(values != null) {
				for(int i = 0; i < values.length; i++) {
					sumQuery.setParameter(i, values[i]);
				}
			}
			int sum = Integer.parseInt(sumQuery.list().get(0).toString());
			return new Wrapper(query.list(), amount + "", sum + " ");
		}
		return new Wrapper(Collections.emptyList(), "0", "0");
	}
}
