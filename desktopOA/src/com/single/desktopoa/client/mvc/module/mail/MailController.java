package com.single.desktopoa.client.mvc.module.mail;

import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.desktop.client.StartMenu;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.AutoRunModel;
import com.single.desktopoa.client.model.ShortcutModel;
import com.single.desktopoa.client.model.ShortcutModel.ShortcutWapper;
import com.single.desktopoa.client.module.mail.MailPortlet;
import com.single.desktopoa.client.mvc.AppView;
import com.single.desktopoa.client.mvc.module.portal.PortalView;

public class MailController extends Controller {

	private MailView mailView;
	public static String COOKIE_SHORTCUT_MAIL="xhf-shortcut-mail";
	public static String COOKIE_AUTORUN_MAIL="xhf-autorun-mail";
	public static String COOKIE_SHORTCUT_MAIL_NEW="xhf-shortcut-mail-new";
	
	public MailController(){
		mailView=new MailView(this);
		
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.MAIL);
		registerEventTypes(AppEvents.MAIL_nav,AppEvents.MAIL_showMailItem);
		registerEventTypes(AppEvents.MAIL_new);
		registerEventTypes(AppEvents.MAIL_reply);
		registerEventTypes(AppEvents.MAIL_forward);
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.Init){
			ShortcutModel model=new ShortcutModel();
			model.setName("邮件系统");
			
			Shortcut shortcut=new Shortcut();
			shortcut.setText("邮件系统");
			shortcut.setIcon(AppView.appIcons.mail32());
			shortcut.setData("event", AppEvents.MAIL);
			shortcut.addSelectionListener(AppView.shortcutListener);
			
			ShortcutWapper wapper=new ShortcutWapper();
			wapper.setShortcut(shortcut);
			wapper.setShow(true);
			wapper.setCookieId(COOKIE_SHORTCUT_MAIL);
			model.getShorts().add(wapper);
			
			shortcut=new Shortcut();
			shortcut.setText("新邮件");
			shortcut.setIcon(AppView.appIcons.mail32());
			shortcut.setData("event", AppEvents.MAIL_new);
			shortcut.addSelectionListener(AppView.shortcutListener);
			wapper=new ShortcutWapper();
			wapper.setShortcut(shortcut);
			wapper.setCookieId(COOKIE_SHORTCUT_MAIL_NEW);
			model.getShorts().add(wapper);
			
			AppView.shortcutList.add(model);
			
			//子启动
			AutoRunModel autoRunModel=new AutoRunModel();
			autoRunModel.setEvent(AppEvents.MAIL);
			autoRunModel.setName("邮件系统");
			autoRunModel.setCookieId(COOKIE_AUTORUN_MAIL);
			AppView.autoRunList.add(autoRunModel);
			
			
			StartMenu startMenu=Registry.get(AppView.START_MENU);
			MenuItem item=new MenuItem("邮件系统");
			item.setData("event", AppEvents.MAIL);
			item.addSelectionListener(AppView.startMenuListener);
			
				Menu menu=new Menu();
				MenuItem newMail=new MenuItem("新邮件");
				newMail.setData("event", AppEvents.MAIL_new);
				newMail.addSelectionListener(AppView.startMenuListener);
				menu.add(newMail);
				item.setSubMenu(menu);
			
			startMenu.add(item);
			
			//添加portlet
			PortalView.portletList.add(MailPortlet.get());
			
		}
		forwardToView(mailView, event);
	}

}
