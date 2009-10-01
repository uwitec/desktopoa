package com.single.desktopoa.module.comet;

import java.util.List;
import java.util.Queue;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CometServiceAsync {

	public void getEvents(AsyncCallback<List<ModelData>> callback);
}
