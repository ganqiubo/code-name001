package com.pojul.fastIM.dao.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.pojul.objectsocket.utils.LogUtil;

public class JDBCConnect {

	private static final String TAG = "JDBCConnect";
	
	public static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/fastim?characterEncoding=utf8&useSSL=true";
	    String username = "root";
	    String password = "7yg257zh";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        LogUtil.i(TAG, e.toString());
	    } catch (SQLException e) {
	    	LogUtil.i(TAG, e.toString());
	    }
	    return conn;
	}
	
}
