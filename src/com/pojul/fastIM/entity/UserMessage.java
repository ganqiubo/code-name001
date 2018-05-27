package com.pojul.fastIM.entity;

import java.sql.ResultSet;

import com.sun.swing.internal.plaf.metal.resources.metal_zh_TW;

public class UserMessage extends BaseEntity{

	private String toUser;
	private String fromUser;
	private String chatRoomUid;
	private int isRead;
	private int isSend;
	private String sendTime;
	
	private Message message;

	public UserMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserMessage(String toUser, String fromUser, String chatRoomUid, int isRead, int isSend,
			String sendTime, Message message) {
		super();
		this.toUser = toUser;
		this.fromUser = fromUser;
		this.chatRoomUid = chatRoomUid;
		this.isRead = isRead;
		this.isSend = isSend;
		this.sendTime = sendTime;
		this.message = message;
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

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		toUser = getString(rs, "to_user");
		fromUser = getString(rs, "from_user");
		chatRoomUid = getString(rs, "chat_room_uid");
		isRead = getInt(rs, "is_read");
		isSend = getInt(rs, "is_send");
		sendTime = getString(rs, "send_time");
		
		Message mMessage = new Message();
		mMessage.setBySql(rs);
		this.message = mMessage;
		
	}
	
	
}
