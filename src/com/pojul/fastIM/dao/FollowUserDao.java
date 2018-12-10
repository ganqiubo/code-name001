package com.pojul.fastIM.dao;

import com.pojul.fastIM.utils.DaoUtil;

public class FollowUserDao {

	public long followUser(String from, String followedUserName, int type) {
		String sql = "";
		if(type == 0) {
			sql = "insert into follow_person(follow_user_name, followed_user_name) values("
					+ "'" + from + "', " 
					+ "'" + followedUserName + "'" + 
					")";
		}else {
			sql = "delete from follow_person where follow_user_name = '" + from + "' "
					+ "and followed_user_name = '" + followedUserName + "'";
		}
		return DaoUtil.executeUpdate(sql, false);
	}
	
}
