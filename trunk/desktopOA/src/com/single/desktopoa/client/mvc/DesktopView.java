package com.single.desktopoa.client.mvc;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.mvc.module.mail.MailView;

public class DesktopView extends View {

	
	private Desktop desktop;
	private SelectionListener<ComponentEvent> listener;
	
	
	public DesktopView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.Init){
			initDesktop();
		}
	}
	@Override
	protected void initialize() {
		desktop=Registry.get(AppView.DESKTOP);
	}
	protected void initDesktop() {
	}
}
