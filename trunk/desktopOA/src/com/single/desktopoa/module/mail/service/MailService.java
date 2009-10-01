package com.single.desktopoa.module.mail.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.single.desktopoa.client.model.ClientInBoxMail;
import com.single.desktopoa.client.model.ClientSendBoxMail;

@RemoteServiceRelativePath("mail")
public interface MailService extends RemoteService {

	public PagingLoadResult<ClientInBoxMail> getInBoxMails(PagingLoadConfig config);
	
	public PagingLoadResult<ClientSendBoxMail> getSendBoxMails(PagingLoadConfig config);
	
	public void sendMail(ClientSendBoxMail mail);
	
	public List<ModelData> getPortalList();
	
	public ClientInBoxMail readInBoxMail(Long mailId);
	
	public ClientSendBoxMail readSendBoxMail(Long mailId);
}
