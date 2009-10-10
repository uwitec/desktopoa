package com.single.desktopoa.client.module.code;

import java.util.List;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreeEvent;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.mvc.AppView;

public class CodeViewer extends Window {

	private static CodeViewer codeViewer;
	public static CodeViewer get(){
		if(codeViewer==null){
			codeViewer=new CodeViewer();
		}
		return codeViewer;
	}
	private Layout layout=new BorderLayout();
	private ContentPanel west;
	private CodeTabs center;
	private BorderLayoutData westData;
	private BorderLayoutData centerData;
	private TreePanel<ModelData> srcDir;
	private TreeStore<ModelData> store;
	private TreeLoader<ModelData> loader;
	private RpcProxy<List<ModelData>> proxy;
	private Listener<TreePanelEvent<ModelData>> treeListener=new Listener<TreePanelEvent<ModelData>>(){
		public void handleEvent(TreePanelEvent<ModelData> be) {
			ModelData data=be.getItem();
			if(data.get("folder")){
				return;
			}
			Dispatcher.forwardEvent(AppEvents.CODE_showPage, data.get("path"));
		}
	};
	private CodeViewer(){
		setHeading("源代码查看");
		setMinimizable(true);
		setMaximizable(true);
		setLayout(layout);
		setSize(800, 600);
		
		
		
	}
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		west=new ContentPanel();
		west.setLayout(new FitLayout());
		center=new CodeTabs();
		
		westData=new BorderLayoutData(LayoutRegion.WEST,200,100,550);
		westData.setCollapsible(true);
		centerData=new BorderLayoutData(LayoutRegion.CENTER);
		
		proxy=new RpcProxy<List<ModelData>>(){
			protected void load(Object loadConfig,
					AsyncCallback<List<ModelData>> callback) {
				ServiceFactory.codeService.getChildren((ModelData)loadConfig, callback);
			}
		};
		loader=new BaseTreeLoader<ModelData>(proxy){
			public boolean hasChildren(ModelData parent) {
				if(parent.<Boolean>get("folder")){
					return true;
				}
				return false;
			}
		};
		store=new TreeStore<ModelData>(loader);
		store.setStoreSorter(new StoreSorter<ModelData>(){
			public int compare(Store<ModelData> store, ModelData m1,
					ModelData m2, String property) {
				boolean folder1=m1.get("folder");
				boolean folder2=m2.get("folder");
				
				if(folder1&&!folder2){
					return -1;
				}else if(!folder1&&folder2){
					return 1;
				}
				return m1.<String>get("name").compareTo(m2.<String>get("name"));
			}
			
		});
		srcDir=new TreePanel<ModelData>(store);
		srcDir.setIconProvider(new ModelIconProvider<ModelData>(){
			public AbstractImagePrototype getIcon(ModelData model) {
				if(!model.<Boolean>get("folder")){
					String ext=model.<String>get("name").substring(model.<String>get("name").lastIndexOf(".")+1);
					if("xml".equals(ext)){
						return AppView.commonIcons.xml16();
					}else if("java".equals(ext)){
						return AppView.commonIcons.java16();
					}
				}
				return null;
			}
		});
		srcDir.addListener(Events.OnDoubleClick, treeListener);
		srcDir.setDisplayProperty("name");
		west.add(srcDir);
		
		
		add(west,westData);
		add(center,centerData);
		
		//首页
		TabItem home=new TabItem();
		home.setText("源代码");
		home.addText("欢迎查看源代码");
		center.add(home);
	}
	
	public void showCodePage(String path){
		TabItem item=center.findItem(path, false);
		if(item==null){
			item=new CodeItem(path);
			center.add(item);
		}
		if(item!=center.getSelectedItem()){
			center.setSelection(item);
		}
	}
}
