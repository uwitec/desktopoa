package com.single.desktopoa.client.mvc.module.portal;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.module.portal.MyProtal;
import com.single.desktopoa.client.module.portal.PortalManager;

public class PortalView extends View {

	public static List<Portlet> portletList=new ArrayList<Portlet>();
	
	private static PortalManager portalManager;
	
	public static PortalManager getPortalManager(){
		if(portalManager==null){
			portalManager=new PortalManager();
		}
		return portalManager;
	}
	
	public PortalView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.PORTAL){
			Dispatcher.forwardEvent(AppEvents.ACTIVE_WIN,getPortalManager());
		}
	}

}
