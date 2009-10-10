package com.single.desktopoa.client.mvc.module.file;

import java.util.List;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.desktop.client.StartMenu;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.AutoRunModel;
import com.single.desktopoa.client.model.ShortcutGroup;
import com.single.desktopoa.client.mvc.AppView;
import com.single.mydesktop.client.MyDesktop;
import com.single.mydesktop.client.MyShortcut;

public class FileController extends Controller {

	public static final String COOKIE_SHORTCUT_FILE="xhf-shortcut-file";
	public static final String COOKIE_AUTORUN_FILE="xhf-autorun-file";
	
	private FileView fileView;
	
	public FileController(){
		fileView=new FileView(this);
		
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.FILE);
		registerEventTypes(AppEvents.FILE_read,AppEvents.FILE_edit);
		
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.Init){
			List<ShortcutGroup> list=Registry.get(AppView.SHORTCUT_GROUP);
			
			ShortcutGroup group=new ShortcutGroup();
			group.setName("文档系统");
			list.add(group);
			
			
			MyShortcut shortcut=new MyShortcut();
			shortcut.setText("文档系统");
			shortcut.setIcon(AppView.appIcons.file32());
			shortcut.setCookie(COOKIE_SHORTCUT_FILE);
			shortcut.setDefaultShow(true);
			shortcut.setEvent(AppEvents.FILE);
			
			group.addMyShortcut(shortcut);
			
			MyDesktop desktop=Registry.get(AppView.DESKTOP);
			desktop.addMyShortcut(shortcut, false);
			
			//自启动
			AutoRunModel autoRunModel=new AutoRunModel();
			autoRunModel.setCookieId(COOKIE_AUTORUN_FILE);
			autoRunModel.setEvent(AppEvents.FILE);
			autoRunModel.setName("文档系统");
			AppView.autoRunList.add(autoRunModel);
			
			
			StartMenu startMenu=Registry.get(AppView.START_MENU);
			MenuItem item=new MenuItem("文档系统");
			item.setData("event", AppEvents.FILE);
			item.addSelectionListener(AppView.startMenuListener);
			startMenu.add(item);
		}
		forwardToView(fileView, event);
	}

}
