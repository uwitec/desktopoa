package com.single.desktopoa.client.mvc;

import com.extjs.gxt.desktop.client.StartMenu;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.client.module.mydesk.MyDesk;
import com.single.desktopoa.client.mvc.module.deptment.DeptmentView;
import com.single.desktopoa.client.mvc.module.file.FileView;
import com.single.desktopoa.client.mvc.module.mail.MailView;
import com.single.desktopoa.client.mvc.module.notice.NoticeView;
import com.single.desktopoa.client.mvc.module.portal.PortalView;
import com.single.desktopoa.client.mvc.module.worktalk.WorkTalkView;

public class StartView extends View {

	private StartMenu startMenu;
	
	
	public StartView(Controller controller) {
		super(controller);
		
		
	}

	@Override
	protected void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.Init){
			initStartMenu();
		}
	}

	private void initStartMenu() {
		startMenu=Registry.get(AppView.START_MENU);
		ClientPerson person=Registry.get("person");
		startMenu.setHeading(person.getName());
		startMenu.setIconStyle("user");
		
//		MenuItem mail=new MenuItem("邮件管理");
//		mail.setData("window", MailView.getMailWindow());
//		mail.addSelectionListener(listener);
//		
//		MenuItem file=new MenuItem("文件管理");
//		file.setData("window", FileView.getFileWindow());
//		file.addSelectionListener(listener);
//		
//		MenuItem notice=new MenuItem("公告管理");
//		notice.setData("window", NoticeView.getNoticeWindow());
//		notice.addSelectionListener(listener);
//		
//		MenuItem portal=new MenuItem("主界面");
//		portal.setData("window", PortalView.getPortalManager());
//		portal.addSelectionListener(listener);
//		
//		MenuItem mydesk=new MenuItem("我的办公桌");
//		mydesk.setData("window", MyDesk.get());
//		mydesk.addSelectionListener(listener);
//		
//		MenuItem workTalk=new MenuItem("工作通");
//		workTalk.setData("window", WorkTalkView.get());
//		workTalk.addSelectionListener(listener);
//		
//		MenuItem position=new MenuItem("职位设置");
//		position.setData("window", DeptmentView.get());
//		position.addSelectionListener(listener);
//		
//		startMenu.add(mail);
//		startMenu.add(file);
//		startMenu.add(notice);
//		startMenu.add(portal);
//		startMenu.add(mydesk);
//		startMenu.add(workTalk);
//		startMenu.add(position);
	}

}
