package com.single.desktopoa.convert;

import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.common.person.Person;

public class PersonConvert implements Convert<Person, ClientPerson> {

	@Override
	public ClientPerson convertToClient(Person x) throws Exception {
		ClientPerson cp=new ClientPerson();
		cp.setId(x.getId());
		cp.setAccount(x.getAccount().getId());
		cp.setEmail(x.getEmail());
		cp.setName(x.getName());
		
		return cp;
	}

	@Override
	public Person convertToModel(ClientPerson y) throws Exception {
		Person person=new Person();
		person.setEmail(y.getEmail());
		person.setId(y.getId());
		person.setName(y.getName());
		return person;
	}

}
