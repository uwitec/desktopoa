package com.single.desktopoa.module.mail.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.model.ClientInBoxMail;
import com.single.desktopoa.client.model.ClientSendBoxMail;

public interface MailServiceAsync {

	public void getInBoxMails(PagingLoadConfig config,AsyncCallback<PagingLoadResult<ClientInBoxMail>> callback);
	
	public void getSendBoxMails(PagingLoadConfig config,AsyncCallback<PagingLoadResult<ClientSendBoxMail>> callback);

	public void sendMail(ClientSendBoxMail mail,AsyncCallback<Void> callback);
	
	public void getPortalList(AsyncCallback<List<ModelData>> callback);
	
	public void readInBoxMail(Long mailId,AsyncCallback<ClientInBoxMail> callback);
	
	public void readSendBoxMail(Long mailId,AsyncCallback<ClientSendBoxMail> callback);
}
