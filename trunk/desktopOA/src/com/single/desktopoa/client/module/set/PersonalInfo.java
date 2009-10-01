package com.single.desktopoa.client.module.set;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.single.desktopoa.client.model.ClientPerson;

public class PersonalInfo extends Window {

	private static PersonalInfo info;
	public static PersonalInfo get(){
		if(info==null){
			info=new PersonalInfo();
		}
		return info;
	}
	private ClientPerson person;
	private PersonalInfo(){
		super();
		setSize(300, 400);
		person=Registry.get("person");
		
		setHeading(person.getName());
		LabelField name=new LabelField("姓名:"+person.getName());
		LabelField id=new LabelField("ID:"+person.getId());
		LabelField email=new LabelField("Email:"+person.getEmail());
		
		add(name);
		add(id);
		add(email);
	}
}
