package com.single.desktopoa.common.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.client.model.ClientUser;

public interface LoginServiceAsync {

	public void login(String username,String password,AsyncCallback<ClientPerson> callback);
	
	public void logout(String username,AsyncCallback<Void> callback);
	
	public void getPersonListWithModelData(AsyncCallback<List<ModelData>> callback);
	
	

	void usernameUniqueCheck(String username, AsyncCallback<Boolean> callback);

	void regist(ClientPerson person, ClientUser user, AsyncCallback<ClientPerson> callback);

	void getPersonListWithDeptment(ModelData dept, AsyncCallback<List<ModelData>> callback);

}
