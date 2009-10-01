package com.single.desktopoa.client.mvc;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.single.desktopoa.client.event.AppEvents;

public class StartController extends Controller {

	private StartView startView;
	
	public StartController(){
		startView=new StartView(this);
		
		registerEventTypes(AppEvents.Init);
	}
	@Override
	public void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.Init){
			forwardToView(startView, event);
		}

	}

}
