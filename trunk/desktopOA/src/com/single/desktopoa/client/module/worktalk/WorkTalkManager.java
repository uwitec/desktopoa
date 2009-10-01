package com.single.desktopoa.client.module.worktalk;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.google.gwt.user.client.Element;
import com.single.desktopoa.client.model.TalkMessage;

public class WorkTalkManager extends Window {

	private OnlinePanel onlines;
	
	public WorkTalkManager(){
		super();
		
		
		setHeading("工作通");
		setSize(200, 500);
		setLayout(new AccordionLayout());
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		createAccordions();
	}

	private void createAccordions() {
		onlines=new OnlinePanel();
		
		add(onlines);
	}
	
	public void refresh(){
		if(isRendered()){
			onlines.refresh();
		}
	}

	public void receiveMsg(TalkMessage message) {
		onlines.receiveMsg(message);
	}
}
