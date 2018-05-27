package com.pojul.fastIM.dao;

import com.google.gson.Gson;
import com.pojul.fastIM.message.chat.ChatMessage;
import com.pojul.fastIM.utils.DaoUtil;
public class MessageDao {

	public void insertMesage(ChatMessage message) {
		String sql = "insert into message(message_uid, message_class, message_content) values (" +
				"'" + message.getChatUid() + "', " + 
				"'" + message.getClass().getName() + "', " + 
				"'" + new Gson().toJson(message) + "'" + 
				")";
		DaoUtil.executeUpdate(sql);
	}
	
}
