package com.single.desktopoa.common.person;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.single.desktopoa.common.deptment.Deptment;
import com.single.desktopoa.common.user.User;

/**
 * 
 * @author Administrator
 *	@hibernate.class
 *		table="person"
 */
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @hibernate.id
	 * 		generator-class="native"
	 */
	private Long id;
	/**
	 * @hibernate.property
	 * 		not-null="true"
	 */
	private String name;
	/**
	 * @hibernate.property
	 */
	private String email;
	/**
	 * @hibernate.many-to-one
	 * 		cascade="all"
	 */
	private User account;
	/**
	 * @hibernate.many-to-one
	 */
	private Deptment deptment;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public User getAccount() {
		return account;
	}
	public void setAccount(User account) {
		this.account = account;
	}
	public Deptment getDeptment() {
		return deptment;
	}
	public void setDeptment(Deptment deptment) {
		this.deptment = deptment;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
