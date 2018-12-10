package com.pojul.fastIM.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pojul.fastIM.entity.Message;
import com.pojul.fastIM.message.chat.ChatMessage;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.objectsocket.message.BaseMessage;
public class MessageDao {

	public void insertMesage(ChatMessage message) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String messonJson = gson.toJson(((BaseMessage)message));
		String sql = "insert into message(message_uid, message_class, message_content) values (" +
				"'" + message.getMessageUid() + "', " + 
				"'" + message.getClass().getName() + "', " + 
				"'" + messonJson + "'" + 
				")";
		DaoUtil.executeUpdate(sql, false);
	}
	
}
