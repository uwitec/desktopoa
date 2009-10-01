package com.single.desktopoa.convert;

import com.single.desktopoa.client.model.ClientDeptment;
import com.single.desktopoa.common.deptment.Deptment;

public class DeptmentConvert implements Convert<Deptment, ClientDeptment> {

	@Override
	public ClientDeptment convertToClient(Deptment x) throws Exception {
		ClientDeptment cd=new ClientDeptment();
		cd.setId(x.getId());
		cd.setName(x.getName());
		if(x.getSubDept().size()>0){
			cd.setLeaf(false);
		}else{
			cd.setLeaf(true);
		}
		return cd;
	}

	@Override
	public Deptment convertToModel(ClientDeptment y) throws Exception {
		Deptment deptment=new Deptment();
		deptment.setId(y.getId());
		deptment.setName(y.getName());
		return deptment;
	}

}
