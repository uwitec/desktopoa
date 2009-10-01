package com.single.desktopoa.client.module.portal;

import java.util.List;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.single.desktopoa.client.mvc.module.portal.PortalView;

public class PortalManager extends Window {

	private MyProtal myProtal;
	private FitLayout layout;
	
	private ToolButton config;
	
	private List<Portlet> portlets=PortalView.portletList;
	
	public PortalManager(){
		super();
		
		setHeading("主界面");
		setSize(600,600);
		setMaximizable(true);
		setMinimizable(true);
		
		layout=new FitLayout();
		setLayout(layout);
		
		config=new ToolButton("x-tool-plus",new SelectionListener<IconButtonEvent>(){
			public void componentSelected(IconButtonEvent ce) {
				new Window().show();
			}
		});
		getHeader().addTool(config);
		
		
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		myProtal=new MyProtal();
		add(myProtal);
		for(Portlet portlet:portlets){
			myProtal.add(portlet, 0);
		}
	}
	protected boolean addPortlet(Portlet item) {
		if(!portlets.contains(item)){
			portlets.add(item);
			myProtal.add(item, 0);
		}
		item.show();
		return true;
	}
	protected void removePortlet(Portlet item){
		if(portlets.contains(item)){
			//myProtal.remove(item, myProtal.getPortletColumn(item));
			//portlets.remove(item);
			item.hide();
		}
	}
}
