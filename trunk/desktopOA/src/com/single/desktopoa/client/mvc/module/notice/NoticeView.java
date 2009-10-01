package com.single.desktopoa.client.mvc.module.notice;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Window;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.ClientNotice;
import com.single.desktopoa.client.module.notice.NoticeBoard;
import com.single.desktopoa.client.module.notice.NoticeManager;
import com.single.desktopoa.client.mvc.AppView;

public class NoticeView extends View {

	
	private static NoticeManager noticeManager;
	
	public static Window getNoticeWindow(){
		if(noticeManager==null){
			noticeManager=new NoticeManager();
		}
		return noticeManager;
	}
	
	public NoticeView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.NOTICE){
			Desktop desktop=Registry.get(AppView.DESKTOP);
			if(!desktop.getWindows().contains(getNoticeWindow())){
				desktop.addWindow(getNoticeWindow());
			}
			if(getNoticeWindow()!=null&&!getNoticeWindow().isVisible()){
				getNoticeWindow().show();
			}else{
				getNoticeWindow().toFront();
			}
		}else if(event.getType()==AppEvents.NOTICE_new){
			noticeManager.activeNewNotice(event.getData());
		}else if(event.getType()==AppEvents.NOTICE_list){
			noticeManager.activeNoticeList(event.getData());
		}else if(event.getType()==AppEvents.NOTICE_display){
			NoticeBoard.display(event.<ClientNotice>getData());
		}
	}

}
