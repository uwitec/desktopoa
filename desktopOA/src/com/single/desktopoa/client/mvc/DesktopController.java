package com.single.desktopoa.client.mvc;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.mvc.module.mail.MailController;

public class DesktopController extends Controller {

	private DesktopView desktopView;
	
	public DesktopController(){
		desktopView=new DesktopView(this);
		
		addChild(new MailController());
		
		registerEventTypes(AppEvents.Init);
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.Init){
			forwardToView(desktopView, event);
		}

	}

}
