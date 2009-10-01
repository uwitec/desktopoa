package com.single.desktopoa.module.worktalk.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.single.desktopoa.common.person.Person;

@RemoteServiceRelativePath("workTalk")
public interface WorkTalkService extends RemoteService {

	List<ModelData> getOnlineList();
	
	boolean talkTo(Long personId,String msg);
}
