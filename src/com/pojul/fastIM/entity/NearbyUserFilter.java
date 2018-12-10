package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class NearbyUserFilter extends BaseEntity{

	private int enable;
	private UserFilter userFilter;
	private String filter;
	
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public UserFilter getUserFilter() {
		return userFilter;
	}
	public void setUserFilter(UserFilter userFilter) {
		this.userFilter = userFilter;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		enable = getInt(rs, "enable");
		filter = getString(rs, "filter");
	}
	
}
