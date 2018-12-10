package com.pojul.fastIM.entity;

import java.sql.ResultSet;

import com.pojul.objectsocket.utils.Constant;

public class MoreSubReply extends BaseEntity{

    private String photo;
    private String userName;
    private String nickName;
    private int certificate;
    private int sex;
    private long timeMilli;
    private String text;

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

    public int getCertificate() {
        return certificate;
    }

    public void setCertificate(int certificate) {
        this.certificate = certificate;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getTimeMilli() {
        return timeMilli;
    }

    public void setTimeMilli(long timeMilli) {
        this.timeMilli = timeMilli;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		photo = getString(rs, "photo");
		photo = Constant.BASE_URL + photo;
		userName = getString(rs, "user_name");
		nickName = getString(rs, "nick_name");
		certificate = getInt(rs, "certificate");
		sex = getInt(rs, "sex");
		text = getString(rs, "text");
		timeMilli = getLong(rs, "time_milli");
	}

}
