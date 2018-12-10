package com.pojul.fastIM.entity;

import java.util.List;
import java.sql.ResultSet;

public class UploadPic extends BaseEntity{

	private long id;
	private int userId;
	private int uploadPicType;
	private int isDelete= 1;
	private String uplodPicTheme;
	private String uplodPicLabel;
	private String uploadPicCountry;
	private String uploadPicCity;
	private String uploadPicDistrict;
	private String uploadPicAddr;
	private double uploadPicLongitude;
	private double uploadPicLatitude;
	private double uploadPicAltitude;
	private String uploadPicLocnote;
	private int uploadPicLocshow;
	private String uploadPicTime;
	private String uploadPicProvince;
	private String picTime;
	private int picLocType;
	
	private List<Pic> pics;
	private String picsStr;

	private int thumbUpNum;
	private int hasThubmUp;
	private int replyNum;
	private int hasLiked;
	private int hasCollected;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUploadPicType() {
		return uploadPicType;
	}

	public void setUploadPicType(int uploadPicType) {
		this.uploadPicType = uploadPicType;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getUplodPicTheme() {
		return uplodPicTheme;
	}

	public void setUplodPicTheme(String uplodPicTheme) {
		this.uplodPicTheme = uplodPicTheme;
	}

	public String getUplodPicLabel() {
		return uplodPicLabel;
	}

	public void setUplodPicLabel(String uplodPicLabel) {
		this.uplodPicLabel = uplodPicLabel;
	}

	public String getUploadPicCountry() {
		return uploadPicCountry;
	}

	public void setUploadPicCountry(String uploadPicCountry) {
		this.uploadPicCountry = uploadPicCountry;
	}

	public String getUploadPicCity() {
		return uploadPicCity;
	}

	public void setUploadPicCity(String uploadPicCity) {
		this.uploadPicCity = uploadPicCity;
	}

	public String getUploadPicDistrict() {
		return uploadPicDistrict;
	}

	public void setUploadPicDistrict(String uploadPicDistrict) {
		this.uploadPicDistrict = uploadPicDistrict;
	}

	public String getUploadPicAddr() {
		return uploadPicAddr;
	}

	public void setUploadPicAddr(String uploadPicAddr) {
		this.uploadPicAddr = uploadPicAddr;
	}

	public double getUploadPicLongitude() {
		return uploadPicLongitude;
	}

	public void setUploadPicLongitude(double uploadPicLongitude) {
		this.uploadPicLongitude = uploadPicLongitude;
	}

	public double getUploadPicLatitude() {
		return uploadPicLatitude;
	}

	public void setUploadPicLatitude(double uploadPicLatitude) {
		this.uploadPicLatitude = uploadPicLatitude;
	}

	public double getUploadPicAltitude() {
		return uploadPicAltitude;
	}

	public void setUploadPicAltitude(double uploadPicAltitude) {
		this.uploadPicAltitude = uploadPicAltitude;
	}

	public String getUploadPicLocnote() {
		return uploadPicLocnote;
	}

	public void setUploadPicLocnote(String uploadPicLocnote) {
		this.uploadPicLocnote = uploadPicLocnote;
	}

	public int getUploadPicLocshow() {
		return uploadPicLocshow;
	}

	public void setUploadPicLocshow(int uploadPicLocshow) {
		this.uploadPicLocshow = uploadPicLocshow;
	}

	public List<Pic> getPics() {
		return pics;
	}

	public void setPics(List<Pic> pics) {
		this.pics = pics;
	}

	public String getUploadPicTime() {
		return uploadPicTime;
	}

	public void setUploadPicTime(String uploadPicTime) {
		this.uploadPicTime = uploadPicTime;
	}

	public String getUploadPicProvince() {
		return uploadPicProvince;
	}

	public void setUploadPicProvince(String uploadPicProvince) {
		this.uploadPicProvince = uploadPicProvince;
	}

	public String getPicTime() {
		return picTime;
	}

	public void setPicTime(String picTime) {
		this.picTime = picTime;
	}

	public int getPicLocType() {
		return picLocType;
	}

	public void setPicLocType(int picLocType) {
		this.picLocType = picLocType;
	}

	public String getPicsStr() {
		return picsStr;
	}

	public void setPicsStr(String picsStr) {
		this.picsStr = picsStr;
	}

	public int getThumbUpNum() {
		return thumbUpNum;
	}

	public void setThumbUpNum(int thumbUpNum) {
		this.thumbUpNum = thumbUpNum;
	}

	public int getHasThubmUp() {
		return hasThubmUp;
	}

	public void setHasThubmUp(int hasThubmUp) {
		this.hasThubmUp = hasThubmUp;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	public int getHasLiked() {
		return hasLiked;
	}

	public void setHasLiked(int hasLiked) {
		this.hasLiked = hasLiked;
	}

	public int getHasCollected() {
		return hasCollected;
	}

	public void setHasCollected(int hasCollected) {
		this.hasCollected = hasCollected;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		id = getLong(rs, "id");
		userId = getInt(rs, "user_id");
		uploadPicType = getInt(rs, "upload_pic_type");
		isDelete = getInt(rs, "is_delete");
		uplodPicTheme = getString(rs, "uplod_pic_theme");
		uplodPicLabel = getString(rs, "upload_pic_label");
		uploadPicCountry = getString(rs, "upload_pic_country");
		uploadPicCity = getString(rs, "upload_pic_city");
		uploadPicDistrict = getString(rs, "upload_pic_district");
		uploadPicAddr = getString(rs, "upload_pic_addr");
		uploadPicLocnote = getString(rs, "upload_pic_locnote");
		uploadPicLocshow = getInt(rs, "upload_pic_locshow");
		uploadPicLongitude = getDouble(rs, "upload_pic_longitude");
		uploadPicLatitude = getDouble(rs, "upload_pic_latitude");
		uploadPicAltitude = getDouble(rs, "upload_pic_altitude");
		uploadPicTime = getString(rs, "upload_pic_time");
		uploadPicProvince = getString(rs, "upload_pic_province");
		picTime = getString(rs, "pic_time");
		picLocType = getInt(rs, "pic_loc_type");
		picsStr = getString(rs, "pics_str");
		thumbUpNum = getInt(rs, "thumb_up_num");
		hasThubmUp = getInt(rs, "has_thubm_up");
		//replyNum = getInt(rs, "reply_num");
		hasLiked = getInt(rs, "has_liked");
		hasCollected = getInt(rs, "has_collected");
		
	}
	
}
