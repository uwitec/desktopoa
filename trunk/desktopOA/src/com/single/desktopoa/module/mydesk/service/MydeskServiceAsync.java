package com.single.desktopoa.module.mydesk.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MydeskServiceAsync {

	void getPicList(AsyncCallback<List<ModelData>> callback);
}
