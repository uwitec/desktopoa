package com.single.desktopoa.client.model;

import com.extjs.gxt.ui.client.event.EventType;

public class AutoRunModel {

	private EventType event;
	private boolean autoRun;
	private String name;
	private String cookieId;
	
	public EventType getEvent() {
		return event;
	}
	public void setEvent(EventType event) {
		this.event = event;
	}
	public boolean isAutoRun() {
		return autoRun;
	}
	public void setAutoRun(boolean autoRun) {
		this.autoRun = autoRun;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCookieId() {
		return cookieId;
	}
	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}
	
}
