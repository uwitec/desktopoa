package com.single.desktopoa.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.single.desktopoa.client.exception.SecurityException;
import com.single.desktopoa.client.model.ClientDeptment;
import com.single.desktopoa.common.dao.DeptmentDao;
import com.single.desktopoa.common.dao.PersonDao;
import com.single.desktopoa.common.deptment.Deptment;
import com.single.desktopoa.common.person.Person;
import com.single.desktopoa.common.service.DeptmentService;
import com.single.desktopoa.module.BaseService;

public class DeptmentServiceImpl extends BaseService implements DeptmentService {

	private DeptmentDao deptmentDao;
	private PersonDao personDao;
	@Override
	public void addDeptment(Long parentId, ClientDeptment cd) {
		Deptment deptment=new Deptment();
		deptment.setName(cd.getName());
		deptmentDao.addDeptment(parentId, deptment);
	}

	@Override
	public void removeDeptment(Long id) {
		Deptment deptment=deptmentDao.findDeptmentById(id);
		if(deptment==null){
			throw new SecurityException("部门不存在");
		}else{
			if(deptment.getSubDept().size()>0){
				throw new SecurityException("该部门有子部门，无法删除");
			}
			List<Person> list=personDao.findPersonByDeptment(deptment.getId());
			if(list.size()>0){
				throw new SecurityException("该部门有人担任，无法删除");
			}
			deptmentDao.removeDeptment(deptment.getId());
		}
	}
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		deptmentDao=(DeptmentDao) springContext.getBean("deptmentDao");
		personDao=(PersonDao) springContext.getBean("personDao");
	}

	@Override
	public void addRootDeptment(ClientDeptment cd) {
		Deptment deptment=new Deptment();
		deptment.setName(cd.getName());
		deptmentDao.addDeptment(null, deptment);
	}

	@Override
	public TreeModel getDeptmentTree() {
		List<Deptment> depts=deptmentDao.findRootDeptment();
		TreeModel root=new BaseTreeModel();
		for(Deptment deptment:depts){
			TreeModel sub=buildDeptmentTree(deptment);
			sub.setParent(root);
			root.add(sub);
		}
		return new BaseTreeModel();
	}
	
	private TreeModel buildDeptmentTree(Deptment deptment){
		TreeModel model=new BaseTreeModel();
		model.set("name", deptment.getName());
		
		for(Deptment dept:deptment.getSubDept()){
			TreeModel sub=buildDeptmentTree(dept);
			sub.setParent(model);
			model.add(sub);
		}
		
		return model;
	}

	@Override
	public List<ClientDeptment> getSubDeptment(Long id) {
		return getDeptments(id);
	}

	@Override
	public List<ClientDeptment> getRootDeptment() {
		return getDeptments(null);
	}
	private List<ClientDeptment> getDeptments(Long id){
		List<Deptment> list;
		if(id==null){
			list=deptmentDao.findRootDeptment();
		}else{
			list=deptmentDao.findSubDeptment(id);
		}
		
		List<ClientDeptment> result=new ArrayList<ClientDeptment>();
		for(Deptment deptment:list){
			ClientDeptment data=convertToClient(deptment);
			result.add(data);
		}
		return result;
	}

}
