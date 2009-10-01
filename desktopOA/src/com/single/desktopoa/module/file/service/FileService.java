package com.single.desktopoa.module.file.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.single.desktopoa.client.exception.BaseException;
import com.single.desktopoa.client.exception.MySecurityException;
import com.single.desktopoa.client.model.ClientFile;
import com.single.desktopoa.client.model.ClientPerson;

@RemoteServiceRelativePath("file")
public interface FileService extends RemoteService {
	
	public void saveFile(ClientFile file) throws BaseException;
	
	
	public List<ModelData> getFileList(ModelData modelData) throws BaseException;
	
	public List<ModelData> getPersonList();
	
	public ClientFile readFile(Long id) throws MySecurityException;
	
	public ClientFile editFile(Long id) throws MySecurityException;
	/**
	 * 开启组内会签
	 * @param id 文件ID
	 * @throws MySecurityException
	 */
	public void groupSign(Long id,String reason) throws MySecurityException;
	/**
	 * 开启自定义会签
	 * @param datas
	 * @throws MySecurityException
	 */
	public void customSign(Long fileId,String reason,List<ClientPerson> datas) throws MySecurityException;
	/**
	 * 签署文件
	 * @param id
	 * @throws MySecurityException
	 */
	public void signFile(Long id,String suggest) throws MySecurityException;
	
	
}
