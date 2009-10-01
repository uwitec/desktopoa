package com.single.desktopoa.module.mail.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.single.desktopoa.client.event.CometEvents;
import com.single.desktopoa.client.model.ClientInBoxMail;
import com.single.desktopoa.client.model.ClientSendBoxMail;
import com.single.desktopoa.common.dao.PersonDao;
import com.single.desktopoa.common.person.Person;
import com.single.desktopoa.module.BaseService;
import com.single.desktopoa.module.mail.dao.MailDao;
import com.single.desktopoa.module.mail.model.InBoxMail;
import com.single.desktopoa.module.mail.model.SendBoxMail;
import com.single.desktopoa.module.mail.service.MailService;
 
public class MailServiceImpl extends BaseService implements MailService {

	private MailDao mailDao;
	
	private PersonDao personDao;
	@Override
	public PagingLoadResult<ClientInBoxMail> getInBoxMails(PagingLoadConfig config) {
		
		int start=config.getOffset();
		int limit=config.getLimit();
		
		
		List<InBoxMail> list=mailDao.findInboxMails(person.getId(), start, limit);
		
		List<ClientInBoxMail> result=new ArrayList<ClientInBoxMail>();
		for(InBoxMail mail:list){
			ClientInBoxMail client=convertToClient(mail);
			result.add(client);
		}
		
		long count=mailDao.findInboxCount(person.getId());
		
		
		return new BasePagingLoadResult<ClientInBoxMail>(result,start,Long.valueOf(count).intValue());
		
	}

	@Override
	public PagingLoadResult<ClientSendBoxMail> getSendBoxMails(PagingLoadConfig config) {
		int start=config.getOffset();
		int limit=config.getLimit();
		
		List<SendBoxMail> list=mailDao.findSendboxMails(person.getId(), start, limit);
		List<ClientSendBoxMail> result=new ArrayList<ClientSendBoxMail>();
		for(SendBoxMail mail:list){
			ClientSendBoxMail client=convertToClient(mail);
			result.add(client);
		}
		
		
		long count=mailDao.findSendboxCount(person.getId());
		
		
		return new BasePagingLoadResult<ClientSendBoxMail>(result,start,Long.valueOf(count).intValue());
		
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		mailDao=(MailDao)springContext.getBean("mailDao");
		personDao=(PersonDao)springContext.getBean("personDao");
	}

	@Override
	public void sendMail(ClientSendBoxMail clientSendBoxMail) {
		SendBoxMail mail=convertToModel(clientSendBoxMail);
		
		String receiveIdList=mail.getReceiveIdList();
		String[] receivers=receiveIdList.split(",");
		List<Person> receiveList=new ArrayList<Person>();
		if(mail.getSubject()==null||"".equals(mail.getSubject())){
			mail.setSubject("无主题");
		}
		
		
		//发送邮件
		for(int i=0;i<receivers.length;i++){
			Person receiver=personDao.findPersonById(Long.valueOf(receivers[i]));
			InBoxMail newMail=new InBoxMail();
			newMail.setContent(mail.getContent());
			newMail.setReceiveNameList(mail.getReceiveNameList());
			newMail.setReceiveIdList(mail.getReceiveIdList());
			newMail.setReceiver(receiver);
			newMail.setSendDate(new Date());
			newMail.setSender(person);
			newMail.setSubject(mail.getSubject());
			mailDao.saveMail(newMail);
			
			receiveList.add(receiver);
		}
		//保存已发送
		mail.setSender(person);
		mail.setSendDate(new Date());
		mailDao.saveMail(mail);
		
		
		//发送在线提醒
		for(int i=0;i<receiveList.size();i++){
			cometUtil.addEvent(receiveList.get(i), CometEvents.MAIL_receive,null);
		}
	}

	@Override
	public List<ModelData> getPortalList() {
		
		List<InBoxMail> mailList=mailDao.findInboxMails(person.getId(), 0, 5);
		List<ModelData> result=new ArrayList<ModelData>();
		for(InBoxMail mail:mailList){
			ModelData data=new BaseModelData();
			data.set("subject", mail.getSubject());
			data.set("id", mail.getId());
			
			result.add(data);
		}
		return result;
	}

	@Override
	public ClientInBoxMail readInBoxMail(Long mailId) {
		InBoxMail mail= mailDao.findInBoxMail(mailId);
		
		return convertToClient(mail);
	}

	@Override
	public ClientSendBoxMail readSendBoxMail(Long mailId) {
		SendBoxMail mail=mailDao.findSendBoxMail(mailId);
		return convertToClient(mail);
	}

}
