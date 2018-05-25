package com.pojul.fastIM.dao;

import java.util.List;

import com.google.gson.Gson;
import com.pojul.fastIM.entity.Message;
import com.pojul.fastIM.message.chat.ChatMessage;
import com.pojul.fastIM.utils.DaoUtil;
public class MessageDao {

	public void insertMesage(ChatMessage message, boolean isSend, String toUser, String chatRoomUid) {
		int send = isSend ? 1:0;
		String sql = "insert into message(message_uid, to_user, from_user, chat_room_uid, "
				+ "is_send, message_class, message_content, send_time) values (" +
				"'" + message.getChatUid() + "', " + 
				"'" + toUser + "', " + 
				"'" + message.getFrom() + "', " + 
				"'" + chatRoomUid + "', " + 
				"'" + send + "', " + 
				"'" + message.getClass().getName() + "', " + 
				"'" + new Gson().toJson(message) + "', " + 
				"'" + message.getSendTime() + "'" +
				")";
		DaoUtil.executeUpdate(sql);
	}
	
	public void updateSendStatus(String messageUid, String to, boolean isSend) {
		int send = isSend ? 1:0;
		String sql = "update message set is_send = '" + send +"' where message_uid = '" + messageUid +"' and to_user = '" + to + "'";
		DaoUtil.executeUpdate(sql);
	}
	
	public List<Message> getUnSendMessage(String toUser) {
		String sql = "select * from message where to_user = '" + toUser + "' and is_send = '" + 0 +"'";
		List<Message> messages = DaoUtil.executeQuery(sql, Message.class);
		return messages;
	}
	
}
