package com.single.mydesktop.client;

import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;

public class MyShortcut extends Shortcut {

	private static SelectionListener<ComponentEvent> listener=new SelectionListener<ComponentEvent>(){
		public void componentSelected(ComponentEvent ce) {
			MyShortcut shortcut=ce.getComponent();
			EventType type=shortcut.getEvent();
			Dispatcher.forwardEvent(type);
		}
	};
	
	private String cookie;
	private Boolean defaultShow;
	private EventType event;

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public Boolean getDefaultShow() {
		return defaultShow;
	}

	public void setDefaultShow(Boolean defaultShow) {
		this.defaultShow = defaultShow;
	}

	public EventType getEvent() {
		return event;
	}

	public void setEvent(EventType event) {
		this.event = event;
		addSelectionListener(listener);
	}
}
