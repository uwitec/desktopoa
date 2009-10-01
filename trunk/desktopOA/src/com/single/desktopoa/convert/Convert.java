package com.single.desktopoa.convert;

public interface Convert<X,Y> {

	public Y convertToClient(X x) throws Exception;
	
	public X convertToModel(Y y) throws Exception;
}
