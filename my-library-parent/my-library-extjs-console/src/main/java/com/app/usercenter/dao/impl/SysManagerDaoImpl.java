package com.app.usercenter.dao.impl;

import org.springframework.stereotype.Repository;

import com.app.usercenter.dao.SysManagerDao;
import com.app.usercenter.domain.SysManager;
import com.app.util.StringUtil;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.impl.HibernateDaoImpl;
import com.sys.ext.PageExtNative;

@Repository("sysManagerDao")
public class SysManagerDaoImpl extends HibernateDaoImpl<SysManager, Long> implements SysManagerDao {

    @Override
    public SysManager getSysManagerByLoginName(String loginName) {
	String findManHql = "select m from SysManager m where m.smanLoginName = ? ";
	return findObject(findManHql, loginName);
    }

    @Override
    public Wrapper getAllManagersBySearchCondition(String condition, PageExtNative page) {
	Object[] values = null;
	String hqlCount = "select count(*) from SysManager sm ";
	String hql = "select sm.smanId,sm.smanName,sm.smanLoginName,"
		+ "sm.smanEmail,sm.smanTel,sm.smanRemark, sm.smanIsStop, sm.createTime, sm.loginedTime, sm.loginedCount, sm.handledOrders, sm.handlingOrders from SysManager sm ";
	if (condition != null && !condition.trim().equals("")) {
	    hqlCount += " where ( sm.smanName like ? or sm.smanLoginName like ? ) ";
	    hql += " where (sm.smanName like ? or sm.smanLoginName like ? ) ";
	    values = new Object[]{"%" + condition.trim() + "%","%" + condition.trim() + "%"};
	}
	if (page != null && StringUtil.isNotNull(page.getSort()) && StringUtil.isNotNull(page.getDir())) {
	    hqlCount += " order by sm." + page.getSort() + " " + page.getDir();
	    hql += " order by sm." + page.getSort() + " " + page.getDir();
	}
	return super.findWrapper(hql, hqlCount, values, page.getStart(), page.getLimit());
    }

}
