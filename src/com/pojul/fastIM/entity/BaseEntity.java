package com.pojul.fastIM.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.pojul.objectsocket.constant.StorageType;
import com.pojul.objectsocket.message.StringFile;
import com.pojul.objectsocket.utils.LogUtil;

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
	
	public Long getLong(ResultSet rs, String columnName) {
		try {
			int columnIndex = rs.findColumn(columnName);
			if (columnIndex > 0) {
				return rs.getLong(columnIndex);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogUtil.d(this.getClass().getName(), e.toString());
		}
		return 0L;
	}
	
	public double getDouble(ResultSet rs, String columnName) {
		try {
			int columnIndex = rs.findColumn(columnName);
			if (columnIndex > 0) {
				return rs.getDouble(columnIndex);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogUtil.d(this.getClass().getName(), e.toString());
		}
		return 0d;
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
		return "";
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
	
	public boolean getBoolean(ResultSet rs, String columnName) {
		try {
			int columnIndex = rs.findColumn(columnName);
			if (columnIndex > 0) {
				return rs.getBoolean(columnIndex);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogUtil.d(this.getClass().getName(), e.toString());
		}
		return false;
	}
	
}
