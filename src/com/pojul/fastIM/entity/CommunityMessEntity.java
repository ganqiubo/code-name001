package com.pojul.fastIM.entity;

import com.google.gson.Gson;
import com.pojul.objectsocket.message.StringFile;
import com.pojul.objectsocket.utils.Constant;

import java.sql.ResultSet;

public class CommunityMessEntity extends BaseEntity{

    private long id;
    private String messageUid;
    private String communityName;
    private int age;
    private int sex;
    private int certificate;
    private String nickName;
    private StringFile photo;
    private String messageClass;
    private String messageContent;
    private long timeMill;

    private int thumbUps;
    private int isEffective;
    private int hasThumbUp;
    private int hasReport;
    private String lastReply;
    private int replyNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessageUid() {
        return messageUid;
    }

    public void setMessageUid(String messageUid) {
        this.messageUid = messageUid;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getCertificate() {
		return certificate;
	}

	public void setCertificate(int certificate) {
		this.certificate = certificate;
	}

	public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public StringFile getPhoto() {
        return photo;
    }

    public void setPhoto(StringFile photo) {
        this.photo = photo;
    }

    public String getMessageClass() {
        return messageClass;
    }

    public void setMessageClass(String messageClass) {
        this.messageClass = messageClass;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    
    public long getTimeMill() {
		return timeMill;
	}

	public void setTimeMill(long timeMill) {
		this.timeMill = timeMill;
	}

    public int getThumbUps() {
		return thumbUps;
	}

	public void setThumbUps(int thumbUps) {
		this.thumbUps = thumbUps;
	}

	public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    public int getHasThumbUp() {
		return hasThumbUp;
	}

	public void setHasThumbUp(int hasThumbUp) {
		this.hasThumbUp = hasThumbUp;
	}

	public int getHasReport() {
        return hasReport;
    }

    public void setHasReport(int hasReport) {
        this.hasReport = hasReport;
    }

    public String getLastReply() {
		return lastReply;
	}

	public void setLastReply(String lastReply) {
		this.lastReply = lastReply;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
    public String toString() {
        return new Gson().toJson(this);
    }

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		id = getLong(rs, "id");
		messageUid = getString(rs, "message_uid");
		communityName = getString(rs, "community_name");
		age = getInt(rs, "age");
		sex = getInt(rs, "sex");
		certificate = getInt(rs, "certificate");
		nickName = getString(rs, "nick_name");
		messageClass = getString(rs, "message_class");
		messageContent = getString(rs, "message_content");
		thumbUps = getInt(rs, "thumb_ups");
		isEffective = getInt(rs, "is_effective");
		hasThumbUp = getInt(rs, "has_thumb_up");
		hasReport = getInt(rs, "has_report");
		photo = getStringFile(rs, "photo");
		if(photo != null) {
			photo.setFilePath(Constant.BASE_URL + photo.getFilePath());
		}
		timeMill = getLong(rs, "time_mill");
		lastReply = getString(rs, "last_reply");
		replyNum = getInt(rs, "reply_num");
	}
}
