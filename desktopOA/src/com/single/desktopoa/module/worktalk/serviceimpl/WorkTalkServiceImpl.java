package com.single.desktopoa.module.worktalk.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.single.desktopoa.client.event.CometEvents;
import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.client.model.TalkMessage;
import com.single.desktopoa.common.dao.PersonDao;
import com.single.desktopoa.common.person.Person;
import com.single.desktopoa.module.BaseService;
import com.single.desktopoa.module.worktalk.service.WorkTalkService;

public class WorkTalkServiceImpl extends BaseService implements WorkTalkService {

	private PersonDao personDao;
	@Override
	public boolean talkTo(Long personId, String msg) {
		ClientPerson self=new ClientPerson();
		self.setId(person.getId());
		self.setName(person.getName());
		
		Person receiver=personDao.findPersonById(personId);
		TalkMessage message=new TalkMessage();
		message.setDate(new Date());
		message.setFrom(self);
		message.setMsg(msg);
		
		return cometUtil.addEvent(receiver, CometEvents.WORKTALK_msg,message);
		
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		personDao=(PersonDao)springContext.getBean("personDao");
	}

	@Override
	public List<ModelData> getOnlineList() {
		List<Person> onlines=cometUtil.getOnlineList();
		List<ModelData> result=new ArrayList<ModelData>();
		
		for(Person p:onlines){
			ModelData data=new BaseModelData();
			data.set("id", p.getId());
			data.set("name", p.getName());
//			data.set("bean", p);
			
			result.add(data);
		}
		return result;
	}
}
