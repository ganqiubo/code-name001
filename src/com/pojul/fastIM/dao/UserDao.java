package com.pojul.fastIM.dao;
import java.io.File;
import java.sql.Date;
import java.util.List;

import com.pojul.fastIM.entity.Count;
import com.pojul.fastIM.entity.ExtendUser;
import com.pojul.fastIM.entity.LoginStatus;
import com.pojul.fastIM.entity.LoginToken;
import com.pojul.fastIM.entity.StringVal;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.message.other.NotifyManagerNotify;
import com.pojul.fastIM.message.request.LoginMessage;
import com.pojul.fastIM.utils.ArrayUtil;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.fastIM.utils.DateUtil;
import com.pojul.objectsocket.utils.Constant;
import com.pojul.objectsocket.utils.EncryptionUtil;
import com.pojul.objectsocket.utils.UidUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;

public class UserDao {
	
	public String loginByUserName(LoginMessage mLoginMessage, String tokenId) {
		String sql = "select * from users where user_name = '" + mLoginMessage.getUserName() + 
				"' and passwd = '" + mLoginMessage.getPassWd() + "'";
		List<User> users = DaoUtil.executeQuery(sql, User.class);
		if(users == null) {
			return "系统错误";
		}
		if(users.size() > 0) {
			User user = users.get(0);
			if(isLogin(user.getId(), mLoginMessage, tokenId)) {
        		return "该用户已登陆";
        	}
			return "success";
		}
		return "用户名或密码错误";
	}
	
	public boolean isLogin(int userId, LoginMessage mLoginMessage, String tokenId) {
		String sql = "select * from login_status where user_id = '" + userId + "'" +
							"and device_type = '" + mLoginMessage.getDeviceType() + "'";
		List<LoginStatus> loginStatus = DaoUtil.executeQuery(sql, LoginStatus.class);
		if(loginStatus == null) {
			return false;
		}
		if(loginStatus.size() > 0) {
			LoginStatus mLoginStatus = loginStatus.get(0);
			if(mLoginStatus.getLoginStatus() == 0 && setLoginStatus(userId, mLoginMessage.getDeviceType(), 1, tokenId) >= 0) {
				return false;
			}
			return true;
		}
		if(insertLoginStatus(new LoginStatus(-1, userId, 1, mLoginMessage.getUserName(), "", mLoginMessage.getDeviceType()), tokenId) >= 0) {
        	return false;
        }
		return true;
	}

