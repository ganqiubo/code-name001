package com.pojul.objectsocket.message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseMessage {
	
	protected String from;
	protected String to;
	protected String sendTime;
	protected String receiveTime;
	
	public BaseMessage() {
		super();
		// TODO Auto-generated constructor stub
		Date ss = new Date();  
		SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		this.sendTime = format0.format(ss.getTime());
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
}
