package com.single.desktopoa.client.mvc.module.worktalk;

import java.util.List;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.desktop.client.StartMenu;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.AutoRunModel;
import com.single.desktopoa.client.model.ShortcutGroup;
import com.single.desktopoa.client.mvc.AppView;
import com.single.desktopoa.client.mvc.module.mail.MailView;
import com.single.mydesktop.client.MyDesktop;
import com.single.mydesktop.client.MyShortcut;

public class WorkTalkController extends Controller {

	public static final String COOKIE_SHORTCUT_WORKTALK="xhf-shortcut-worktalk";
	public static final String COOKIE_AUTORUN_WORKTALK="xhf-autorun-worktalk";
	
	private WorkTalkView workTalkView;
	
	public WorkTalkController(){
		workTalkView=new WorkTalkView(this);
		
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.WORKTALK);
		registerEventTypes(AppEvents.WORKTALK_msg);
		registerEventTypes(AppEvents.WORKTALK_logout);
		registerEventTypes(AppEvents.WORKTALK_newlogin);
	}
	@Override
	public void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.Init){
			MyDesktop desktop=Registry.get(AppView.DESKTOP);
			List<ShortcutGroup> list=Registry.get(AppView.SHORTCUT_GROUP);
			
			ShortcutGroup group=new ShortcutGroup();
			group.setName("工作通");
			list.add(group);
			
			
			MyShortcut shortcut=new MyShortcut();
			shortcut.setText("工作通");
			shortcut.setEvent(AppEvents.WORKTALK);
			shortcut.setIcon(AppView.appIcons.workTalk32());
			shortcut.setDefaultShow(true);
			shortcut.setCookie(COOKIE_SHORTCUT_WORKTALK);

			group.addMyShortcut(shortcut);
			desktop.addMyShortcut(shortcut, false);
			
			//自启动
			AutoRunModel autoRunModel=new AutoRunModel();
			autoRunModel.setCookieId(COOKIE_AUTORUN_WORKTALK);
			autoRunModel.setEvent(AppEvents.WORKTALK);
			autoRunModel.setName("工作通");
			
			StartMenu startMenu=Registry.get(AppView.START_MENU);
			MenuItem item=new MenuItem("工作通");
			item.setData("event", AppEvents.WORKTALK);
			item.addSelectionListener(AppView.startMenuListener);
			startMenu.add(item);
		}
		forwardToView(workTalkView, event);
	}

}
