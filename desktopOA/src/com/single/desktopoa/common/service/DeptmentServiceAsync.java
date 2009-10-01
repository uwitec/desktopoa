package com.single.desktopoa.common.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.model.ClientDeptment;

public interface DeptmentServiceAsync {

	void addDeptment(Long parentId, ClientDeptment deptment,
			AsyncCallback<Void> callback);

	void removeDeptment(Long id, AsyncCallback<Void> callback);

	void addRootDeptment(ClientDeptment deptment, AsyncCallback<Void> callback);

	void getDeptmentTree(AsyncCallback<TreeModel> callback);

	void getSubDeptment(Long id, AsyncCallback<List<ClientDeptment>> callback);

	void getRootDeptment(AsyncCallback<List<ClientDeptment>> callback);

}
