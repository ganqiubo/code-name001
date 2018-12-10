package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class TagMessLabels extends BaseEntity{

	private String labels;

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		labels = getString(rs, "labels");
	}
	
	
	
}
