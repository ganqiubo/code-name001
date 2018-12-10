package com.pojul.fastIM.dao;

import com.pojul.fastIM.utils.DaoUtil;

public class DownLoadsDao {

	public long downloadsAdd() {
		String sql = "update downloads set apk_downloads = apk_downloads + 1 where id = 1";
		return DaoUtil.executeUpdate(sql, false);
	}
	
}
