package com.single.desktopoa.module.file.model;

import java.io.Serializable;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BeanModelTag;
import com.single.desktopoa.common.person.Person;

/**
 * 
 * @author Administrator
 *	@hibernate.class
 *		table="file"
 */
public class File implements Serializable,BeanModelTag {

	public static int READ_SELF=1;
	public static int READ_ASSIGN=2;
	public static int READ_ALL=3;
	
	public static int EDIT_SELF=1;
	public static int EDIT_ASSIGN=2;
	public static int EDIT_ALL=3;
	
	public static int STATUS_NONE=0;
	public static int STATUS_SIGN=1;
	
	
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
	 * 		lazy="false"
	 */
	private Person creator;
	/**
	 * @hibernate.property
	 */
	private Date createDate;
	/**
	 * @hibernate.property
	 */
	private Date lastModifyDate;
	/**
	 * @hibernate.many-to-one
	 */
	private Person lastModifyer;
	/**
	 * @hibernate.property
	 * 		length="2000"
	 */
	private String content;
	
	/**
	 * @hibernate.property
	 */
	private Integer readDegree;
	/**
	 * @hibernate.property
	 */
	private String readList;
	/**
	 * @hibernate.property
	 */
	private Integer editDegree;
	/**
	 * @hibernate.property
	 */
	private String editList;
	/**
	 * 会签
	 * @hibernate.many-to-one
	 */
	private CounterSign counterSign;
	/**
	 * 是否结束
	 * @hibernate.property
	 */
	private Boolean over=false;
	/**
	 * 是否可编辑
	 * @hibernate.property
	 */
	private Boolean editable=true;
	/**
	 * @hibernate.property
	 */
	private Integer status=STATUS_NONE;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReadList() {
		return readList;
	}
	public void setReadList(String readList) {
		this.readList = readList;
	}
	public String getEditList() {
		return editList;
	}
	public void setEditList(String editList) {
		this.editList = editList;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getReadDegree() {
		return readDegree;
	}
	public void setReadDegree(Integer readDegree) {
		this.readDegree = readDegree;
	}
	public Integer getEditDegree() {
		return editDegree;
	}
	public void setEditDegree(Integer editDegree) {
		this.editDegree = editDegree;
	}
	public Person getCreator() {
		return creator;
	}
	public void setCreator(Person creator) {
		this.creator = creator;
	}
	public Person getLastModifyer() {
		return lastModifyer;
	}
	public void setLastModifyer(Person lastModifyer) {
		this.lastModifyer = lastModifyer;
	}
	public CounterSign getCounterSign() {
		return counterSign;
	}
	public void setCounterSign(CounterSign counterSign) {
		this.counterSign = counterSign;
	}
	public Boolean getOver() {
		return over;
	}
	public void setOver(Boolean over) {
		this.over = over;
	}
	public Boolean getEditable() {
		return editable;
	}
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
