package com.single.desktopoa.client.module.mail;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.DomEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.model.ClientInBoxMail;
import com.single.desktopoa.client.mvc.module.portal.PortalView;

public class MailPortlet extends Portlet {

	private static MailPortlet mailPortlet;
	
	private ToolButton close;
	private ToolButton refresh;
	
	private Listener<DomEvent> listener=new Listener<DomEvent>(){
		public void handleEvent(DomEvent be) {
			Html html=(Html)be.getSource();
			ServiceFactory.mailService.readInBoxMail(html.<ModelData>getData("mail").<Long>get("id"), readMailCallback);
		}
	};
	
	private AsyncCallback<List<ModelData>> callback=new AsyncCallback<List<ModelData>>(){
		public void onFailure(Throwable caught) {
		}
		public void onSuccess(List<ModelData> result) {
			removeAll();
			for(ModelData data:result){
				Html html=new Html(data.<String>get("subject"));
				html.setStyleAttribute("cursor", "pointer");
				html.setStyleAttribute("margin", "5px");
				html.setStyleAttribute("background-color", "#FFFF66");
				html.setData("mail", data);
				html.addListener(Events.OnClick, listener);
				
				add(html);
				layout();
				html.el().addEventsSunk(Event.ONCLICK);
			}
			
			
		}
	};
	private AsyncCallback<ClientInBoxMail> readMailCallback=new AsyncCallback<ClientInBoxMail>(){
		public void onFailure(Throwable caught) {
		}
		public void onSuccess(ClientInBoxMail result) {
			Dispatcher.forwardEvent(AppEvents.MAIL_showMailItem, result);
		}
	};
	
	public static MailPortlet get(){
		if(mailPortlet==null)
			mailPortlet=new MailPortlet();
		return mailPortlet;
	}
	
	private MailPortlet(){
		super();
		
		setHeight(300);
		
		setHeading("我的邮件");
		refresh=new ToolButton("x-tool-refresh",new SelectionListener<IconButtonEvent>(){
			public void componentSelected(IconButtonEvent ce) {
				refresh();
			}
		});
		
		getHeader().addTool(refresh);
		
		close=new ToolButton("x-tool-close",new SelectionListener<IconButtonEvent>(){
			public void componentSelected(IconButtonEvent ce) {
				MailPortlet.this.hide();
			}
		});
		getHeader().addTool(close);
		
		ServiceFactory.mailService.getPortalList(callback);
	}
	public void refresh(){
		ServiceFactory.mailService.getPortalList(callback);
	}
}
