package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.message.other.NotifyReplyMess;
import com.pojul.fastIM.utils.DaoUtil;

public class NotifyReplyMessDao {

	public long insertNotify(NotifyReplyMess notifyReplyMess) {
		String sql = "insert into notify_reply(mess_from, mess_to, send_time, message_uid, is_send, reply_tag_mess_uid, reply_text, "
				+ "tag_mess_title, reply_nick_name, reply_type, photo) values("
				+ "'" + notifyReplyMess.getFrom() + "', " 
				+ "'" + notifyReplyMess.getTo() + "', " 
				+ "'" + notifyReplyMess.getSendTime() + "', " 
				+ "'" + notifyReplyMess.getMessageUid() + "', " 
				+ "'" + notifyReplyMess.getIsSend() + "', " 
				+ "'" + notifyReplyMess.getReplyTagMessUid() + "', " 
				+ "'" + notifyReplyMess.getReplyText() + "', " 
				+ "'" + notifyReplyMess.getTagMessTitle() + "', " 
				+ "'" + notifyReplyMess.getReplyNickName() + "', " 
				+ "'" + notifyReplyMess.getReplyType() + "', " 
				+ "'" + notifyReplyMess.getPhoto() + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}

	public long updateSendStatus(String messageUid, int isSend) {
		// TODO Auto-generated method stub
		String sql = "update notify_reply set is_send = " + isSend + " where message_uid = '" + messageUid + "'";
		return DaoUtil.executeUpdate(sql, false);
	}

	public List<NotifyReplyMess> getUnSendMessage(String chatId) {
		// TODO Auto-generated method stub
		String sql = "select b.* from " + 
				"(select max(id) as id, reply_tag_mess_uid from notify_reply group by reply_tag_mess_uid) as a " + 
				"inner join notify_reply as b on a.reply_tag_mess_uid = b.reply_tag_mess_uid " + 
				"and a.id = b.id and b.is_send = 0 and b.mess_to = '" + chatId + "'";
		List<NotifyReplyMess> notifyReplyMesses = DaoUtil.executeQueryChat(sql, NotifyReplyMess.class);
		return notifyReplyMesses;
	}
	
}
