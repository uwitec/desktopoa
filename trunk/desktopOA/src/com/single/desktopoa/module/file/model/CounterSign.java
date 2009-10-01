package com.single.desktopoa.module.file.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.sun.org.apache.bcel.internal.generic.NEW;
/**
 * 会签信息
 * @author Administrator
 *	@hibernate.class
 *		table="CounterSign"
 */
public class CounterSign {

	/**
	 * @hibernate.id
	 * 		generator-class="native"
	 */
	private Long id;
	/**
	 * @hibernate.many-to-one
	 */
	private File file;
	/**
	 * @hibernate.property
	 */
	private Date createDate;
	/**
	 * 会签发起原因或目的
	 * @hibernate.property
	 */
	private String reason;
	/**
	 * @hibernate.set inverse="true" lazy="extra" cascade="all"
	 * @hibernate.key column="counterSign"
	 * @hibernate.one-to-many class="com.single.desktopoa.module.file.model.SignRecord"
	 */
	private Set<SignRecord> signers=new HashSet<SignRecord>();
	/**
	 * @hibernate.property
	 */
	private Boolean signOver=false;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Set<SignRecord> getSigners() {
		return signers;
	}
	public void setSigners(Set<SignRecord> signers) {
		this.signers = signers;
	}
	public Boolean getSignOver() {
		return signOver;
	}
	public void setSignOver(Boolean signOver) {
		this.signOver = signOver;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
