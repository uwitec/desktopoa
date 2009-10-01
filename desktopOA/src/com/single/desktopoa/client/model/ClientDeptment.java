package com.single.desktopoa.client.model;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BeanModelTag;



public class ClientDeptment extends BaseModelData implements Serializable{
	
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
	public boolean isLeaf() {
		return get("leaf");
	}
	public void setLeaf(boolean leaf) {
		set("leaf", leaf);
	}
	
}
