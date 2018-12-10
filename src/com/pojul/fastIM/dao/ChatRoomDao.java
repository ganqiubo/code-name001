package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.ChatRoom;
import com.pojul.fastIM.utils.ServerConstant;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.fastIM.utils.DateUtil;
import com.pojul.objectsocket.utils.LogUtil;

public class ChatRoomDao {

	public boolean chatRoomExits(String chatRoomUid) {
		String sql = "select * from chat_room where chat_room_uid = '" + chatRoomUid + "'";
		List<ChatRoom> chatRooms = DaoUtil.executeQuery(sql, ChatRoom.class);
		if(chatRooms == null || chatRooms.size() <= 0) {
			return false;
		}
		return true;
	}
	
	public int getChatRoomId(String chatRoomUid) {
		String sql = "select * from chat_room where chat_room_uid = '" + chatRoomUid + "'";
		List<ChatRoom> chatRooms = DaoUtil.executeQuery(sql, ChatRoom.class);
		if(chatRooms != null && chatRooms.size() > 0) {
			return chatRooms.get(0).getId();
		}
		return -1;
	}
	
	public void CreateSingleChatRoom(String chatRoomUid, String chatRoomName) {
		int chatRoomType = ServerConstant.CHAT_TYPE_SINGLE;
		String createDate = DateUtil.getFormatDate();
		String sql = "insert into chat_room(chat_room_uid, chat_room_name, chat_room_type, create_date) values ("
				+ "'" + chatRoomUid + "', " 
				+ "'" + chatRoomName + "', "
				+ "'" + chatRoomType + "', "
				+ "'" + createDate + "'" + 
				")";
		if(DaoUtil.executeUpdate(sql, false) < 0) {
			LogUtil.i(getClass().getName(), "创建聊天室失败");
		}
	}
}
