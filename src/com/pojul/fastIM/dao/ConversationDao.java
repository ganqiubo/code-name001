package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.Conversation;
import com.pojul.fastIM.utils.DaoUtil;

public class ConversationDao {
	
	public List<Conversation> getConversationInfo(int chatRoomType, String conversionFrom, String owner) {
		String sql = "";
		if(chatRoomType == 1) {
			sql = "select nick_name as conversation_name, photo as conversation_photo from users where user_name = '" + conversionFrom + "'";
		}else if(chatRoomType == 2) {
			sql = "select chat_room_name as conversation_name, photo as conversation_photo from chat_room where chat_room_uid = '" + conversionFrom + "'";
		}
		return DaoUtil.executeQuery(sql, Conversation.class);
	}
	

}
