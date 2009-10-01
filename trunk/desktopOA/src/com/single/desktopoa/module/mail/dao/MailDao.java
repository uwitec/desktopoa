package com.single.desktopoa.module.mail.dao;

import java.util.List;

import com.single.desktopoa.module.mail.model.InBoxMail;
import com.single.desktopoa.module.mail.model.SendBoxMail;

public interface MailDao {

	public void saveMail(InBoxMail mail);
	
	public void saveMail(SendBoxMail mail);
	
	public List<InBoxMail> findInboxMails(Long personId,int start,int limit);
	
	public long findInboxCount(Long personId);
	
	public List<SendBoxMail> findSendboxMails(Long personId,int start,int limit);
	
	public long findSendboxCount(Long personId);
	
	public InBoxMail findInBoxMail(Long id);
	
	public SendBoxMail findSendBoxMail(Long id);
}
