package com.single.desktopoa.common.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.single.desktopoa.client.exception.SecurityException;
import com.single.desktopoa.common.dao.DeptmentDao;
import com.single.desktopoa.common.deptment.Deptment;
import com.single.desktopoa.common.person.Person;

public class DeptmentDaoImpl extends HibernateDaoSupport implements DeptmentDao {

	@Override
	public void addDeptment(Long leaderId ,Deptment position) {
		if(leaderId==null){
			position.setHighDept(null);
			
			getHibernateTemplate().save(position);
		}else{
			Deptment highDept=(Deptment) getHibernateTemplate().load(Deptment.class, leaderId);
			position.setHighDept(highDept);
			highDept.getSubDept().add(position);
			
			getHibernateTemplate().save(position);
			getHibernateTemplate().update(highDept);
		}
	}

	@Override
	public void removeDeptment(Long id) {
		Deptment deptment=(Deptment) getHibernateTemplate().get(Deptment.class, id);
		getHibernateTemplate().delete(deptment);
	}

	@Override
	public Deptment findDeptmentById(Long id) {
		return (Deptment) getHibernateTemplate().get(Deptment.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Deptment> findRootDeptment() {
		return getHibernateTemplate().find("from Deptment d where d.highDept is null");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Deptment> findSubDeptment(Long parentId) {
		return getHibernateTemplate().find("from Deptment d where d.highDept.id="+parentId);
	}

}
