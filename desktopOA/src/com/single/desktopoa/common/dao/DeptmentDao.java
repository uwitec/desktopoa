package com.single.desktopoa.common.dao;

import java.util.List;

import com.single.desktopoa.common.deptment.Deptment;

public interface DeptmentDao {

	void addDeptment(Long leaderId,Deptment deptment);
	
	void removeDeptment(Long id);
	
	Deptment findDeptmentById(Long id);
	
	List<Deptment> findRootDeptment();
	
	List<Deptment> findSubDeptment(Long parentId);
}
