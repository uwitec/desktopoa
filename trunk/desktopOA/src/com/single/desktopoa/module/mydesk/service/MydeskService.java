package com.single.desktopoa.module.mydesk.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("mydesk")
public interface MydeskService extends RemoteService {

	List<ModelData> getPicList();
}
