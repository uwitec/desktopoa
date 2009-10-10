package com.single.desktopoa.client.model;

import java.util.ArrayList;
import java.util.List;

import com.single.mydesktop.client.MyShortcut;

public class ShortcutGroup {

	private String name;
	private List<MyShortcut> myShortcuts=new ArrayList<MyShortcut>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<MyShortcut> getMyShortcuts() {
		return myShortcuts;
	}
	public void addMyShortcut(MyShortcut shortcut){
		myShortcuts.add(shortcut);
	}
	
}
