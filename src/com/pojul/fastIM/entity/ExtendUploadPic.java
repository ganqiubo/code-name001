package com.pojul.fastIM.entity;

import java.security.KeyStore.PrivateKeyEntry;
import java.sql.ResultSet;
import java.util.List;

import com.pojul.objectsocket.utils.Constant;

public class ExtendUploadPic extends UploadPic{

    private String galleryType;
    private int sex;
    private String photo;
    private String userName;
    private String nickName;
    private int age;
    private List<PicComment> picComments;
    private int comments;

    public String getGalleryType() {
        return galleryType;
    }

    public void setGalleryType(String galleryType) {
        this.galleryType = galleryType;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

	public List<PicComment> getPicComments() {
		return picComments;
	}

	public void setPicComments(List<PicComment> picComments) {
		this.picComments = picComments;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		sex = getInt(rs, "sex");
		photo = getString(rs, "photo");
		if(photo != null && !photo.isEmpty()) {
			photo = Constant.BASE_URL + photo;
		}
		userName = getString(rs, "user_name");
		nickName = getString(rs, "nick_name");
		age = getInt(rs, "age");
		comments = getInt(rs, "comments");
	}
    
}
