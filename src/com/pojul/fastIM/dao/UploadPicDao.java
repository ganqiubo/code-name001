package com.pojul.fastIM.dao;

import java.util.List;

import javax.activation.URLDataSource;

import com.pojul.fastIM.entity.ExtendUploadPic;
import com.pojul.fastIM.entity.Pic;
import com.pojul.fastIM.entity.PicFilter;
import com.pojul.fastIM.entity.SimpleUploadPic;
import com.pojul.fastIM.entity.TagMessLabels;
import com.pojul.fastIM.entity.ThirdPicLikes;
import com.pojul.fastIM.entity.UploadPic;
import com.pojul.fastIM.utils.ArrayUtil;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.fastIM.utils.DateUtil;
import com.pojul.objectsocket.utils.LogUtil;

public class UploadPicDao {
	
	private static final String TAG = "UploadPicDao";
	
	public Long insertUploadPic(UploadPic uploadPic) {
		if(uploadPic == null || uploadPic.getPics() == null || uploadPic.getPics().size() <= 0) {
			LogUtil.i(TAG, "上传图片为空");
			return -1L;
		}
		
		String sql = "insert into upload_pic(user_id, upload_pic_type, is_delete, uplod_pic_theme, "
				+ "upload_pic_country, upload_pic_city, upload_pic_district, "
				+ "upload_pic_addr, upload_pic_longitude, upload_pic_latitude, upload_pic_altitude,"
				+ "upload_pic_locnote, upload_pic_time, upload_pic_province, pic_time, pic_loc_type, "
				+ "upload_pic_locshow) values ("
				+ "'" + uploadPic.getUserId() + "', " 
				+ "'" + uploadPic.getUploadPicType() + "', " 
				+ "'" + uploadPic.getIsDelete() + "', " 
				+ "'" + uploadPic.getUplodPicTheme() + "', " 
				+ "'" + uploadPic.getUploadPicCountry() + "', " 
				+ "'" + uploadPic.getUploadPicCity() + "', " 
				+ "'" + uploadPic.getUploadPicDistrict() + "', " 
				+ "'" + uploadPic.getUploadPicAddr() + "', " 
				+ "'" + uploadPic.getUploadPicLongitude() + "', " 
				+ "'" + uploadPic.getUploadPicLatitude() + "', " 
				+ "'" + uploadPic.getUploadPicAltitude() + "', " 
				+ "'" + uploadPic.getUploadPicLocnote() + "', " 
				+ "'" + uploadPic.getUploadPicTime() + "', " 
				+ "'" + uploadPic.getUploadPicProvince() + "', " 
				+ "'" + DateUtil.getFormatDate() + "', " 
				+ "'" + uploadPic.getPicLocType() + "', " 
				+ "'" + uploadPic.getUploadPicLocshow() + "'"
				+ ")";
		long addUploadPicId = DaoUtil.executeUpdate(sql, true);
		if(addUploadPicId < 0) {
			return -1L;
		}
		insertLabels(addUploadPicId, uploadPic.getUplodPicLabel());
		long result = new PicDao().insertPic(uploadPic.getPics(), addUploadPicId);
		return result;
	}
	
