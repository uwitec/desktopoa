package com.single.desktopoa.client.module.mail;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.util.Params;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.single.desktopoa.client.model.ClientInBoxMail;

public class MailItemPanel extends ContentPanel {

	private ContentPanel content;
	private Html header;
	private String headerHTML="<div class=mail-item-detail><h1>{0}</h1><h2><b>From:</b> {1}</h2><h3><b>To:</b> {2}</h3></div>";
	
	public MailItemPanel(){
		setHeaderVisible(false);
		setLayout(new FitLayout());
		
		content=new ContentPanel();
		content.setHeaderVisible(false);
		content.setScrollMode(Scroll.AUTO);
		
		header=new Html();
		header.setStyleName("mail-item-detail");
		content.setTopComponent(header);
		
		add(content);
	}
	
	public void showMailItem(ClientInBoxMail mail){
	    if (mail != null) {
	        Params p = new Params();
	        p.add(mail.getSubject());
	        p.add(mail.getSenderName());
	        p.add(mail.getReceiveNameList());

	        String s = Format.substitute(headerHTML, p);
	        header.setHtml(s);

	        content.removeAll();
	        content.addText("<div style='padding: 12px;font-size: 12px'>" + mail.getContent() + "</div>");

	        layout();

	      } else {
	        header.setHtml("");
	        content.removeAll();
	      }
	}
}
