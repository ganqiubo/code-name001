package com.pojul.fastIM.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pojul.fastIM.dao.conn.JDBCConnect;
import com.pojul.fastIM.entity.BaseEntity;
import com.pojul.fastIM.message.chat.ChatMessage;
import com.pojul.objectsocket.message.BaseMessage;
import com.pojul.objectsocket.utils.LogUtil;

public class DaoUtil {
	
	private static final String TAG = "DaoUtil";

	public static void closeConn(PreparedStatement pstmt, ResultSet rs, Connection conn) {
		 try {
			 if(rs != null) {
				 rs.close();
			 }
			 if(pstmt != null) {
				 pstmt.close();
			 }
			 if(conn != null) {
				 conn.close();
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.i(TAG, e.toString());
		}
	}
	
	public static <T> List<T> executeQuery(String sql, Class<?> c){
		List<T> entitys = new ArrayList<T>();
		Connection conn = JDBCConnect.getConn();
		if(conn == null) {
			return null;
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement)conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
	        while(rs.next()) {
	        	BaseEntity entity = (BaseEntity) c.newInstance();
	        	entity.setBySql(rs);
	        	entitys.add((T) entity);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.i(TAG, e.toString());
			//e.printStackTrace();
			return null;
		}finally {
			DaoUtil.closeConn(pstmt, rs, conn);
		}
		return entitys;
	}
	
	public static <T> List<T> executeQueryMulti(String[] sqls, Class<?> c){
		List<T> entitys = new ArrayList<T>();
		Connection conn = JDBCConnect.getConn();
		if(conn == null) {
			return null;
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			for (int i = 0; i < sqls.length; i++) {
				pstmt = (PreparedStatement)conn.prepareStatement(sqls[i]);
				rs = pstmt.executeQuery();
		        while(rs.next()) {
		        	BaseEntity entity = (BaseEntity) c.newInstance();
		        	entity.setBySql(rs);
		        	entitys.add((T) entity);
		        }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.i(TAG, e.toString());
			return null;
		}finally {
			DaoUtil.closeConn(pstmt, rs, conn);
		}
		return entitys;
	}
	
	public static <T> List<T> executeQueryChat(String sql, Class<?> c){
		List<T> entitys = new ArrayList<T>();
		Connection conn = JDBCConnect.getConn();
		if(conn == null) {
			return null;
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement)conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
	        while(rs.next()) {
	        	BaseMessage entity = (BaseMessage) c.newInstance();
	        	entity.setBySql(rs);
	        	entitys.add((T) entity);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.i(TAG, e.toString());
			return null;
		}finally {
			DaoUtil.closeConn(pstmt, rs, conn);
		}
		return entitys;
	}
	
	public static Long executeUpdate(String sql, boolean lastInsert) {
		Connection conn = JDBCConnect.getConn();
		if(conn == null) {
			return -1L;
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			Long exeResult = (long) pstmt.executeUpdate();
			if(lastInsert) {
				return getLastInsertId(conn);
			}else {
				return exeResult;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.i(TAG, e.toString());
			//e.printStackTrace();
			return -1L;
		}finally {
			closeConn(pstmt, rs, conn);
		}
	}

	public static Long getLastInsertId(Connection conn) throws SQLException {
		PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement("SELECT LAST_INSERT_ID()");
		ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
        	return rs.getLong("LAST_INSERT_ID()");
        }
		return -1L;
	}
	
}
