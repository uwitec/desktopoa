package com.single.desktopoa.module.notice.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.model.ClientNotice;

public interface NoticeServiceAsync {

	public void addNotice(ClientNotice notice,AsyncCallback<Void> callback);
	
	public void deleteNotice(Long noticeId,AsyncCallback<Void> callback);
	
	public void getNoticeListWithModelData(AsyncCallback<List<ModelData>> callback);
	
	public void getPortalList(AsyncCallback<List<ModelData>> callback);
	
	public void readNotice(Long id,AsyncCallback<ClientNotice> callback);
}
