package com.single.desktopoa.client.mvc.module.worktalk;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Info;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.TalkMessage;
import com.single.desktopoa.client.module.worktalk.WorkTalkManager;
import com.single.desktopoa.client.mvc.AppView;

public class WorkTalkView extends View {

	private static WorkTalkManager workTalkManager;
	
	public static WorkTalkManager get(){
		if(workTalkManager==null){
			workTalkManager=new WorkTalkManager();
		}
		return workTalkManager;
	}
	
	public WorkTalkView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.WORKTALK){
			Desktop desktop=Registry.get(AppView.DESKTOP);
			if(!desktop.getWindows().contains(get())){
				desktop.addWindow(get());
			}
			if(get()!=null&&!get().isVisible()){
				get().show();
			}else{
				get().toFront();
			}
		}else if(event.getType()==AppEvents.WORKTALK_newlogin){
			workTalkManager.refresh();
			Info.display("工作通", "用户登陆了");
		}else if(event.getType()==AppEvents.WORKTALK_msg){
			TalkMessage message=event.getData();
			workTalkManager.receiveMsg(message);
		}
	}

	@Override
	protected void initialize() {
		get();
	}

}
