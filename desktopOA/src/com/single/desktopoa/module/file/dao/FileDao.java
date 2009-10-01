package com.single.desktopoa.module.file.dao;

import java.util.List;

import com.single.desktopoa.module.file.model.File;

public interface FileDao {

	public void addFile(File file);
	
	public void editFile(File file);
	
	public List<File> findFilesByCreator(Long personId);
	
	public File findFile(Long id);
}
