package com.pojul.fastIM.message.other;

import java.sql.ResultSet;

import com.pojul.objectsocket.message.BaseMessage;

public class NotifyReplyMess extends BaseMessage{

    private String replyTagMessUid;
    private String replyText;
    private String tagMessTitle;
    private String replyNickName;
    private int replyType; //0: 公开; 1: 私密
    private String photo;
    private int unSendCount;

    public String getReplyTagMessUid() {
        return replyTagMessUid;
    }

    public void setReplyTagMessUid(String replyTagMessUid) {
        this.replyTagMessUid = replyTagMessUid;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getTagMessTitle() {
        return tagMessTitle;
    }

    public void setTagMessTitle(String tagMessTitle) {
        this.tagMessTitle = tagMessTitle;
    }

    public String getReplyNickName() {
        return replyNickName;
    }

    public void setReplyNickName(String replyNickName) {
        this.replyNickName = replyNickName;
    }

    public int getReplyType() {
        return replyType;
    }

    public void setReplyType(int replyType) {
        this.replyType = replyType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

	public int getUnSendCount() {
		return unSendCount;
	}

	public void setUnSendCount(int unSendCount) {
		this.unSendCount = unSendCount;
	}

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		replyTagMessUid = getString(rs, "reply_tag_mess_uid");
		replyText = getString(rs, "reply_text");
		tagMessTitle = getString(rs, "tag_mess_title");
		replyNickName = getString(rs, "reply_nick_name");
		replyType = getInt(rs, "reply_type");
		photo = getString(rs, "photo");
		from = getString(rs, "mess_from");
		to = getString(rs, "mess_to");
		sendTime = getString(rs, "send_time");
		MessageUid = getString(rs, "message_uid");
		isSend = getInt(rs, "is_send");
		unSendCount = getInt(rs, "un_send_count");
	}
    
}
