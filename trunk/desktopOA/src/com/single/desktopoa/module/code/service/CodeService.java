package com.single.desktopoa.module.code.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("code")
public interface CodeService extends RemoteService {

	List<ModelData> getChildren(ModelData data);
	
	ModelData loadFile(String path);
}
