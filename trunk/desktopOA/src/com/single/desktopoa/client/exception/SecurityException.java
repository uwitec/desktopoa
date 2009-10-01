package com.single.desktopoa.client.exception;

import java.io.Serializable;

public class SecurityException extends RuntimeException implements Serializable {

	public SecurityException(){
		super();
	}
	public SecurityException(String message){
		super(message);
	}
}
