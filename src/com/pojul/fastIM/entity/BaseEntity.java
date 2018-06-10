package com.pojul.fastIM.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.pojul.objectsocket.message.StringFile;
import com.pojul.objectsocket.utils.LogUtil;
import com.pojul.objectsocket.utils.StorageType;

public class BaseEntity{
	
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
			LogUtil.d(this.getClass().getName(), e.toString());
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
			LogUtil.d(this.getClass().getName(), e.toString());
		}
		return null;
	}
	
	public StringFile getStringFile(ResultSet rs, String columnName) {
		try {
			int columnIndex = rs.findColumn(columnName);
			if (columnIndex > 0 && rs.getString(columnIndex) != null) {
				StringFile mStringFile = new StringFile(StorageType.SERVER);
				mStringFile.setFilePath(rs.getString(columnIndex));
				return mStringFile;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogUtil.d(this.getClass().getName(), e.toString());
		}
		return null;
	}
	
	
}
