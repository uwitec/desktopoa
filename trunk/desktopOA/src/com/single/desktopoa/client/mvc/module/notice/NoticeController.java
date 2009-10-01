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
import com.single.desktopoa.client.model.ShortcutModel;
import com.single.desktopoa.client.model.ShortcutModel.ShortcutWapper;
import com.single.desktopoa.client.module.notice.NoticePortlet;
import com.single.desktopoa.client.mvc.AppView;
import com.single.desktopoa.client.mvc.module.portal.PortalView;

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
			Shortcut shortcut=new Shortcut();
			shortcut.setText("公告系统");
			shortcut.setIcon(AppView.appIcons.notice32());
			shortcut.setData("event", AppEvents.NOTICE);
			shortcut.addSelectionListener(AppView.shortcutListener);
			
			ShortcutModel model=new ShortcutModel();
			model.setName("公告系统");
			ShortcutWapper short1=new ShortcutWapper();
			short1.setCookieId(COOKIE_SHORTCUT_NOTICE);
			short1.setShortcut(shortcut);
			
			model.getShorts().add(short1);
			AppView.shortcutList.add(model);
			
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
