package com.single.desktopoa.client.event;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.event.EventType;

public class CometEvents {

	public static Map<String, EventType> events=new HashMap<String, EventType>();
	
	public static String MAIL_receive="MAIL_receive";
	
	public static String NOTICE_receive="NOTICE_receive";
	
	public static String WORKTALK_msg="WORKTALK_msg";
	
	public static String WORKTALK_newlogin="WORKTALK_newlogin";
	
	static{
		
		events.put(WORKTALK_msg, AppEvents.WORKTALK_msg);
		
		events.put(WORKTALK_newlogin, AppEvents.WORKTALK_newlogin);
	}
}
