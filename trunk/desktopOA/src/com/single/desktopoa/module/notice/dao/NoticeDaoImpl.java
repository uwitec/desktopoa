package com.single.desktopoa.module.notice.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.single.desktopoa.module.notice.model.Notice;

public class NoticeDaoImpl extends HibernateDaoSupport implements NoticeDao {

	@Override
	public void add(Notice notice) {
		getHibernateTemplate().save(notice);
	}

	@Override
	public void delete(Long noticeId) {
		getHibernateTemplate().delete(getHibernateTemplate().load(Notice.class, noticeId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notice> findNoticeList(final int start,final int limit) {
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery("from Notice order by createDate asc")
								.setFirstResult(start)
								.setMaxResults(limit)
								.list();
			}
		});
	}

	@Override
	public Notice findNoticeById(Long id) {
		return (Notice)getHibernateTemplate().get(Notice.class, id);
	}

}
