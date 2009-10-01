package com.single.desktopoa.client.exception;



import java.io.Serializable;

import com.google.gwt.user.client.rpc.SerializableException;




public class BaseException extends SerializableException implements Serializable{

	public BaseException(){
		
	}
	
	public BaseException(String message){
		super(message);
	}
}
