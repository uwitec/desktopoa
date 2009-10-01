package com.single.desktopoa.client.module.login;

import java.util.List;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.event.CometEvents;
import com.single.desktopoa.client.exception.MySecurityException;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.client.module.notice.NoticeBoard;
import com.single.desktopoa.common.service.LoginService;
import com.single.desktopoa.common.service.LoginServiceAsync;
import com.single.desktopoa.module.comet.CometServiceAsync;

public class LoginDialog extends Dialog {
	protected TextField<String> username;
	protected TextField<String> password;
	protected Button regist;
	protected Button login;
	protected Status status;
	
	private LoginServiceAsync loginService=GWT.create(LoginService.class);
	
	public LoginDialog(){
		FormLayout layout=new FormLayout();
		layout.setLabelWidth(90);
		layout.setDefaultWidth(155);
		setLayout(layout);
		
		setClosable(false);
		setButtonAlign(HorizontalAlignment.LEFT);
		setHeading("请登陆");
		setModal(true);
		setBodyBorder(true);
		setBodyStyle("padding: 8px;background: none");
		setWidth(300);
		setResizable(false);
		setButtons("");
		
		KeyListener keyListener=new KeyListener(){

			@Override
			public void componentKeyUp(ComponentEvent event) {
				// TODO Auto-generated method stub
				super.componentKeyUp(event);
			}
			
		};
		
		username=new TextField<String>();
		username.setValue("admin");
		username.setFieldLabel("用户名");
		username.addKeyListener(keyListener);
		add(username);
		
		password=new TextField<String>();
		password.setValue("123");
		password.setFieldLabel("密码");
		password.setPassword(true);
		password.addKeyListener(keyListener);
		add(password);
		
		setFocusWidget(username);
	}

	@Override
	protected void createButtons() {
		super.createButtons();
		status=new Status();
		
		status.hide();
		status.setAutoWidth(true);
		getButtonBar().add(status);
		
		getButtonBar().add(new FillToolItem());
		
		regist=new Button("注册");
		regist.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				LoginDialog.this.hide();
				RegistDialog registDialog=new RegistDialog();
				registDialog.loginDialog=LoginDialog.this;
				registDialog.show();
			}
		});
		
		login=new Button("登陆");
		login.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				onSubmit();
			}

			
		});
		
		addButton(regist);
		addButton(login);
	}
	private void onSubmit() {
		status.setBusy("登陆中,请稍后...");
		status.show();
		getButtonBar().disable();
		
		loginService.login(username.getValue(), password.getValue(), new AsyncCallback<ClientPerson>(){
			@Override
			public void onFailure(Throwable caught) {
				MySecurityException e=(MySecurityException)caught;
				LoginDialog.this.getButtonBar().enable();
//				status.setText(e.getMessage());
				password.reset();
				username.focus();
				username.selectAll();
				status.setStatus(e.getMessage(), "");
			}
			@Override
			public void onSuccess(ClientPerson result) {
				Registry.register("person", result);
				LoginDialog.this.hide();
				MessageBox wait=MessageBox.wait("", "", "程序启动中");
				Dispatcher.get().dispatch(AppEvents.Init);
				Dispatcher.forwardEvent(AppEvents.Init_Shortcut);
				Dispatcher.forwardEvent(AppEvents.Init_AutoRun);
				wait.close();
				
				//浏览器判断
				if(!GXT.isChrome){
					if(GXT.isIE){
						if(!GXT.isWebKit){
							InfoConfig config=new InfoConfig("友情提示", "您使用的是IE浏览器，为了获得更好的体验，请安装<a target='_blank' href='http://code.google.com/chrome/chromeframe'>插件</a>");
							config.display=100000;
							Info.display(config);
						}
					}else {
						InfoConfig config=new InfoConfig("友情提示", "为了获得更好的体验，请您使用谷歌浏览器");
						config.display=100000;
						Info.display(config);
					}
				}
			}
		}); 
		
	}
	  
}
