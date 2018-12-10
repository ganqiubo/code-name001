package com.pojul.fastIM.entity;

import java.sql.ResultSet;

import com.pojul.objectsocket.utils.Constant;
import com.sun.org.omg.CORBA.ParDescriptionSeqHelper;

public class LocUser extends BaseEntity{

    private double longitude;
    private double latitude;
    private String userName;
    private long id;
    private String nickName;
    private String photo;
    private int sex;
    private int age;
    private String hobby;
    private String occupation;
    private String updateTime;
    private String filter;
    private UserFilter userFilter;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public UserFilter getUserFilter() {
		return userFilter;
	}

	public void setUserFilter(UserFilter userFilter) {
		this.userFilter = userFilter;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		userName = getString(rs, "user_name");
		nickName = getString(rs, "nick_name");
		photo = getString(rs, "photo");
		if(photo != null && !photo.isEmpty()) {
			photo = Constant.BASE_URL + photo;
		}
		sex = getInt(rs, "sex");
		age = getInt(rs, "age");
		hobby = getString(rs, "hobby");
		occupation = getString(rs, "occupation");
		updateTime = getString(rs, "update_time");
		longitude = getDouble(rs, "longitude");
		latitude = getDouble(rs, "latitude");
		id = getLong(rs, "id");
		filter = getString(rs, "filter");
	}
}
