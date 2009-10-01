package com.single.desktopoa.client.model;

import java.io.Serializable;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModelData;


public class ClientSignRecord  extends BaseModelData implements Serializable{
	public Long getId() {
		return get("id");
	}
	public void setId(Long id) {
		set("id", id);
	}
	public Date getSignDate() {
		return get("signDate");
	}
	public void setSignDate(Date signDate) {
		set("signDate", signDate);
	}
	public String getSuggest() {
		return get("suggest");
	}
	public void setSuggest(String suggest) {
		set("suggest", suggest);
	}
	public Boolean getSigned() {
		return get("signed");
	}
	public void setSigned(Boolean signed) {
		set("signed", signed);
	}
	public Long getSignerId() {
		return get("signerId");
	}
	public void setSignerId(Long signerId) {
		set("signerId", signerId);
	}
	public String getSignerName() {
		return get("signerName");
	}
	public void setSignerName(String signerName) {
		set("signerName", signerName);
	}
}
