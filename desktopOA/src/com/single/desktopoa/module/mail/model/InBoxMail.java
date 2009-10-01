package com.single.desktopoa.module.mail.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.extjs.gxt.ui.client.data.BeanModelTag;
import com.single.desktopoa.common.person.Person;

/**
 * 
 * @author Administrator
 *	@hibernate.class
 *		table="inboxMail"
 */
@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class InBoxMail implements Serializable,BeanModelTag{

	/**
	 * @hibernate.id
	 * 		generator-class="native"
	 */
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private long id;
	/**
	 * @hibernate.many-to-one
	 * 		lazy="false"
	 */
	@Persistent
	private Person sender;
	/**
	 * @hibernate.many-to-one
	 * 		not-null="true"
	 */
	@Persistent
	private Person receiver;
	/**
	 * @hibernate.property
	 */
	@Persistent
	private String subject;
	/**
	 * @hibernate.property
	 */
	@Persistent
	private String content;
	/**
	 * @hibernate.property
	 */
	@Persistent
	private String receiveIdList;
	
	/**
	 * @hibernate.property
	 */
	@Persistent
	private String receiveNameList;
	/**
	 * @hibernate.property
	 */
	@Persistent
	private Date sendDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


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


	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Person getSender() {
		return sender;
	}

	public void setSender(Person sender) {
		this.sender = sender;
	}

	public Person getReceiver() {
		return receiver;
	}

	public void setReceiver(Person receiver) {
		this.receiver = receiver;
	}

	public String getReceiveIdList() {
		return receiveIdList;
	}

	public void setReceiveIdList(String receiveIdList) {
		this.receiveIdList = receiveIdList;
	}

	public String getReceiveNameList() {
		return receiveNameList;
	}

	public void setReceiveNameList(String receiveNameList) {
		this.receiveNameList = receiveNameList;
	}
}
