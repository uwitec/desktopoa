package com.single.desktopoa.client.mvc.module.mail;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.ClientInBoxMail;
import com.single.desktopoa.client.module.mail.MailManager;
import com.single.desktopoa.client.mvc.AppView;

public class MailView extends View {

	private static MailManager mailManager;
	
	public static MailManager getMailWindow(){
		if(mailManager==null){
			mailManager= new MailManager();
		}
		return mailManager;
	}
	
	public MailView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		EventType type=event.getType();
		if(event.getType()==AppEvents.MAIL){
			Dispatcher.forwardEvent(AppEvents.ACTIVE_WIN, getMailWindow());
		}else if(event.getType()==AppEvents.MAIL_nav){
			Dispatcher.forwardEvent(AppEvents.ACTIVE_WIN, getMailWindow());
			mailManager.activeMailNav();
		}else if(event.getType()==AppEvents.MAIL_showMailItem){
			Dispatcher.forwardEvent(AppEvents.ACTIVE_WIN, getMailWindow());
			mailManager.showMailItem((ClientInBoxMail)event.getData());
		}else if(type==AppEvents.MAIL_new||type==AppEvents.MAIL_forward||type==AppEvents.MAIL_reply){
			Dispatcher.forwardEvent(AppEvents.ACTIVE_WIN, getMailWindow());
			mailManager.activeNewMail(event);
		}

	}
	
	@Override
	protected void initialize() {
		super.initialize();
//		Dispatcher.forwardEvent(AppEvents.TASKBAR_newWin, getMailWindow());
	}

}
