package com.single.desktopoa.client.module.mail;

import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.ClientInBoxMail;
import com.single.desktopoa.client.model.ClientSendBoxMail;
import com.single.desktopoa.client.module.login.UserCheckboxWindow;
import com.single.desktopoa.client.mvc.AppView;
import com.single.desktopoa.module.mail.service.MailService;
import com.single.desktopoa.module.mail.service.MailServiceAsync;

public class NewMailPanel extends ContentPanel {

	private FormPanel form;
	private TextField<String> sendTo;
	private TextField<String> sendToId;
	private TextField<String> subject;
	private HtmlEditor editor;
	
	private MailServiceAsync mailService=GWT.create(MailService.class);
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		setLayout(new FitLayout());
		setHeading("新邮件");
		
		form=new FormPanel();
		form.setBorders(false);
		form.setBodyBorder(false);
		form.setLabelWidth(55);
		form.setPadding(5);
		form.setHeaderVisible(false);
		
		sendToId=new TextField<String>();
		sendToId.hide();
		
		sendTo=new TextField<String>();
		sendTo.setFieldLabel("发送至");
		sendTo.setAllowBlank(false);
		sendTo.setReadOnly(true);
		sendTo.addListener(Events.OnClick, new Listener<FieldEvent>(){
			@Override
			public void handleEvent(FieldEvent be) {
				UserCheckboxWindow window=Registry.get(AppView.USER_CHECKBOX);
				window.showTree(sendTo.getValue(), new AsyncCallback<List<ModelData>>(){
					@Override
					public void onFailure(Throwable caught) {
					}
					@Override
					public void onSuccess(List<ModelData> result) {
						StringBuffer names=new StringBuffer();
						StringBuffer ids=new StringBuffer();
						for(int i=0;i<result.size();i++){
							ids.append(result.get(i).get("id"));
							names.append(result.get(i).get("name"));
							if(i!=result.size()-1){
								names.append(",");
								ids.append(",");
							}
							sendTo.setValue(names.toString());
							sendToId.setValue(ids.toString());
						}
					}
				});
			}
		});
		form.add(sendTo, new FormData("100%"));
		
		subject=new TextField<String>();
		subject.setFieldLabel("主题");
		form.add(subject,new FormData("100%"));
		
		editor=new HtmlEditor();
		editor.setHideLabel(true);
		form.add(editor,new FormData("100% -53"));
		
		form.addButton(new Button("发送",new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(form.isValid()){
					ClientSendBoxMail mail=new ClientSendBoxMail();
					mail.setContent(editor.getValue());
					mail.setReceiveIdList(sendToId.getValue());
					mail.setReceiveNameList(sendTo.getValue());
					mail.setSubject(subject.getValue());
					mailService.sendMail(mail, new AsyncCallback<Void>(){
						@Override
						public void onFailure(Throwable caught) {
							MessageBox.alert("出错了", caught.getMessage(), null);
						}
						@Override
						public void onSuccess(Void result) {
							Info.display("邮件消息", "邮件发送成功");
							form.reset();
							Dispatcher.forwardEvent(AppEvents.MAIL_nav);
						}
					});
				}
			}
		}));
		form.addButton(new Button("清空"));
		
		add(form);
		
	}
 
	public void active(AppEvent event){
		System.out.println(event.getData());
		if(event.getData()instanceof ClientInBoxMail){
			System.out.println("ok");
		}
		
		
		if(event.getType()==AppEvents.MAIL_reply){
			ClientInBoxMail mail=event.getData();
			sendTo.setValue(mail.getSenderName());
			sendToId.setValue(mail.getReceiveIdList());
			subject.setValue("回复:"+mail.getSubject());
			editor.setValue(mail.getContent());
		}else if(event.getType()==AppEvents.MAIL_forward){
			ClientInBoxMail mail=event.getData();
			subject.setValue("转发:"+mail.getSubject());
			editor.setValue(mail.getContent());
		}
	}
}