	public Long setLoginStatus(int userId, String deviceType, int loginStatus, String tokenId) {
		String sql = "update login_status set login_status = '" + loginStatus + 
				"', login_token = '" + tokenId + "' where user_id = '" + userId + "' and device_type = '" + deviceType + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public Long insertLoginStatus(LoginStatus loginStatus, String tokenId) {
		String sql = "insert into login_status(user_id, login_status, chat_id, login_token, login_device, device_type) values (" + 
				"'" + loginStatus.getUserId() + "', " + 
				"'" + loginStatus.getLoginStatus() + "', " + 
				"'" + loginStatus.getChatId() + "', " + 
				"'" + tokenId + "', " + 
				"'" + loginStatus.getLoginDevice() + "', " +
				"'" + loginStatus.getDeviceType() + 
				"')"; 
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public Long loginOut(String chatId, String deviceType) {
		String sql = "update login_status set login_status = '0' where chat_id = '" + chatId +
				"' and device_type = '" + deviceType + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public User getUserInfo(String userName) {
		String sql = "select * from users where user_name = '" + userName  + "'";
		List<User> users = DaoUtil.executeQuery(sql, User.class);
		if(users == null || users.size() <= 0) {
			return null;
		}
		return users.get(0);
	}
	
	public User getUserInfo(long userId) {
		String sql = "select * from users where id = '" + userId  + "'";
		List<User> users = DaoUtil.executeQuery(sql, User.class);
		if(users == null || users.size() <= 0) {
			return null;
		}
		return users.get(0);
	}

	public String getLastToken(String userName, String deviceType) {
		// TODO Auto-generated method stub
		String sql = "select login_token from login_status where chat_id = '" + userName  + "' and device_type = '" + deviceType + "'";
		List<LoginToken> loginTokens = DaoUtil.executeQuery(sql, LoginToken.class);
		if(loginTokens == null || loginTokens.size() <= 0) {
			return "";
		}
		return loginTokens.get(0).getLoginToken();
	}

	public boolean exitLoginStatus(String userName, String deviceType) {
		// TODO Auto-generated method stub
		String sql = "select login_token from login_status where chat_id = '" + userName  + "' and device_type = '" + deviceType + "'";
		List<LoginToken> loginTokens = DaoUtil.executeQuery(sql, LoginToken.class);
		if(loginTokens == null || loginTokens.size() <= 0) {
			return false;
		}
		return true;
	}
	
	public long updateUserPhoto(String path, int type, String userName, String rawName) {
		String sql = "";
		if(type == 0) {
			sql = "update users set photo = '" + path + "' where user_name = '" + userName + "'";
		}else {
			sql = "update users set mypage_photo = '" + path + "' where user_name = '" + userName + "'";
		}
		long result = DaoUtil.executeUpdate(sql, false);
		if(result >= 0) {
			if(rawName!= null && !rawName.equals("photo_default.jpg")) {
				//File file = new File("D:\\websource\\photo\\" + rawName);
				File file = new File(Constant.USER_PHOTO_PATH + rawName);
				if(file.exists()) {
					file.delete();
				}
			}
		}
		return result;
	}
	
	public long updateAutograph(String autograph, String userName) {
		String sql = "update users set autograph = '" + autograph + "' where user_name = '" + userName + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	
	public long updateUserInfo(String userName, User user) {
		String sql = "update users set height = " + user.getHeight() + ", weight = " + user.getWeight() + ", "
				+ "hobby = '" + user.getHobby() + "', occupation = '" + user.getOccupation() + "', "
				+ "educational_level = '" + user.getEducationalLevel() + "', "
				+ "graduate_school = '" + user.getGraduateSchool() + "' where user_name = '" + userName + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	
	public List<ExtendUser> likedUses(String userName, int num, long lastId){
		String sql = "";
		if(lastId < 0) {
			sql = "select *, " + 
					"(select count(*) from friend where friend_uid = concat('" + userName + "', '_', a.liked_user_name) or friend_uid = concat(a.liked_user_name, '_', '" + userName + "')) as is_friend " + 
					"from (select id as extend_id, liked_user_name from like_person where own_user_name = '" + userName + "' " + 
					"and id < (select (max(id) + 1) from like_person) order by id desc limit " + num + ") as a " + 
					"inner join users as b on a.liked_user_name = b.user_name";
		}else {
			sql = "select *, " + 
					"(select count(*) from friend where friend_uid = concat('" + userName + "', '_', a.liked_user_name) or friend_uid = concat(a.liked_user_name, '_', '" + userName + "')) as is_friend " + 
					"from (select id as extend_id, liked_user_name from like_person where own_user_name = '" + userName + "' " + 
					"and id < " + lastId + " order by id desc limit " + num + ") as a " + 
					"inner join users as b on a.liked_user_name = b.user_name";
		}
		return DaoUtil.executeQuery(sql, ExtendUser.class);
	}
	
	public int beLikedUserCount(String userName) {
		String sql = "select count(*) as num from like_person where liked_user_name = '" + userName + "'";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0) {
			return 0;
		}
		return counts.get(0).getNum();
	}
	
	public List<ExtendUser> beLikedUses(String userName, int num, long lastId){
		String sql = "";
		if(lastId < 0) {
			sql = "select *, " + 
					"(select count(*) from friend where friend_uid = concat('" + userName + "', '_', a.own_user_name) or friend_uid = concat(a.own_user_name, '_', '" + userName + "')) as is_friend " + 
					"from (select id as extend_id, own_user_name from like_person where liked_user_name = '" + userName + "' " + 
					"and id < (select (max(id) + 1) from like_person) order by id desc limit " + num + ") as a " + 
					"inner join users as b on a.own_user_name = b.user_name";
		}else {
			sql = "select *, " + 
					"(select count(*) from friend where friend_uid = concat('" + userName + "', '_', a.own_user_name) or friend_uid = concat(a.own_user_name, '_', '" + userName + "')) as is_friend " + 
					"from (select id as extend_id, own_user_name from like_person where liked_user_name = '" + userName + "' " + 
					"and id < " + lastId + " order by id desc limit " + num + ") as a " + 
					"inner join users as b on a.own_user_name = b.user_name";
		}
		return DaoUtil.executeQuery(sql, ExtendUser.class);
	}
	
	public List<ExtendUser> followedUses(String userName, int num, long lastId){
		String sql = "";
		if(lastId < 0) {
			sql = "select *, " + 
					"(select count(*) from friend where friend_uid = concat('"+ userName + "', '_', a.followed_user_name) or friend_uid = concat(a.followed_user_name, '_', '" + userName + "')) as is_friend " + 
					"from (select id as extend_id, followed_user_name from follow_person where follow_user_name = '" + userName + "' and id < (select (max(id) + 1) from follow_person) order by id desc limit " + num + ") as a " + 
					"inner join users as b on a.followed_user_name = b.user_name";
		}else {
			sql = "select *, " + 
					"(select count(*) from friend where friend_uid = concat('"+ userName + "', '_', a.followed_user_name) or friend_uid = concat(a.followed_user_name, '_', '" + userName + "')) as is_friend " + 
					"from (select id as extend_id, followed_user_name from follow_person where follow_user_name = '" + userName + "' and id < " + lastId + " order by id desc limit " + num + ") as a " + 
					"inner join users as b on a.followed_user_name = b.user_name";
		}
		return DaoUtil.executeQuery(sql, ExtendUser.class);
	}
	
	public int beFollowedUserCount(String userName) {
		String sql = "select count(*) as num from follow_person where followed_user_name = '" + userName + "'";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0) {
			return 0;
		}
		return counts.get(0).getNum();
	}
	
	public List<ExtendUser> beFollowedUses(String userName, int num, long lastId){
		String sql = "";
		if(lastId < 0) {
			sql = "select *, " + 
					"(select count(*) from friend where friend_uid = concat('" + userName + "', '_', a.follow_user_name) or friend_uid = concat(a.follow_user_name, '_', '" + userName + "')) as is_friend " + 
					"from (select id as extend_id, follow_user_name from follow_person where followed_user_name = '" + userName + "' and id < (select (max(id) + 1) from follow_person) order by id desc limit " + num + ") as a " + 
					"inner join users as b on a.follow_user_name = b.user_name";
		}else {
			sql = "select *, " + 
					"(select count(*) from friend where friend_uid = concat('" + userName + "', '_', a.follow_user_name) or friend_uid = concat(a.follow_user_name, '_', '" + userName + "')) as is_friend " + 
					"from (select id as extend_id, follow_user_name from follow_person where followed_user_name = '" + userName + "' and id < " + lastId + " order by id desc limit " + num + ") as a " + 
					"inner join users as b on a.follow_user_name = b.user_name";
		}
		return DaoUtil.executeQuery(sql, ExtendUser.class);
	}
	
	public List<User> getUsersByNickName(String nickName, long startNum, int num, String from) {
		String sql = "select * from users where user_name <> '" + from + "' and "
				+ "nick_name like '%" + nickName + "%' limit " + startNum + ", " + num;
		return DaoUtil.executeQuery(sql, User.class);
	}
	
	public List<User> getusersByName(List<String> userNames){
		String sql = "select * from users where user_name in (" + ArrayUtil.toCommaSplitSqlStr(userNames) + ")";
		return DaoUtil.executeQuery(sql, User.class);
	}
	
	public long updateLoginStatus(String chatId, String deviceType, int status) {
		String sql = "update login_status set login_status = " + status + " "
				+ "where chat_id = '" + chatId + "' and device_type = '" + deviceType + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public String register(String nickName, String imei, String imsi, String birthday, int birthdayType,
					int sex, String passwd) {
		if(isUserExit(imsi)) {
			return "该sim卡已被注册";
		}
		String sql = "insert into users(user_name, passwd, nick_name, regist_date, photo, sex, "
				+ "age, birthday, birthday_type, imei) values(" + 
				"'" + imsi + "', " + 
				"'" + passwd + "', " + 
				"'" + nickName + "', " + 
				"'" + DateUtil.getFormatDate() + "', " + 
				"'photo/photo_default.jpg', " + 
				"" + sex + ", " + 
				"" + DateUtil.getAgeByBirthDay(birthday) + ", " + 
				"'" + birthday + "', " + 
				"" + birthdayType + ", " + 
				"'" + imei + "'" + 
				")";
		long result = DaoUtil.executeUpdate(sql, false);
		if(result >= 0) {
			return "success";
		}else {
			return "系统错误";
		}
	}
	
	public boolean isUserExit(String userName) {
		String sql = "select count(*) as num from users where user_name = '" + userName + "'";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0) {
			return false;
		}
		if(counts.get(0).getNum() > 0) {
			return true;
		}
		return false;
	}
	
	public long updateNick(String nickName, String from) {
		String sql = "update users set nick_name = '" + nickName + "' where user_name = '" + from + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public boolean canExperience(String from) {
		String sql = "select can_experience as num from users where user_name = '" + from + "'";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0) {
			return false;
		}
		if(counts.get(0).getNum() == 1) {
			return true;
		}
		return false;
	}
	
	public long updateExperience(String from, int value) {
		String sql = "update users set can_experience = " + value + " where user_name = '" + from + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public String getMemberValidDate(String from) {
		String sql = "select number_valid_time as value from users where user_name = '" + from + "'";
		List<StringVal> stringVals = DaoUtil.executeQuery(sql, StringVal.class);
		if(stringVals == null || stringVals.size() <= 0) {
			return null;
		}
		String stringVal = stringVals.get(0).getValue();
		if(stringVal == null || stringVal.isEmpty()) {
			return null;
		}
		return stringVal;
	}
	
	public long updateMemberValidDate(String from, String validDate) {
		String sql = "update users set number_valid_time = '" + validDate + "' where user_name = '" + from + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public String[] createCommunManager(String communityRoomUid) {
		//String nickName, String imei, String imsi, String birthday, int birthdayType,
		//int sex, String passwd
		String nickName = communityRoomUid.replace("_", "") + "管理员";
		String imei = 000000 + "";
		String imsi = System.currentTimeMillis() + "";
		String birthday = DateUtil.getFormatDate().split(" ")[0];
		int birthdayType = 0;
		int sex = 1;
		String passwdRaw = UidUtil.getRandomPasswd();
		String passwd = EncryptionUtil.md5Encryption(passwdRaw);
		if(!"success".equals(register(nickName, imei, imsi, birthday, birthdayType, sex, passwd))) {
			return null;
		}
		String sql = "update community_room set manager = '" + imsi + "', hsa_claimed = 1 "
				+ "where community_uid = '" + communityRoomUid + "'";
		if(DaoUtil.executeUpdate(sql, false) < 0) {
			return null;
		}
		return new String[] {imsi, passwdRaw};
	}
	
	public List<StringVal> getManagerNotifyUsers(String commuUid, int level){
		String sql = "select user_name as value from community_room_follows where "
				+ "community_uid = '" + commuUid + "' and notify_level >= " + level;
		return DaoUtil.executeQuery(sql, StringVal.class);
	}
	
	public boolean isCommunityManager(String userName){
		String sql = "select count(*) as num from community_room where manager = '" + userName + "'";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size()<=0 || counts.get(0).getNum()<=0) {
			return false;
		}
		return true;
	}

}
