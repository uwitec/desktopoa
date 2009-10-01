package com.single.desktopoa.common.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.single.desktopoa.common.dao.PersonDao;
import com.single.desktopoa.common.person.Person;
import com.sun.xml.internal.bind.v2.model.core.ID;

public class PersonDaoImpl extends HibernateDaoSupport implements PersonDao {

	@Override
	public Person findPersonById(Long id) {
		return (Person)getHibernateTemplate().get(Person.class, id);
	}

	@Override
	public List<Person> findPersonList() {
		return getHibernateTemplate().find("from Person");
	}

	@Override
	public void addPerson(Person person) {
		System.out.println(person.getAccount());
		if(person.getAccount().getId()==null){
			getHibernateTemplate().save(person.getAccount());
		}
		getHibernateTemplate().save(person);
	}

	@Override
	public void updatePerson(Person person) {
		getHibernateTemplate().update(person);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Person findPersonByUsername(String username) {
		List<Person> list=getHibernateTemplate().find("from Person p where p.account.username='"+username+"'");
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> findPersonByDeptment(Long deptmentId) {
		return getHibernateTemplate().find("from Person p where p.deptment.id="+deptmentId);
	}

}
