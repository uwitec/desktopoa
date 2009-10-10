package com.single.desktopoa.client.mvc.module.code;

import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.ShortcutModel;
import com.single.desktopoa.client.model.ShortcutModel.ShortcutWapper;
import com.single.desktopoa.client.mvc.AppView;

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
			ShortcutModel model=new ShortcutModel();
			model.setName("源代码");
			
			Shortcut shortcut=new Shortcut();
			shortcut.setText("源代码");
			shortcut.setIcon(AppView.appIcons.file32());
			shortcut.setData("event", AppEvents.CODE);
			shortcut.addSelectionListener(AppView.shortcutListener);
			
			ShortcutWapper wapper=new ShortcutWapper();
			wapper.setCookieId(COOKIE_SHORTCUT_CODE);
			wapper.setShow(true);
			wapper.setShortcut(shortcut);
			
			model.getShorts().add(wapper);
			AppView.shortcutList.add(model);
		}
		forwardToView(codeView, event);
	}
	

}
