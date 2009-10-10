package com.single.desktopoa.client.mvc.module.notice;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.desktop.client.StartMenu;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.AutoRunModel;
import com.single.desktopoa.client.model.ShortcutGroup;
import com.single.desktopoa.client.module.notice.NoticePortlet;
import com.single.desktopoa.client.mvc.AppView;
import com.single.desktopoa.client.mvc.module.portal.PortalView;
import com.single.mydesktop.client.MyDesktop;
import com.single.mydesktop.client.MyShortcut;

public class NoticeController extends Controller {

	public static final String COOKIE_SHORTCUT_NOTICE="xhf-shortcut-notice";
	public static final String COOKIE_AUTORUN_NOTICE="xhf-auto-notice";
	
	private NoticeView noticeView;
	
	public NoticeController(){
		noticeView=new NoticeView(this);
		
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.NOTICE);
		registerEventTypes(AppEvents.NOTICE_list,AppEvents.NOTICE_new);
		registerEventTypes(AppEvents.NOTICE_display);
	}
	@Override
	public void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.Init){
			MyDesktop desktop=Registry.get(AppView.DESKTOP);
			
			ShortcutGroup group=new ShortcutGroup();
			group.setName("公告系统");
			
			MyShortcut shortcut=new MyShortcut();
			shortcut.setText("公告系统");
			shortcut.setIcon(AppView.appIcons.notice32());
			shortcut.setEvent(AppEvents.NOTICE);
			shortcut.setCookie(COOKIE_SHORTCUT_NOTICE);
			shortcut.setDefaultShow(true);
			
			group.addMyShortcut(shortcut);
			desktop.addMyShortcut(shortcut, false);
			
			
			//自启动
			AutoRunModel autoRunModel=new AutoRunModel();
			autoRunModel.setCookieId(COOKIE_AUTORUN_NOTICE);
			autoRunModel.setEvent(AppEvents.NOTICE);
			autoRunModel.setName("公告系统");
			AppView.autoRunList.add(autoRunModel);
			
			StartMenu startMenu=Registry.get(AppView.START_MENU);
			MenuItem item=new MenuItem("公告系统");
			item.setData("event", AppEvents.NOTICE);
			item.addSelectionListener(AppView.startMenuListener);
			startMenu.add(item);
			
			//添加portlet
			PortalView.portletList.add(NoticePortlet.get());
		}
		forwardToView(noticeView, event);
	}

}
