package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class SimpleUploadPic extends BaseEntity{

    private long id;
    private String uid;
    private String picsStr;
    private int hasThubmUp;
    private int hasLiked;
    private int hasCollected;
    private String gallery;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPicsStr() {
        return picsStr;
    }

    public void setPicsStr(String picsStr) {
        this.picsStr = picsStr;
    }

    public int getHasThubmUp() {
        return hasThubmUp;
    }

    public void setHasThubmUp(int hasThubmUp) {
        this.hasThubmUp = hasThubmUp;
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

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		id = getLong(rs, "id");
		uid = getString(rs, "pic_uid");
		picsStr = getString(rs, "pics_str");
		hasThubmUp = getInt(rs, "has_thubm_up");
		hasLiked = getInt(rs, "has_liked");
		hasCollected = getInt(rs, "has_collected");
		gallery = getString(rs, "gallery");
	}
    
}
