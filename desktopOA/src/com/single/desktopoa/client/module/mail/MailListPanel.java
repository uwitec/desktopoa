package com.single.desktopoa.client.module.mail;


import java.util.Arrays;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.ClientInBoxMail;
import com.single.desktopoa.module.mail.service.MailService;
import com.single.desktopoa.module.mail.service.MailServiceAsync;

public class MailListPanel extends ContentPanel {

	private MailServiceAsync mailService=GWT.create(MailService.class);
	
	private Grid<ClientInBoxMail> grid;
	private ListStore<ClientInBoxMail> store;
	
	public MailListPanel(){
		setLayout(new FitLayout());
		
		ToolBar toolBar=new ToolBar();
		Button create=new Button("写邮件",new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				Dispatcher.forwardEvent(AppEvents.MAIL_new);
			}
		});
		Button reply=new Button("回复",new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(grid.getSelectionModel().getSelection().size()!=1){
					Info.display("邮件消息", "请选择一条邮件后再点<b><font color='red'>回复</font></b>");
				}else {
					AppEvent event=new AppEvent(AppEvents.MAIL_reply);
					event.setData(grid.getSelectionModel().getSelectedItem());
					Dispatcher.forwardEvent(event);
				}
			}
		});
		Button forward=new Button("转发",new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(grid.getSelectionModel().getSelection().size()!=1){
					Info.display("邮件消息", "请选择一条邮件后再点<b><font color='red'>转发</font></b>");
				}else {
					Dispatcher.forwardEvent(AppEvents.MAIL_forward,grid.getSelectionModel().getSelectedItem());
				}
			}
			
		});
		toolBar.add(create);
		toolBar.add(reply);
		toolBar.add(forward);
		
		setTopComponent(toolBar);
		
		ColumnConfig sender=new ColumnConfig("sender","发件人",100);
		sender.setRenderer(new GridCellRenderer<ModelData>(){
			@Override
			public Object render(ModelData model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<ModelData> store, Grid<ModelData> grid) {
				ClientInBoxMail mail=(ClientInBoxMail) model;;
				return mail.getSenderName();
			}
		});
		ColumnConfig subject=new ColumnConfig("subject","主题",100);
		ColumnConfig receiveList=new ColumnConfig("receiveNameList","收件人",100);
		
		ColumnModel cm=new ColumnModel(Arrays.asList(sender,subject,receiveList));
		
		RpcProxy<PagingLoadResult<ClientInBoxMail>> proxy=new RpcProxy<PagingLoadResult<ClientInBoxMail>>(){
			@Override
			protected void load(Object loadConfig,
					AsyncCallback<PagingLoadResult<ClientInBoxMail>> callback) {
				mailService.getInBoxMails((PagingLoadConfig)loadConfig, callback);
			}
		};
		
		PagingLoader<PagingLoadResult<ClientInBoxMail>> loader=new BasePagingLoader<PagingLoadResult<ClientInBoxMail>>(proxy);
		
		store=new ListStore<ClientInBoxMail>(loader);
		
		
		
		grid=new Grid<ClientInBoxMail>(store,cm);
		grid.getSelectionModel().addSelectionChangedListener(
			new SelectionChangedListener<ClientInBoxMail>(){
				@Override
				public void selectionChanged(SelectionChangedEvent<ClientInBoxMail> se) {
					ClientInBoxMail mail=(ClientInBoxMail) se.getSelectedItem();
					
					Dispatcher.forwardEvent(AppEvents.MAIL_showMailItem, mail);
				}
			}
		);
		grid.getView().setForceFit(true);
		
		PagingToolBar bar=new PagingToolBar(10);
		bar.bind(loader);
		
		setBottomComponent(bar);
		
		add(grid);
		//加载数据
		loader.load();
	}

	public Grid<ClientInBoxMail> getGrid() {
		return grid;
	}

	public ListStore<ClientInBoxMail> getStore() {
		return store;
	}
}
