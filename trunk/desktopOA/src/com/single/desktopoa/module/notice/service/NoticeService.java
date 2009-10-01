package com.single.desktopoa.module.notice.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.single.desktopoa.client.model.ClientNotice;

@RemoteServiceRelativePath("notice")
public interface NoticeService extends RemoteService {

	void addNotice(ClientNotice notice);
	
	void deleteNotice(Long noticeId);
	
	List<ModelData> getNoticeListWithModelData();
	
	List<ModelData> getPortalList();
	
	ClientNotice readNotice(Long id);
}
