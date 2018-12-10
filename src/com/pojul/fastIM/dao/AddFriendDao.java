package com.pojul.fastIM.dao;

import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.pojul.fastIM.entity.AddFriend;
import com.pojul.fastIM.entity.Count;
import com.pojul.fastIM.message.other.NotifyAcceptFriend;
import com.pojul.fastIM.message.other.NotifyChatClosed;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.fastIM.utils.DateUtil;
import com.pojul.objectsocket.utils.UidUtil;

public class AddFriendDao {
	
	public long insertFriendReq(AddFriend addFriend) {
		String sql = "insert into friend_req(req_user_name, reqed_user_name, req_type, req_text, req_time) values("
				+ "'" + addFriend.getReqUserName() + "', "
				+ "'" + addFriend.getReqedUserName() + "', "
				+ "'" + addFriend.getReqType() + "', "
				+ "'" + addFriend.getReqText() + "', "
				+ "'" + DateUtil.getFormatDate() + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}

	public long updateFriendReqedNotify(String reqUserName, String reqedUserName, int type, int hasNotified) {
		String sql = "update friend_req set reqed_notified = " + hasNotified + " where "
				+ "req_user_name = '" + reqUserName + "' and reqed_user_name = '" + reqedUserName + "' and req_type = " + type;
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long updateFriendReqNotify(String reqUserName, String reqedUserName, int type, int hasNotified) {
		String sql = "update friend_req set req_notified = " + hasNotified + " where "
				+ "req_user_name = '" + reqUserName + "' and reqed_user_name = '" + reqedUserName + "' and req_type = " + type;
		return DaoUtil.executeUpdate(sql, false);
	}


	public boolean isFriendReqExit(String reqUserName, String reqedUserName, int type) {
		String sql = "select count(*) as num from friend_req where "
				+ "req_user_name = '" + reqUserName + "' and reqed_user_name = '" + reqedUserName + "' and req_type = " + type;
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0) {
			return false;
		}
		return (counts.get(0).getNum() > 0 ? true : false);
	}
	
