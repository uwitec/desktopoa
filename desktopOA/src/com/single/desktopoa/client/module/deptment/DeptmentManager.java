package com.single.desktopoa.client.module.deptment;

import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel.CheckCascade;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.model.ClientDeptment;
import com.single.desktopoa.common.deptment.Deptment;

public class DeptmentManager extends Window {

	private RpcProxy<List<ClientDeptment>> proxy;
	private TreeLoader<ClientDeptment> loader;
	private TreeStore<ClientDeptment> store;
	private TreePanel<ClientDeptment> tree;
	private Button addDept;
	private Button removeDept;
	
	private ToolButton refresh;
	
	
	private AsyncCallback<Void> addCallback=new AsyncCallback<Void>(){
		public void onFailure(Throwable caught) {
			refresh();
			MessageBox.alert("部门添加失败", caught.getMessage(), null);
		}
		public void onSuccess(Void result) {
			refresh();
			MessageBox.alert("提醒", "添加部门成功", null);
		}
	};
	private AsyncCallback<Void> removeCallback=new AsyncCallback<Void>(){
		public void onFailure(Throwable caught) {
			refresh();
			MessageBox.alert("部门删除失败", caught.getMessage(), null);
		}
		public void onSuccess(Void result) {
			refresh();
			MessageBox.alert("提醒", "部门删除成功", null);
		}
	};
	
	public DeptmentManager(){
		setHeading("部门设置");
		setLayout(new ColumnLayout());
		setHeight(300);
		
		refresh=new ToolButton("x-tool-refresh",new SelectionListener<IconButtonEvent>(){
			public void componentSelected(IconButtonEvent ce) {
				refresh();
			}
		});
		
		getHeader().addTool(refresh);
		
		setButtonAlign(HorizontalAlignment.RIGHT);
		addDept=new Button("新增部门",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				final List<ClientDeptment> selections=tree.getCheckedSelection();
				if(selections.size()==0){
					MessageBox.confirm("提醒", "确定在根节点添加部门吗?",new Listener<MessageBoxEvent>(){
						public void handleEvent(MessageBoxEvent be) {
							if(be.getButtonClicked().getText().equals("Yes")){
								MessageBox.prompt("添加部门", "请输入新部门名称",new Listener<MessageBoxEvent>(){
									public void handleEvent(MessageBoxEvent be) {
										ClientDeptment deptment=new ClientDeptment();
										deptment.setName(be.getValue());
										ServiceFactory.deptmentService.addRootDeptment(deptment, addCallback);
									}
								});
							} 
						}
					});
				}else if(selections.size()==1){
					MessageBox.prompt("添加部门", "请输入新部门名称",new Listener<MessageBoxEvent>(){
						public void handleEvent(MessageBoxEvent be) {
							ClientDeptment deptment=new ClientDeptment();
							deptment.setName(be.getValue());
							ServiceFactory.deptmentService.addDeptment(selections.get(0).<Long>get("id"), deptment, addCallback);
						}
					});
				}else if(selections.size()>1){
					MessageBox.alert("提醒", "请不要多选", null);
				}
			}
		});
		removeDept=new Button("删除部门",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				final List<ClientDeptment> selections=tree.getCheckedSelection();
				int size=selections.size();
				if(size==0){
					MessageBox.alert("提醒", "请先选择一个部门", null);
				}else if(size==1){
					ModelData data=selections.get(0);
					ServiceFactory.deptmentService.removeDeptment(data.<Long>get("id"),removeCallback);
				}else if(size>1){
					MessageBox.alert("提醒", "请不要多选", null);
				}
			}
		});
		addButton(addDept);
		addButton(removeDept);
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		
		
		proxy=new RpcProxy<List<ClientDeptment>>(){
			protected void load(Object loadConfig,
					AsyncCallback<List<ClientDeptment>> callback) {
				if(loadConfig==null){
					ServiceFactory.deptmentService.getRootDeptment(callback);
				}
				else{
					ServiceFactory.deptmentService.getSubDeptment(((ClientDeptment)loadConfig).<Long>get("id"), callback);
				}
			}
		};
		loader=new BaseTreeLoader<ClientDeptment>(proxy){
			public boolean hasChildren(ClientDeptment parent) {
				if(!parent.isLeaf()){
					return true;
				}
				return false;
			}
		};
		
		store=new TreeStore<ClientDeptment>(loader);
		tree=new TreePanel<ClientDeptment>(store);
		tree.setDisplayProperty("name");
		tree.setCheckStyle(CheckCascade.NONE);
		add(tree);
		tree.setCheckable(true);
		
		loader.load();
		
		layout();
	}
	private void refresh(){
		loader.load();
//		String a=alert(AppEvents.MAIL_new);
//		MessageBox.alert("", a, null);
		//Dispatcher.forwardEvent(AppEvents.MAIL_new);
		
//		String regex = "#&(\\d+)&#";
//	    String str1 = "mm#&123&# nn #&344&# xx";
//	    Pattern p = Pattern.compile(regex);
//	    Matcher m = p.matcher(str1);
//	    StringBuffer sb=new StringBuffer();
//	    while(m.find()) {
//	    	m.appendReplacement(sb, "aaa");
////	      System.out.println(">>>>>"+m.group(1));
//	    }
//	    System.out.println(sb);
//		
//		Html html=new Html("FFFFFF");
//		html.addListener(Events.OnClick, new Listener<BaseEvent>(){
//			public void handleEvent(BaseEvent be) {
//				MessageBox.alert("", "click", null);
//			}
//		});
//		add(html);
		
//		
//		layout();
//		html.el().addEventsSunk(Event.ONCLICK);
	}
	
//	private native String alert(EventType type)/*-{
//		$wnd.testobject=
//			@com.extjs.gxt.ui.client.mvc.Dispatcher::forwardEvent(Lcom/extjs/gxt/ui/client/event/EventType;)(type);
//		
//		return [
//		'<a href="javascript:testobject',
//		@com.extjs.gxt.ui.client.mvc.Dispatcher::forwardEvent(Lcom/extjs/gxt/ui/client/event/EventType;)(type),
//		'">abc</a>'
//		].join("");
//	}-*/;
	//@com.extjs.gxt.ui.client.mvc.Dispatcher::forwardEvent(Lcom/extjs/gxt/ui/client/event/EventType;)(type);
}
