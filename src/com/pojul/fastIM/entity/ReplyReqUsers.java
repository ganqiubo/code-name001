package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class ReplyReqUsers extends BaseEntity{

	private String userName;
	private String replyMessageUid; //tag消息uid

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReplyMessageUid() {
		return replyMessageUid;
	}

	public void setReplyMessageUid(String replyMessageUid) {
		this.replyMessageUid = replyMessageUid;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		userName = getString(rs, "user_name");
	}
	
}
