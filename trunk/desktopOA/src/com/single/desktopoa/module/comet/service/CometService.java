package com.single.desktopoa.module.comet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("comet")
public interface CometService extends RemoteService {
	String MEMBER="member";
	
	
	public List<ModelData> getEvents();
}

