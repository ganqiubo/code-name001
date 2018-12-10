package com.pojul.fastIM.entity;

import java.sql.ResultSet;

public class Suggest extends BaseEntity{

    private long id;
    private String fromUserName;
    private String content;
    private String suggestTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSuggestTime() {
        return suggestTime;
    }

    public void setSuggestTime(String suggestTime) {
        this.suggestTime = suggestTime;
    }

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		id = getLong(rs, "id");
		fromUserName = getString(rs, "from_user_name");
		content = getString(rs, "content");
		suggestTime = getString(rs, "suggest_time");
	}
}
