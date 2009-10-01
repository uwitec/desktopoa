package com.single.desktopoa.client.module.portal;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.google.gwt.user.client.Element;
import com.single.desktopoa.client.module.file.FilePortlet;
import com.single.desktopoa.client.module.mail.MailPortlet;
import com.single.desktopoa.client.module.notice.NoticePortlet;

public class PortletList extends ContentPanel {

	private PortalManager portalManager;
	
	private Button mail;
	private Button file;
	private Button notice;
	
	private SelectionListener<ButtonEvent> listener=new SelectionListener<ButtonEvent>(){
		public void componentSelected(ButtonEvent ce) {
			Button button=ce.getButton();
			if(button==mail){
				portalManager.addPortlet(MailPortlet.get());
			}else if(button==file){
				portalManager.addPortlet(FilePortlet.get());
			}else if(button==notice){
				portalManager.addPortlet(NoticePortlet.get());
			}
		}
	};
	
	public PortletList(PortalManager manager){
		super();
		this.portalManager=manager;
		
		
		VBoxLayout layout=new VBoxLayout();
		setLayout(layout);
		
		layout.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
		
		
		mail=new Button("邮件",listener);
		
		notice=new Button("公告",listener);
		
		add(mail);
		add(notice);
		
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
//		PortalView.getPortalManager().layout.collapse(LayoutRegion.EAST);
	}
}
