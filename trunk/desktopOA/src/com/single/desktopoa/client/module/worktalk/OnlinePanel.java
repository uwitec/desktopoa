package com.single.desktopoa.client.module.worktalk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.model.TalkMessage;

public class OnlinePanel extends ContentPanel {

	private TreePanel<ModelData> tree;
	private TreeStore<ModelData> store;
	private TreeLoader<ModelData> loader;
	private RpcProxy<List<ModelData>> proxy;
	
	private List<TalkDialog> dialogs=new ArrayList<TalkDialog>();
	private Map<Long, Queue<TalkMessage>> leaves=new HashMap<Long, Queue<TalkMessage>>();
	
	private WindowListener dialogListener;
	
	private Menu contextMenu;
	private MenuItem talk;
	private MenuItem personInfo;
	
	
	
	private Listener<MenuEvent> talkListener=new Listener<MenuEvent>(){
		public void handleEvent(MenuEvent be) {
			
		}
	};
	
	public OnlinePanel(){
		super();
		
		setHeading("在线同事");
		setLayout(new FitLayout());
	}

	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		initialListener();
		
		createTree();
	}

	private void initialListener() {
		dialogListener=new WindowListener(){

			@Override
			public void windowActivate(WindowEvent we) {
				// TODO Auto-generated method stub
				super.windowActivate(we);
			}

			@Override
			public void windowDeactivate(WindowEvent we) {
				// TODO Auto-generated method stub
				super.windowDeactivate(we);
			}

			@Override
			public void windowHide(WindowEvent we) {
				// TODO Auto-generated method stub
				super.windowHide(we);
			}

			@Override
			public void windowMinimize(WindowEvent we) {
				// TODO Auto-generated method stub
				super.windowMinimize(we);
			}
			
		};
	}

	private void createTree() {
		contextMenu=new Menu();
		personInfo=new MenuItem("XXX");
		talk=new MenuItem("聊天");
			talk.addSelectionListener(new SelectionListener<MenuEvent>(){
				public void componentSelected(MenuEvent ce) {
					createDialog(talk.<ModelData>getData("personData"));
				}
			});
		contextMenu.add(talk);
		contextMenu.add(personInfo);
		
		
		proxy=new RpcProxy<List<ModelData>>(){
			protected void load(Object loadConfig,
					AsyncCallback<List<ModelData>> callback) {
				ServiceFactory.workTalkService.getOnlineList(callback);
			}
		};
		loader=new BaseTreeLoader<ModelData>(proxy);
		store=new TreeStore<ModelData>(loader);
		
		tree=new TreePanel<ModelData>(store);
		tree.addListener(Events.ContextMenu, new Listener<TreePanelEvent<ModelData>>(){
			public void handleEvent(TreePanelEvent<ModelData> be) {
				talk.setText("与"+be.getItem().get("name")+"聊天");
				talk.setData("personData", be.getItem());
				personInfo.setText(be.getItem().<String>get("name"));
			}
		});
		tree.setContextMenu(contextMenu);
		tree.setDisplayProperty("name");
		
		add(tree);
		
		loader.load();
	}
	
	public void refresh(){
		store.removeAll();
		loader.load();
	}
	private void createDialog(ModelData personData){
		for(TalkDialog dialog:dialogs){
			if(dialog.getPersonId()==personData.get("id")){
				if(!dialog.isVisible()){
					dialog.show();
				}
				dialog.toFront();
				return;
			}
		}
		TalkDialog dialog=new TalkDialog(personData);
		dialogs.add(dialog);
		dialog.show();
		dialog.toFront();
	}
	private void activeDialog(Long personId){
		for(TalkDialog dialog:dialogs){
			if(dialog.getPersonId()==personId){
				if(!dialog.isVisible()){
					dialog.show();
				}
				dialog.toFront();
				//加载未读消息
				Queue<TalkMessage> queue=leaves.get(personId);
				if(queue!=null&&queue.size()>0){
					while(queue.size()>0){
						dialog.addMessage(queue.poll());
					}
				}
				
				return;
			}
		}
		
	}
	
	public void receiveMsg(TalkMessage message) {
		Long personId=message.getFrom().getId();
		if(leaves.get(personId)==null){
			Queue<TalkMessage> queue=new LinkedList<TalkMessage>();
			leaves.put(personId, queue);
		}
		leaves.get(personId).add(message);
		
		activeDialog(personId);
	}
}
