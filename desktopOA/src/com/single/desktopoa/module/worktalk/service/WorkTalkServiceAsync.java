package com.single.desktopoa.module.worktalk.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.common.person.Person;

public interface WorkTalkServiceAsync {

	void talkTo(Long personId, String msg, AsyncCallback<Boolean> callback);

	void getOnlineList(AsyncCallback<List<ModelData>> callback);

}
