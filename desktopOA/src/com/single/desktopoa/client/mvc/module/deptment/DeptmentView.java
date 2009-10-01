package com.single.desktopoa.client.mvc.module.deptment;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.single.desktopoa.client.module.deptment.DeptmentManager;

public class DeptmentView extends View {

	private static DeptmentManager positionManager;
	public static DeptmentManager get(){
		if(positionManager==null){
			positionManager=new DeptmentManager();
		}
		return positionManager;
	}
	
	
	public DeptmentView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub

	}
	@Override
	protected void initialize() {
		super.initialize();
		
		if(positionManager==null){
			positionManager=new DeptmentManager();
		}
	}
}
