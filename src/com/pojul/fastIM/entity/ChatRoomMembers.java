package com.pojul.fastIM.entity;

import java.util.List;

public class ChatRoomMembers extends BaseEntity{

	private int id;
	private int chatRoomId;
	private List<User> users;
	public ChatRoomMembers() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ChatRoomMembers(int chatRoomId) {
		// TODO Auto-generated constructor stub
		super();
		this.chatRoomId = chatRoomId;
	}
	
	public ChatRoomMembers(int id, int chatRoomId, List<User> users) {
		super();
		this.id = id;
		this.chatRoomId = chatRoomId;
		this.users = users;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(int chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
