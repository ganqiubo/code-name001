package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.Message;
import com.pojul.fastIM.entity.UserMessage;
import com.pojul.fastIM.message.chat.ChatMessage;
import com.pojul.fastIM.utils.DaoUtil;

public class UserMessageDao {

	public void insertUserMessage(ChatMessage message, boolean isSend, String toUser, String chatRoomUid) {
		int send = isSend ? 1:0;
		String sql = "insert into user_message(user_message_uid, to_user, from_user, chat_room_uid, "
				+ "is_send, send_time) values (" +
				"'" + message.getMessageUid() + "', " + 
				"'" + toUser + "', " + 
				"'" + message.getFrom() + "', " + 
				"'" + chatRoomUid + "', " + 
				"'" + send + "', " + 
				"'" + message.getSendTime() + "'" +
				")";
		DaoUtil.executeUpdate(sql, false);
	}
	
	public void updateSendStatus(String messageUid, String to, boolean isSend) {
		int send = isSend ? 1 : 0;
		String sql = "update user_message set is_send = '" + send + "' where user_message_uid = '" + messageUid
				+ "' and to_user = '" + to + "'";
		DaoUtil.executeUpdate(sql, false);
	}

	public List<UserMessage> getUnSendMessage(String toUser) {
		String sql = "select * from user_message INNER JOIN message on (message.message_uid = user_message.user_message_uid "
				+ "and user_message.to_user = '" + toUser + "'"
				+ "and user_message.is_send = 0)";
		List<UserMessage> userMessages = DaoUtil.executeQuery(sql, UserMessage.class);
		return userMessages;
	}
	
	public List<Message> getHistoryChat(String lastSendTime, String chatRoomUid, int num) {
		String sql = "select a.message_class, a.message_content from message as a join "
				+ "(select * from user_message where user_message.send_time < '"+ lastSendTime + "' "
				+ "and chat_room_uid = '" + chatRoomUid + "' order by send_time desc limit " + num + ") b "
				+ "on b.user_message_uid = a.message_uid;";
		List<Message> messages = DaoUtil.executeQuery(sql, Message.class);
		 return messages;
	}
}
