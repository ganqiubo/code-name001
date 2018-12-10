package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class LikeFollowInfo extends BaseEntity{
	
	private int likeCount;
	private int followCount;
	
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getFollowCount() {
		return followCount;
	}
	public void setFollowCount(int followCount) {
		this.followCount = followCount;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		likeCount = getInt(rs, "like_count");
		followCount = getInt(rs, "follow_count");
	}

}
