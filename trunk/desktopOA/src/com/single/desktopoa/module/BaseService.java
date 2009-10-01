package com.single.desktopoa.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


import net.sf.hibernate4gwt.core.HibernateBeanManager;
import net.sf.hibernate4gwt.gwt.HibernateRemoteService;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationThrowable;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.UnexpectedException;
import com.single.desktopoa.client.event.CometEvents;
import com.single.desktopoa.common.person.Person;
import com.single.desktopoa.exception.RetryException;
import com.single.desktopoa.util.ConvertUtils;

public class BaseService extends RemoteServiceServlet {

	public static final String PAYLOAD = "com.google.gwt.payload";

	public static final String PERSON = "person";
	public static final String COMET_UTIL = "CometUtil";

	 public static final CometUtil cometUtil=new CometUtil();

	protected Person person;

	protected WebApplicationContext springContext;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		springContext = WebApplicationContextUtils.getWebApplicationContext(
				config.getServletContext(),
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//		setBeanManager((HibernateBeanManager) springContext
//				.getBean("hibernateBeanManager"));
	}

	@Override
	protected String readContent(HttpServletRequest request)
			throws ServletException, IOException {
		String payload = (String) request.getAttribute(PAYLOAD);
		if (payload == null) {
			payload = super.readContent(request);
			request.setAttribute(PAYLOAD, payload);
		}

		return payload;
	}

	@Override
	protected void doUnexpectedFailure(Throwable e) {
		throwIfRetryRequest(e);
		super.doUnexpectedFailure(e);
	}

	protected void throwIfRetryRequest(Throwable caught) {
		if (caught instanceof UnexpectedException) {
			caught = caught.getCause();
		}
//		if (JETTY_RETRY_REQUEST_EXCEPTION.equals(caught.getClass().getName())) {
//			throw new ContinuationThrowable();
//		}
		if(caught instanceof RetryException){
			throw new ContinuationThrowable();
		}
		
	}

	@Override
	protected void onBeforeRequestDeserialized(String serializedRequest) {
		person = (Person) getThreadLocalRequest().getSession().getAttribute(
				PERSON);
		super.onBeforeRequestDeserialized(serializedRequest);
	}

	@SuppressWarnings("unchecked")
	protected <X>X convertToClient(Object object){
		Object result=null;
		try {
			result=ConvertUtils.convertToClient(object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return (X)result;
	}
	@SuppressWarnings("unchecked")
	protected <X>X convertToModel(Object object){
		Object result=null;
		try {
			result=ConvertUtils.convertToModel(object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (X)result;
	}
	
	public class CometMember {
		public Person person;
		public Continuation continuation;
		public Queue<ModelData> queue = new LinkedList<ModelData>();

		public void addEvent(String event,Object data) {
			boolean isContain = false;
			for (Iterator<ModelData> it = queue.iterator(); it.hasNext();) {
				if (it.next().get("event").equals(event)) {
					isContain = true;
					break;
				}
			}
			if (!isContain) {
				ModelData modeldata=new BaseModelData();
				modeldata.set("event", event);
				if(data!=null)
					modeldata.set("data", data);
				queue.add(modeldata);
			}
			synchronized (this) {
//				if (continuation != null) {
//					continuation.resume();
//				}
			}
		}
	}

	public static class CometUtil {
		protected HashMap<Long, CometMember> memberMap = new HashMap<Long, CometMember>();

		public List<Person> getOnlineList(){
			List<Person> onlines=new ArrayList<Person>();
			for(CometMember member:memberMap.values()){
				onlines.add(member.person);
			}
			return onlines;
		}
		public boolean addEvent(Person receiver, String event,Object data) {
			if (isContain(receiver)) {
				CometMember member = memberMap.get(receiver.getId());
				member.addEvent(event,data);
				return true;
			}
			return false;
		}

		public void addMember(CometMember member) {
			for(Iterator<CometMember> it=memberMap.values().iterator();it.hasNext();){
				CometMember mem=it.next();
				mem.addEvent(CometEvents.WORKTALK_newlogin, null);
			}
			memberMap.put(member.person.getId(), member);
			//test
			member.addEvent(CometEvents.WORKTALK_newlogin, null);
		}

		private boolean isContain(Person receiver) {
			if (memberMap.get(receiver.getId()) == null) {
				return false;
			}
			return true;
		}
	}

}
