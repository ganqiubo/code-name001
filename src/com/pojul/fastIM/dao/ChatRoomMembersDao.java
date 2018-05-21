package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.ChatRoomMembers;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.utils.DaoUtil;

public class ChatRoomMembersDao {

	public ChatRoomMembers getChatRoomMembers(int chatRoomId) {
		ChatRoomMembers mChatRoomMembers = new ChatRoomMembers(chatRoomId);
		String sql = "select users.user_name as user_name from users INNER JOIN chat_room_members on "
				+ "(users.id = chat_room_members.user_id) where chat_room_members.chat_room_id = '"
				+ chatRoomId + "'";
		List<User> users = DaoUtil.executeQuery(sql, User.class);
		mChatRoomMembers.setUsers(users);
		return mChatRoomMembers;
	}
	
}
