package com.sys.common.service.impl;

import java.util.List;

import com.sys.common.dao.impl.HibernateDaoImpl;
import com.sys.common.service.BaseService;

public abstract class BaseServiceImpl<T, ID extends java.io.Serializable>
		implements BaseService<T, ID> {
	protected HibernateDaoImpl<T, ID> hibernateDao;

	public abstract void setHibernateDao(HibernateDaoImpl<T, ID> hibernateDao);

	@Override
	public T findById(ID id) {
		return hibernateDao.findById(id);
	}

	@Override
	public List<T> listAll() {
		return hibernateDao.listAll();
	}

	@Override
	public T save(T t) {
		return hibernateDao.save(t);
	}

	@Override
	public void delete(T t) {
		hibernateDao.delete(t);
	}

	@Override
	public T update(T t) {
		return hibernateDao.update(t);
	}

	@Override
	public T saveOrUpdate(T t) {
		return hibernateDao.saveOrUpdate(t);
	}
}
