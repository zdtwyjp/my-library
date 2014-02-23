//package com.fxpgy.lfd.usercenter.service.impl;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.fxpgy.common.core.Wrapper;
//import com.fxpgy.common.dao.impl.HibernateDaoImpl;
//import com.fxpgy.common.service.impl.BaseServiceImpl;
//import com.fxpgy.ext.PageExtNative;
//import com.fxpgy.lfd.project.domain.Consultantcount;
//import com.fxpgy.lfd.usercenter.dao.ConsultantcountDao;
//import com.fxpgy.lfd.usercenter.service.ConsultantcountService;
//
//@Service("consultantcountService")
//public class ConsultantcountServiceImpl  extends BaseServiceImpl<Consultantcount, Long>
//implements ConsultantcountService {
//
//    private ConsultantcountDao consultantcountDao;
//
//    @Resource(name = "consultantcountDao")
//    @Override
//    public void setHibernateDao(HibernateDaoImpl<Consultantcount, Long> hibernateDao) {
//	super.hibernateDao = hibernateDao;
//	this.consultantcountDao = (ConsultantcountDao) hibernateDao;
//    }	
//
//	@Override
//	public Wrapper getAllConsultantCountBySearchCondition(
//			String searchCondition, PageExtNative page) {
//		return this.consultantcountDao.getAllConsultantCountBySearchCondition(searchCondition, page);
//	}
//
//	@Override
//	public List<String> getCountYears() {
//		return this.consultantcountDao.getCountYears();
//	}
//
//	@Override
//	public List<String> getCountDate(String year) {
//		return this.consultantcountDao.getCountDate(year);
//	}	
//
//}
