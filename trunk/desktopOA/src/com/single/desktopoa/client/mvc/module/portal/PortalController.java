package com.single.desktopoa.client.mvc.module.portal;

import com.extjs.gxt.desktop.client.StartMenu;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.AutoRunModel;
import com.single.desktopoa.client.model.ShortcutGroup;
import com.single.desktopoa.client.mvc.AppView;
import com.single.mydesktop.client.MyDesktop;
import com.single.mydesktop.client.MyShortcut;

public class PortalController extends Controller {
	public static final String COOKIE_SHORTCUT_PORTAL="xhf-shortcut-portal";
	public static final String COOKIE_AUTORUN_PORTAL="xhf-autorun-portal";
	
	private PortalView portalView;
	
	public PortalController(){
		portalView=new PortalView(this);
		
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.PORTAL);
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.Init){
			MyDesktop desktop=Registry.get(AppView.DESKTOP);
			
			ShortcutGroup group=new ShortcutGroup();
			group.setName("主界面");
			
			
			
			MyShortcut shortcut=new MyShortcut();
			shortcut.setText("主界面");
			shortcut.setEvent(AppEvents.PORTAL);
			shortcut.setCookie(COOKIE_SHORTCUT_PORTAL);
			shortcut.setDefaultShow(true);
			
			group.addMyShortcut(shortcut);
			desktop.addMyShortcut(shortcut,false);
			
			//自启动
			AutoRunModel autoRunModel=new AutoRunModel();
			autoRunModel.setCookieId(COOKIE_AUTORUN_PORTAL);
			autoRunModel.setName("主界面");
			autoRunModel.setEvent(AppEvents.PORTAL);
			AppView.autoRunList.add(autoRunModel);
			//开始菜单
			StartMenu startMenu=Registry.get(AppView.START_MENU);
			MenuItem item=new MenuItem("主界面");
			item.setData("event", AppEvents.PORTAL);
			item.addSelectionListener(AppView.startMenuListener);
			startMenu.add(item);
			
		}
		
		forwardToView(portalView, event);
	}

}
