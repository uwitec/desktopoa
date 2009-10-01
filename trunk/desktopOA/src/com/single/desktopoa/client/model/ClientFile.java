package com.single.desktopoa.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModelData;


public class ClientFile extends BaseModelData implements Serializable {
	public static int READ_SELF=1;
	public static int READ_ASSIGN=2;
	public static int READ_ALL=3;
	
	public static int EDIT_SELF=1;
	public static int EDIT_ASSIGN=2;
	public static int EDIT_ALL=3;
	
	public static int STATUS_NONE=0;
	public static int STATUS_SIGN=1;
	
//	private Boolean over;
//	//是否有编辑权限
//	private Boolean editable;
//	//是否需要签核
//	private Boolean needCounterSign;
//	//是否可以开始会签
//	private Boolean signable;
//	//是否可以呈主管
//	private Boolean giveinable;
//	
//	private List<ClientSignRecord> signedList=new ArrayList<ClientSignRecord>();
//	private List<ClientSignRecord> unsignedList=new ArrayList<ClientSignRecord>();
	public Long getId() {
		return get("id");
	}
	public void setId(Long id) {
		set("id", id);
	}
	public int getStatus(){
		return get("status");
	}
	public void setStatus(int status){
		set("status", status);
	}
	public String getSubject() {
		return get("subject");
	}
	public void setSubject(String subject) {
		set("subject", subject);
	}
	public Date getCreateDate() {
		return get("createDate");
	}
	public void setCreateDate(Date createDate) {
		set("createDate", createDate);
	}
	public Date getLastModifyDate() {
		return get("lastModifyDate");
	}
	public void setLastModifyDate(Date lastModifyDate) {
		set("lastModifyDate", lastModifyDate);
	}
	public String getLastModifyerName() {
		return get("lastModiferName");
	}
	public void setLastModifyerName(String lastModifyerName) {
		set("lastModifyerName", lastModifyerName);
	}
	public String getContent() {
		return get("content");
	}
	public void setContent(String content) {
		set("content", content);
	}
	public Integer getReadDegree() {
		return get("readDegree");
	}
	public void setReadDegree(Integer readDegree) {
		set("readDegree", readDegree);
	}
	public String getReadList() {
		return get("readList");
	}
	public void setReadList(String readList) {
		set("readList", readList);
	}
	public Integer getEditDegree() {
		return get("editDegree");
	}
	public void setEditDegree(Integer editDegree) {
		set("editDegree", editDegree);
	}
	public String getEditList() {
		return get("editList");
	}
	public void setEditList(String editList) {
		set("editList", editList);
	}
	public Boolean getOver() {
		return get("over");
	}
	public void setOver(Boolean over) {
		set("over", over);
	}
	public Boolean getEditable() {
		return get("editable");
	}
	public void setEditable(Boolean editable) {
		set("editable", editable);
	}
	public List<ClientSignRecord> getSignedList() {
		return get("signedList");
	}
	public void setSignedList(List<ClientSignRecord> signedList) {
		set("signedList", signedList);
	}
	public List<ClientSignRecord> getUnsignedList() {
		return get("unsignedList");
	}
	public void setUnsignedList(List<ClientSignRecord> unsignedList) {
		set("unsignedList", unsignedList);
	}
	public Long getCreatorId() {
		return get("creatorId");
	}
	public void setCreatorId(Long creatorId) {
		set("creatorId", creatorId);
	}
	public Boolean getNeedCounterSign() {
		return get("needCounterSign");
	}
	public void setNeedCounterSign(Boolean needCounterSign) {
		set("needCounterSign", needCounterSign);
	}
	public String getCreatorName() {
		return get("creatorName");
	}
	public void setCreatorName(String creatorName) {
		set("creatorName", creatorName);
	}
	public Boolean getGiveinable() {
		return get("giveinable");
	}
	public void setGiveinable(Boolean giveinable) {
		set("giveinable", giveinable);
	}
	public Boolean getSignable() {
		return get("signable");
	}
	public void setSignable(Boolean signable) {
		set("signable", signable);
	}
	public String getCounterSignReason(){
		return get("counterSignReason");
	}
	public void setCounterSignReason(String reason){
		set("counterSignReason", reason);
	}
}
