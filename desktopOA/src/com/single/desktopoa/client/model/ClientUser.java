package com.single.desktopoa.client.model;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class ClientUser extends BaseModelData implements Serializable {

	public Long getId() {
		return get("id");
	}
	public void setId(Long id) {
		set("id", id);
	}
	public String getUsername() {
		return get("username");
	}
	public void setUsername(String username) {
		set("username", username);
	}
	public String getPassword() {
		return get("password");
	}
	public void setPassword(String password) {
		set("password", password);
	}
	
	
}
