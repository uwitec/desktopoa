package com.single.desktopoa.module.mail.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.single.desktopoa.module.mail.dao.MailDao;
import com.single.desktopoa.module.mail.model.InBoxMail;
import com.single.desktopoa.module.mail.model.SendBoxMail;

public class MailDaoImpl extends HibernateDaoSupport implements MailDao {

	@Override
	public long findInboxCount(Long personId) {
		return (Long)getHibernateTemplate().find("select count(*) from InBoxMail where receiver.id="+personId).get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<InBoxMail> findInboxMails(final Long personId,final int start,final int limit) {
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery("from InBoxMail where receiver.id="+personId +"order by sendDate desc")
						.setFirstResult(start)
						.setMaxResults(limit).list();
			}
		});
	}

	@Override
	public long findSendboxCount(Long personId) {
		return (Long)getHibernateTemplate().find("select count(*) from SendBoxMail where sender.id"+personId).get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SendBoxMail> findSendboxMails(final Long personId,final int start,final int limit) {
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery("from SendBoxMail where sender.id="+personId)
							.setFirstResult(start)
							.setMaxResults(limit)
							.list();
				
			}
		});
	}

	@Override
	public void saveMail(InBoxMail mail) {
		getHibernateTemplate().save(mail);
		
	}

	@Override
	public void saveMail(SendBoxMail mail) {
		getHibernateTemplate().save(mail);
		
	}

	@Override
	public InBoxMail findInBoxMail(Long id) {
		return (InBoxMail)getHibernateTemplate().get(InBoxMail.class, id);
	}

	@Override
	public SendBoxMail findSendBoxMail(Long id) {
		return (SendBoxMail)getHibernateTemplate().get(SendBoxMail.class, id);
	}


}
