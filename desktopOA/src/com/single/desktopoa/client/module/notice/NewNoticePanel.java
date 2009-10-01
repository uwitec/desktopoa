package com.single.desktopoa.client.module.notice;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.model.ClientNotice;

public class NewNoticePanel extends ContentPanel {

	private NoticeManager manager;
	
	private Button preview;
	private Button save;
	private Button back;
	
	private FormPanel form;
	private TextField<String> subject;
	private NumberField sizeX;
	private NumberField sizeY;
	
	private HtmlEditor editor;
	
	private AsyncCallback<Void> callback=new AsyncCallback<Void>(){
		@Override
		public void onFailure(Throwable caught) {
		}
		@Override
		public void onSuccess(Void result) {
			Info.display("公告消息","公告发布成功");
		}
	};
	
	public NewNoticePanel(NoticeManager manager) {
		super();
		
		this.manager=manager;
		
		setHeading("新建公告");
		
		
		
		preview=new Button("预览",new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				
				NoticeBoard.display(subject.getValue(), editor.getValue(),sizeX.getValue().intValue(),sizeY.getValue().intValue());
			}
		});
		
		save=new Button("发布",new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				ClientNotice notice=new ClientNotice();
				notice.setContent(editor.getValue());
				notice.setSubject(subject.getValue());
				notice.setWidth(sizeX.getValue().intValue());
				notice.setHeigth(sizeY.getValue().intValue());
				
				ServiceFactory.noticeService.addNotice(notice, callback);
				
			}
		});
		
		back=new Button("返回",new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				Dispatcher.forwardEvent(AppEvents.NOTICE_list);
			}
		});
		
		addButton(back);
		addButton(preview);
		addButton(save);
		
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		

		setLayout(new FitLayout());
		
		createForm();
	}

	private void createForm() {
		form=new FormPanel();
		form.setHeaderVisible(false);
		form.setLabelWidth(50);
		add(form);
		
		subject=new TextField<String>();
		subject.setFieldLabel("主题");
		subject.setAllowBlank(false);
		
		sizeX=new NumberField();
		sizeX.setFieldLabel("公告宽");
		sizeX.setValue(250);
		
		sizeY=new NumberField();
		sizeY.setFieldLabel("公告高");
		sizeY.setValue(250);
		
		editor=new HtmlEditor();
		editor.setHideLabel(true);
		
		form.add(subject,new FormData("100%"));
		form.add(sizeX);
		form.add(sizeY);
		form.add(editor,new FormData("100% -30"));
		
		
		FormButtonBinding binding=new FormButtonBinding(form);
		binding.addButton(save);
		
	}

	
}
