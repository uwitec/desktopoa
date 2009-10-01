package com.single.desktopoa.client.module.portal;


import java.util.List;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;

public class MyProtal extends Portal {

	public MyProtal() {
		super(2);
		
		this.setColumnWidth(0, .5);
		this.setColumnWidth(0, .5);
	}
	
	@Override
	protected boolean remove(LayoutContainer item) {
		List<Listener<?>> listeners=getListeners(Events.Remove);
		listeners.toString();
		return super.remove(item);
		
	}
	
	@Override
	public void remove(Portlet portlet, int column) {
		super.remove(portlet, column);
		fireEvent(Events.Remove);
	}

}
