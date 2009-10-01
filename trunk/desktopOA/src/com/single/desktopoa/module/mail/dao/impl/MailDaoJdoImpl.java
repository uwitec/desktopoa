package com.single.desktopoa.module.mail.dao.impl;

import java.util.List;

import javax.jdo.Query;

import com.single.desktopoa.common.dao.BaseJdoSupport;
import com.single.desktopoa.module.mail.dao.MailDao;
import com.single.desktopoa.module.mail.model.InBoxMail;
import com.single.desktopoa.module.mail.model.SendBoxMail;

public class MailDaoJdoImpl extends BaseJdoSupport implements MailDao {

	@Override
	public InBoxMail findInBoxMail(Long id) {
		return getPM().getObjectById(InBoxMail.class, id);
	}

	@Override
	public long findInboxCount(Long personId) {
		Query query=getPM().newQuery();
		return 0;
	}

	@Override
	public List<InBoxMail> findInboxMails(Long personId, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SendBoxMail findSendBoxMail(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long findSendboxCount(Long personId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SendBoxMail> findSendboxMails(Long personId, int start,
			int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveMail(InBoxMail mail) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveMail(SendBoxMail mail) {
		// TODO Auto-generated method stub

	}

}
