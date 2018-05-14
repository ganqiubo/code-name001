package com.pojul.objectsocket.message;

public class LoginMessage extends BaseMessage{

	private String userName;
	private String PassWd;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWd() {
		return PassWd;
	}
	public void setPassWd(String passWd) {
		PassWd = passWd;
	}
	
}
