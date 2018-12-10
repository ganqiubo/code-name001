package com.pojul.fastIM.dao;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pojul.fastIM.entity.CheckTagEffictive;
import com.pojul.fastIM.entity.CommunityMessEntity;
import com.pojul.fastIM.entity.LatLonRange;
import com.pojul.fastIM.entity.MessageFilter;
import com.pojul.fastIM.message.chat.CommunityMessage;
import com.pojul.fastIM.message.chat.TagCommuMessage;
import com.pojul.fastIM.utils.ArrayUtil;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.fastIM.utils.DateUtil;
import com.pojul.objectsocket.utils.LogUtil;

public class CommunityMessEntityDao {

	public long insertMessage(CommunityMessage communityMessage) {
		if(communityMessage.getTo() == null) {
			LogUtil.i("非法的社区uid");
			return -1;
		}
		String[] checks = communityMessage.getTo().split("_");
		if(checks.length != 2) {
			LogUtil.i("非法的社区uid");
			return -1;
		}
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String messonJson = gson.toJson(communityMessage);
		int messageType = 0;
		int isEffective = -1;
		if("com.pojul.fastIM.message.chat.TagCommuMessage".equals(communityMessage.getClass().getName())) {
			messageType = 1;
			isEffective = 0;
		}
		String sql = "insert into community_message(message_uid, user_name, community_name, community_uid,"
				+ "is_effective, message_class, message_content, time_mill, message_type, message_private, "
				+ "longitude, latitude, send_time) values(" + 
				"'" + communityMessage.getMessageUid() + "', " + 
				"'" + communityMessage.getFrom() + "', " + 
				"'" + checks[1] + "', " + 
				"'" + communityMessage.getTo() + "', " + 
				isEffective + ", " + 
				"'" + communityMessage.getClass().getName() + "', " + 
				"'" + messonJson + "', " + 
				System.currentTimeMillis() + ", " + 
				messageType + ", " + 
				communityMessage.getMessagePrivate() + ", " + 
				communityMessage.getLongitude() + ", " + 
				communityMessage.getLatitude() + ", " + 
				"'" + DateUtil.getFormatDate() + "'" + 
				")";
		long result = DaoUtil.executeUpdate(sql, false);
		if(communityMessage instanceof TagCommuMessage) {
			TagCommuMessage tagCommuMessage = (TagCommuMessage) communityMessage;
			long filterId = new UserFilterDao().insertFilter( tagCommuMessage );
			if(tagCommuMessage.getLabels() != null && tagCommuMessage.getLabels().size() > 0) {
				insertLabels(tagCommuMessage.getMessageUid(), tagCommuMessage.getFrom(), tagCommuMessage.getLabels());
			}
		}
		
		return result;
	}
	
