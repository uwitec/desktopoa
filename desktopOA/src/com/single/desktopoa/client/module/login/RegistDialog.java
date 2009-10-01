package com.single.desktopoa.client.module.login;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.factory.ServiceFactory;
import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.client.model.ClientUser;

public class RegistDialog extends Dialog {

	
	public LoginDialog loginDialog;
	
	private FormPanel form;
	private TextField<String> username;
	private TextField<String> password;
	private TextField<String> rePassword;
	private TextField<String> name;
	
	private Button regist;
	private Button back;
	
	private String errMsg;
	
	private AsyncCallback<ClientPerson> registCallback=new AsyncCallback<ClientPerson>(){
		public void onFailure(Throwable caught) {
			
		}
		public void onSuccess(ClientPerson result) {
			Registry.register("person", result);
			RegistDialog.this.hide();
			Dispatcher.forwardEvent(AppEvents.Init);
		}
	};
	
	public RegistDialog(){
		
		setHeading("注册");
		setLayout(new FitLayout());
		setButtons("");
		setSize(300, 200);
		setClosable(false);
		setResizable(false);
		
		back=new Button("返回登陆",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				RegistDialog.this.hide();
				loginDialog.show();
			}
		});
		
		regist=new Button("注册",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				if(!password.getValue().equals(rePassword.getValue())){
					rePassword.markInvalid("输入密码不一致");
					return;
				}
				//验证用户名
				ServiceFactory.loginService.usernameUniqueCheck(username.getValue(), new AsyncCallback<Boolean>(){
					public void onFailure(Throwable caught) {
					}
					public void onSuccess(Boolean result) {
						if(!result){
							username.markInvalid("帐号名已存在");
						}else {
							if(form.isValid()){
								ClientPerson person=new ClientPerson();
								person.setName(name.getValue());
								person.setEmail("email");
								
								
								ClientUser user=new ClientUser();
								user.setUsername(username.getValue());
								user.setPassword(password.getValue());
								
								
								ServiceFactory.loginService.regist(person, user, registCallback);
							}
						}
					}
				});
				
			}
		});
		addButton(back);
		addButton(regist);
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		form=new FormPanel();
		form.setHeaderVisible(false);
		form.setLabelWidth(100);
		add(form);
		
		
		
		username=new TextField<String>();
		username.setFieldLabel("登陆帐号");
		username.setAllowBlank(false);
		
		password=new TextField<String>();
		password.setFieldLabel("登陆密码");
		password.setPassword(true);
		password.setAllowBlank(false);
		
		rePassword=new TextField<String>();
		rePassword.setFieldLabel("重复登陆密码");
		rePassword.setPassword(true);
		rePassword.setAllowBlank(false);
		
		
		name=new TextField<String>();
		name.setAllowBlank(false);
		name.setFieldLabel("真实姓名");
		
		
		form.add(username,new FormData("-20"));
		form.add(password,new FormData("-20"));
		form.add(rePassword,new FormData("-20"));
		form.add(name,new FormData("-20"));
		
		FormButtonBinding binding=new FormButtonBinding(form);
		binding.addButton(regist);
	}
	
}
