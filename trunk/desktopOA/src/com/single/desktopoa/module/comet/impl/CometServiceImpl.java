package com.single.desktopoa.module.comet.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.mortbay.jetty.RetryRequest;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.single.desktopoa.exception.RetryException;
import com.single.desktopoa.module.BaseService;
import com.single.desktopoa.module.comet.CometService;

public class CometServiceImpl extends BaseService implements CometService {

	
	
	

	
	public List<ModelData> getEvents() {
		HttpServletRequest request=getThreadLocalRequest();
		HttpServletResponse response=getThreadLocalResponse();
		
		
		CometMember member=(CometMember)request.getSession().getAttribute(MEMBER);
		if(member==null){
			member=new CometMember();
			member.person=person;
			
			request.getSession().setAttribute(MEMBER, member);
			cometUtil.addMember(member);
		}
		if(member.queue.size()>0){
			List<ModelData> result=new ArrayList<ModelData>();
			while(member.queue.size()>0){
				result.add(member.queue.poll());
			}
			return result;
		}else {
			Continuation continuation=ContinuationSupport.getContinuation(request);
			if(continuation.isInitial()){
				//如果是新创建的
				continuation.suspend(response);
				member.continuation=continuation;
			}else{
				return new ArrayList<ModelData>();
			}
		}
		
		
		throw new RetryException();
		
		
	}

	

}
