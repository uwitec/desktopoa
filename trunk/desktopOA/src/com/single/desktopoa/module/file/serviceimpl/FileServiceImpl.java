package com.single.desktopoa.module.file.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.single.desktopoa.client.exception.BaseException;
import com.single.desktopoa.client.exception.MySecurityException;
import com.single.desktopoa.client.model.ClientFile;
import com.single.desktopoa.client.model.ClientPerson;
import com.single.desktopoa.common.dao.DeptmentDao;
import com.single.desktopoa.common.dao.PersonDao;
import com.single.desktopoa.common.deptment.Deptment;
import com.single.desktopoa.common.person.Person;
import com.single.desktopoa.module.BaseService;
import com.single.desktopoa.module.file.dao.CounterSignDao;
import com.single.desktopoa.module.file.dao.FileDao;
import com.single.desktopoa.module.file.model.CounterSign;
import com.single.desktopoa.module.file.model.File;
import com.single.desktopoa.module.file.model.SignRecord;
import com.single.desktopoa.module.file.service.FileService;
import com.single.desktopoa.module.mail.dao.MailDao;
import com.single.desktopoa.module.mail.model.InBoxMail;

public class FileServiceImpl extends BaseService implements FileService {

	private FileDao fileDao;
	
	private PersonDao personDao;
	
	private DeptmentDao deptmentDao;
	
	private CounterSignDao counterSignDao;
	
	private MailDao mailDao;
	
	@Override
	public void saveFile(ClientFile clientFile)  throws BaseException{
		File file=convertToModel(clientFile);
		
		if(clientFile.getId()==null){
			file.setCreator(person);
			file.setCreateDate(new Date());
			file.setLastModifyDate(new Date());
			file.setLastModifyer(person);
			fileDao.addFile(file);
		}else {
			File older=fileDao.findFile(clientFile.getId());
			older.setContent(clientFile.getContent());
			older.setLastModifyDate(new Date());
			older.setLastModifyer(person);
			older.setEditDegree(clientFile.getEditDegree());
			older.setEditList(clientFile.getEditList());
			older.setReadDegree(clientFile.getReadDegree());
			older.setReadList(clientFile.getReadList());
			older.setSubject(clientFile.getSubject());
			
			fileDao.editFile(older);
		}

	}

	@Override
	public List<ModelData> getFileList(ModelData modelData) {
		if(modelData==null||modelData.get("subject")==null){
			return getPersonList();
		}
		
		
		List<File> files=fileDao.findFilesByCreator((Long)modelData.get("personId"));
		List<ModelData> result=new ArrayList<ModelData>();
		for(int i=0;i<files.size();i++){
			File file=files.get(i);
			ModelData data=new BaseModelData();
			data.set("id", file.getId());
			data.set("subject", file.getSubject());
			data.set("createDate", file.getCreateDate());
			data.set("lastModifyDate", file.getLastModifyDate());
			data.set("lastModifyer", file.getLastModifyer().getName());
			
			result.add(data);
		}
		
		
		return result;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		fileDao=(FileDao)springContext.getBean("fileDao");
		personDao=(PersonDao)springContext.getBean("personDao");
		deptmentDao=(DeptmentDao)springContext.getBean("deptmentDao");
		counterSignDao=(CounterSignDao)springContext.getBean("counterSignDao");
		mailDao=(MailDao)springContext.getBean("mailDao");
	}

	@Override
	public List<ModelData> getPersonList() {
		List<Person> persons=personDao.findPersonList();
		List<ModelData> result=new ArrayList<ModelData>();
		
		for(int i=0;i<persons.size();i++){
			ModelData data=new BaseModelData();
			data.set("subject", persons.get(i).getName());
			data.set("personId", persons.get(i).getId());
			data.set("folder", "true");
			result.add(data);
		}
		
		return result;
	}

	@Override
	public ClientFile editFile(Long id) throws MySecurityException {
		
		File file=fileDao.findFile(id);
		if(file==null){
			throw new MySecurityException("文件不存在,或已删除!");
		}else if(file.getEditDegree()==File.EDIT_ALL||person.getId().equals(file.getCreator().getId())){
			return convertToClient(file);
		}else{
			if(file.getEditDegree()==File.EDIT_ASSIGN){
				String[] names=file.getEditList().split(",");
				for(int i=0;i<names.length;i++){
					if(person.getId().equals(Long.valueOf(names[i]))){
						return convertToClient(file);
					}
				}
			}
		}
		throw new MySecurityException("你无权编辑此文档");
	}

