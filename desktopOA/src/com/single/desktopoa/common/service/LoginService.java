package com.single.desktopoa.common.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.single.desktopoa.client.exception.MySecurityException;
import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.client.model.ClientUser;
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService{

	public ClientPerson login(String username,String password) throws MySecurityException;
	
	public void logout(String username);
	
	List<ModelData> getPersonListWithDeptment(ModelData dept);
	
	public List<ModelData> getPersonListWithModelData();
	
	public boolean usernameUniqueCheck(String username);
	
	public ClientPerson regist(ClientPerson person,ClientUser user);
}
