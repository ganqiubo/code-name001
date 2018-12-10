package com.pojul.fastIM.entity;

import java.sql.ResultSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pojul.fastIM.message.chat.CommunityMessage;
import com.pojul.fastIM.message.chat.TagCommuMessage;

public class TagMessInfo extends BaseEntity {

	private String userName;
	private int isEffective;
	private String messageContent;
	private TagCommuMessage tagMess;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIsEffective() {
		return isEffective;
	}

	public void setIsEffective(int isEffective) {
		this.isEffective = isEffective;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public TagCommuMessage getTagMess() {
		return tagMess;
	}

	public void setTagMess(TagCommuMessage tagMess) {
		this.tagMess = tagMess;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		userName = getString(rs, "user_name");
		isEffective = getInt(rs, "is_effective");
		messageContent = getString(rs, "message_content");
		Gson gs = new GsonBuilder().disableHtmlEscaping().create();
		try {
			tagMess = (TagCommuMessage) gs.fromJson(messageContent, TagCommuMessage.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

}
