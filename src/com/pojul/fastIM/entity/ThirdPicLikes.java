package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class ThirdPicLikes extends BaseEntity{

    private String userName;
    private String uid;
    private String url;
    private int hasLiked;
    private int hasCollected;
    private int hasThumbuped;
    private int thumbupNum;
    private String gallery;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getHasThumbuped() {
        return hasThumbuped;
    }

    public void setHasThumbuped(int hasThumbuped) {
        this.hasThumbuped = hasThumbuped;
    }

    public int getThumbupNum() {
        return thumbupNum;
    }

    public void setThumbupNum(int thumbupNum) {
        this.thumbupNum = thumbupNum;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		userName = getString(rs, "user_name");
		uid = getString(rs, "pic_uid");
		url = getString(rs, "url");
		hasLiked = getInt(rs, "has_liked");
		hasCollected = getInt(rs, "has_collected");
		hasThumbuped = getInt(rs, "has_thumbuped");
		thumbupNum = getInt(rs, "thumbup_num");
		gallery = getString(rs, "gally");
	}
    
}
