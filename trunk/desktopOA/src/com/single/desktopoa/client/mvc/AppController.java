package com.single.desktopoa.client.mvc;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Window;
import com.single.desktopoa.client.event.AppEvents;

public class AppController extends Controller{

	private AppView appView;
	public AppController(){
		appView=new AppView(this);
		
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.Init_Shortcut);
		registerEventTypes(AppEvents.Init_AutoRun);
		registerEventTypes(AppEvents.ACTIVE_WIN);
	}
	@Override
	public void handleEvent(AppEvent event) {
			forwardToView(appView, event);
	}

}
