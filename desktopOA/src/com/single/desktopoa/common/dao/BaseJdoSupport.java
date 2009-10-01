package com.single.desktopoa.common.dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class BaseJdoSupport {

	private JdoTemplate jdoTemplate;
	{
		jdoTemplate=new JdoTemplate(factory);
	}
	
	private static final PersistenceManagerFactory factory=
		JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	
	protected PersistenceManager getPM(){
		return factory.getPersistenceManager();
	}
	
	
	
	

	public JdoTemplate getJdoTemplate() {
		return jdoTemplate;
	}
}
