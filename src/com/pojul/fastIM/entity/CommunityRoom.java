package com.pojul.fastIM.entity;


import com.google.gson.Gson;
import com.pojul.objectsocket.utils.Constant;

import java.sql.ResultSet;

public class CommunityRoom extends BaseEntity{

    private long id;
    private String communityUid;
    private String name;
    private String createDate;
    private String communityType;
    private String communitySubtype;
    private String country;
    private String province;
    private String city;
    private String district;
    private String addr;
    private double longitude;
    private double latitude;
    private double altitude;
    private double distance;
    private String photo;
    private String detail;
    private String manager;
    private String phone;
    private int hsaClaimed;
    private int follows;
    private int hasFollowed;
    private int notifyLevel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommunityUid() {
		return communityUid;
	}

	public void setCommunityUid(String communityUid) {
		this.communityUid = communityUid;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCommunityType() {
        return communityType;
    }

    public void setCommunityType(String communityType) {
        this.communityType = communityType;
    }

    public String getCommunitySubtype() {
        return communitySubtype;
    }

    public void setCommunitySubtype(String communitySubtype) {
        this.communitySubtype = communitySubtype;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

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

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getHsaClaimed() {
		return hsaClaimed;
	}

	public void setHsaClaimed(int hsaClaimed) {
		this.hsaClaimed = hsaClaimed;
	}

	@Override
    public String toString() {
        return new Gson().toJson(this);
    }

	public int getFollows() {
		return follows;
	}

	public void setFollows(int follows) {
		this.follows = follows;
	}

	public int getHasFollowed() {
		return hasFollowed;
	}

	public void setHasFollowed(int hasFollowed) {
		this.hasFollowed = hasFollowed;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		id = getLong(rs, "id");
		communityUid = getString(rs, "community_uid");
		name = getString(rs, "name");
		createDate = getString(rs, "create_date");
		communityType = getString(rs, "community_type");
		communitySubtype = getString(rs, "community_subtype");
		country = getString(rs, "country");
		province = getString(rs, "province");
		city = getString(rs, "city");
		district = getString(rs, "district");
		addr = getString(rs, "addr");
		longitude = getDouble(rs, "longitude");
		latitude = getDouble(rs, "latitude");
		altitude = getDouble(rs, "altitude");
		photo = getString(rs, "photo");
		if(photo != null && !photo.isEmpty()) {
			photo = (Constant.BASE_URL + photo);
		}
	    detail = getString(rs, "detail");
	    manager = getString(rs, "manager");
	    phone = getString(rs, "phone");
	    hsaClaimed = getInt(rs, "hsa_claimed");
	    follows = getInt(rs, "follows");
	    hasFollowed = getInt(rs, "has_followed");
	    notifyLevel = getInt(rs, "notify_level");
	}
}
