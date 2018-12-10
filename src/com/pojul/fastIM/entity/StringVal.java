package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class StringVal extends BaseEntity{

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		value = getString(rs, "value");
	}
	
}
