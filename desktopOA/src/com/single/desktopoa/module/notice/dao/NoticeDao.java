package com.single.desktopoa.module.notice.dao;

import java.util.List;

import com.single.desktopoa.module.notice.model.Notice;

public interface NoticeDao {

	void add(Notice notice);
	
	void delete(Long noticeId);
	
	List<Notice> findNoticeList(int start,int limit);
	
	Notice findNoticeById(Long id);
}
