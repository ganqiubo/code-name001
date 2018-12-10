package com.pojul.objectsocket.message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pojul.objectsocket.constant.StorageType;
import com.pojul.objectsocket.utils.LogUtil;
import com.pojul.objectsocket.utils.UidUtil;

public class BaseMessage {
	
	protected String from;
	protected String to;
	protected String sendTime;
	protected String receiveTime;
	protected String MessageUid;
	protected int isSend;
	
	public BaseMessage() {
		super();
		// TODO Auto-generated constructor stub
		Date ss = new Date();  
		SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		this.sendTime = format0.format(ss.getTime());
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
		this.MessageUid = UidUtil.getMessageUid(from);
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getMessageUid() {
		return MessageUid;
	}

	public void setMessageUid(String messageUid) {
		MessageUid = messageUid;
	}
	public int getIsSend() {
		return isSend;
	}
	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}

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
