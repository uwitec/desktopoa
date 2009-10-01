package com.single.desktopoa.client.model;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BeanModelTag;


public class ClientPerson extends BaseModelData implements Serializable{
	
	
	public Long getId() {
		return get("id");
	}
	public void setId(Long id) {
		set("id", id);
	}
	public String getName() {
		return get("name");
	}
	public void setName(String name) {
		set("name", name);
	}
	public String getEmail() {
		return get("email");
	}
	public void setEmail(String email) {
		set("email", email);
	}
	public Long getAccount() {
		return get("account");
	}
	public void setAccount(Long account) {
		set("account", account);
	}
}
