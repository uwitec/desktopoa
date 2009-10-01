package com.single.desktopoa.client.module.worktalk;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.model.TalkMessage;

public class TalkDialog extends Window {

	private Long personId;
	private String name;
	
	private ContentPanel center;
	private ContentPanel south;
	private HtmlEditor textArea=new HtmlEditor();
	
	private Button send;
	
	private AsyncCallback<Boolean> callback=new AsyncCallback<Boolean>(){
		public void onFailure(Throwable caught) {
		}
		public void onSuccess(Boolean result) {
			if(result){
				MessageBox.alert("Msg", "发送成功", null);
			}else{
				MessageBox.alert("Msg", "不在线", null);
			}
		}
	};
	
	public TalkDialog(ModelData personData){
		this.personId=personData.get("id");
		this.name=personData.get("name");
		
		setWidth(400);
		setHeight(300);
		setHeading("与"+name+"对话");
		BorderLayout layout=new BorderLayout();
		setLayout(layout);
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		send=new Button("发送",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				String text="我:<br>"+textArea.getValue();
				center.addText(text);
				center.layout();
				ServiceFactory.workTalkService.talkTo(personId, textArea.getValue(), callback);
				textArea.reset();
			}
		});
		
		center=new ContentPanel();
		center.setScrollMode(Scroll.AUTO);
		BorderLayoutData centerData=new BorderLayoutData(LayoutRegion.CENTER);
		
		south=new ContentPanel();
		south.setHeaderVisible(false);
		south.setLayout(new FitLayout());
		south.add(textArea);
		south.setButtonAlign(HorizontalAlignment.RIGHT);
		south.addButton(send);
		BorderLayoutData southData=new BorderLayoutData(LayoutRegion.SOUTH,100,50,200);
		
		
		
		add(center,centerData);
		add(south,southData);
	}
	
	public void addMessage(TalkMessage msg){
		String name=msg.getFrom().getName();
		center.addText("<br>"+name+":<br>"+msg.getMsg());
		center.layout();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Long getPersonId() {
		return personId;
	}


	public void setPersonId(Long personId) {
		this.personId = personId;
	}
}
