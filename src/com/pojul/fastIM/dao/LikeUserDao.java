package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.LikeFollowInfo;
import com.pojul.fastIM.utils.DaoUtil;

public class LikeUserDao {

	public long likeUser(String from, String likedUserName, int type) {
		String sql = "";
		if(type == 0) {
			sql = "insert into like_person(own_user_name, liked_user_name) values("
					+ "'" + from + "', " 
					+ "'" + likedUserName + "'" + 
					")";
		}else {
			sql = "delete from like_person where own_user_name = '" + from + "' "
					+ "and liked_user_name = '" + likedUserName + "'";
		}
		return DaoUtil.executeUpdate(sql, false);
	}
	
	
	public LikeFollowInfo likeFollow(String from, String beUserName) {
		String sql = "select (select count(*) from follow_person where follow_user_name = '" + from + "' "
				+ "and followed_user_name = '" + beUserName + "') as follow_count, count(*) as like_count "
				+ "from like_person where own_user_name = '" + from + "' and liked_user_name = '"+ beUserName + "'";
		List<LikeFollowInfo> likeFollowInfos = DaoUtil.executeQuery(sql, LikeFollowInfo.class);
		if(likeFollowInfos == null || likeFollowInfos.size() <= -1) {
			return null;
		}
		return likeFollowInfos.get(0);
	} 
}
