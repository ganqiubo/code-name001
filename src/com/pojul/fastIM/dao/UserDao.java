package com.pojul.fastIM.dao;
import java.util.List;

import com.pojul.fastIM.entity.LoginStatus;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.message.request.LoginMessage;
import com.pojul.fastIM.utils.DaoUtil;

public class UserDao {
	
	public String loginByUserName(LoginMessage mLoginMessage) {
		String sql = "select * from users where user_name = '" + mLoginMessage.getUserName() + 
				"' and passwd = '" + mLoginMessage.getPassWd() + "'";
		List<User> users = DaoUtil.executeQuery(sql, User.class);
		if(users == null) {
			return "系统错误";
		}
		if(users.size() > 0) {
			User user = users.get(0);
			if(isLogin(user.getId(), mLoginMessage)) {
        		return "该用户已登陆";
        	}
			return "success";
		}
		return "用户名或密码错误";
	}
	
	public boolean isLogin(int userId, LoginMessage mLoginMessage) {
		String sql = "select * from login_status where user_id = '" + userId + "'" +
							"and device_type = '" + mLoginMessage.getDeviceType() + "'";
		List<LoginStatus> loginStatus = DaoUtil.executeQuery(sql, LoginStatus.class);
		if(loginStatus == null) {
			return false;
		}
		if(loginStatus.size() > 0) {
			LoginStatus mLoginStatus = loginStatus.get(0);
			if(mLoginStatus.getLoginStatus() == 0 && setLoginStatus(userId, mLoginMessage.getDeviceType(), 1) > 0) {
				return false;
			}
			return true;
		}
		if(insertLoginStatus(new LoginStatus(-1, userId, 1, mLoginMessage.getUserName(), "", mLoginMessage.getDeviceType())) > 0) {
        	return false;
        }
		return true;
	}

	public int setLoginStatus(int userId, String deviceType, int loginStatus) {
		String sql = "update login_status set login_status = '" + loginStatus + 
				"' where user_id = '" + userId + "' and device_type = '" + deviceType + "'";
		return DaoUtil.executeUpdate(sql);
	}
	
	public int insertLoginStatus(LoginStatus loginStatus) {
		String sql = "insert into login_status(user_id, login_status, chat_id, login_device, device_type) values (" + 
				"'" + loginStatus.getUserId() + "', " + 
				"'" + loginStatus.getLoginStatus() + "', " + 
				"'" + loginStatus.getChatId() + "', " + 
				"'" + loginStatus.getLoginDevice() + "', " +
				"'" + loginStatus.getDeviceType() + 
				"')"; 
		return DaoUtil.executeUpdate(sql);
	}
	
	public int loginOut(String chatId, String deviceType) {
		String sql = "update login_status set login_status = '0' where chat_id = '" + chatId +
				"' and device_type = '" + deviceType + "'";
		return DaoUtil.executeUpdate(sql);
	}
	
	public User getUserInfo(String userName) {
		String sql = "select * from users where user_name = '" + userName  + "'";
		List<User> users = DaoUtil.executeQuery(sql, User.class);
		if(users == null || users.size() <= 0) {
			return null;
		}
		return users.get(0);
	}
	
}
