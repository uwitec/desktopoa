package com.single.desktopoa.client;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.event.DomEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.state.CookieProvider;
import com.extjs.gxt.ui.client.state.StateManager;
import com.extjs.gxt.ui.client.util.Theme;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.custom.ThemeSelector;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.desktopoa.client.module.login.LoginDialog;
import com.single.desktopoa.client.mvc.AppController;
import com.single.desktopoa.client.mvc.DesktopController;
import com.single.desktopoa.client.mvc.StartController;
import com.single.desktopoa.client.mvc.module.code.CodeController;
import com.single.desktopoa.client.mvc.module.deptment.DeptmentController;
import com.single.desktopoa.client.mvc.module.file.FileController;
import com.single.desktopoa.client.mvc.module.mail.MailController;
import com.single.desktopoa.client.mvc.module.notice.NoticeController;
import com.single.desktopoa.client.mvc.module.portal.PortalController;
import com.single.desktopoa.client.mvc.module.worktalk.WorkTalkController;

public class DesktopOA implements EntryPoint {

	private Dispatcher dispatcher;

	public void onModuleLoad() {
		
//		CookieProvider provider=new CookieProvider(null,null,null,false);
//		StateManager.get().setProvider(provider);
//		provider.set("xhf", "hello111");
//		
//		com.google.gwt.user.client.Window.alert(provider.getString("xhf"));
		
		
		
		dispatcher = Dispatcher.get();
		dispatcher.addController(new AppController());
		dispatcher.addController(new DesktopController());
		dispatcher.addController(new StartController());
		dispatcher.addController(new MailController());
		dispatcher.addController(new FileController());

		dispatcher.addController(new NoticeController());

		dispatcher.addController(new PortalController());
		dispatcher.addController(new WorkTalkController());

		dispatcher.addController(new DeptmentController());
		dispatcher.addController(new CodeController());
		
		new LoginDialog().show();

		// DatePick picker=new DatePick();
		// Window win=new Window();
		// win.setLayout(new FitLayout());
		// win.add(picker);
		// win.show();

	}

	private void innerWedget() {
		Html html = new Html("gggggggggggggggggggggggggggg");
		// html.setStyleAttribute("margin", "5px");
		html.setStyleAttribute("background-color", "#FFFF66");
		html.setStyleAttribute("cursor", "pointer");
		html.addListener(Events.OnClick, new Listener<DomEvent>() {
			public void handleEvent(DomEvent be) {
				MessageBox.alert("", "shit", null);
			}
		});

		Window win = new Window();
		win.setLayout(new ColumnLayout());
		win.addText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		win.add(html);
		win.addText("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		win.show();
		html.el().addEventsSunk(Event.ONCLICK);
	}
}
