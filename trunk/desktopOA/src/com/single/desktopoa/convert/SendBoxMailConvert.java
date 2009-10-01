package com.single.desktopoa.convert;

import com.single.desktopoa.client.model.ClientSendBoxMail;
import com.single.desktopoa.module.mail.model.SendBoxMail;

public class SendBoxMailConvert implements Convert<SendBoxMail, ClientSendBoxMail> {

	@Override
	public ClientSendBoxMail convertToClient(SendBoxMail x) throws Exception {
		ClientSendBoxMail mail=new ClientSendBoxMail();
		mail.setContent(x.getContent());
		mail.setId(x.getId());
		mail.setReceiveIdList(x.getReceiveIdList());
		mail.setReceiveNameList(x.getReceiveNameList());
		mail.setSendDate(x.getSendDate());
		mail.setSenderId(x.getSender().getId());
		mail.setSenderName(x.getSender().getName());
		mail.setSubject(x.getSubject());
		return mail;
	}

	@Override
	public SendBoxMail convertToModel(ClientSendBoxMail y) throws Exception {
		SendBoxMail mail=new SendBoxMail();
		mail.setContent(y.getContent());
		mail.setId(y.getId());
		mail.setReceiveIdList(y.getReceiveIdList());
		mail.setReceiveNameList(y.getReceiveNameList());
		mail.setSendDate(y.getSendDate());
		mail.setSubject(y.getSubject());
		return mail;
	}

}
