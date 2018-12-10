package com.pojul.fastIM.dao;

import java.util.List;
import com.pojul.fastIM.entity.Count;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.objectsocket.utils.EncryptionUtil;
import com.pojul.objectsocket.utils.UidUtil;

public class MakeQRCodeDao {

	public long makeQRCode(String roomUid, long milli) {
		if(makeQRCodeExit(roomUid)) {
			return updateQRCode(roomUid, milli);
		}else {
			return insertQRCode(roomUid, milli);
		}
	}
	
	public boolean makeQRCodeExit(String roomUid) {
		String sql = "select count(*) as num from makeQRCode where commun_room_uid = '" + roomUid + "'";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0 || counts.get(0).getNum() <= 0) {
			return false;
		}
		return true;
	}
	
	public long insertQRCode(String roomUid, long milli) {
		String sql = "insert into makeQRCode(commun_room_uid, milli, create_milli) values("
				+ "'" + roomUid + "', "
				+ "" + milli + ", "
				+ "" + System.currentTimeMillis() + ")";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long updateQRCode(String roomUid, long milli) {
		String sql = "update makeQRCode set milli = " + milli + ", "
				+ "create_milli = " + System.currentTimeMillis() +" "
				+ "where commun_room_uid = '" + roomUid + "'";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long checkClaimCommun(String roomUid, long milli) {
		if(isCommunClaimed(roomUid)) {
			return -2;
		}
		long currentMilli = System.currentTimeMillis() - 60 * 1000;
		String sql = "select count(*) as num from makeQRCode where commun_room_uid = '" + roomUid +"' "
				+ "and milli = " + milli + " and create_milli > " + currentMilli + "";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0 || counts.get(0).getNum() == 0) {
			return -1;
		}
		return 0;
	}
	
	public boolean isCommunClaimed(String roomUid) {
		String sql = "select hsa_claimed as num from community_room where community_uid = '" + roomUid + "'";
		List<Count> counts = DaoUtil.executeQuery(sql, Count.class);
		if(counts == null || counts.size() <= 0 || counts.get(0).getNum() <= 0) {
			return false;
		}
		return true;
	}
	
	public long createCommManager(String communName, String communRoomUid){
		UserDao userDao = new UserDao();
		String imsi = System.currentTimeMillis() + "";
		String passwd = EncryptionUtil.md5Encryption(imsi.substring((imsi.length() - 8), imsi.length()));
		String result = userDao.register((communName + "管理员"), "1111111111", imsi, "1949-10-01 00:00:00", 1, 1, passwd);
		if(!"success".equals(result)) {
			return -1;
		}
		long updateManager = new CommunityRoomDao().updateManager(imsi, communRoomUid);
		if(updateManager < 0) {
			return -2;
		}
		return 0;
	}
	
}
