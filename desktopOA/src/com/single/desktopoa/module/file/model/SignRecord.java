package com.single.desktopoa.module.file.model;

import java.util.Date;

import com.single.desktopoa.common.person.Person;
/**
 * 会签记录
 * @author Administrator
 *	@hibernate.class
 *		table="SignRecord"
 */
public class SignRecord {

	/**
	 * @hibernate.id
	 * 		generator-class="native"
	 */
	private Long id;
	/**
	 * @hibernate.many-to-one
	 */
	private Person signer;
	/**
	 * @hibernate.property
	 */
	private Date signDate;
	/**
	 * @hibernate.property
	 */
	private String suggest;
	/**
	 * @hibernate.many-to-one
	 */
	private CounterSign counterSign;
	/**
	 * @hibernate.property
	 */
	private Boolean signed=false;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Person getSigner() {
		return signer;
	}
	public void setSigner(Person signer) {
		this.signer = signer;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	public CounterSign getCounterSign() {
		return counterSign;
	}
	public void setCounterSign(CounterSign counterSign) {
		this.counterSign = counterSign;
	}
	public Boolean getSigned() {
		return signed;
	}
	public void setSigned(Boolean signed) {
		this.signed = signed;
	}
}
