package com.single.desktopoa.module.notice.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.single.desktopoa.client.model.ClientNotice;
import com.single.desktopoa.module.BaseService;
import com.single.desktopoa.module.notice.dao.NoticeDao;
import com.single.desktopoa.module.notice.model.Notice;
import com.single.desktopoa.module.notice.service.NoticeService;

public class NoticeServiceImpl extends BaseService implements NoticeService {

	private NoticeDao noticeDao;
	@Override
	public void addNotice(ClientNotice cn) {
		Notice notice=new Notice();
		notice.setContent(cn.getContent());
		notice.setHeigth(cn.getHeigth());
		notice.setSubject(cn.getSubject());
		notice.setWidth(cn.getWidth());
		
		notice.setCreateDate(new Date());
		notice.setCreator(person);
		
		if(notice.getWidth()==null){
			notice.setWidth(350);
		}
		if(notice.getHeigth()==null){
			notice.setHeigth(350);
		}
		
		
		noticeDao.add(notice);
	}

	@Override
	public void deleteNotice(Long noticeId) {
		noticeDao.delete(noticeId);
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		noticeDao=(NoticeDao)springContext.getBean("noticeDao");
	}

	@Override
	public List<ModelData> getNoticeListWithModelData() {
		List<Notice> noticeList=noticeDao.findNoticeList(0, 10);
		
		List<ModelData> result=new ArrayList<ModelData>();
		for(Notice notice:noticeList){
			ModelData data=new BaseModelData();
			data.set("subject", notice.getSubject());
			data.set("createDate", notice.getCreateDate());
			
			result.add(data);
		}
		
		return result;
	}

	@Override
	public List<ModelData> getPortalList() {
		List<Notice> list=noticeDao.findNoticeList(0, 5);
		List<ModelData> result=new ArrayList<ModelData>();
		
		for(Notice notice:list){
			ModelData data=new BaseModelData();
			data.set("id", notice.getId());
			data.set("subject", notice.getSubject());
			
			result.add(data);
		}
		
		
		return result;
	}

	@Override
	public ClientNotice readNotice(Long id) {
		Notice notice= noticeDao.findNoticeById(id);
		
		ClientNotice cn=new ClientNotice();
		cn.setContent(notice.getContent());
		cn.setCreateDate(notice.getCreateDate());
		cn.setHeigth(notice.getHeigth());
		cn.setSubject(notice.getSubject());
		cn.setWidth(notice.getWidth());
		
		return cn;
	}

}