	public int friendReqStatus(String reqUserName, String reqedUserName, int type) {
		String sql = "select req_status as num from friend_req where "
				+ "req_user_name = '" + reqUserName + "' and reqed_user_name = '" + reqedUserName + "' and req_type = " + type;
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null) {
			return -3;
		}
		if(counts.size() <= 0) {
			return -2;
		}
		return counts.get(0).getNum();
	}
	
	public long updateReqStatus(String reqUserName, String reqedUserName, int reqStatus, int type) {
		String sql = "update friend_req set req_status = " + reqStatus + " where "
				+ "req_user_name = '" + reqUserName + "' and reqed_user_name = '" + reqedUserName + "' and req_type = " + type;
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long updateNewReq(String reqUserName, String reqedUserName, int reqStatus, int type, String note) {
		String sql = "update friend_req set req_status = " + reqStatus + ", req_notified = 0, reqed_notified = 0, req_text = '" + note + "' "
				+ "where req_user_name = '" + reqUserName + "' and reqed_user_name = '" + reqedUserName + "' and req_type = " + type;
		return DaoUtil.executeUpdate(sql, false);
	}

	public List<AddFriend> getUnNotifiedFriendReq(String reqedUserName){
		String sql = "select * from (select * from friend_req where reqed_user_name = '" + reqedUserName + "' and reqed_notified = 0 and req_status = 0) as a " + 
				"inner join users as b on a.req_user_name = b.user_name";
		return DaoUtil.executeQuery(sql, AddFriend.class);
	}
	
	public List<AddFriend> getAddFriendReqs(String reqedUserName, long startNum, int num){
		String sql = "select * from (select * from friend_req where reqed_user_name = '" + reqedUserName + "' "
				+ "order by req_time desc limit " + startNum + ", " + num + ") as a " + 
				"inner join users as b on a.req_user_name = b.user_name";
		return DaoUtil.executeQuery(sql, AddFriend.class);
	}

	public long acceptFriend(String reqUserName, String reqedUserName, int type) {
		// TODO Auto-generated method stub
		if(type == 1) {
			if(isFriendExit(reqUserName, reqedUserName)) {
				return 0;
			}
			if(insertFriend(reqUserName, reqedUserName) >= 0) {
				if(updateReqStatus(reqUserName, reqedUserName, 1, type) >= 0) {
					return 0;
				}else {
					return -1;
				}
			}else {
				return -1;
			}
		}else if(type == 2){
			if(updateReqStatus(reqUserName, reqedUserName, 1, type) >= 0) {
				return 0;
			}else {
				return -1;
			}
		}
		return -1;
	}
	
	public long insertFriend(String reqUserName, String reqedUserName) {
		String friendUid = UidUtil.getSingleChatRoomUid(reqUserName, reqedUserName);
		String sql = "insert into friend(friend_uid, frienda_user_name, friendb_user_name) values("
				+ "'" + friendUid + "', "
				+ "'" + reqUserName + "', "
				+ "'" + reqedUserName + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public boolean isFriendExit(String reqUserName, String reqedUserName) {
		String sql = "select count(*) as num from friend where (frienda_user_name = '" + reqUserName + "' and friendb_user_name = '" + reqedUserName + "') or "
				+ "(friendb_user_name = '" + reqUserName + "' and frienda_user_name = '" + reqedUserName + "')";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0) {
			return false;
		}
		if(counts.get(0).getNum() > 0) {
			return true;
		}
		return false;
	}
	
	public List<NotifyAcceptFriend> getUnNotiftAcceptFriends(String reqUserName){
		String sql = "select * from (select req_user_name, reqed_user_name, req_type, req_text from friend_req where req_user_name = '韩信' and req_notified = 0 and req_status = 0) as a " + 
				"inner join users as b on a.reqed_user_name = b.user_name";
		return DaoUtil.executeQueryChat(sql, NotifyAcceptFriend.class);
	}
	
	public int chatLegal(String owner, String friend) {
		if(isFriendExit(owner, friend)) {
			return 1;
		}
		if(isGreetValid(owner, friend)) {
			return 2;
		}
		return 3;
	}
	
	public boolean isGreetValid(String owner, String friend) {
		String sql = "select req_status as num from friend_req where req_type = 2 and " + 
				"((req_user_name = '" + owner + "' and reqed_user_name ='" + friend + "') or " + 
				"(reqed_user_name = '" + owner + "' and req_user_name = '" + friend + "'))";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0) {
			return false;
		}
		int reqStatue = counts.get(0).getNum();
		if(reqStatue == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	public long closeGreetChat(String from, String to) {
		String sql = "update friend_req set req_status = -1 where " + 
				"(req_user_name = '" + from + "' and reqed_user_name = '" + to + "') or " + 
				"(reqed_user_name = '" + from + "' and req_user_name = '" + to + "')";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long insertCloseChat(String from, String to) {
		String chatUid = UidUtil.getSingleChatRoomUid(from, to);
		String sql = "insert into chat_room_close(close_user_name, closed_user_name, chat_uid) values ("
				+ "'" + from + "', "
				+ "'" + to + "', "
				+ "'" + chatUid + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public List<NotifyChatClosed> getUnNotifyChatClosed(String closedUserName){
		String sql = "select chat_uid from chat_room_close where closed_user_name = '" + closedUserName + "' and notified = 0";
		return DaoUtil.executeQueryChat(sql, NotifyChatClosed.class);
	}
	
	public long updateChatClosedNotify(String chatUid) {
		String sql = "update chat_room_close set notified = 1 where chat_uid = '" + chatUid + "'";
		return DaoUtil.executeUpdate(sql, false);
	}

	public long deleteFriend(String owner, String friend) {
		// TODO Auto-generated method stub
		String uid = UidUtil.getSingleChatRoomUid(owner, friend);
		String sql = "delete from friend where friend_uid = '" + uid + "'";
		if(DaoUtil.executeUpdate(sql, false) >= 0) {
			closeGreetChat(owner, friend);
			return 0;
		}else {
			return -1;
		}
	}
	
}


