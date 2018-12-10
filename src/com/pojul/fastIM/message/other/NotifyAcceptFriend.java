package com.pojul.fastIM.message.other;

import com.pojul.fastIM.entity.User;
import com.pojul.objectsocket.message.BaseMessage;
import java.sql.ResultSet;

public class NotifyAcceptFriend extends BaseMessage{

	private String reqUserName;
	private String reqedUserName;
	private User reqedUserInfo;
	private int type;
	private String reqText;
	
	public String getReqUserName() {
		return reqUserName;
	}
	public void setReqUserName(String reqUserName) {
		this.reqUserName = reqUserName;
	}
	public String getReqedUserName() {
		return reqedUserName;
	}
	public void setReqedUserName(String reqedUserName) {
		this.reqedUserName = reqedUserName;
	}
	public User getReqedUserInfo() {
		return reqedUserInfo;
	}
	public void setReqedUserInfo(User reqedUserInfo) {
		this.reqedUserInfo = reqedUserInfo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getReqText() {
		return reqText;
	}
	public void setReqText(String reqText) {
		this.reqText = reqText;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		reqUserName = getString(rs, "req_user_name");
		reqedUserName = getString(rs, "reqed_user_name");
		type = getInt(rs, "req_type");
		reqText = getString(rs, "req_text");
		reqedUserInfo = new User();
		reqedUserInfo.setBySql(rs);
	}
	
}
