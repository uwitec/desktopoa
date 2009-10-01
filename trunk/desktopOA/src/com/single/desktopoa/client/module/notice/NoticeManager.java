package com.single.desktopoa.client.module.notice;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.google.gwt.user.client.Element;

public class NoticeManager extends Window {

	
	private NoticeListPanel noticeListPanel;
	private NewNoticePanel newNoticePanel;
	
	private CardLayout layout=new CardLayout();
	
	public NoticeManager(){
		super();
		
		setSize(500, 400);
		setMinimizable(true);
		setMaximizable(true);
		setLayout(layout);
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		noticeListPanel=new NoticeListPanel(this);
		newNoticePanel=new NewNoticePanel(this);
		
		add(noticeListPanel);
		add(newNoticePanel);
		
		layout.setActiveItem(newNoticePanel);
	}
	
	public void activeNewNotice(Object data){
		layout.setActiveItem(newNoticePanel);
	}
	public void activeNoticeList(Object data){
		layout.setActiveItem(noticeListPanel);
	}
}
