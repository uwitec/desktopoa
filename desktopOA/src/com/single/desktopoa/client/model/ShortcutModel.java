package com.single.desktopoa.client.model;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.desktop.client.Shortcut;

public class ShortcutModel {
	private String name;
	private List<ShortcutWapper> shorts=new ArrayList<ShortcutWapper>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ShortcutWapper> getShorts() {
		return shorts;
	}
	public void setShorts(List<ShortcutWapper> shorts) {
		this.shorts = shorts;
	}
	
	public static class ShortcutWapper{
		private Shortcut shortcut;
		private boolean show;
		private String cookieId;
		public Shortcut getShortcut() {
			return shortcut;
		}
		public void setShortcut(Shortcut shortcut) {
			this.shortcut = shortcut;
		}
		public boolean isShow() {
			return show;
		}
		public void setShow(boolean show) {
			this.show = show;
		}
		public String getCookieId() {
			return cookieId;
		}
		public void setCookieId(String cookieId) {
			this.cookieId = cookieId;
		}
	}
	
}


