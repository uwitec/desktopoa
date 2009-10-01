package com.single.desktopoa.client.module.file;

import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.model.ClientFile;
import com.single.desktopoa.client.mvc.AppView;
import com.single.desktopoa.module.file.model.File;
import com.single.desktopoa.module.file.service.FileService;
import com.single.desktopoa.module.file.service.FileServiceAsync;

public class FileManager extends Window {

	private FileServiceAsync fileService=GWT.create(FileService.class);
	
	private TabPanel tabPanel;
	
	private TreeGrid<ModelData> grid;
	private TreeStore<ModelData> store;
	private TreeLoader<ModelData> loader;
	
	private Menu gridMenu;
	//查看
	private MenuItem view;
	//编辑
	private MenuItem edit;
	
	private SelectionListener<MenuEvent> listener=new SelectionListener<MenuEvent>(){

		@Override
		public void componentSelected(MenuEvent ce) {
			MenuItem item=(MenuItem)ce.getItem();
			ModelData data=grid.getSelectionModel().getSelectedItem();
			Long id=data.get("id");
			if(item==view){
				ServiceFactory.fileService.readFile(id, readCallback);
			}else if(item==edit){
				ServiceFactory.fileService.editFile(id, editCallback);
			}
		}
	};
	
	private AsyncCallback<ClientFile> readCallback=new AsyncCallback<ClientFile>(){
		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("出错了", caught.getMessage(), null);
		}
		@Override
		public void onSuccess(ClientFile result) {
			Dispatcher.forwardEvent(AppEvents.FILE_read, result);
		}
	};
	private AsyncCallback<ClientFile> editCallback=new AsyncCallback<ClientFile>(){
		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("出错了", caught.getMessage(), null);
		}
		@Override
		public void onSuccess(ClientFile result) {
			Dispatcher.forwardEvent(AppEvents.FILE_edit, result);
		}
	};
	
	public FileManager(){
		setHeading("文件管理中心");
		setPlain(true);
		setMinimizable(true);
		setMaximizable(true);
		setSize(500, 400);
		
		
		FitLayout layout=new FitLayout();
		setLayout(layout);
		
		tabPanel=new TabPanel();
		tabPanel.setMinTabWidth(50);
		
		add(tabPanel);
		
		createMainItem();
		
	}

	private void createMainItem() {
		TabItem main=new TabItem();
		main.setText("文件中心");
		main.setLayout(new FitLayout());
		tabPanel.add(main);
		
		
		ContentPanel mainPanel=new ContentPanel();
		mainPanel.setHeaderVisible(false);
		mainPanel.setFrame(true);
		mainPanel.setLayout(new FitLayout());
		main.add(mainPanel);
		
		
		
		RpcProxy<List<ModelData>> proxy=new RpcProxy<List<ModelData>>(){
			@Override
			protected void load(Object loadConfig,
					AsyncCallback<List<ModelData>> callback) {
				ModelData data=(ModelData)loadConfig;
				if(data==null){
					data=new BaseModelData();
				}
				fileService.getFileList(data, callback);
			}
		};
		
		loader=new BaseTreeLoader<ModelData>(proxy){
			@Override
			public boolean hasChildren(final ModelData parent) {
				if("true".equals(parent.get("folder"))){
					return true;
				}
				return super.hasChildren(parent);
			}
		};
		
		store=new TreeStore<ModelData>(loader);
		
		ColumnConfig subject=new ColumnConfig("subject","文件名",100);
		subject.setRenderer(new TreeGridCellRenderer<ModelData>());
		ColumnConfig creatDate=new ColumnConfig("createDate","创建时间",100);
		ColumnConfig lastModifyer=new ColumnConfig("lastModifyer","最后修改人",100);
		ColumnConfig lastModifyDate=new ColumnConfig("lastModifyDate","最后修改时间",100);
		
		ColumnModel cm=new ColumnModel(Arrays.asList(subject,creatDate,lastModifyer,lastModifyDate));
		
		grid=new TreeGrid<ModelData>(store,cm);
		grid.getView().setForceFit(true);
		grid.setContextMenu(createGridMenu());
		
		
		mainPanel.add(grid);
		
		
		ToolBar bar=new ToolBar();
		Button create=new Button("新建文档",AppView.commonIcons.add16(), new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				FileManager.this.tabPanel.add(new NewFilePanel());
			}
		});
		
		Button refresh=new Button("刷新",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				store.removeAll();
				loader.load();
			}
		});
		bar.add(create);
		bar.add(refresh);
		mainPanel.setTopComponent(bar);
		
	}
	private Menu createGridMenu() {
		gridMenu=new Menu();
		
		view=new MenuItem("查看");
		edit=new MenuItem("编辑");
		
		gridMenu.add(view);
		gridMenu.add(edit);
		
		view.addSelectionListener(listener);
		edit.addSelectionListener(listener);
//		gridMenu.addListener(Events.OnClick, listener);
		
		return gridMenu;
	}

	public void read(ClientFile file){
		TabItem item=new ViewFilePanel(file);
		tabPanel.add(item);
		tabPanel.getLayout().setActiveItem(item);
		
	}
	public void edit(ClientFile file){
		TabItem item=new EditFilePanel(file);
		tabPanel.add(item);
		tabPanel.getLayout().setActiveItem(item);
	}

	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
//		maximize();
	}
	
}
