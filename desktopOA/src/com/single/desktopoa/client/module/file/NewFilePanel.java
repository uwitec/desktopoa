package com.single.desktopoa.client.module.file;

import java.util.List;


import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.model.ClientFile;
import com.single.desktopoa.client.module.login.UserCheckboxWindow;
import com.single.desktopoa.client.mvc.AppView;

public class NewFilePanel extends TabItem {

	private FormPanel form;
	private TextField<String> subject;
	private RadioGroup readDegree;
	private RadioGroup editDegree;
	private TextField<String> readList;
	private TextField<String> editList;
	private HtmlEditor editor;
	
	private AsyncCallback<Void> callback=new AsyncCallback<Void>(){
		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("文档保存失败", caught.getMessage(), null);
		}
		@Override
		public void onSuccess(Void result) {
			Info.display("文档中心", "保存文档成功！");
			close();
		}
	};
	public NewFilePanel(){
		super();
		setClosable(true);
		setText("新建文档");
	}
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		
		
		setLayout(new FitLayout());
		
		createFormPanel();
		
	}

	
	
	private void createFormPanel(){
		form=new FormPanel();
		form.setBorders(false);
		form.setBodyBorder(false);
		form.setLabelWidth(70);
		form.setPadding(5);
		form.setHeaderVisible(false);
		
		FormData data=new FormData();
		
		subject=new TextField<String>();
		subject.setFieldLabel("标题");
		subject.setAllowBlank(false);
		
		Radio selfRead=new Radio();
		selfRead.setData("flag", ClientFile.READ_SELF);
		selfRead.setBoxLabel("仅自己");
		
		final Radio someRead=new Radio();
		someRead.setBoxLabel("指定人员");
		someRead.setData("flag", ClientFile.READ_ASSIGN);
		someRead.addListener(Events.Change, new Listener<BaseEvent>(){
			@Override
			public void handleEvent(BaseEvent be) {
				if(someRead.getValue()){
					readList.show();
				}else {
					readList.hide();
				}
			}
		});
		
		Radio allRead=new Radio();
		allRead.setBoxLabel("所有人");
		allRead.setData("flag", ClientFile.READ_ALL);
		allRead.setValue(true);
		
		readDegree=new RadioGroup("read");
		readDegree.setFieldLabel("阅读权限");
		readDegree.add(selfRead);
		readDegree.add(someRead);
		readDegree.add(allRead);
		
		Radio selfEdit=new Radio();
		selfEdit.setData("flag", ClientFile.EDIT_SELF);
		selfEdit.setBoxLabel("仅自己");
		selfEdit.setValue(true);
		
		final Radio someEdit=new Radio();
		someEdit.setData("flag", ClientFile.EDIT_ASSIGN);
		someEdit.setBoxLabel("指定人员");
		someEdit.addListener(Events.Change, new Listener<BaseEvent>(){
			@Override
			public void handleEvent(BaseEvent be) {
				if(someEdit.getValue()){
					editList.show();
				}else {
					editList.hide();
				}
			}
		});
		
		Radio allEdit=new Radio();
		allEdit.setData("flag", ClientFile.EDIT_ALL);
		allEdit.setBoxLabel("所有人");
		
		editDegree=new RadioGroup("edit");
		editDegree.setFieldLabel("编辑权限");
		editDegree.add(selfEdit);
		editDegree.add(someEdit);
		editDegree.add(allEdit);
		
		
		readList=new TextField<String>();
		readList.setFieldLabel("指定阅读人");
		readList.hide();
		readList.setReadOnly(true);
		readList.addListener(Events.OnClick, new Listener<BaseEvent>(){
			@Override
			public void handleEvent(BaseEvent be) {
				UserCheckboxWindow userCheckTree=Registry.get(AppView.USER_CHECKBOX);
				userCheckTree.showTree("", new AsyncCallback<List<ModelData>>(){
					@Override
					public void onFailure(Throwable caught) {
					}
					@Override
					public void onSuccess(List<ModelData> result) {
						StringBuffer value=new StringBuffer();
						for(int i=0;i<result.size();i++){
							value.append(result.get(i).get("username"));
							if(i!=result.size()-1)
								value.append(",");
							readList.setValue(value.toString());
						}
					}
					
				});
			}
		});
		
		editList=new TextField<String>();
		editList.setFieldLabel("指定编辑人");
		editList.hide();
		editList.setReadOnly(true);
		editList.addListener(Events.OnClick, new Listener<BaseEvent>(){
			@Override
			public void handleEvent(BaseEvent be) {
				UserCheckboxWindow userCheckTree=Registry.get(AppView.USER_CHECKBOX);
				userCheckTree.showTree("", new AsyncCallback<List<ModelData>>(){
					@Override
					public void onFailure(Throwable caught) {
					}
					@Override
					public void onSuccess(List<ModelData> result) {
						StringBuffer value=new StringBuffer();
						for(int i=0;i<result.size();i++){
							value.append(result.get(i).get("username"));
							if(i!=result.size()-1)
								value.append(",");
							editList.setValue(value.toString());
						}
					}
					
				});
			}
		});
		
		editor=new HtmlEditor();
		editor.setHideLabel(true);
		
		form.add(subject,new FormData("100%"));
		form.add(readDegree);
		form.add(readList);
		form.add(editDegree);
		form.add(editList);
		form.add(editor,new FormData("100% -53"));
		
		
		Button submit=new Button("保存",new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				ClientFile file=new ClientFile();
				file.setContent(editor.getValue());
				file.setEditList(editList.getValue());
				file.setReadList(readList.getValue());
				file.setSubject(subject.getValue());
				
				file.setReadDegree(readDegree.getValue().<Integer>getData("flag"));
				file.setEditDegree(editDegree.getValue().<Integer>getData("flag"));
				
				ServiceFactory.fileService.saveFile(file, callback);
			}
		});
		
		FormButtonBinding binding=new FormButtonBinding(form);
		binding.addButton(submit);
		
		form.addButton(submit);
		
		
		add(form);
	}
	
	
}
