package com.single.desktopoa.common.dao;

import java.util.List;

import com.single.desktopoa.common.person.Person;
import com.single.desktopoa.common.user.User;

public interface PersonDao {

	public Person findPersonById(Long id);
	
	public List<Person> findPersonList();
	
	public void addPerson(Person person);
	
	public void updatePerson(Person person);
	
	public Person findPersonByUsername(String username);
	/**
	 * 根据部门查找下属员工
	 * @param deptmentId
	 * @return
	 */
	public List<Person> findPersonByDeptment(Long deptmentId);
	
}
