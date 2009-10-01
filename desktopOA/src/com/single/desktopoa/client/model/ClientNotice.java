package com.single.desktopoa.client.model;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModelData;


public class ClientNotice extends BaseModelData {

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
	public Long getCreator() {
		return get("creator");
	}
	public void setCreator(Long creator) {
		set("creator", creator);
	}
	public Date getCreateDate() {
		return get("createDate");
	}
	public void setCreateDate(Date createDate) {
		set("createDate", createDate);
	}
	public String getContent() {
		return get("content");
	}
	public void setContent(String content) {
		set("content", content);
	}
	public Integer getWidth() {
		return get("width");
	}
	public void setWidth(Integer width) {
		set("width", width);
	}
	public Integer getHeigth() {
		return get("height");
	}
	public void setHeigth(Integer heigth) {
		set("height", heigth);
	}
	
}
