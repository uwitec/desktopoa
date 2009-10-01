package com.single.desktopoa.common.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.single.desktopoa.client.model.ClientDeptment;

@RemoteServiceRelativePath("deptment")
public interface DeptmentService extends RemoteService{

	void addRootDeptment(ClientDeptment deptment);
	
	void addDeptment(Long parentId,ClientDeptment deptment);
	
	void removeDeptment(Long id);
	
	TreeModel getDeptmentTree();
	
	List<ClientDeptment> getSubDeptment(Long id);
	
	List<ClientDeptment> getRootDeptment();
}
