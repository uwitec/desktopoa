package com.single.desktopoa.client.module.code;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.widget.TabPanel;

public class CodeTabs extends TabPanel {

	public CodeTabs(){
		setAnimScroll(true);
		setTabScroll(true);
		setCloseContextMenu(true);
		addListener(Events.Remove, new Listener<TabPanelEvent>(){
			public void handleEvent(TabPanelEvent be) {
				
			}
		});
	}
}
