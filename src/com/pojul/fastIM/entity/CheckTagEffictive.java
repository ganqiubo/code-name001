package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class CheckTagEffictive extends BaseEntity{

	private int effictive;

	public int getEffictive() {
		return effictive;
	}

	public void setEffictive(int effictive) {
		this.effictive = effictive;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		effictive = getInt(rs, "effictive");
	}
	
}
