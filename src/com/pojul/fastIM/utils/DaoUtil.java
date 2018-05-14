package com.pojul.fastIM.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pojul.fastIM.dao.conn.JDBCConnect;
import com.pojul.fastIM.entity.BaseEntity;
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
		} catch (SQLException e) {
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
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			LogUtil.i(TAG, e.toString());
			return null;
		}finally {
			DaoUtil.closeConn(pstmt, rs, conn);
		}
		return entitys;
	}
	
	public static int executeUpdate(String sql) {
		Connection conn = JDBCConnect.getConn();
		if(conn == null) {
			return -1;
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogUtil.i(TAG, e.toString());
			e.printStackTrace();
			return -1;
		}finally {
			closeConn(pstmt, rs, conn);
		}
	}
	
}
