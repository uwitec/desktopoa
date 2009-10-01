package com.single.desktopoa.client.module.file;

import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.model.ClientFile;
import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.client.model.ClientSignRecord;
import com.single.desktopoa.client.module.login.UserCheckboxWindow;
import com.single.desktopoa.client.mvc.AppView;

public class ViewFilePanel extends TabItem {

	private ClientFile file;
	
	private Html header;
	private static String headerHTML="<div><h1>标题:{0}</h1></div>";
	private ContentPanel content;
	
	private Button counterSign;
	private Menu signMenu;
	private Button sign;
	private Button edit;
	//呈主管
	private Button givein;
	
	private MenuItem groupSign;
	private MenuItem customSign;
	
	private static AsyncCallback<Void> signCallback=new AsyncCallback<Void>(){
		public void onFailure(Throwable caught) {
			MessageBox.alert("异常", caught.getMessage(), null);
		}
		public void onSuccess(Void result) {
			MessageBox.alert("成功", "会签设置成功", null);
		}
	};
	
	public ViewFilePanel(ClientFile inFile){
		super();
		
		this.file=inFile;
		setClosable(true);
		setText(file.getSubject());
		
		setLayout(new FitLayout());
		
//		Params p=new Params();
//		p.add(file.getSubject());
//		
//		String s=Format.substitute(headerHTML, p);
		
//		header=new Html();
//		header.getElement().setInnerHTML(s);
		
		content=new ContentPanel();
		content.setScrollMode(Scroll.AUTO);
		content.addText("<div style='padding: 12px;font-size: 12px'>" + file.getContent() + "</div>");
		content.setHeaderVisible(false);
		//签核信息
		StringBuffer sb=new StringBuffer();
		if(file.getStatus()==ClientFile.STATUS_SIGN){
			sb.append(file.getCreatorName()+"发起会签:"+file.getCounterSignReason()+"<br>");
			if(file.getSignedList().size()>0){
				sb.append("已签核:<br>");
				for(ClientSignRecord record:file.getSignedList()){
					sb.append(record.getSignerName()+":"+record.getSuggest()+"<br>");
				}
			}
			if(file.getUnsignedList().size()>0){
				sb.append("待签核:<br>");
				for(ClientSignRecord record:file.getUnsignedList()){
					sb.append(record.getSignerName()+"<br>");
				}
			}
		}
		content.addText(sb.toString());
		
		add(content);
		
		
		ToolBar toolBar=new ToolBar();
		//检测编辑权限
		if(file.getEditable()){
			edit=new Button("编辑");
			toolBar.add(edit);
		}
		//检测是否需要签核
		if(file.getSignable()){
			signMenu=new Menu();
			counterSign=new Button("会签");
			groupSign=new MenuItem("组内会签",new SelectionListener<MenuEvent>(){
				public void componentSelected(MenuEvent ce) {
					MessageBox.prompt("会签", "请说明发起会签的原因或目的", true, new Listener<MessageBoxEvent>(){
						public void handleEvent(MessageBoxEvent be) {
							ServiceFactory.fileService.groupSign(file.getId(),be.getValue(), signCallback);
						}
					});
					
				}
			});
			customSign=new MenuItem("自定义会签",new SelectionListener<MenuEvent>(){
				public void componentSelected(MenuEvent ce) {
					UserCheckboxWindow window=Registry.get(AppView.USER_CHECKBOX);
					window.showTree("", new AsyncCallback<List<ModelData>>(){
						public void onFailure(Throwable caught) {
						}
						public void onSuccess(final List<ModelData> result) {
							if(result.size()>0){
								MessageBox.prompt("会签", "请说明发起会签的原因或目的", true, new Listener<MessageBoxEvent>(){
									public void handleEvent(MessageBoxEvent be) {
										ServiceFactory.fileService.customSign(file.getId(),be.getValue(), (List)result,signCallback);
									}
								});
								
							}
						}
					});
				}
			});
			signMenu.add(groupSign);
			signMenu.add(customSign);
			counterSign.setMenu(signMenu);
			
			toolBar.add(counterSign);
		}
		//检测是否开始会签
		if(file.getNeedCounterSign()){
			sign=new Button("签核",new SelectionListener<ButtonEvent>(){
				public void componentSelected(ButtonEvent ce) {
					MessageBox.prompt("签核", "签核意见", true, new Listener<MessageBoxEvent>(){
						public void handleEvent(MessageBoxEvent be) {
							String suggest=be.getValue();
							ServiceFactory.fileService.signFile(file.getId(), suggest, new AsyncCallback<Void>(){
								public void onFailure(Throwable caught) {
									MessageBox.alert("", caught.getMessage(), null);
								}
								public void onSuccess(Void result) {
									MessageBox.alert("", "签核成功", null);
								}
							});
						}
					});
				}
			});
			toolBar.add(sign);
		}
		//是否可以呈主管
		if(file.getGiveinable()){
			givein=new Button("呈主管");
			toolBar.add(givein);
		}
		
		
		content.setTopComponent(toolBar);
		
	}
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		
		
	}
	
}
