package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class ExtendUser extends User{

    private long extendId;
    private int isFriend;

    public long getExtendId() {
        return extendId;
    }

    public void setExtendId(long extendId) {
        this.extendId = extendId;
    }

	public int getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(int isFriend) {
		this.isFriend = isFriend;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		extendId = getLong(rs, "extend_id");
		isFriend = getInt(rs, "is_friend");
	}
}