	public long insertLabels(long addUploadPicId, String uplodPicLabel) {
		// TODO Auto-generated method stub
		List<String> labels = ArrayUtil.toCommaSplitList(uplodPicLabel);
		if(labels.size() <= 0) {
			return 0;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("insert into upload_pic_labels(upload_pic_id, label) values ");
		for (int i = 0; i < labels.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			String value = "('" + addUploadPicId + "', " + "'" + labels.get(i) + "'" + ")";
			sb.append(value);
		}
		String sql = sb.toString();
		return DaoUtil.executeUpdate(sql, false);
		
	}

	public List<UploadPic> queryRecords(long id, String lastTime, int num) {
		String sql = "select * from upload_pic where user_id = "
				+ id + "  and is_delete = 1 and upload_pic_time < '" + lastTime + "' order by upload_pic_time desc limit "+ num;
		List<UploadPic> uploadPics = DaoUtil.executeQuery(sql, UploadPic.class);
		if(uploadPics == null || uploadPics.size() <= 0) {
			return uploadPics;
		}
		for (int i = 0; i < uploadPics.size(); i++) {
			UploadPic uploadPic = uploadPics.get(i);
			List<Pic> pics = new PicDao().queryRecords(uploadPic.getId());
			uploadPic.setPics(pics);
		}
		return uploadPics;
	}
	
	public List<UploadPic> uesrUploadPic(long userId, long startNum, int num){
		String sql = "";
		sql = "select a.*, " + 
				"(select GROUP_CONCAT(concat(id, '*', upload_pic_id, '*', upload_pic_url, '*', width, '*', height) SEPARATOR ';') from pic where upload_pic_id = a.id) as pics_str, " + 
				"(select GROUP_CONCAT(label SEPARATOR ',') from upload_pic_labels where upload_pic_id = a.id) as upload_pic_label, " + 
				"(select count(*) from upload_pic_like where upload_pic_id = a.id and like_user_id = " + userId + ") as has_liked, " + 
				"(select count(*) from upload_pic_collect where upload_pic_id = a.id and collect_user_id = " + userId + ") as has_collected, " + 
				"(select count(*) from upload_pic_thumbup where upload_pic_id = a.id) as thumb_up_num, " + 
				"(select count(*) from upload_pic_thumbup where upload_pic_id = a.id and thumbup_user_id = " + userId + ") as has_thubm_up " + 
				"from (select * from upload_pic where user_id = " + userId + " order by id desc limit "+ startNum + ", " + num + ") as a";
		return DaoUtil.executeQuery(sql, UploadPic.class);
	}
	
	public long likeUploadPic(long userId, long uploadPicId, int type) {
		String sql = "";
		if(type == 0) {
			sql = "insert into upload_pic_like(upload_pic_id, like_user_id) values "
					+ "(" + uploadPicId + ", " + userId + ")";
		}else {
			sql = "delete from upload_pic_like where upload_pic_id = " + uploadPicId + " and "
					+ "like_user_id = " + userId;
		}
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long collectUploadPic(long userId, long uploadPicId, int type) {
		String sql = "";
		if(type == 0) {
			sql = "insert into upload_pic_collect(upload_pic_id, collect_user_id) values "
					+ "(" + uploadPicId + ", " + userId + ")";
		}else {
			sql = "delete from upload_pic_collect where upload_pic_id = " + uploadPicId + " and "
					+ "collect_user_id = " + userId;
		}
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public long thumbupUploadPic(long userId, long uploadPicId) {
		String sql = "insert into upload_pic_thumbup(upload_pic_id, thumbup_user_id) values "
				+ "(" + uploadPicId + ", " + userId + ")";
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public List<String> picFilterLabels(){
		String sql = "select label as labels from pic_filter_show_labels";
		List<TagMessLabels> labels = DaoUtil.executeQuery(sql, TagMessLabels.class);
		List<String> templabels = ArrayUtil.getLabels(labels);
		return templabels;
	}
	
	public List<ExtendUploadPic> getPics(long fromId, PicFilter picFilter, 
			long startNum, int num, boolean isChoice) {
		String labelSql  = "";
		String labelStrs = ArrayUtil.toCommaSplitSqlStr(picFilter.getLabels());
		if(!labelStrs.isEmpty()) {
			labelSql = " (select distinct a.id as temp_id, a.* from upload_pic as a " + 
					"inner join upload_pic_labels as b on a.id = b.upload_pic_id and b.label in (" + labelStrs + ")) ";
		}else {
			labelSql = " upload_pic ";
		}
		String sexWhereSql = "";
		if(picFilter.getSex() != -1) {
			sexWhereSql = " where sex = " + picFilter.getSex();
		}
		String orderSql = "e.id desc";
		if(isChoice) {
			orderSql = "e.thumb_up_num desc, e.id desc";
		}
		String sql = "";
		if(picFilter.isNearBy()) {
			String nearBySql = " (select * from upload_pic where upload_pic_city = '" + picFilter.getCity() + "' and upload_pic_type = 2)";
			if(!labelStrs.isEmpty()) {
				nearBySql = " (select distinct a.id as temp_id, a.* from upload_pic as a " + 
						"inner join upload_pic_labels as b on a.id = b.upload_pic_id and a.upload_pic_city = '" + picFilter.getCity() + "' " + 
						"and upload_pic_type = 2 and b.label in (" + labelStrs + ")) ";
			}
			sql = "select * from " + 
					"(select c.*, d.sex, d.photo, d.user_name, d.nick_name, d.age , " + 
					"(select count(*) from pic_comment where comment_level = 0 and upload_pic_id = c.id) as comments, " + 
					"(select GROUP_CONCAT(concat(id, '*', upload_pic_id, '*', upload_pic_url, '*', width, '*', height) SEPARATOR ';') from pic where upload_pic_id = c.id) as pics_str, " + 
					"(select GROUP_CONCAT(label SEPARATOR ',') from upload_pic_labels where upload_pic_id = c.id) as upload_pic_label, " + 
					"(select count(*) from upload_pic_like where upload_pic_id = c.id and like_user_id = " + fromId + ") as has_liked, " + 
					"(select count(*) from upload_pic_collect where upload_pic_id = c.id and collect_user_id = " + fromId + ") as has_collected, " + 
					"(select count(*) from upload_pic_thumbup where upload_pic_id = c.id) as thumb_up_num, " + 
					"(select (abs(c.upload_pic_longitude - " + picFilter.getLon() + ") + abs(c.upload_pic_latitude - " + picFilter.getLan() + ")) ) as distance, " + 
					"(select count(*) from upload_pic_thumbup where upload_pic_id = c.id and thumbup_user_id = " + fromId + ") as has_thubm_up " + 
					"from" + nearBySql + "as c inner join users as d on c.user_id = d.id" + sexWhereSql + ") as e " + 
					"order by e.distance, e.id desc limit " + startNum + ", " + num + "";
		}else {
			sql = "select * from " + 
					"(select c.*, d.sex, d.photo, d.user_name, d.nick_name, d.age , " + 
					"(select count(*) from pic_comment where comment_level = 0 and upload_pic_id = c.id) as comments, " + 
					"(select GROUP_CONCAT(concat(id, '*', upload_pic_id, '*', upload_pic_url, '*', width, '*', height) SEPARATOR ';') from pic where upload_pic_id = c.id) as pics_str, " + 
					"(select GROUP_CONCAT(label SEPARATOR ',') from upload_pic_labels where upload_pic_id = c.id) as upload_pic_label, " + 
					"(select count(*) from upload_pic_like where upload_pic_id = c.id and like_user_id = " + fromId + ") as has_liked, " + 
					"(select count(*) from upload_pic_collect where upload_pic_id = c.id and collect_user_id = " + fromId + ") as has_collected, " + 
					"(select count(*) from upload_pic_thumbup where upload_pic_id = c.id) as thumb_up_num, " + 
					"(select count(*) from upload_pic_thumbup where upload_pic_id = c.id and thumbup_user_id = " + fromId + ") as has_thubm_up " + 
					"from" + labelSql + "as c inner join users as d on c.user_id = d.id" + sexWhereSql + ") as e " + 
					"order by " + orderSql + " limit " + startNum + ", " + num + "";
		}
		List<ExtendUploadPic> uploadPics = DaoUtil.executeQuery(sql, ExtendUploadPic.class);
		return uploadPics;
	}

	public long likeUploadPicUnsplash(String from, String url, String uid, int type, String gallery) {
		String sql = "";
		// TODO Auto-generated method stub
		if(type == 0) {
			sql = "insert into upload_pic_liked_third(like_uid, gally, url, user_name) values("
					+ "'" + uid + "', " 
					+ "'" + gallery + "', " 
					+ "'" + url + "', " 
					+ "'" + from + "'" 
					+ ")";
		}else {
			sql = "delete from upload_pic_liked_third where like_uid = '" + uid + "' and gally = '"+ gallery+"'";
		}
		return DaoUtil.executeUpdate(sql, false);
	}

	public long likeUploadPicPexels(String from, String url, int type) {
		String sql = "";
		if(type == 0) {
			sql = "insert into upload_pic_liked_third(gally, url, user_name) values("
					+ "'pexels', " 
					+ "'" + url + "', " 
					+ "'" + from + "'"
					+ ")";
		}else {
			sql = "delete from upload_pic_liked_third where url = '" + url + "' and gally = 'pexels'";
		}
		return DaoUtil.executeUpdate(sql, false);
	}
	
	public List<ThirdPicLikes> thirdPicLikes(String from, String gallery, List<String> uids, List<String> urls){
		String[] sqls;
		List<ThirdPicLikes> thirdPicLikes = null;
		if("unsplash".equals(gallery) || "pixabay".equals(gallery)) {
			if(uids == null) {
				return null;
			}
			sqls = new String[uids.size()];
			for (int i = 0; i < uids.size(); i++) {
				String uid = uids.get(i);
				String sql = "select (select count(*) from upload_pic_liked_third where like_uid = '" + uid + "' and user_name = '" + from + "') as has_liked, " + 
						"(select count(*) from upload_pic_collect_third where collect_uid = '" + uid + "' and user_name = '" + from + "') as has_collected, " + 
						"(select count(*) from upload_pic_thumbup_third where thumbup_uid = '" + uid + "' and user_name = '" + from + "') as has_thumbuped, " + 
						"(select count(*) from upload_pic_thumbup_third where thumbup_uid = '" + uid + "') as thumbup_num, " + 
						"(select count(*) from pic_comment where comment_level = 0 and upload_pic_id = '" + uid + "') as comments, " + 
						"'" + from + "' as user_name, " + 
						"'"+gallery+"' as gally, " + 
						"'' as url, " + 
						"'" + uid + "' as pic_uid";
				sqls[i] = sql;
			}
			thirdPicLikes = DaoUtil.executeQueryMulti(sqls, ThirdPicLikes.class);
		}else if("pexels".equals(gallery)) {
			if(urls == null) {
				return null;
			}
			sqls = new String[urls.size()];
			for (int i = 0; i < urls.size(); i++) {
				String url = urls.get(i);
				String sql = "select (select count(*) from upload_pic_liked_third where url = '" + url + "' and user_name = '" + from + "') as has_liked, " + 
						"(select count(*) from upload_pic_collect_third where url = '" + url + "' and user_name = '" + from + "') as has_collected, " + 
						"(select count(*) from upload_pic_thumbup_third where url = '" + url + "' and user_name = '" + from + "') as has_thumbuped, " + 
						"(select count(*) from upload_pic_thumbup_third where url = '" + url + "') as thumbup_num, " + 
						"'" + from + "' as user_name, " + 
						"'pexels' as gally, " + 
						"'" + url + "' as url, " + 
						"'' as pic_uid";
				sqls[i] = sql;
			}
			thirdPicLikes = DaoUtil.executeQueryMulti(sqls, ThirdPicLikes.class);
		}
		return thirdPicLikes;
	}

	public long collectUploadPicUnsplash(String from, String url, String uid, int type, String gallery) {
		// TODO Auto-generated method stub
		String sql = "";
		// TODO Auto-generated method stub
		if(type == 0) {
			sql = "insert into upload_pic_collect_third(collect_uid, gally, url, user_name) values("
					+ "'" + uid + "', " 
					+ "'" + gallery + "', " 
					+ "'" + url + "', " 
					+ "'" + from + "'"
					+ ")";
		}else {
			sql = "delete from upload_pic_collect_third where collect_uid = '" + uid + "' and gally = '"+gallery+"'";
		}
		return DaoUtil.executeUpdate(sql, false);
	}

	public long collectUploadPicPexels(String from, String url, int type) {
		// TODO Auto-generated method stub
		String sql = "";
		if(type == 0) {
			sql = "insert into upload_pic_collect_third(gally, url, user_name) values("
					+ "'pexels', " 
					+ "'" + url + "', " 
					+ "'" + from + "'"
					+ ")";
		}else {
			sql = "delete from upload_pic_collect_third where url = '" + url + "' and gally = 'pexels'";
		}
		return DaoUtil.executeUpdate(sql, false);
	}

	public long thumbupUploadPicUnsplash(String from, String url, String uid, String gallery) {
		// TODO Auto-generated method stub
		String sql = "insert into upload_pic_thumbup_third(thumbup_uid, gally, url, user_name) values("
					+ "'" + uid + "', " 
					+ "'"+gallery+"', " 
					+ "'" + url + "', " 
					+ "'" + from + "'"
					+ ")";
		return DaoUtil.executeUpdate(sql, false);
	}

	public long thumbupUploadPicPexels(String from, String url) {
		String sql = "insert into upload_pic_thumbup_third(gally, url, user_name) values("
					+ "'pexels', " 
					+ "'" + url + "', " 
					+ "'" + from + "'"
					+ ")";
		return DaoUtil.executeUpdate(sql, false);	
	}
	
	public List<SimpleUploadPic> getLikedPics(String gallery, int num, long startNum, String from, long fromId){
		String sql = "";
		if("脚步".equals(gallery)) {
			sql = "select a.upload_pic_id as id, '脚步' as gallery, " + 
					"(select count(*) from upload_pic_like where upload_pic_id = a.upload_pic_id and like_user_id = " + fromId + ") as has_liked, " + 
					"(select count(*) from upload_pic_collect where upload_pic_id = a.upload_pic_id and collect_user_id = " + fromId + ") as has_collected, " + 
					"(select count(*) from upload_pic_thumbup where upload_pic_id = a.upload_pic_id and thumbup_user_id = " + fromId + ") as has_thubm_up, " + 
					"(select GROUP_CONCAT(concat(upload_pic_url) SEPARATOR ';') from pic where upload_pic_id = a.upload_pic_id) as pics_str " + 
					"from (select upload_pic_id from upload_pic_like where like_user_id = " + fromId + " "
					+ "order by id desc limit " + startNum + ", " + num + ") as a " + 
					"inner join upload_pic as b on a.upload_pic_id = b.id";
		}else {
			sql = "select a.like_uid as pic_uid, a.gally as gallery, " + 
					"(select count(*) from upload_pic_liked_third where url = a.url and user_name = '" + from + "') as has_liked, " + 
					"(select count(*) from upload_pic_collect_third where url = a.url and user_name = '" + from + "') as has_collected, " + 
					"(select count(*) from upload_pic_thumbup_third where url = a.url and user_name = '" + from + "') as has_thubm_up, " + 
					"a.url as pics_str from " + 
					"(select * from upload_pic_liked_third where user_name = '" + from + "' order by id desc limit " + startNum + ", " + num+ ") as a";
		}
		return DaoUtil.executeQuery(sql, SimpleUploadPic.class);
	}
	
	
	public List<SimpleUploadPic> getCollectedPics(String gallery, int num, long startNum, String from, long fromId){
		String sql = "";
		if("脚步".equals(gallery)) {
			sql = "select a.upload_pic_id as id, '脚步' as gallery, " + 
					"(select count(*) from upload_pic_like where upload_pic_id = a.upload_pic_id and like_user_id = " + fromId + ") as has_liked, " + 
					"(select count(*) from upload_pic_collect where upload_pic_id = a.upload_pic_id and collect_user_id = " + fromId + ") as has_collected, " + 
					"(select count(*) from upload_pic_thumbup where upload_pic_id = a.upload_pic_id and thumbup_user_id = " + fromId + ") as has_thubm_up, " + 
					"(select GROUP_CONCAT(concat(upload_pic_url) SEPARATOR ';') from pic where upload_pic_id = a.upload_pic_id) as pics_str " + 
					"from (select upload_pic_id from upload_pic_collect where collect_user_id = " + fromId + " "
					+ "order by id desc limit " + startNum + ", " + num + ") as a " + 
					"inner join upload_pic as b on a.upload_pic_id = b.id";
		}else {
			sql = "select a.collect_uid as pic_uid, a.gally as gallery, " + 
					"(select count(*) from upload_pic_liked_third where url = a.url and user_name = '" + from + "') as has_liked, " + 
					"(select count(*) from upload_pic_collect_third where url = a.url and user_name = '" + from + "') as has_collected, " + 
					"(select count(*) from upload_pic_thumbup_third where url = a.url and user_name = '" + from + "') as has_thubm_up, " + 
					"a.url as pics_str from " + 
					"(select * from upload_pic_collect_third where user_name = '" + from + "' order by id desc limit " + startNum + ", " + num+ ") as a";
		}
		return DaoUtil.executeQuery(sql, SimpleUploadPic.class);
	}
	
}
