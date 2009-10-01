package com.single.desktopoa.client.mvc.module.deptment;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class DeptmentController extends Controller {

	private DeptmentView positionView;
	
	public DeptmentController(){
		positionView=new DeptmentView(this);
		
	}
	@Override
	public void handleEvent(AppEvent event) {
		forwardToView(positionView, event);
	}

}
