package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class LoginToken extends BaseEntity{

	private String loginToken;

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		loginToken = getString(rs, "login_token");
	}
	
}
