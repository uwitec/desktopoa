package com.single.desktopoa.client.mvc.module.code;

import java.util.List;

import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.ShortcutGroup;
import com.single.desktopoa.client.mvc.AppView;
import com.single.mydesktop.client.MyDesktop;
import com.single.mydesktop.client.MyShortcut;

public class CodeController extends Controller {

	public static String COOKIE_SHORTCUT_CODE="xhf-shortcut-code";
	public static String COOKIE_AUTORUN_CODE="xhf-autorun-code";
	
	private CodeView codeView;
	public CodeController(){
		codeView=new CodeView(this);
		
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.CODE);
		registerEventTypes(AppEvents.CODE_showPage);
	}
	@Override
	public void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.Init){
			List<ShortcutGroup> list=Registry.get(AppView.SHORTCUT_GROUP);
			
			ShortcutGroup group=new ShortcutGroup();
			group.setName("源代码");
			list.add(group);
			
			MyShortcut shortcut=new MyShortcut();
			shortcut.setText("源代码");
			shortcut.setIcon(AppView.appIcons.file32());
			shortcut.setCookie(COOKIE_SHORTCUT_CODE);
			shortcut.setDefaultShow(true);
			shortcut.setEvent(AppEvents.CODE);
			
			group.addMyShortcut(shortcut);
			
			MyDesktop desktop=Registry.get(AppView.DESKTOP);
			desktop.addMyShortcut(shortcut, false);
		}
		forwardToView(codeView, event);
	}
	

}
