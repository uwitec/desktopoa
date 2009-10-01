package com.single.desktopoa.client.module.set;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;

public class LockDialog extends Dialog {

	private String password;
	private Button lock;
	private Button unlock;
	private LabelField text;
	private TextField<String> psdField;
	
	private static LockDialog dialog;
	public static LockDialog get(){
		if(dialog==null){
			dialog=new LockDialog();
		}
		return dialog;
	}
	
	private LockDialog(){
		setModal(true);
		setClosable(false);
		
		lock=new Button("锁定",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				if(psdField.validate()){
					password=psdField.getValue();
					psdField.reset();
					unlock();
				}
			}
		});
		unlock=new Button("解锁",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				if(psdField.validate()){
					if(password.equals(psdField.getValue())){
						password="";
						psdField.reset();
						LockDialog.this.hide();
					}else{
						
					}
				}
			}
		});
		setButtons("");
		addButton(lock);
		addButton(unlock);
		
		text=new LabelField();
		add(text);
		psdField=new TextField<String>();
		psdField.setPassword(true);
		psdField.setAllowBlank(false);
		add(psdField);
	}
	
	public void lock(){
		setHeading("锁定系统");
		text.setText("请设置锁定密码");
		
		lock.show();
		unlock.hide();
		show();
	}
	public void unlock(){
		setHeading("解锁系统");
		text.setText("请输入解锁密码");
		
		unlock.show();
		lock.hide();
		show();
	}
}
