package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.CommunityMessEntity;
import com.pojul.fastIM.entity.Count;
import com.pojul.fastIM.entity.LocUser;
import com.pojul.fastIM.utils.DaoUtil;
import com.sun.org.apache.xml.internal.utils.LocaleUtility;

public class RecommendDao{

	public long insertRecommendMess(String uid, List<String> recommend, String from) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into recommend_message(message_uid, from_user_name, to_user_name) values ");
		for (int i = 0; i < recommend.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			String value = "('" + uid + "', '" + from + "', " + "'" + recommend.get(i) + "'" + ")";
			sb.append(value);
		}
		String sql = sb.toString();
		return DaoUtil.executeUpdate(sql, false);
	}

	public long updateMessNotified(String toUserName) {
		// TODO Auto-generated method stub
		String sql = "update recommend_message set notificated = 1 where to_user_name = '" + toUserName + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public boolean hasUnNotifyMess(String userName){
		String sql = "select count(*) as num from recommend_message "
				+ "where to_user_name = '" + userName + "' and notificated = 0";
		List<Count> counts =  DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0) {
			return false;
		}
		if(counts.get(0).getNum() > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<CommunityMessEntity> getRecomdMess(String from){
		String sql = "select end_tab.*, " + 
				"(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid) as thumb_ups, " + 
				"(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid and user_name = '" + from + "') as has_thumb_up, " + 
				"(select count(*) from report where report_message_uid = end_tab.message_uid and reporter = '" + from + "') as has_report, " + 
				"(select count(*) from reply where reply_message_uid = end_tab.message_uid) as reply_num, " + 
				"(select concat(text, ',', user_name, ',', nick_name) from reply where reply_message_uid = end_tab.message_uid order by id desc limit 1) as last_reply " + 
				"from (select c.*, d.certificate, d.age, d.photo, d.sex, d.nick_name \r\n" + 
				"from (select b.* from (select * from recommend_message where to_user_name = '" + from + "' and has_read = 0) as a " + 
				"inner join community_message as b on a.message_uid = b.message_uid) as c inner join users as d on c.user_name = d.user_name) as end_tab";
		List<CommunityMessEntity> communityMessEntities = DaoUtil.executeQuery(sql, CommunityMessEntity.class);
		String sql1 = "update recommend_message set has_read = 1 where to_user_name = '" + from + "'";
		DaoUtil.executeUpdate(sql1, false);
		return communityMessEntities;
	}
	
	public long insertRecomdUsers(String from, List<String> toUsers) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into recomd_user(from_user_name, to_user_name) values ");
		for (int i = 0; i < toUsers.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			String value = "('" + from + "', " + "'" + toUsers.get(i) + "'" + ")";
			sb.append(value);
		}
		String sql = sb.toString();
		return DaoUtil.executeUpdate(sql, false);
	}

	public long updateUserNotified(String toUserName) {
		// TODO Auto-generated method stub
		String sql = "update recomd_user set notificated = 1 where to_user_name = '" + toUserName + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public boolean hasUnNotifyUser(String userName){
		String sql = "select count(*) as num from recomd_user "
				+ "where to_user_name = '" + userName + "' and notificated = 0";
		List<Count> counts =  DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0) {
			return false;
		}
		if(counts.get(0).getNum() > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<LocUser> getRecomdUser(String from){
		String sql = "select c.*, d.user_name, d.id, d.nick_name, d.photo, d.age, d.sex, d.hobby, d.occupation from " + 
				"(select b.longitude, b.latitude, b.filter, b.update_time, a.from_user_name from " + 
				"(select distinct(from_user_name) from recomd_user where to_user_name = '" + from + "' and has_read = 0) as a " + 
				"inner join nearby_people as b on a.from_user_name = b.user_name) as c " + 
				"inner join users as d on c.from_user_name = d.user_name";
		List<LocUser> locUsers =  DaoUtil.executeQuery(sql, LocUser.class);
		String sql1 = "update recomd_user set has_read = 1 where to_user_name = '" + from + "'";
		DaoUtil.executeUpdate(sql1, false);
		return locUsers;
	}
	
	
	
	
	
}
