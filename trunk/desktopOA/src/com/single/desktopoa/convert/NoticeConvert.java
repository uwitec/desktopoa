package com.single.desktopoa.convert;

import com.single.desktopoa.client.model.ClientNotice;
import com.single.desktopoa.module.notice.model.Notice;
import com.sun.security.auth.NTDomainPrincipal;

public class NoticeConvert implements Convert<Notice, ClientNotice> {

	@Override
	public ClientNotice convertToClient(Notice x) throws Exception {
		ClientNotice notice=new ClientNotice();
		notice.setContent(x.getContent());
		notice.setCreateDate(x.getCreateDate());
		notice.setHeigth(x.getHeigth());
		notice.setId(x.getId());
		notice.setSubject(x.getSubject());
		notice.setWidth(x.getWidth());
		return notice;
	}

	@Override
	public Notice convertToModel(ClientNotice y) throws Exception {
		Notice notice=new Notice();
		notice.setContent(y.getContent());
		notice.setCreateDate(y.getCreateDate());
		notice.setHeigth(y.getHeigth());
		notice.setId(y.getId());
		notice.setSubject(y.getSubject());
		notice.setWidth(y.getWidth());
		return notice;
	}

}
