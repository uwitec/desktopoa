package com.single.desktopoa.convert;

import com.single.desktopoa.client.model.ClientUser;
import com.single.desktopoa.common.user.User;

public class UserConvert implements Convert<User, ClientUser> {

	@Override
	public ClientUser convertToClient(User x) throws Exception {
		ClientUser cUser=new ClientUser();
		cUser.setId(x.getId());
		cUser.setUsername(x.getUsername());
		cUser.setPassword(x.getPassword());
		return cUser;
	}

	@Override
	public User convertToModel(ClientUser y) throws Exception {
		User user=new User();
		user.setId(y.getId());
		user.setUsername(y.getUsername());
		user.setPassword(y.getPassword());
		return user;
	}

}
