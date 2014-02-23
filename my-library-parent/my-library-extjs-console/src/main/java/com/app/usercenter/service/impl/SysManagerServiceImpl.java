package com.app.usercenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.exception.ErrorMessageException;
import com.app.usercenter.dao.SysManagerDao;
import com.app.usercenter.dao.SysRoleDao;
import com.app.usercenter.dao.SysUserRoleDao;
import com.app.usercenter.domain.SysManager;
import com.app.usercenter.domain.SysRole;
import com.app.usercenter.domain.SysUserRole;
import com.app.usercenter.service.SysManagerService;
import com.sys.common.core.Wrapper;
import com.sys.common.dao.impl.HibernateDaoImpl;
import com.sys.common.service.impl.BaseServiceImpl;
import com.sys.core.PasswordAuthenticator;
import com.sys.ext.PageExtNative;

@Service("sysManagerService")
public class SysManagerServiceImpl extends BaseServiceImpl<SysManager, Long> implements SysManagerService {

    private SysManagerDao sysManagerDao;

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Resource
    private SysRoleDao sysRoleDao;

    @Resource(name = "sysManagerDao")
    @Override
    public void setHibernateDao(HibernateDaoImpl<SysManager, Long> hibernateDao) {
	this.hibernateDao = hibernateDao;
	this.sysManagerDao = (SysManagerDao) hibernateDao;
    }

    @Override
    public SysManager managerLogin(String userName, String password, String remoteHost) {
	String hql = "from SysManager sui where sui.smanLoginName = ? and sui.smanIsStop is false";
	SysManager sysManager = this.sysManagerDao.findObject(hql, userName);
	if (sysManager != null) {
	    try {
		if (!PasswordAuthenticator.authenticatePassword(password, sysManager.getSmanPwd())) {
		    sysManager = null;
		}
	    } catch (Exception e) {
		e.printStackTrace();
		sysManager = null;
	    }
	}
	return sysManager;
    }

    @Override
    public Map<String, List> getManagerInfoById(Long id) {
	Map retv = new HashMap();
	List<SysUserRole> managerRoles = sysUserRoleDao.getRolesByManagerId(id);
	retv.put("managerRoles", managerRoles);
	SysManager sysManager = sysManagerDao.findById(id);
	List manList = new ArrayList();
	manList.add(sysManager);
	retv.put("manager", manList);
	return retv;
    }

    @Override
    public SysManager saveManager(SysManager manager, SysRole[] roles) {
	SysManager man = this.sysManagerDao.getSysManagerByLoginName(manager.getSmanLoginName());
	if (man != null) {
	    throw new ErrorMessageException("账号重复，请重新填写!");
	}
	;
	this.save(manager);
	if (roles != null && roles.length > 0) {
	    for (int i = 0; i < roles.length; i++) {
		SysUserRole sur = new SysUserRole();
		sur.setRole(roles[i]);
		sur.setUser(manager);
		this.sysUserRoleDao.save(sur);
	    }
	}
	return manager;
    }

    @Override
    public Wrapper getAllManagersBySearchCondition(String condition, PageExtNative page) {
	Wrapper wrapper = sysManagerDao.getAllManagersBySearchCondition(condition, page);
	List list = (List) wrapper.getResult();
	List managerRoles = null;
	for (int i = 0; i < list.size(); i++) {
	    Object[] objects = (Object[]) list.get(i);
	    Object[] newObjects = new Object[objects.length + 1];
	    System.arraycopy(objects, 0, newObjects, 0, objects.length);
	    managerRoles = sysRoleDao.getRoleByRoleName(objects[0].toString());
	    newObjects[newObjects.length - 1] = managerRoles.toString().substring(1, managerRoles.toString().length() - 1);
	    list.set(i, newObjects);
	}

	return wrapper;
    }

    @Override
    public void updateManagerInfo(SysManager manager, SysRole[] roles) {
	SysManager man = this.sysManagerDao.getSysManagerByLoginName(manager.getSmanLoginName());
	if (man != null && !man.getSmanId().equals(manager.getSmanId())) {
	    throw new ErrorMessageException("账号重复，请重新填写!");
	}
	List managerRoles = this.sysUserRoleDao.getRolesByManagerId(manager.getSmanId());
	if (roles != null && roles.length > 0) {
	    if (managerRoles == null || managerRoles.size() == 0) {
		for (int i = 0; i < roles.length; i++) {
		    SysUserRole newSur = new SysUserRole();
		    newSur.setRole(roles[i]);
		    newSur.setUser(manager);
		    this.sysUserRoleDao.save(newSur);
		}
	    } else {
		for (int j = 0; j < managerRoles.size(); j++) {
		    SysUserRole sur = (SysUserRole) managerRoles.get(j);
		    for (int k = 0; k < roles.length; k++) {
			if (sur.getSuroId().equals(roles[k].getSrolId())) {
			    continue;
			} else {
			    this.sysUserRoleDao.delete(sur);
			    SysUserRole newSur = new SysUserRole();
			    newSur.setRole(roles[k]);
			    newSur.setUser(manager);
			    this.sysUserRoleDao.save(newSur);
			}
		    }
		}
	    }
	} else {
	    this.sysUserRoleDao.deleteSysUserRoleByManagerId(manager.getSmanId());
	}
	this.sysManagerDao.merge(manager);
    }

    @Override
    public void updateSysManagerStatus(Long managerId) {
	SysManager manager = this.sysManagerDao.findById(managerId);
	if (manager.getSmanIsStop()) {
	    manager.setSmanIsStop(false);
	} else {
	    manager.setSmanIsStop(true);
	}
	manager.setUpdateTime(new Date());
	this.sysManagerDao.save(manager);
    }

}
