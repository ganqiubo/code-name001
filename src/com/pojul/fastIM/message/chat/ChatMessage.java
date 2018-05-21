package com.pojul.fastIM.message.chat;

import java.util.Random;

import com.pojul.objectsocket.message.BaseMessage;

public class ChatMessage extends BaseMessage{
	
	protected int chatType;
	protected String chatUid;

	@Override
	public void setFrom(String from) {
		// TODO Auto-generated method stub
		super.setFrom(from);
		this.chatUid = System.currentTimeMillis() + "_" + from + "_" + (int)(Math.random()*20);
	}

	public String getChatUid() {
		return chatUid;
	}

	public void setChatUid(String chatUid) {
		this.chatUid = chatUid;
	}

	public int getChatType() {
		return chatType;
	}

	public void setChatType(int chatType) {
		this.chatType = chatType;
	}
	
}
