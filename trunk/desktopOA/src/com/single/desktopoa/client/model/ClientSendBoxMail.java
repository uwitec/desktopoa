package com.single.desktopoa.client.model;

import java.util.Date;


import com.extjs.gxt.ui.client.data.BaseModelData;

public class ClientSendBoxMail extends BaseModelData {
	public Long getId() {
		return get("id");
	}
	public void setId(Long id) {
		set("id", id);
	}
	public String getSubject() {
		return get("subject");
	}
	public void setSubject(String subject) {
		set("subject", subject);
	}
	public String getContent() {
		return get("content");
	}
	public void setContent(String content) {
		set("content", content);
	}
	public String getReceiveIdList() {
		return get("receiveIdList");
	}
	public void setReceiveIdList(String receiveIdList) {
		set("receiveIdList", receiveIdList);
	}
	public String getReceiveNameList() {
		return get("receiveNameList");
	}
	public void setReceiveNameList(String receiveNameList) {
		set("receiveNameList", receiveNameList);
	}
	public Date getSendDate() {
		return get("sendDate");
	}
	public void setSendDate(Date sendDate) {
		set("sendDate", sendDate);
	}
	public Long getSenderId() {
		return get("senderId");
	}
	public void setSenderId(Long senderId) {
		set("senderId", senderId);
	}
	public String getSenderName() {
		return get("senderName");
	}
	public void setSenderName(String senderName) {
		set("senderName", senderName);
	}
	
}
