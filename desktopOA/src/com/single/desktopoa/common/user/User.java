package com.single.desktopoa.common.user;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.single.desktopoa.common.person.Person;

/**
 * 
 * @author Administrator
 *	@hibernate.class
 *		table="user"
 */
public class User implements Serializable{

	/**
	 * @hibernate.id
	 * 		generator-class="native"
	 */
	private Long id;
	/**
	 * @hibernate.property
	 * 		unique="true"
	 */
	private String username;
	/**
	 * @hibernate.property
	 */
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
