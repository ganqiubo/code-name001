package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.MoreSubReply;
import com.pojul.fastIM.utils.DaoUtil;

public class MoreSubReplyDao {

	public List<MoreSubReply> getMoreSubReply(String replyMessUid, long lastMilli, int num){
		String sql = "select a.text, a.time_milli, b.photo, b.user_name, b.nick_name, b.certificate, b.sex from "
				+ "(select * from sub_reply where " 
				+ "reply_message_uid = '" + replyMessUid + "' and time_milli < " + lastMilli + " order by id desc limit " + num + ") "
				+ "as a left join users as b on a.user_name = b.user_name";
		return DaoUtil.executeQuery(sql, MoreSubReply.class);
	}
	
}
