package com.single.desktopoa.common.dao.impl;

import java.util.List;

import javax.jdo.Query;

import com.single.desktopoa.common.dao.BaseJdoSupport;
import com.single.desktopoa.common.dao.PersonDao;
import com.single.desktopoa.common.person.Person;

public class PersonDaoJdoImpl extends BaseJdoSupport implements PersonDao {

	@Override
	public void addPerson(Person person) {
		getJdoTemplate().makePersistent(person);
	}

	@Override
	public Person findPersonById(Long id) {
		return getPM().getObjectById(Person.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Person> findPersonList() {
		return (List<Person>) getJdoTemplate().find("select from Person");
	}

	@Override
	public void updatePerson(Person person) {
		getJdoTemplate().makePersistent(person);

	}

	@Override
	@SuppressWarnings("unchecked")
	public Person findPersonByUsername(String username) {
		
		String sql="select from Person p where " +
				"p.account.username=='"+username+"'";
		List<Person> list;
		try {
			list = (List<Person>)getJdoTemplate().find(sql);
		} catch (NullPointerException e) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<Person> findPersonByDeptment(Long deptmentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
