package com.pojul.fastIM.dao;

import java.util.List;

import javax.print.attribute.standard.RequestingUserName;

import com.pojul.fastIM.message.other.NotifyManagerNotify;
import com.pojul.fastIM.utils.DaoUtil;

public class ManageNotifyUsersDao {

	public long insertNotify(NotifyManagerNotify notify) {
		String sql = "insert into manage_notify_users(manager, manager_nickname, manager_photo, commu_room_uid, mess_uid, "
				+ "message_title, time_mill, user_name) values("
				+ "'" + notify.getManager() + "', "
				+ "'" + notify.getManagerNickname() + "', "
				+ "'" + notify.getManagerPhoto() + "', "
				+ "'" + notify.getCommuRoomUid() + "', "
				+ "'" + notify.getMessUid() + "', "
				+ "'" + notify.getMessageTitle() + "', "
				+ "'" + notify.getTimeMill() + "', "
				+ "'" + notify.getUserName() + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public NotifyManagerNotify getUnSendManagerNotify(String userName) {
		String sql = "select * from manage_notify_users where user_name = '" + 
				userName + "' order by id desc limit 0,1";
		List<NotifyManagerNotify> notifies = DaoUtil.executeQueryChat(sql, NotifyManagerNotify.class);
		if(notifies == null || notifies.size() <= 0) {
			return null;
		}
		return notifies.get(0);
	}
	
	public long clearNotify(String userName) {
		String sql = "delete from manage_notify_users where user_name = '" + userName + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
}
