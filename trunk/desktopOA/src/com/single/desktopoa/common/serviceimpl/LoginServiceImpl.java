package com.single.desktopoa.common.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.single.desktopoa.client.exception.MySecurityException;
import com.single.desktopoa.client.model.ClientDeptment;
import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.client.model.ClientUser;
import com.single.desktopoa.common.dao.DeptmentDao;
import com.single.desktopoa.common.dao.PersonDao;
import com.single.desktopoa.common.deptment.Deptment;
import com.single.desktopoa.common.person.Person;
import com.single.desktopoa.common.service.LoginService;
import com.single.desktopoa.common.user.User;
import com.single.desktopoa.module.BaseService;

public class LoginServiceImpl extends BaseService implements LoginService {

	
	private PersonDao personDao;
	private DeptmentDao deptmentDao;
	
	@Override
	public ClientPerson login(String username, String password)
			throws MySecurityException {
		Person person=personDao.findPersonByUsername(username);
		if(person==null){
			throw new MySecurityException("用户不存在！");
		}
		if(!password.equals(person.getAccount().getPassword())){
			throw new MySecurityException("密码不正确！");
		}
		
		HttpServletRequest request=getThreadLocalRequest();
		request.getSession().setAttribute("person", person);
		
		ClientPerson cp=new ClientPerson();
		cp.setId(person.getId());
		cp.setName(person.getName());
		cp.setEmail(person.getEmail());
		
		return cp;

	}

	@Override
	public void logout(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		personDao=(PersonDao)springContext.getBean("personDao");
		deptmentDao=(DeptmentDao)springContext.getBean("deptmentDao");
	}

	@Override
	public List<ModelData> getPersonListWithModelData() {
		List<Person> personList= personDao.findPersonList();
		List<ModelData> result=new ArrayList<ModelData>();
		for(int i=0;i<personList.size();i++){
			ModelData data=new BaseModelData();
			data.set("name", personList.get(i).getName());
			data.set("id", personList.get(i).getId());
			result.add(data);
		}
		
		return result;
	}


	@Override
	public boolean usernameUniqueCheck(String username) {
		Person person=personDao.findPersonByUsername(username);
		return person==null?true:false;
	}

	@Override
	public ClientPerson regist(ClientPerson cp, ClientUser user) {
		
		User u=new User();
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		
		Person p=new Person();
		p.setName(cp.getName());
		p.setEmail(cp.getEmail());
		p.setAccount(u);
		
		try {
			
			personDao.addPerson(p);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("注册用户失败");
		}
		
		getThreadLocalRequest().getSession().setAttribute("person", p);
		
		cp.setId(p.getId());
		
		return cp;
	}

	@Override
	public List<ModelData> getPersonListWithDeptment(ModelData dept) {
		
		List<ModelData> result=new ArrayList<ModelData>();
		if(dept==null){
			List<Deptment> depts=deptmentDao.findRootDeptment();
			for(Deptment deptment:depts){
				ClientDeptment clientDeptment=convertToClient(deptment);
				result.add(clientDeptment);
			}
		}else{
			Deptment deptment=deptmentDao.findDeptmentById(dept.<Long>get("id"));
			for(Person p:deptment.getEmployee()){
				ClientPerson cp=convertToClient(p);
				result.add(cp);
			}
			
			List<Deptment> depts=deptmentDao.findSubDeptment(dept.<Long>get("id"));
			for(Deptment dept1:depts){
				ClientDeptment clientDeptment=convertToClient(dept1);
				result.add(clientDeptment);
			}
		}
		
		return result;
	}

}
