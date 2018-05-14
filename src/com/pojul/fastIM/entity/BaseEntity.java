package com.pojul.fastIM.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.pojul.objectsocket.message.BaseMessage;
import com.pojul.objectsocket.utils.LogUtil;

public class BaseEntity extends BaseMessage{
	
	public void setBySql(ResultSet rs) {
	}
	
	public int getInt(ResultSet rs, String columnName) {
		try {
			int columnIndex = rs.findColumn(columnName);
			if (columnIndex > 0) {
				return rs.getInt(columnIndex);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogUtil.i(this.getClass().getName(), e.toString());
		}
		return 0;
	}
	
	public String getString(ResultSet rs, String columnName) {
		try {
			int columnIndex = rs.findColumn(columnName);
			if (columnIndex > 0) {
				return rs.getString(columnIndex);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogUtil.i(this.getClass().getName(), e.toString());
		}
		return null;
	}
	
	
}
