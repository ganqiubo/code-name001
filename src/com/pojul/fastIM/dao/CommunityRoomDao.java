package com.pojul.fastIM.dao;

import java.io.File;
import java.util.List;

import com.pojul.fastIM.entity.ChatRoom;
import com.pojul.fastIM.entity.CommunityRoom;
import com.pojul.fastIM.entity.Count;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.fastIM.utils.DateUtil;
import com.pojul.objectsocket.utils.Constant;
import com.pojul.objectsocket.utils.LogUtil;
import com.sun.org.apache.bcel.internal.generic.Select;

public class CommunityRoomDao {

	public boolean isCommunityExit(String CommunityRoomUid) {
		String sql = "select * from community_room where community_uid = '" + CommunityRoomUid + "'";
		List<CommunityRoom> communityRooms = DaoUtil.executeQuery(sql, CommunityRoom.class);
		if(communityRooms == null || communityRooms.size() <= 0) {
			return false;
		}else {
			return true;
		}
	}
	
	public long insertCommunity(CommunityRoom communityRoom) {
		String sql = "insert into community_room(community_uid, name, create_date, community_type, community_subtype"
				+ ", country, province, city, district, addr, longitude, latitude, altitude) values ("
				+ "'" + communityRoom.getCommunityUid() + "', " 
				+ "'" + communityRoom.getName() + "', " 
				+ "'" + DateUtil.getFormatDate() + "', " 
				+ "'" + communityRoom.getCommunityType() + "', " 
				+ "'" + communityRoom.getCommunitySubtype() + "', "
				+ "'" + communityRoom.getCountry() + "', " 	
				+ "'" + communityRoom.getProvince() + "', " 	
				+ "'" + communityRoom.getCity() + "', "
				+ "'" + communityRoom.getDistrict() + "', "
				+ "'" + communityRoom.getAddr() + "', "
				+ "'" + communityRoom.getLongitude() + "', "
				+ "'" + communityRoom.getLatitude() + "', "
				+ "'" + communityRoom.getAltitude() + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long communityMessageReq(String userUid, String communityUid) {
		String sql = "insert into community_message_req(user_uid, community_uid)  values ("
				+ "'" + userUid + "', " 
				+ "'" + communityUid + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long communityMessageUnReq(String userUid, String communityUid) {
		String sql = "delete from community_message_req where user_uid = '" + userUid +"' and community_uid = '" + communityUid +"'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long communityMessageUnReq(String userUid) {
		String sql = "delete from community_message_req where user_uid = '" + userUid +"'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public List<User> queryReqUsers(String communityUid){
		String sql = "select * from users as a inner join community_message_req as b on a.user_name = b.user_uid "
				+ "and b.community_uid = '" + communityUid + "'";
		return DaoUtil.executeQuery(sql, User.class);
	}
	
	public CommunityRoom getChatRoomByMessage(String messageUid) {
		String sql = "select a.* from community_room as a inner join community_message as b "
				+ "on a.community_uid = b.community_uid and b.message_uid = '" + messageUid + "'";
		List<CommunityRoom> communityRooms = DaoUtil.executeQuery(sql, CommunityRoom.class);
		if(communityRooms == null || communityRooms.size() <= 0) {
			return null;
		}
		return communityRooms.get(0);
	}
	
	public long updateManager(String manager, String communRoomUid) {
		String sql = "update community_room set manager = '" + manager + "', "
				+ "hsa_claimed = 1 where community_uid = '" + communRoomUid + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public CommunityRoom getCommuDeatil(String communityUid, String from) {
		String sql = "select *, "
				+ "(select notify_level from community_room_follows where user_name = '" + from + "' "
						+ "and community_uid = '" + communityUid + "') as notify_level, "
				+ "(select count(*) from community_room_follows where community_uid = '" + communityUid + "') as follows, "
				+ "(select count(*) from community_room_follows where user_name = '" + from + "' and community_uid = '" + communityUid + "') "
						+ "as has_followed from community_room where community_uid = '" + communityUid + "'";
		List<CommunityRoom> communityRooms = DaoUtil.executeQuery(sql, CommunityRoom.class);
		if(communityRooms == null || communityRooms.size() <= 0) {
			return null;
		}
		return communityRooms.get(0);
	}
	
	public long editCommuDetail(String roomUid, String detail) {
		String sql = "update community_room set detail = '" + detail + "' where community_uid = '" + roomUid + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long editCommuPhone(String roomUid, String phone) {
		String sql = "update community_room set phone = '" + phone + "' where community_uid = '" + roomUid + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long editCommuPhoto(String roomUid, String path, String rawName) {
		String sql = "update community_room set photo = '" + path + "' where community_uid = '" + roomUid + "'";
		long result = DaoUtil.executeUpdate(sql, false);
		LogUtil.i("rawName: " + rawName);
		if(result >= 0 && rawName != null && !rawName.isEmpty()) {
			LogUtil.i("rawName: " + (Constant.USER_PHOTO_PATH + rawName));
			File file = new File(Constant.USER_PHOTO_PATH + rawName);
			if(file.exists()) {
				file.delete();
			}
		}
		return result;
	}
	
	/*public List<CommunityRoom> getCommunityRoomMulti(List<String> roomUids){
		StringBuilder uids = new StringBuilder();
		for (int i = 0; i < roomUids.size(); i++) {
			if(i == (roomUids.size() - 1)) {
				uids.append("'" + roomUids.get(i) + "'");
			}else {
				uids.append("'" + roomUids.get(i) + "', ");
			}
		}
		String sql = "select * from community_room where community_uid in (" + uids.toString() + ")";
		return DaoUtil.executeQuery(sql, CommunityRoom.class);
	}*/
	
	public long followCommu(String from, String commuUid) {
		if(isFolloedCommu(from, commuUid)) {
			return 0;
		}
		String sql = "insert into community_room_follows(community_uid, user_name) values("
				+ "'" + commuUid + "', " 
				+ "'" + from + "'" + 
				")";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public boolean isFolloedCommu(String from, String commuUid) {
		String sql = "select count(*) as num from community_room_follows where "
				+ "community_uid = '" + commuUid + "' and user_name = '" + from + "'";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts != null && counts.size() > 0 && counts.get(0).getNum() > 0) {
			return true;
		}
		return false;
	}
	
	public long cancelFollowCommu(String from, String commuUid) {
		String sql = "delete from community_room_follows where "
				+ "community_uid = '" + commuUid + "' and user_name = '" + from + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long setNotLevel(String from, String community, int level) {
		String sql = "update community_room_follows set notify_level = " + level + " "
				+ "where community_uid = '" + community + "' and user_name = '" + from + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public boolean isManager(String userName, String roomUid) {
		String sql = "select count(*) as num from community_room where "
				+ "community_uid = '" + roomUid + "' and manager = '" + userName + "'";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0 || counts.get(0).getNum() <= 0) {
			return false;
		}
		return true;
	}
	
}
