package com.single.desktopoa.common.dao;

import javax.jdo.PersistenceManager;

public interface JdoCallback {

	Object doInJdo(PersistenceManager pm);
}
