package com.pojul.fastIM.dao;

import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.fastIM.utils.DateUtil;

public class SuggestDao {

	public long insertSuggest(String from, String content) {
		String sql = "insert into suggest(from_user_name, content, suggest_time) values ("
				+ "'" + from + "', "
				+ "'" + content + "', "
				+ "'" + DateUtil.getFormatDate() + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}
	
}
