package com.single.desktopoa.convert;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.single.desktopoa.client.model.ClientFile;
import com.single.desktopoa.client.model.ClientSignRecord;
import com.single.desktopoa.module.file.model.File;
import com.single.desktopoa.module.file.model.SignRecord;

public class FileConvert implements Convert<File,ClientFile> {

	private SignRecordConvert signRecordConvert=new SignRecordConvert();
	@Override
	public ClientFile convertToClient(File x) throws Exception{
		ClientFile clientFile=new ClientFile();
		
		clientFile.setContent(x.getContent());
		clientFile.setCreateDate(x.getCreateDate());
		clientFile.setCreatorId(x.getCreator().getId());
		clientFile.setCreatorName(x.getCreator().getName());
		clientFile.setEditDegree(x.getEditDegree());
		clientFile.setEditList(x.getEditList());
		clientFile.setId(x.getId());
		clientFile.setLastModifyDate(x.getLastModifyDate());
		clientFile.setLastModifyerName(x.getLastModifyer().getName());
		clientFile.setOver(x.getOver());
		clientFile.setReadDegree(x.getReadDegree());
		clientFile.setReadList(x.getReadList());
		clientFile.setSubject(x.getSubject());
		clientFile.setStatus(x.getStatus());
		
		if(x.getCounterSign()!=null){
			clientFile.setCounterSignReason(x.getCounterSign().getReason());
			List<ClientSignRecord> resultSign=new ArrayList<ClientSignRecord>();
			List<ClientSignRecord> resultUnsign=new ArrayList<ClientSignRecord>();
			Set<SignRecord> records=x.getCounterSign().getSigners();
			for(SignRecord record:records){
				if(record.getSigned()){
					resultSign.add(signRecordConvert.convertToClient(record));
				}else{
					resultUnsign.add(signRecordConvert.convertToClient(record));
				}
			}
			clientFile.setSignedList(resultSign);
			clientFile.setUnsignedList(resultUnsign);
		}else{
			clientFile.setSignedList(new ArrayList<ClientSignRecord>());
			clientFile.setUnsignedList(new ArrayList<ClientSignRecord>());
		}
		
		return clientFile;
	}

	@Override
	public File convertToModel(ClientFile y) throws Exception {
		File file=new File();
		file.setContent(y.getContent());
		file.setEditDegree(y.getEditDegree());
		file.setEditList(y.getEditList());
		file.setReadDegree(y.getReadDegree());
		file.setReadList(y.getReadList());
		file.setSubject(y.getSubject());
		
		
		return file;
	}
	class SignRecordConvert implements Convert<SignRecord, ClientSignRecord>{
		@Override
		public ClientSignRecord convertToClient(SignRecord x) throws Exception {
			ClientSignRecord record=new ClientSignRecord();
			record.setId(x.getId());
			record.setSignDate(x.getSignDate());
			record.setSigned(x.getSigned());
			record.setSignerId(x.getSigner().getId());
			record.setSignerName(x.getSigner().getName());
			record.setSuggest(x.getSuggest());
			return record;
		}

		@Override
		public SignRecord convertToModel(ClientSignRecord y) throws Exception {
			SignRecord record=new SignRecord();
			record.setId(y.getId());
			record.setSignDate(y.getSignDate());
			record.setSigned(y.getSigned());
			record.setSuggest(y.getSuggest());
			return record;
		}
	}
}
