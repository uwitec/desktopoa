package com.single.desktopoa.client.factory;

import com.google.gwt.core.client.GWT;
import com.single.desktopoa.common.service.DeptmentService;
import com.single.desktopoa.common.service.DeptmentServiceAsync;
import com.single.desktopoa.common.service.LoginService;
import com.single.desktopoa.common.service.LoginServiceAsync;
import com.single.desktopoa.module.code.service.CodeService;
import com.single.desktopoa.module.code.service.CodeServiceAsync;
import com.single.desktopoa.module.comet.service.CometService;
import com.single.desktopoa.module.comet.service.CometServiceAsync;
import com.single.desktopoa.module.file.service.FileService;
import com.single.desktopoa.module.file.service.FileServiceAsync;
import com.single.desktopoa.module.mail.service.MailService;
import com.single.desktopoa.module.mail.service.MailServiceAsync;
import com.single.desktopoa.module.notice.service.NoticeService;
import com.single.desktopoa.module.notice.service.NoticeServiceAsync;
import com.single.desktopoa.module.worktalk.service.WorkTalkService;
import com.single.desktopoa.module.worktalk.service.WorkTalkServiceAsync;

public class ServiceFactory {
	
	public static LoginServiceAsync loginService=GWT.create(LoginService.class);

	public static FileServiceAsync fileService=GWT.create(FileService.class);
	
	public static CometServiceAsync cometService=GWT.create(CometService.class);
	
	public static NoticeServiceAsync noticeService=GWT.create(NoticeService.class);
	
	public static MailServiceAsync mailService=GWT.create(MailService.class);
	
	public static WorkTalkServiceAsync workTalkService=GWT.create(WorkTalkService.class);
	
	public static DeptmentServiceAsync deptmentService=GWT.create(DeptmentService.class);
	
	public static CodeServiceAsync codeService=GWT.create(CodeService.class);
	
}
