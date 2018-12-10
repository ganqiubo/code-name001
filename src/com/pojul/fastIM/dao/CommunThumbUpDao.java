package com.pojul.fastIM.dao;

import com.pojul.fastIM.utils.DaoUtil;

public class CommunThumbUpDao {

	public long insertThumbUp(String messageUid, String userName) {
		String sql = "insert into community_mess_thumbup values ("
				+ "'" + messageUid + "', '" + userName + "')";
		return DaoUtil.executeUpdate(sql, false);
	}
	
}
