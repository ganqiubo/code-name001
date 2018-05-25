package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class Message extends BaseEntity{

	private String messageUid;
	private String toUser;
	private String fromUser;
	private String chatRoomUid;
	private int isRead;
	private int isSend;
	private String messageClass;
	private String messageContent;
	private String sendTime;
	
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Message(String messageUid, String toUser, String fromUser, String chatRoomUid, int isRead, int isSend,
			String messageClass, String messageContent, String sendTime) {
		super();
		this.messageUid = messageUid;
		this.toUser = toUser;
		this.fromUser = fromUser;
		this.chatRoomUid = chatRoomUid;
		this.isRead = isRead;
		this.isSend = isSend;
		this.messageClass = messageClass;
		this.messageContent = messageContent;
		this.sendTime = sendTime;
	}

	public String getMessageUid() {
		return messageUid;
	}

	public void setMessageUid(String messageUid) {
		this.messageUid = messageUid;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getChatRoomUid() {
		return chatRoomUid;
	}

	public void setChatRoomUid(String chatRoomUid) {
		this.chatRoomUid = chatRoomUid;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}

	public String getMessageClass() {
		return messageClass;
	}

	public void setMessageClass(String messageClass) {
		this.messageClass = messageClass;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		messageUid = getString(rs, "message_uid");
		toUser = getString(rs, "to_user");
		fromUser = getString(rs, "from_user");
		chatRoomUid = getString(rs, "chat_room_uid");
		isRead = getInt(rs, "is_read");
		isSend = getInt(rs, "is_send");
		messageClass = getString(rs, "message_class");
		messageContent = getString(rs, "message_content");
		sendTime = getString(rs, "send_time");
	}
	
	
}
