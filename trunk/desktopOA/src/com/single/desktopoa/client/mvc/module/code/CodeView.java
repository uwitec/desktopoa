package com.single.desktopoa.client.mvc.module.code;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.module.code.CodeViewer;

public class CodeView extends View {

	private static CodeViewer codeViewer;
	
	public CodeView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		if(codeViewer==null){
			codeViewer=CodeViewer.get();
		}
		if(event.getType()==AppEvents.CODE){
			Dispatcher.forwardEvent(AppEvents.ACTIVE_WIN,codeViewer);
		}else if(event.getType()==AppEvents.CODE_showPage){
			Dispatcher.forwardEvent(AppEvents.ACTIVE_WIN,codeViewer);
			codeViewer.showCodePage(event.<String>getData());
		}
	}

}
