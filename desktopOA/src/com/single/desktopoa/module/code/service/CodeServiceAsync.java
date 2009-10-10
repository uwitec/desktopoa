package com.single.desktopoa.module.code.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CodeServiceAsync {

	void getChildren(ModelData data, AsyncCallback<List<ModelData>> callback);

	void loadFile(String path, AsyncCallback<ModelData> callback);

}
