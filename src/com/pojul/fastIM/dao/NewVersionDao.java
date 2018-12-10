package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.NewVersion;
import com.pojul.fastIM.utils.DaoUtil;

public class NewVersionDao {
	
	public NewVersion getNewVersion() {
		String sql = "select * from new_version where id = 1";
		List<NewVersion> newVersions = DaoUtil.executeQuery(sql, NewVersion.class);
		if(newVersions == null || newVersions.size() <= 0 || 
				newVersions.get(0) == null || newVersions.get(0).getVersionCode().isEmpty()) {
			return null;
		}
		return newVersions.get(0);
	}
	

}
