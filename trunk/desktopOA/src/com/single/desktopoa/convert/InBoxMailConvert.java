package com.single.desktopoa.convert;

import com.single.desktopoa.client.model.ClientInBoxMail;
import com.single.desktopoa.module.mail.model.InBoxMail;

public class InBoxMailConvert implements Convert<InBoxMail, ClientInBoxMail> {

	@Override
	public ClientInBoxMail convertToClient(InBoxMail x) throws Exception {
		ClientInBoxMail mail=new ClientInBoxMail();
		mail.setContent(x.getContent());
		mail.setId(x.getId());
		mail.setReceiveIdList(x.getReceiveIdList());
		mail.setReceiveNameList(x.getReceiveNameList());
		mail.setReceiverId(x.getReceiver().getId());
		mail.setReceiverName(x.getReceiver().getName());
		mail.setSendDate(x.getSendDate());
		if(x.getSender()==null){
			mail.setSenderId(Long.valueOf(0));
			mail.setSenderName("系统");
		}else{
			mail.setSenderId(x.getSender().getId());
			mail.setSenderName(x.getSender().getName());
		}
		mail.setSubject(x.getSubject());
		return mail;
	}

	@Override
	public InBoxMail convertToModel(ClientInBoxMail y) throws Exception {
		InBoxMail mail=new InBoxMail();
		mail.setContent(y.getContent());
		mail.setId(y.getId());
		mail.setReceiveIdList(y.getReceiveIdList());
		mail.setReceiveNameList(y.getReceiveNameList());
		mail.setSendDate(y.getSendDate());
		mail.setSubject(y.getSubject());
		return mail;
	}

}
