package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.ReplyReqUsers;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.entity.TagMessInfo;
import com.pojul.fastIM.message.chat.ReplyMessage;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.fastIM.utils.DateUtil;

public class ReplyMessageDao {

	public long insertReplyMess(ReplyMessage replyMessage) {
		String sql = "insert into reply(reply_message_uid, user_name, nick_name, reply_uid, is_space_travel"
				+ ", text, time_milli, send_time) values("
				+ "'" + replyMessage.getReplyMessageUid() + "', " 
				+ "'" + replyMessage.getFrom() + "', " 
				+ "'" + replyMessage.getNickName() + "', " 
				+ "'" + replyMessage.getMessageUid() + "', " 
				+ "'" + replyMessage.getIsSpaceTravel() + "', " 
				+ "'" + replyMessage.getText() + "', " 
				+ "'" + System.currentTimeMillis() + "', " 
				+ "'" + DateUtil.getFormatDate() + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	
	public List<ReplyMessage> getReplys(String lastMessUid, String replyTagMessUid, int num){
		String sql = "";
		if(lastMessUid == null) {
			long timeMilli = System.currentTimeMillis();
			sql = "select a.*, b.photo, b.sex, b.certificate, " + 
					"(select GROUP_CONCAT(" + 
					"concat(reply_message_uid, ',', user_name, ',', nick_name, ',', sub_reply_uid , ',',is_space_travel, ',', text, ',', time_milli, ',', reply_tag_mess_uid) " + 
					"order by id desc SEPARATOR ';') " + 
					"from sub_reply where reply_message_uid = a.reply_uid) as sub_replys "/*, " + 
					"(select count(*) from sub_reply where reply_message_uid = a.reply_uid) as sub_replys_num "*/
					+ "from (select * from reply where reply_message_uid = '" + replyTagMessUid + "' "
					+ "and time_milli < " + timeMilli + " order by id desc limit " + num
					+ ") as a inner join users as b on a.user_name = b.user_name";
		}else {
			sql = "select a.*, b.photo, b.sex, b.certificate, " + 
					"(select GROUP_CONCAT(" + 
					"concat(reply_message_uid, ',', user_name, ',', nick_name, ',', sub_reply_uid , ',',is_space_travel, ',', text, ',', time_milli, ',', reply_tag_mess_uid) " + 
					"order by id desc SEPARATOR ';') " + 
					"from sub_reply where reply_message_uid = a.reply_uid) as sub_replys "/*, " + 
					"(select count(*) from sub_reply where reply_message_uid = a.reply_uid) as sub_replys_num "*/
					+ "from (select * from reply where reply_message_uid = '" + replyTagMessUid + "' and time_milli < "
					+ "(select time_milli from reply where reply_uid = '" + lastMessUid + "') "
					+ "order by id desc limit " + num + ") as a inner join users as b on a.user_name = b.user_name";
		}
		List<ReplyMessage> replyMessages = DaoUtil.executeQueryChat(sql, ReplyMessage.class);
		return replyMessages;
	}
	
	public long replyMessReq(String userName, String replyMessUid) {
		String sql = "insert into reply_message_req(user_name, reply_message_uid) values('" + userName +"', "
				+ "'" + replyMessUid + "')";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long replyMessUnReq(String userName) {
		String sql = "delete from reply_message_req where user_name = '" + userName + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public List<ReplyReqUsers> getReqMessUsers(String tagMessUid) {
		String sql = "select * from reply_message_req where reply_message_uid = '" + tagMessUid + "'";
		return DaoUtil.executeQuery(sql, ReplyReqUsers.class);
	}
	
	public TagMessInfo getRepliedTagOwner(String tagMessUid) {
		String sql = "select user_name, is_effective, message_content from community_message where message_uid = '" + tagMessUid + "'";
		List<TagMessInfo> userNames = DaoUtil.executeQuery(sql, TagMessInfo.class);
		if(userNames == null || userNames.size() <= 0) {
			return null;
		}
		return userNames.get(0);
	}
	
}