	public long insertLabels(String messageUid, String userName, List<String> labels) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into community_message_label(message_uid, user_name, label) values ");
		for (int i = 0; i < labels.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			String value = "('" + messageUid + "', '" + userName + "', " + "'" + labels.get(i) + "'" + ")";
			sb.append(value);
		}
		String sql = sb.toString();
		return DaoUtil.executeUpdate(sql, false);
	}
	
	
	public List<CommunityMessEntity> getHistory(long lastMill, String chatRoomUid, int num, 
			MessageFilter messageFilter, String from){
		String sql = generateSql(lastMill, chatRoomUid, num, messageFilter, from);
		return DaoUtil.executeQuery(sql, CommunityMessEntity.class);
	}
	
	private String generateSql(long lastMill, String chatRoomUid, int num, MessageFilter messageFilter, String from) {
		// TODO Auto-generated method stub
		String orderTableName = "a";
		String startSql = "select end_tab.*, " + 
				"(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid) as thumb_ups, " + 
				"(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid and user_name = '" + from + "') as has_thumb_up, " + 
				"(select count(*) from report where report_message_uid = end_tab.message_uid and reporter = '" + from + "') as has_report, " +
				"(select count(*) from reply where reply_message_uid = end_tab.message_uid) as reply_num, " + 
				"(select concat(text, ',', user_name, ',', nick_name) from reply where reply_message_uid = end_tab.message_uid order by id desc limit 1) as last_reply " +
				"from(";
		String endSql = " order by " + orderTableName + ".time_mill desc limit " + num + ") as end_tab order by end_tab.time_mill asc";
		
		String middleSql = "select a.*, b.certificate, b.age, b.photo, b.sex, b.nick_name from (select * from community_message"
				+ " where community_uid = '" + chatRoomUid + "' and time_mill < " + lastMill + ")"
				+ " as a inner join users as b on a.user_name = b.user_name";
		if(messageFilter == null) {
			endSql = " order by " + orderTableName + ".time_mill desc limit " + num + ") as end_tab order by end_tab.time_mill asc";
			return startSql + middleSql + endSql;
		}
		if(messageFilter != null) {
			if(messageFilter.getCertificat() != -1) {
				middleSql = middleSql + " and b.certificate = 1";
			}
			if(messageFilter.getSex() != -1) {
				middleSql = middleSql + " and b.sex = " + messageFilter.getSex();
			}
			if(messageFilter.getEfficative() != -1) {
				middleSql = middleSql + " and a.is_effective = " + messageFilter.getEfficative();
			}
			if(messageFilter.getLabels() != null && messageFilter.getLabels().size() > 0) {
				orderTableName = "c";
				middleSql = "select c.* from (" + middleSql
					+ ") as c inner join"
					+ " (select distinct message_uid from community_message_label where label in"
					+ " (" + ArrayUtil.toCommaSplitSqlStr(messageFilter.getLabels()) +")) as d on c.message_uid = d.message_uid";
			}
		}
		endSql = " order by " + orderTableName + ".time_mill desc limit " + num + ") as end_tab order by end_tab.time_mill asc";
		return startSql + middleSql + endSql;
	}

	public long getTimeMilli(String messageUid) {
		String sql = "select time_mill from community_message where message_uid = '" + messageUid + "'";
		List<CommunityMessEntity> communityMessages = DaoUtil.executeQuery(sql, CommunityMessEntity.class);
		if(communityMessages == null || communityMessages.size() <= 0) {
			return -1;
		}
		return communityMessages.get(0).getTimeMill();
	}
	
	public long CloseTagMess(String tagMessUid, String userName) {
		String sql = "update community_message set is_effective = 1 where "
				+ "message_uid = '"+ tagMessUid + "' and user_name = '" + userName + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public int checkTagEffictive(String messageUid) {
		String sql = "select is_effective as effictive from community_message where message_uid = '" + messageUid + "'";
		List<CheckTagEffictive> entities = DaoUtil.executeQuery(sql, CheckTagEffictive.class);
		if(entities == null || entities.size() <= 0) {
			return -1;
		}
		return entities.get(0).getEffictive();
	}
	
	public CommunityMessEntity getTagMessByUid(String tagMessUid) {
		String sql = "select end_tab.*, " + 
				"(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid) as thumb_ups, " + 
				"(select count(*) from reply where reply_message_uid = end_tab.message_uid) as reply_num " + 
				"from (select a.*, b.certificate, b.age, b.photo, b.sex, b.nick_name from (select * from community_message where message_uid = '" + tagMessUid + "') " + 
				"as a inner join users as b on a.user_name = b.user_name " + 
				") as end_tab";
		List<CommunityMessEntity> communityMessEntities = DaoUtil.executeQuery(sql, CommunityMessEntity.class);
		if(communityMessEntities == null || communityMessEntities.size() <= 0) {
			return null;
		}else {
			return communityMessEntities.get(0);
		}
	}
	
	public List<CommunityMessEntity> getTopMess(String communityUid, int num){
		long timeMilli = (System.currentTimeMillis() - 24 * 60 * 60 * 1000L);
		String sql = "select a.*, " + 
				"(select count(*) from community_mess_thumbup where message_uid = a.message_uid) as thumb_ups " + 
				"from (select * " + 
				"from community_message where community_uid = '" + communityUid + "' and time_mill > " + timeMilli + 
				" and is_effective = 0) as a order by thumb_ups desc limit " + num;
		List<CommunityMessEntity> communityMessEntities = DaoUtil.executeQuery(sql, CommunityMessEntity.class);
		if(communityMessEntities == null || communityMessEntities.size() <= 0) {
			return null;
		}else {
			return communityMessEntities;
		}
	}
	
	public List<CommunityMessEntity> getTopMessMulti(List<String> communityUids, int num){
		long timeMilli = (System.currentTimeMillis() - 24 * 60 * 60 * 1000L);
		String[] sqls = new String[communityUids.size()];
		for (int i = 0; i < communityUids.size(); i++) {
			String sql = "select a.*, " + 
					"(select count(*) from community_mess_thumbup where message_uid = a.message_uid) as thumb_ups " + 
					"from (select * " + 
					"from community_message where community_uid = '" + communityUids.get(i) + "' and time_mill > " + timeMilli + 
					" and is_effective = 0) as a order by thumb_ups desc limit " + num;
			sqls[i] = sql;
		}
		List<CommunityMessEntity> communityMessEntities = DaoUtil.executeQueryMulti(sqls, CommunityMessEntity.class);
		if(communityMessEntities == null || communityMessEntities.size() <= 0) {
			return null;
		}else {
			return communityMessEntities;
		}
	}
	
	public List<CommunityMessEntity> getTagMessByUser(String from, String userName, int num, long lastMessId){
		String sql = "";
		if(lastMessId < 0) {
			sql = "select a.*, " + 
					"(select count(*) from community_mess_thumbup where message_uid = a.message_uid) as thumb_ups, " + 
					"(select count(*) from community_mess_thumbup where message_uid = a.message_uid and user_name = '" + from + "') as has_thumb_up, " + 
					"(select count(*) from report where report_message_uid = a.message_uid and reporter = '" + from + "') as has_report, " + 
					"(select count(*) from reply where reply_message_uid = a.message_uid) as reply_num, " + 
					"(select concat(text, ',', user_name, ',', nick_name) from reply where reply_message_uid = a.message_uid order by id desc limit 1) as last_reply " + 
					"from (select * from community_message where user_name = '" + userName + "' and message_type = 1 " + 
					"and id < (select (max(id) + 1) from community_message) order by id desc limit " + num + ") as a";
		}else {
			sql = "select a.*, " + 
					"(select count(*) from community_mess_thumbup where message_uid = a.message_uid) as thumb_ups, " + 
					"(select count(*) from community_mess_thumbup where message_uid = a.message_uid and user_name = '" + from + "') as has_thumb_up, " + 
					"(select count(*) from report where report_message_uid = a.message_uid and reporter = '" + from + "') as has_report, " + 
					"(select count(*) from reply where reply_message_uid = a.message_uid) as reply_num, " + 
					"(select concat(text, ',', user_name, ',', nick_name) from reply where reply_message_uid = a.message_uid order by id desc limit 1) as last_reply " + 
					"from (select * from community_message where user_name = '" + userName + "' and message_type = 1 " + 
					"and id < " + lastMessId + " order by id desc limit " + num + ") as a";
		}
		return DaoUtil.executeQuery(sql, CommunityMessEntity.class);
	}
	
	public List<CommunityMessEntity> getNearBy(LatLonRange latLonRange, int num, long startNum, MessageFilter filter, String from){
		String sql = generateNearBySql(latLonRange, num, startNum, filter, from);
		return DaoUtil.executeQuery(sql, CommunityMessEntity.class);
	}
	
	private String generateNearBySql(LatLonRange latLonRange, int num, long startNum, MessageFilter messageFilter, String from) {
		String orderTableName = "a";
		String startSql = "select end_tab.*, " + 
				"(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid) as thumb_ups, " + 
				"(select count(*) from community_mess_thumbup where message_uid = end_tab.message_uid and user_name = '" + from + "') as has_thumb_up, " + 
				"(select count(*) from report where report_message_uid = end_tab.message_uid and reporter = '" + from + "') as has_report, " +
				"(select count(*) from reply where reply_message_uid = end_tab.message_uid) as reply_num, " + 
				"(select concat(text, ',', user_name, ',', nick_name) from reply where reply_message_uid = end_tab.message_uid order by id desc limit 1) as last_reply " +
				"from(";
		String endSql = " order by " + orderTableName + ".distance, " + orderTableName + ".id desc limit " + startNum + ", " + num + ") as end_tab";
		
		String middleSql = "select a.*, b.certificate, b.age, b.photo, b.sex, b.nick_name from"
				+ " (select *, (select (abs(longitude - " + latLonRange.getRawLon() + ") + abs(latitude - " + latLonRange.getRawLat() + "))) as distance" + " from community_message"
				+ " where longitude >= " + latLonRange.getMinLon() + " and  longitude <= " + latLonRange.getMaxLon()
				+ " and latitude >= "+ latLonRange.getMinLat() + " and latitude <= " + latLonRange.getMaxLat() + " and message_type = 1 and is_effective = 0)"
				+ " as a inner join users as b on a.user_name = b.user_name";
		if(messageFilter == null) {
			endSql = " order by " + orderTableName + ".distance, " + orderTableName + ".id desc limit " + startNum + ", " + num + ") as end_tab";
			return startSql + middleSql + endSql;
		}
		if(messageFilter != null) {
			if(messageFilter.getCertificat() != -1) {
				middleSql = middleSql + " and b.certificate = 1";
			}
			if(messageFilter.getSex() != -1) {
				middleSql = middleSql + " and b.sex = " + messageFilter.getSex();
			}
			/*if(messageFilter.getEfficative() != -1) {
				middleSql = middleSql + " and a.is_effective = 0";
			}*/
			if(messageFilter.getLabels() != null && messageFilter.getLabels().size() > 0) {
				orderTableName = "c";
				middleSql = "select c.* from (" + middleSql
					+ ") as c inner join"
					+ " (select distinct message_uid from community_message_label where label in"
					+ " (" + ArrayUtil.toCommaSplitSqlStr(messageFilter.getLabels()) +")) as d on c.message_uid = d.message_uid";
			}
		}
		endSql = " order by " + orderTableName + ".distance, " + orderTableName + ".id desc limit " + startNum + ", " + num + ") as end_tab";
		return startSql + middleSql + endSql;
	}
	
	
}
