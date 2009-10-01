package com.single.desktopoa.client.module.notice;

import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.module.notice.service.NoticeServiceAsync;

public class NoticeListPanel extends ContentPanel {

	
	private NoticeManager manager;
	private Button create;
	private Button delete;
	
	private Grid<ModelData> grid;
	private ListStore<ModelData> store;
	
	private NoticeServiceAsync noticeService;
	
	private AsyncCallback<List<ModelData>> callback=new AsyncCallback<List<ModelData>>(){
		@Override
		public void onFailure(Throwable caught) {
		}
		@Override
		public void onSuccess(List<ModelData> result) {
			store.add(result);
		}
	};
	
	public NoticeListPanel(NoticeManager manager){
		super();
		
		this.manager=manager;
		setHeading("公告列表");
		
		create=new Button("新建公告",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				Dispatcher.forwardEvent(AppEvents.NOTICE_new);
			}
		});
		delete=new Button("删除");
		
		addButton(delete);
		addButton(create);
		
		setLayout(new FitLayout());
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		createGrid();
		
		ToolButton refresh=new ToolButton("x-tool-refresh",new SelectionListener<IconButtonEvent>(){
			public void componentSelected(IconButtonEvent ce) {
				store.removeAll();
				ServiceFactory.noticeService.getNoticeListWithModelData(callback);
			}
		});
		getHeader().addTool(refresh);
	}
	
	private void createGrid(){
		
		ColumnConfig subject=new ColumnConfig("subject","主题",100);
		ColumnConfig createDate=new ColumnConfig("createDate","发布时间",100);
		
		ColumnModel cm=new ColumnModel(Arrays.asList(subject,createDate));
		store=new ListStore<ModelData>();
		grid=new Grid<ModelData>(store,cm);
		grid.setAutoExpandColumn("subject");
		grid.getView().setForceFit(true);
		
		noticeService=ServiceFactory.noticeService;
		noticeService.getNoticeListWithModelData(callback);
		
		add(grid);
	}
}
