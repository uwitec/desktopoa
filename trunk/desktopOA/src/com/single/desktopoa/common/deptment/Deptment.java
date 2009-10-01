package com.single.desktopoa.common.deptment;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.single.desktopoa.common.person.Person;
/**
 * 
 * @author Administrator
 *	@hibernate.class
 *		table="Deptment"
 */
public class Deptment implements Serializable{

	/**
	 * @hibernate.id
	 * 		generator-class="native"
	 */
	private Long id;
	/**
	 * @hibernate.property
	 * 		length="50"
	 */
	private String name;
	/**
	 * @hibernate.many-to-one
	 */
	private Deptment highDept;
	/**
	 * @hibernate.set inverse="true" lazy="extra"
	 * @hibernate.key column="highDept"
	 * @hibernate.one-to-many class="com.single.desktopoa.common.deptment.Deptment"
	 */
	private Set<Deptment> subDept;
	/**
	 * @hibernate.property
	 */
	private Person leader;
	/**
	 * @hibernate.set inverse="true" lazy="extra"
	 * @hibernate.key column="deptment"
	 * @hibernate.one-to-many class="com.single.desktopoa.common.person.Person"
	 */
	private Set<Person> employee=new HashSet<Person>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Deptment getHighDept() {
		return highDept;
	}
	public void setHighDept(Deptment highDept) {
		this.highDept = highDept;
	}
	public Set<Deptment> getSubDept() {
		return subDept;
	}
	public void setSubDept(Set<Deptment> subDept) {
		this.subDept = subDept;
	}
	public Person getLeader() {
		return leader;
	}
	public void setLeader(Person leader) {
		this.leader = leader;
	}
	public Set<Person> getEmployee() {
		return employee;
	}
	public void setEmployee(Set<Person> employee) {
		this.employee = employee;
	}
}