	@Override
	public ClientFile readFile(Long id) throws MySecurityException {
		
		
		File file=fileDao.findFile(id);
		if(file==null){
			throw new MySecurityException("文件不存在,或已删除!");
		}
		//检测阅读权限
		boolean readable=false;
		if(file.getReadDegree()==File.READ_ALL||person.getId().equals(file.getCreator().getId())){
			readable=true;
		}else if(file.getReadDegree()==File.READ_ASSIGN){
			String[] names=file.getReadList().split(",");
			for(int i=0;i<names.length;i++){
				if(person.getId().equals(Long.valueOf(names[i]))){
					readable=true;
				}
			}
		}
		if(!readable){
			throw new MySecurityException("你无权查看此文档！");
		}
		//检测编辑权限
		boolean editable=false;
		if(file.getStatus()==File.STATUS_NONE){
			if(file.getEditDegree()==File.EDIT_ALL||person.getId().equals(file.getCreator().getId())){
				editable=true;
			}else if(file.getEditDegree()==File.EDIT_ASSIGN){
				String[] names=file.getEditList().split(",");
				for(int i=0;i<names.length;i++){
					if(person.getId().equals(Long.valueOf(names[i]))){
						editable=true;
					}
				}
			}
		}
		//是否需要签核
		boolean needSign=false;
		if(file.getStatus()==File.STATUS_SIGN&&!file.getOver()){
			for(SignRecord record:file.getCounterSign().getSigners()){
				if(record.getSigner().getId().equals(person.getId())&&!record.getSigned()){
					needSign=true;
				}
			}
		}
		
		//是否可以呈主管
		//是否可以开始会签
		boolean giveinable=false;
		boolean signable=false;
		if(file.getCreator().getId().equals(person.getId())&&file.getStatus()==File.STATUS_NONE){
			giveinable=true;
			signable=true;
		}
		
		ClientFile clientFile=convertToClient(file);
		clientFile.setNeedCounterSign(needSign);
		clientFile.setGiveinable(giveinable);
		clientFile.setEditable(editable);
		clientFile.setSignable(signable);
		
		return clientFile;
	}

	@Override
	public void groupSign(Long id,String reason) throws MySecurityException {
		File file=fileDao.findFile(id);
		Deptment deptment=personDao.findPersonById(person.getId()).getDeptment();
		if(deptment==null){
			throw new MySecurityException("您没有部门");
		}
		if(deptment.getEmployee().size()==1){
			throw new MySecurityException("部门没有其他人可以会签");
		}
		if(file.getStatus()==File.STATUS_NONE){
			file.setStatus(File.STATUS_SIGN);
		}else{
			throw new MySecurityException("文档当前状态无法进行会签");
		}
		
		CounterSign counterSign=new CounterSign();
		counterSign.setReason(reason);
		counterSignDao.add(counterSign);
		counterSign.setFile(file);
		counterSign.setCreateDate(new Date());
		file.setCounterSign(counterSign);
		for(Person p:deptment.getEmployee()){
			if(p.getId().equals(person.getId())){
				continue;
			}
			SignRecord record=new SignRecord();
			record.setCounterSign(counterSign);
			record.setSigner(p);
			counterSign.getSigners().add(record);
			
			InBoxMail mail=new InBoxMail();
			mail.setContent("您有一份文件:"+file.getSubject()+" 需要会签");
			mail.setReceiveIdList(String.valueOf(p.getId()));
			mail.setReceiveNameList(p.getName());
			mail.setReceiver(p);
			mail.setSendDate(new Date());
			mail.setSubject("[会签]"+file.getSubject());
			mailDao.saveMail(mail); 
		}
		
		fileDao.editFile(file);
		
	}

	@Override
	public void signFile(Long id,String suggest) throws MySecurityException {
		
		File file=fileDao.findFile(id);
		CounterSign counterSign=file.getCounterSign();
		Set<SignRecord> records=counterSign.getSigners();
		for(SignRecord record:records){
			if(record.getSigner().getId().equals(person.getId())){
				if(record.getSigned()){
					throw new MySecurityException("您已经签核过了");
				}else{
					record.setSigned(true);
					record.setSuggest(suggest);
					record.setSignDate(new Date());
					counterSignDao.update(counterSign);
				}
			}
		}
	}

	@Override
	public void customSign(Long fileId,String reason, List<ClientPerson> datas)
			throws MySecurityException {
		File file=fileDao.findFile(fileId);
		if(datas.size()==0){
			throw new MySecurityException("没有选择会签人员");
		}
		if(file.getStatus()==File.STATUS_NONE){
			file.setStatus(File.STATUS_SIGN);
		}else{
			throw new MySecurityException("文档当前状态无法进行会签");
		}
		CounterSign counterSign=new CounterSign();
		counterSign.setReason(reason);
		counterSignDao.add(counterSign);
		counterSign.setFile(file);
		counterSign.setCreateDate(new Date());
		file.setCounterSign(counterSign);
		for(ClientPerson cp:datas){
			if(cp.getId().equals(person.getId())){
				continue;
			}
			Person p=personDao.findPersonById(cp.getId());
			SignRecord record=new SignRecord();
			record.setCounterSign(counterSign);
			record.setSigner(p);
			counterSign.getSigners().add(record);
			
			InBoxMail mail=new InBoxMail();
			mail.setContent("您有一份文件:"+file.getSubject()+" 需要会签");
			mail.setReceiveIdList(String.valueOf(p.getId()));
			mail.setReceiveNameList(p.getName());
			mail.setReceiver(p);
			mail.setSendDate(new Date());
			mail.setSubject("[会签]"+file.getSubject());
			mailDao.saveMail(mail);
		}
		
		fileDao.editFile(file);
	}

}
