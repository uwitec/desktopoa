package com.single.desktopoa.module.file.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.single.desktopoa.module.file.dao.FileDao;
import com.single.desktopoa.module.file.model.File;

public class FileDaoImpl extends HibernateDaoSupport implements FileDao {

	@Override
	public void addFile(File file) {
		getHibernateTemplate().save(file);

	}

	@Override
	public void editFile(File file) {
		getHibernateTemplate().update(file);
	}

	@Override
	public List<File> findFilesByCreator(Long personId) {
		return getHibernateTemplate().find("from File where creator.id="+personId);
	}

	@Override
	public File findFile(Long id) {
		return (File)getHibernateTemplate().get(File.class, id);
	}

}
