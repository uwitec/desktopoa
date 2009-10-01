package com.single.desktopoa.client.module.login;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.CheckChangedEvent;
import com.extjs.gxt.ui.client.event.CheckChangedListener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel.CheckCascade;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.model.ClientDeptment;
import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.common.service.LoginService;
import com.single.desktopoa.common.service.LoginServiceAsync;

public class UserCheckboxWindow extends Dialog {

	private RpcProxy<List<ModelData>> proxy;
	private TreeLoader<ModelData> loader;
	private TreeStore<ModelData> store;
	private TreePanel<ModelData> tree;
	private LoginServiceAsync loginService=GWT.create(LoginService.class);
	private Status status;
	
	private List<ModelData> selectedUsers;
	//选择完成后的回调函数
	private static AsyncCallback<List<ModelData>> callback;
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		setHeading("人员选择");
		setSize(200, 200);
		setLayout(new FitLayout());
		setModal(true);
		setButtons("");
		
		proxy=new RpcProxy<List<ModelData>>(){
			protected void load(Object loadConfig,
					AsyncCallback<List<ModelData>> callback) {
				loginService.getPersonListWithDeptment((ModelData)loadConfig, callback);
			}
		};
		loader=new BaseTreeLoader<ModelData>(proxy){

			@Override
			public boolean hasChildren(ModelData parent) {
				if(parent instanceof ClientDeptment)
					return true;
				return false;
			}

			@Override
			public boolean loadChildren(ModelData parent) {
				return super.loadChildren(parent);
			}
			
		};
		store=new TreeStore<ModelData>(loader);
		
		tree=new TreePanel<ModelData>(store);
		tree.setCheckable(true);
		tree.setCheckStyle(CheckCascade.CHILDREN);
		tree.setDisplayProperty("name");
		
		tree.addCheckListener(new CheckChangedListener<ModelData>(){
			@Override
			public void checkChanged(CheckChangedEvent<ModelData> event) {
				List<ModelData> list=tree.getCheckedSelection();
				List<ModelData> result=new ArrayList<ModelData>();
				for(int i=0;i<list.size();i++){
					if(list.get(i) instanceof ClientPerson){
						result.add(list.get(i));
					}
				}
				selectedUsers=result;
				status.setText("选择了"+result.size()+"人");
			}
		});
		
		
		add(tree);
		
		
	}

	@Override
	protected void createButtons() {
		super.createButtons();
		status=new Status();
		status.setBox(true);
		status.setText("请选择");
		status.show();
		getButtonBar().add(status);
		getButtonBar().add(new FillToolItem());
		
		Button confirm=new Button("确定",new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				callback.onSuccess(selectedUsers);
				hide();
			}
		});
		Button cancel=new Button("取消",new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				hide();
			}
		});
		addButton(confirm);
		addButton(cancel);
	}
	
	public void showTree(String selected,AsyncCallback<List<ModelData>> callback){
		show();
//		tree.setCheckedSelection(null);
		tree.getCheckedSelection().clear();
		
//		tree.getCheckedSelection().get(arg0);
//		tree.getCheckedSelection().size();
//		tree.getView().
//		
//		if(selected!=null&&!"".equals(selected)){
//			String[] names=selected.split(",");
//			for(int i=0;i<names.length;i++){
//				for(int j=0;j<tree.getCheckedSelection().size();j++){
//					if(tree.getCheckedSelection().get(j).get("name").equals(names[i])){
//						tree.getCheckedSelection().
//					}
//				}
//			}
//		}
		this.callback=callback;
	}
	
	
	
}
