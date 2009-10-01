package com.single.desktopoa.client.module.mydesk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class MyDesk extends Window {

	private static MyDesk myDesk;

	public static MyDesk get() {
		if (myDesk == null) {
			myDesk = new MyDesk();
		}
		return myDesk;
	}

	public MyDesk() {
		setStateId("xhf-mydesk");
		setStateful(true);
		setHeading("我的办公桌");
		setId("images-view");
		setScrollMode(Scroll.AUTO);
		setSize(500, 500);
		setLayout(new ColumnLayout());
		
		ListView<ModelData> view = new ListView<ModelData>();
		view.setItemSelector("div.thumb-wrap");
		view.setTemplate(getTemplate());
		ListStore<ModelData> store = new ListStore<ModelData>();

		List<ModelData> list=new ArrayList<ModelData>();
		ModelData mail=new BaseModelData();
		mail.set("name", "邮件系统");
		mail.set("path", "icons/mydesk/mail.png");
		mail.set("shortName", "我的邮件");
		list.add(mail);
		
		ModelData notice=new BaseModelData();
		notice.set("name", "公告系统");
		notice.set("path", "icons/mydesk/notice.png");
		notice.set("shortName", "公告系统");
		list.add(notice);
		
		ModelData file=new BaseModelData();
		file.set("name", "文档系统");
		file.set("path", "icons/mydesk/file.png");
		file.set("shortName", "文档系统");
		list.add(file);
		
		
		store.add(list);

		view.setStore(store);

		add(view);
		
		
	}

	private native String getTemplate() /*-{
		return ['<tpl for=".">',
		'<div class="thumb-wrap" id="{name}" style="border: 1px solid white">',
		'<div class="thumb"><img src="{path}" title="{name}" width=128 height=128></div>',
		'<span class="x-editable">{shortName}</span></div>',
		'</tpl>',
		'<div class="x-clear"></div>'].join("");
	}-*/;
}
