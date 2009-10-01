package com.single.desktopoa.common.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

public class JdoTemplate {

	private PersistenceManagerFactory factory;
	
	public JdoTemplate(PersistenceManagerFactory factory){
		this.factory=factory;
	}
	
	public <T> T makePersistent (T pc){
		T t=null;
		PersistenceManager pm=factory.getPersistenceManager();
		try {
			t=pm.makePersistent(pc);
		} finally{
			pm.close();
		}
		return t;
	}
	public Object execute(JdoCallback callback){
		Object object=null;
		PersistenceManager pm=factory.getPersistenceManager();
		
		try {
			object=callback.doInJdo(pm);
		} finally{
			pm.close();
		}
		return object;
	}
	public Object find(final String sql){
		return execute(new JdoCallback(){
			@Override
			public Object doInJdo(PersistenceManager pm) {
				List<?> result=null;
				Query query=pm.newQuery(sql);
				result= (List<?>) query.execute();
				return result;
			}
		});
	}
}
