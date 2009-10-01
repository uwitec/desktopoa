package com.single.desktopoa.module.mydesk.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.single.desktopoa.module.BaseService;
import com.single.desktopoa.module.mydesk.service.MydeskService;

public class MydeskServiceImpl extends BaseService implements MydeskService {

	@Override
	public List<ModelData> getPicList() {
		
		List<ModelData> result=new ArrayList<ModelData>();
		
		ModelData mail=new BaseModelData();
		mail.set("name", "我的邮件");
		mail.set("path", "icons/mydesk/mail.png");
		
		ModelData notice=new BaseModelData();
		notice.set("name", "公告系统");
		notice.set("path", "icons/mydesk/notice.png");
		
		result.add(mail);
		result.add(notice);
		
		return result;
	}

}
