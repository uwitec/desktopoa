package com.single.desktopoa.client.model;

import java.io.Serializable;
import java.util.Date;


public class TalkMessage implements Serializable {

	private ClientPerson from;
	
	private String msg;
	
	private Date date;

	public ClientPerson getFrom() {
		return from;
	}

	public void setFrom(ClientPerson from) {
		this.from = from;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
