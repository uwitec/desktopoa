package com.single.desktopoa.client.module.file;

import com.extjs.gxt.ui.client.widget.custom.Portlet;

public class FilePortlet extends Portlet {

	private static FilePortlet filePortlet;
	
	public static FilePortlet get(){
		if(filePortlet==null)
			filePortlet=new FilePortlet();
		return filePortlet;
	}
	
	private FilePortlet(){
		super();
	}
}
