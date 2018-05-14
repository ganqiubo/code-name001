package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class LoginStatus extends BaseEntity{

	private int id;
	private int userId;
	private int loginStatus;
	private String chatId; 
	private String loginDevice;
	private String deviceType;
	public LoginStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LoginStatus(int id, int userId, int loginStatus, String chatId, String loginDevice, String deviceType) {
		super();
		this.id = id;
		this.userId = userId;
		this.loginStatus = loginStatus;
		this.chatId = chatId;
		this.loginDevice = loginDevice;
		this.deviceType = deviceType;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}
	public String getLoginDevice() {
		return loginDevice;
	}
	public void setLoginDevice(String loginDevice) {
		this.loginDevice = loginDevice;
	}
	
	@Override
	public String toString() {
		return "LoginStatus [id=" + id + ", userId=" + userId + ", loginStatus=" + loginStatus + ", loginDevice="
				+ loginDevice + "]";
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		id = getInt(rs, "id");
		userId = getInt(rs, "user_id");
		loginStatus = getInt(rs, "login_status");
		chatId = getString(rs, "chat_id");
		loginDevice = getString(rs, "login_device");
		deviceType = getString(rs, "device_type");
	}

}
