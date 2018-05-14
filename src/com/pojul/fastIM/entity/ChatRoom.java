package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class ChatRoom extends BaseEntity{

	private int id;
	private String chatRoomUid;
	private String chatRoomName;
	private String chatRoomType;
	private int maxMember;
	private String createDate;
	
	private ChatRoom() {
		super();
		// TODO Auto-generated constructor stub
	}

	private ChatRoom(int id, String chatRoomUid, String chatRoomName, String chatRoomType, int maxMember,
			String createDate) {
		super();
		this.id = id;
		this.chatRoomUid = chatRoomUid;
		this.chatRoomName = chatRoomName;
		this.chatRoomType = chatRoomType;
		this.maxMember = maxMember;
		this.createDate = createDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChatRoomUid() {
		return chatRoomUid;
	}

	public void setChatRoomUid(String chatRoomUid) {
		this.chatRoomUid = chatRoomUid;
	}

	public String getChatRoomName() {
		return chatRoomName;
	}

	public void setChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	public String getChatRoomType() {
		return chatRoomType;
	}

	public void setChatRoomType(String chatRoomType) {
		this.chatRoomType = chatRoomType;
	}

	public int getMaxMember() {
		return maxMember;
	}

	public void setMaxMember(int maxMember) {
		this.maxMember = maxMember;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "ChatRoom [id=" + id + ", chatRoomUid=" + chatRoomUid + ", chatRoomName=" + chatRoomName
				+ ", chatRoomType=" + chatRoomType + ", maxMember=" + maxMember + ", createDate=" + createDate
				+ ", from=" + from + ", to=" + to + ", sendTime=" + sendTime + ", receiveTime=" + receiveTime
				+ ", mMessageHeader=" + mMessageHeader + ", chatType=" + chatType + "]";
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		id = getInt(rs, "id");
		chatRoomUid = getString(rs, "chat_room_uid");
		chatRoomName = getString(rs, "chat_room_name");
		chatRoomType = getString(rs, "chat_room_type");
		maxMember = getInt(rs, "max_member");
		createDate = getString(rs, "create_date");
	}
	
}
