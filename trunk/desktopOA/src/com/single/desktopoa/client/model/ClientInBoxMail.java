package com.single.desktopoa.client.model;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModelData;



public class ClientInBoxMail extends BaseModelData {
	public long getId() {
		return get("id");
	}
	public void setId(long id) {
		set("id", id);
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
	public Long getReceiverId() {
		return get("receiverId");
	}
	public void setReceiverId(Long receiverId) {
		set("receiverId", receiverId);
	}
	public String getReceiverName() {
		return get("receiverName");
	}
	public void setReceiverName(String receiverName) {
		set("receiverName", receiverName);
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
		return get("receiverIdList");
	}
	public void setReceiveIdList(String receiveIdList) {
		set("receiverIdList", receiveIdList);
	}
	public String getReceiveNameList() {
		return get("receiverNameList");
	}
	public void setReceiveNameList(String receiveNameList) {
		set("receiverNameList", receiveNameList);
	}
	public Date getSendDate() {
		return get("sendDate");
	}
	public void setSendDate(Date sendDate) {
		set("sendDate", sendDate);
	}

}
