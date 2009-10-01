package com.single.desktopoa.module.file.dao.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.single.desktopoa.module.file.dao.CounterSignDao;
import com.single.desktopoa.module.file.model.CounterSign;

public class CounterSignDaoImpl extends HibernateDaoSupport implements
		CounterSignDao {

	@Override
	public void add(CounterSign counterSign) {
		getHibernateTemplate().save(counterSign);
	}

	@Override
	public void update(CounterSign counterSign) {
		getHibernateTemplate().update(counterSign);
	}

}
