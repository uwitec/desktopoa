package com.single.desktopoa.client.event;


import com.extjs.gxt.ui.client.event.EventType;

public class AppEvents {

	public static EventType Init=new EventType();
	public static EventType Init_Shortcut=new EventType();
	public static EventType Init_AutoRun=new EventType();
	
	public static EventType ACTIVE_WIN=new EventType();
	
	public static EventType MAIL=new EventType();
	public static EventType MAIL_new=new EventType();
	public static EventType MAIL_reply=new EventType();
	public static EventType MAIL_forward=new EventType();
	public static EventType MAIL_nav=new EventType();
	public static EventType MAIL_showMailItem=new EventType();
	
	
	public static EventType FILE=new EventType();
	public static EventType FILE_read=new EventType();
	public static EventType FILE_edit=new EventType();
	
	
	public static EventType NOTICE=new EventType();
	public static EventType NOTICE_new=new EventType();
	public static EventType NOTICE_list=new EventType();
	public static EventType NOTICE_display=new EventType();
	
	public static EventType PORTAL=new EventType();
	
	public static EventType WORKTALK=new EventType();
	//聊天消息
	public static EventType WORKTALK_msg=new EventType();
	//用户上线
	public static EventType WORKTALK_newlogin=new EventType();
	//用户下线
	public static EventType WORKTALK_logout=new EventType();
}
