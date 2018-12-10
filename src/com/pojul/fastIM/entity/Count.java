package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class Count extends BaseEntity{

	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		num = getInt(rs, "num");
	}
	
	
	
}
