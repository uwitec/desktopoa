package com.single.desktopoa.client.module.notice;

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
import com.single.desktopoa.client.model.ClientNotice;
import com.single.desktopoa.client.mvc.module.portal.PortalView;

public class NoticePortlet extends Portlet {

	private static NoticePortlet noticePortlet;
	private ToolButton close;
	private ToolButton refresh;
	
	
	public static NoticePortlet get(){
		if(noticePortlet==null){
			noticePortlet=new NoticePortlet();
		}
		return noticePortlet;
	}
	/**
	 * 刷新内容
	 */
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
				html.setData("notice", data);
				html.addListener(Events.OnClick, listener);
				
				add(html);
				layout();
				html.el().addEventsSunk(Event.ONCLICK);
			}
		}
	};
	
	private AsyncCallback<ClientNotice> readNoticeCallback=new AsyncCallback<ClientNotice>(){
		public void onFailure(Throwable caught) {
		}
		public void onSuccess(ClientNotice result) {
			Dispatcher.forwardEvent(AppEvents.NOTICE_display, result);
		}
	};
	/**
	 * 点击内容的处理
	 */
	private Listener<DomEvent> listener=new Listener<DomEvent>(){
		public void handleEvent(DomEvent be) {
			Html html=(Html)be.getSource();
			ServiceFactory.noticeService.readNotice(html.<ModelData>getData("notice").<Long>get("id"), readNoticeCallback);
		}
	};
	
	private NoticePortlet(){
		super();
		
		setHeading("公告栏");
		close=new ToolButton("x-tool-close",new SelectionListener<IconButtonEvent>(){
			public void componentSelected(IconButtonEvent ce) {
				NoticePortlet.this.hide();
			}
		});
		refresh=new ToolButton("x-tool-refresh",new SelectionListener<IconButtonEvent>(){
			public void componentSelected(IconButtonEvent ce) {
				refresh();
			}
		});
		
		
		getHeader().addTool(refresh);
		getHeader().addTool(close);
		
		ServiceFactory.noticeService.getPortalList(callback);
	}
	
	public void refresh(){
		ServiceFactory.noticeService.getPortalList(callback);
	}
}
