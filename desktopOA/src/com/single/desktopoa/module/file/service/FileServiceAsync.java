package com.single.desktopoa.module.file.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.model.ClientFile;
import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.module.file.model.File;

public interface FileServiceAsync {

	
	public void saveFile(ClientFile file,AsyncCallback<Void> callback) ;
	
	
	public void getFileList(ModelData modelData,AsyncCallback<List<ModelData>> callback);

	public void getPersonList(AsyncCallback<List<ModelData>> callback);
	
	public void readFile(Long id,AsyncCallback<ClientFile> callback);
	
	public void editFile(Long id,AsyncCallback<ClientFile> callback);


	void groupSign(Long id,String reason, AsyncCallback<Void> callback);




	void signFile(Long id, String suggest, AsyncCallback<Void> callback);




	void customSign(Long fileId,String reason, List<ClientPerson> datas,
			AsyncCallback<Void> callback);
}
