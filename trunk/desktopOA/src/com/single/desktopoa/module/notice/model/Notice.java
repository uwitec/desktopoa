package com.single.desktopoa.module.notice.model;

import java.io.Serializable;
import java.util.Date;

import com.single.desktopoa.common.person.Person;

/**
 * 
 * @author Administrator
 *	@hibernate.class
 *		table="notice"
 *	
 */
public class Notice implements Serializable {

	/**
	 * @hibernate.id
	 * 		generator-class="native"
	 */
	private Long id;
	/**
	 * @hibernate.property
	 */
	private String subject;
	/**
	 * @hibernate.many-to-one
	 */
	private Person creator;
	/**
	 * @hibernate.property
	 */
	private Date createDate;
	/**
	 * @hibernate.property
	 * 		length="2000"
	 */
	private String content;
	/**
	 * @hibernate.property
	 */
	private Integer width;
	/**
	 * @hibernate.property
	 */
	private Integer heigth;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Person getCreator() {
		return creator;
	}
	public void setCreator(Person creator) {
		this.creator = creator;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeigth() {
		return heigth;
	}
	public void setHeigth(Integer heigth) {
		this.heigth = heigth;
	}
}
