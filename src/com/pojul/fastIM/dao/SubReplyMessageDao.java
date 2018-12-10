package com.pojul.fastIM.dao;

import com.pojul.fastIM.message.chat.SubReplyMessage;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.fastIM.utils.DateUtil;

public class SubReplyMessageDao {

	public long insertSubReplyMess(SubReplyMessage message) {
		String sql = "insert into sub_reply(reply_message_uid, reply_tag_mess_uid, user_name, nick_name, sub_reply_uid, is_space_travel, "
				+ "text, time_milli, send_time) values("
				+ "'" + message.getReplyMessageUid() + "', " 
				+ "'" + message.getReplyTagMessUid() + "', " 
				+ "'" + message.getUserName() + "', " 
				+ "'" + message.getNickName() + "', "
				+ "'" + message.getMessageUid() + "', "
				+ "'" + message.getIsSpaceTravel() + "', "
				+ "'" + message.getText() + "', "
				+ "'" + System.currentTimeMillis() + "', " 
				+ "'" + DateUtil.getFormatDate() + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}
	
}
