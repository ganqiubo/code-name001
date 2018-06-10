package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.Friend;
import com.pojul.fastIM.utils.DaoUtil;

public class FriendDao {

	public List<Friend> getFriends(String userName) {
		String sql = "select * from users INNER JOIN friend on ("
				+ "(users.user_name = friend.frienda_user_name and friend.friendb_user_name = '" + userName + "') or "
				+ "(users.user_name = friend.friendb_user_name and friend.frienda_user_name = '" + userName + "')"
				+ ")";
		/*String sql = "select a.*, count(c.user_message_uid) as unsend_message from "
				+ "( select a.* from users as a INNER JOIN friend as b on ((a.user_name = b.frienda_user_name and b.friendb_user_name = '"+ userName + "') or "
						+ "(a.user_name = b.friendb_user_name and b.frienda_user_name = '"+ userName + "')) ) as a "
				+ "LEFT JOIN user_message as c on (a.user_name = c.from_user and c.to_user = '" + userName + "' and c.is_send = '0') group by a.user_name";*/
		List<Friend> friends = DaoUtil.executeQuery(sql, Friend.class);
		return friends;
	}
	
}
