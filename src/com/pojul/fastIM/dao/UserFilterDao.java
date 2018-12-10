package com.pojul.fastIM.dao;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.List;

import com.pojul.fastIM.entity.Pic;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.entity.UserFilter;
import com.pojul.fastIM.message.chat.TagCommuMessage;
import com.pojul.fastIM.utils.DaoUtil;

public class UserFilterDao {

	/**
	 * 转发社区消息用户过滤
	 * */
	public List<User> commuRoomUserFilter(UserFilter userFilter, String commuRoomUid){
		String sql = generateSql(userFilter, commuRoomUid);
		List<User> users = DaoUtil.executeQuery(sql, User.class);
		return users;
	}

	private String generateSql(UserFilter userFilter, String commuRoomUid) {
		// TODO Auto-generated method stub
		
		String rawSql = "select * from users as a inner join community_message_req as b on a.user_name = b.user_uid "
				+ "and b.community_uid = '" + commuRoomUid + "'";
		if(userFilter == null || !userFilter.isFilterEnabled()) {
			return rawSql;
		}
		if(userFilter.isWhiteListEnabled()) {
			return "select c.* from (" + rawSql 
					+ ") as c inner join message_user_filter_white as d "
					+ "on c.user_name = d.user_name and d.filter_id = " + userFilter.getId();
		}
		if(userFilter.isBlackListEnabled()) {
			rawSql = "select c.* from (" + rawSql + ") as c left join "
					+ "message_user_filter_black as d on c.user_name = d.user_name and d.filter_id = " + userFilter.getId()
					+ " where d.user_name is null";
		}
		List<String> whereSqls = new ArrayList<>();
		if(userFilter.isAgeEnabled()) {
			String whereSql = "age >= " + userFilter.getMinAge() +" and age <= " + userFilter.getMinAge();
			whereSqls.add(whereSql);
		}
		if(userFilter.isSexEnabled()) {
			String whereSql = "sex = " + userFilter.getSex();
			whereSqls.add(whereSql);
		}
		if(userFilter.isCreditEnabled()) {
			String whereSql = "credit >= " + userFilter.getCredit();
			whereSqls.add(whereSql);
		}
		
		if(userFilter.isCertificatEnabled()) {
			String whereSql = "credit = 1";
			whereSqls.add(whereSql);
		}
		StringBuffer whereSqlStr = new StringBuffer();
		for (int i = 0; i < whereSqls.size(); i++) {
			if(i == 0) {
				whereSqlStr.append(" where ");
			}
			whereSqlStr.append(whereSqls.get(i));
			if(i != (whereSqls.size() -1)) {
				whereSqlStr.append(" and ");
			}
		}
		return "select e.* from (" + rawSql + ") as e" + whereSqlStr.toString();
	}
	
	public long insertFilter(TagCommuMessage tagCommuMessage) {
		UserFilter userFilter = tagCommuMessage.getUserFilter();
		if(userFilter == null || !userFilter.isFilterEnabled()) {
			return -1;
		}
		String sql = "insert into message_user_filter(message_uid, is_filter_enabled, is_white_list_enabled, "
				+ "is_black_list_enabled, is_age_enabled, min_age, max_age, is_sex_enabled, sex, is_certificat_enabled, "
				+ "is_credit_enabled, credit) values(" + 
				"'" + tagCommuMessage.getMessageUid() + "', " + 
				userFilter.isFilterEnabled() + ", " + 
				userFilter.isWhiteListEnabled() + ", " + 
				userFilter.isBlackListEnabled() + ", " + 
				userFilter.isAgeEnabled() + ", " + 
				userFilter.getMinAge() + ", " + 
				userFilter.getMaxAge() + ", " + 
				userFilter.isSexEnabled() + ", " + 
				userFilter.getSex() + ", " + 
				userFilter.isCertificatEnabled() + ", " + 
				userFilter.isCreditEnabled() + ", " + 
				userFilter.getCredit() + 
				")";
		long filterId = DaoUtil.executeUpdate(sql, true);
		if(filterId == -1) {
			return filterId;
		}
		tagCommuMessage.getUserFilter().setId(filterId);
		if(userFilter.isWhiteListEnabled() && userFilter.getWhiteListNames() != null
				&& userFilter.getWhiteListNames().size() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("insert into message_user_filter_white(filter_id, user_name) values ");
			sb = whiteBlackValue(sb, userFilter.getWhiteListNames(), filterId);
			String sqlWhite = sb.toString();
			DaoUtil.executeUpdate(sqlWhite, false);
		}else if(userFilter.isBlackListEnabled() && userFilter.getBlackListNames() != null
				&& userFilter.getBlackListNames().size() > 0){
			StringBuffer sb = new StringBuffer();
			sb.append("insert into message_user_filter_black(filter_id, user_name) values ");
			sb = whiteBlackValue(sb, userFilter.getBlackListNames(), filterId);
			String sqlWhite = sb.toString();
			DaoUtil.executeUpdate(sqlWhite, false);
		}
		return filterId;
	}
	
	public StringBuffer whiteBlackValue(StringBuffer sb, List<String> mlist, long filterId) {
		for (int i = 0; i < mlist.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			String value = "(" + filterId + ", " + "'" + mlist.get(i) + "'" + ")";
			sb.append(value);
		}
		return sb;
	}
}
