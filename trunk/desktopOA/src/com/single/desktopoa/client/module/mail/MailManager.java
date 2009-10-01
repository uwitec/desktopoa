package com.single.desktopoa.client.module.mail;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.Element;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.ClientInBoxMail;

public class MailManager extends Window {

	private NewMailPanel newMail;

	private LayoutContainer west;
	private LayoutContainer center;
	private LayoutContainer content;
	private MailListPanel mailListPanel;
	private MailItemPanel mailItemPanel;
	
	private ContentPanel mail;
	private ContentPanel contacts;
	
	
	
	public MailManager(){
		super();
		setPlain(true);
		setHeading("邮件管理");
		setSize(500, 400);
		setMinimizable(true);
		setMaximizable(true);
	}
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		initialize();
	}
	private void initialize() {
		BorderLayout layout=new BorderLayout();
		setLayout(layout);
		
		createCenterPanel();
		createWestPanel();
		
		newMail=new NewMailPanel();
		
	}

	private void createCenterPanel() {
		center=new LayoutContainer();
		center.setLayout(new FitLayout());
		BorderLayoutData data=new BorderLayoutData(LayoutRegion.CENTER);
		data.setMargins(new Margins(5,5,5,5));
		add(center, data);
		
		content=new LayoutContainer();
		content.setLayout(new BorderLayout());
		
		mailListPanel=new MailListPanel();
		mailItemPanel=new MailItemPanel();
		
		content.add(mailListPanel, new BorderLayoutData(LayoutRegion.CENTER));
		BorderLayoutData southData=new BorderLayoutData(LayoutRegion.SOUTH,.5f,200,1000);
		southData.setSplit(true);
		southData.setMargins(new Margins(5,0,0,0));
		content.add(mailItemPanel,southData);
		
		center.add(content);
	}

	private void createWestPanel() {
		west=new LayoutContainer();
		west.setLayout(new AccordionLayout());
		
		BorderLayoutData data=new BorderLayoutData(LayoutRegion.WEST,150);
		data.setMargins(new Margins(5,0,5,5));
		
		mail=new ContentPanel();
		mail.setHeading("邮件");
		mail.addListener(Events.Expand, new Listener<BaseEvent>(){
			@Override
			public void handleEvent(BaseEvent be) {
				Dispatcher.get().dispatch(AppEvents.MAIL_nav);
			}
		});
		
		mail.setLayout(new FitLayout());
			TreeStore<ModelData> store=new TreeStore<ModelData>();
			ModelData inbox=new BaseModelData();
			inbox.set("name", "收件箱");
			ModelData sendbox=new BaseModelData();
			sendbox.set("name", "发件箱");
			ModelData draft=new BaseModelData();
			draft.set("name", "草稿箱");
			store.add(inbox, false);
			store.add(sendbox, false);
			store.add(draft, false);
			TreePanel<ModelData> tree=new TreePanel<ModelData>(store);
			tree.setDisplayProperty("name");
//			tree.getStyle().setLeafIcon(itemIcon);
		mail.add(tree);
		
		contacts=new ContentPanel();
		contacts.setHeading("联系人");
		
		west.add(mail);
		west.add(contacts);
		add(west, data);
	}
	
	public void activeMailNav(){
		center.removeAll();
		center.add(content);
		center.layout();
	}
	
	public void activeNewMail(AppEvent event){
		center.removeAll();
		center.add(newMail);
		
		center.layout();
		newMail.active(event);
	}
	
	public void showMailItem(ClientInBoxMail mail){
		mailItemPanel.showMailItem(mail);
	}
	
	
}
