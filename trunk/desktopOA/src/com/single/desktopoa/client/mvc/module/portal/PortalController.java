package com.single.desktopoa.client.mvc.module.portal;

import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.desktop.client.StartMenu;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.AutoRunModel;
import com.single.desktopoa.client.model.ShortcutModel;
import com.single.desktopoa.client.model.ShortcutModel.ShortcutWapper;
import com.single.desktopoa.client.mvc.AppView;

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
			ShortcutModel model=new ShortcutModel();
			
			ShortcutWapper wapper=new ShortcutWapper();
			
			Shortcut shortcut=new Shortcut();
			shortcut.setText("主界面");
			shortcut.setData("event", AppEvents.PORTAL);
			shortcut.addSelectionListener(AppView.shortcutListener);
			
			wapper.setShortcut(shortcut);
			wapper.setCookieId(COOKIE_SHORTCUT_PORTAL);
			
			model.setName("主界面");
			model.getShorts().add(wapper);
			AppView.shortcutList.add(model);
			
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
