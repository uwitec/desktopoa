package com.single.desktopoa.client.module.set;

import com.extjs.gxt.ui.client.widget.Window;

public class About extends Window {

	private static About about;
	public static About get(){
		if(about==null){
			about=new About();
		}
		return about;
	}
	
	private About(){
		super();
		String text="DesktopOA v0.1<br>版权所有:徐恒飞";
		addText(text);
		setSize(300, 400);
	}
}
