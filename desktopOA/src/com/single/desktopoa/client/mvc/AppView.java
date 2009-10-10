package com.single.desktopoa.client.mvc;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.desktop.client.StartMenu;
import com.extjs.gxt.desktop.client.TaskBar;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.state.CookieProvider;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.event.CometEvents;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.icons.AppIcons;
import com.single.desktopoa.client.icons.common.CommonIcons;
import com.single.desktopoa.client.model.AutoRunModel;
import com.single.desktopoa.client.model.ShortcutGroup;
import com.single.desktopoa.client.module.login.UserCheckboxWindow;
import com.single.desktopoa.client.module.set.About;
import com.single.desktopoa.client.module.set.LockDialog;
import com.single.desktopoa.client.module.set.PersonalInfo;
import com.single.desktopoa.client.module.set.Preferences;
import com.single.desktopoa.module.comet.service.CometServiceAsync;
import com.single.mydesktop.client.MyDesktop;

public class AppView  extends View{
	//图标资源
	public static AppIcons appIcons=GWT.create(AppIcons.class);
	public static CommonIcons commonIcons=GWT.create(CommonIcons.class);
	//开始菜单listener
	public static SelectionListener<MenuEvent> startMenuListener=new SelectionListener<MenuEvent>(){
		@Override
		public void componentSelected(MenuEvent ce) {
			EventType type=ce.getItem().getData("event");
			Dispatcher.forwardEvent(type);
		}
		
	};
	//桌面快捷分组
	public List<ShortcutGroup> shortcutGroupList;
	
	//自启动列表
	public static List<AutoRunModel> autoRunList=new ArrayList<AutoRunModel>();
	
	public static String DESKTOP="desktop";
	public static String COOKIE_PROVIDER="cookieProvider";
	public static String TASKBAR="taskBar";
	public static String START_MENU="startMenu";
	public static String SHORTCUT_GROUP="shortcutGroupList";
	
	public static String USER_CHECKBOX="user_checkbox";
	
	private MyDesktop desktop;
	private TaskBar taskBar;
	private StartMenu startMenu;
	@Override
	protected void initialize() {
		desktop=new MyDesktop();
		taskBar=desktop.getTaskBar();
		
		startMenu=desktop.getStartMenu();
		
		Registry.register(DESKTOP, desktop);
		Registry.register(TASKBAR, taskBar);
		Registry.register(START_MENU, startMenu);
		
		shortcutGroupList=new ArrayList<ShortcutGroup>();
		Registry.register(SHORTCUT_GROUP, shortcutGroupList);
		
		//用户选择树
		UserCheckboxWindow window=new UserCheckboxWindow();
		Registry.register(USER_CHECKBOX,window);
	}

	public AppView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.Init){
			initApp();
		}else if(event.getType()==AppEvents.ACTIVE_WIN){
			Window window=event.getData();
			if(!desktop.getWindows().contains(window)){
				desktop.addWindow(window);
			}
			if(window!=null&&!window.isVisible()){
				window.show();
			}else{
				window.toFront();
			}
		}else if(event.getType()==AppEvents.Init_AutoRun){
			initAutoRun();
		}
		
	}

	private void initApp() {
		//Comet开启
		final CometServiceAsync cometService=ServiceFactory.cometService;
		cometService.getEvents(new AsyncCallback<List<ModelData>>(){
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(List<ModelData> result) {
				Info.display("comet", "come in");
				for(ModelData data:result){
					Dispatcher.forwardEvent(CometEvents.events.get(data.get("event")),data.get("data"));
					MessageBox.alert("event", data.<String>get("event"), null);
				}
				cometService.getEvents(this);
			}
		});
		
		MenuItem shutdown=new MenuItem("关机",new SelectionListener<MenuEvent>(){
			public void componentSelected(MenuEvent ce) {
				MessageBox.confirm("关机", "确定关闭系统吗", new Listener<MessageBoxEvent>(){
					public void handleEvent(MessageBoxEvent be) {
						if(be.getButtonClicked().getText().equals("Yes")){
							close();
						}
						
					}
				});
			}
		});
		shutdown.setIcon(commonIcons.lock16());
		MenuItem logout=new MenuItem("注销",new SelectionListener<MenuEvent>(){
			public void componentSelected(MenuEvent ce) {
				MessageBox.confirm("注销", "确定注销系统吗", new Listener<MessageBoxEvent>(){
					public void handleEvent(MessageBoxEvent be) {
						if(be.getButtonClicked().getText().equals("Yes")){
							com.google.gwt.user.client.Window.Location.reload();
						}
					}
				});
				
			}
		});
		MenuItem lock=new MenuItem("锁机",new SelectionListener<MenuEvent>(){
			public void componentSelected(MenuEvent ce) {
				LockDialog.get().lock();
			}
		});
		lock.setIcon(commonIcons.lock16());
		
		MenuItem setting=new MenuItem("设置",new SelectionListener<MenuEvent>(){
			public void componentSelected(MenuEvent ce) {
				Preferences.get().show();
			}
		});
		setting.setIcon(commonIcons.config16());
		
		startMenu.addTool(setting);
		startMenu.addToolSeperator();
		startMenu.addTool(lock);
		startMenu.addToolSeperator();
		startMenu.addTool(logout);
		startMenu.addTool(shutdown);
		
		
		//桌面右键菜单
		Menu desktopMenu=new Menu();
		MenuItem about=new MenuItem("关于",new SelectionListener<MenuEvent>(){
			public void componentSelected(MenuEvent ce) {
				About.get().show();
			}
		});
		MenuItem info=new MenuItem("用户信息",new SelectionListener<MenuEvent>(){
			public void componentSelected(MenuEvent ce) {
				PersonalInfo.get().show();
			}
		});
		MenuItem settings=new MenuItem("设置",new SelectionListener<MenuEvent>(){
			public void componentSelected(MenuEvent ce) {
				Preferences.get().show();
			}
		});
		settings.setIcon(commonIcons.config16());
		
		desktopMenu.add(settings);
		desktopMenu.add(info);
		desktopMenu.add(new SeparatorMenuItem());
		desktopMenu.add(about);
		desktop.getDesktop().setContextMenu(desktopMenu);
		
	}
	
	
	private void initAutoRun(){
		for(AutoRunModel model:autoRunList){
			model.setAutoRun(desktop.getCookieProvider().getBoolean(model.getCookieId()));
			if(model.isAutoRun()){
				Dispatcher.forwardEvent(model.getEvent());
			}
		}
	}
	
	private native void close()/*-{
	
		$wnd.close();
	}-*/;

}
