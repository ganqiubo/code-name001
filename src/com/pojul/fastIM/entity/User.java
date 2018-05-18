package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class User extends BaseEntity{

	private int id;
	private String userName;
	private String passwd;
	private String nickName;
	private String registDate;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, String userName, String passwd, String nickName, String registDate) {
		super();
		this.id = id;
		this.userName = userName;
		this.passwd = passwd;
		this.nickName = nickName;
		this.registDate = registDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passwd=" + passwd + ", nickName=" + nickName
				+ ", registDate=" + registDate + "]";
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		id = getInt(rs, "id");
		userName = getString(rs, "user_name");
		passwd = getString(rs, "passwd");
		nickName = getString(rs, "nick_name");
		registDate = getString(rs, "regist_date");
	}
	
}
