package com.pojul.fastIM.dao;

import java.util.List;

import com.pojul.fastIM.entity.ChatRoom;
import com.pojul.fastIM.entity.CommunityRoom;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.fastIM.utils.DateUtil;
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
}
