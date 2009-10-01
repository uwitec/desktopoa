package com.single.desktopoa.client.exception;


import java.io.Serializable;



public class MySecurityException extends BaseException implements Serializable{

	public MySecurityException(){
		
	}
	public MySecurityException(String message){
		super(message);
	}
}
