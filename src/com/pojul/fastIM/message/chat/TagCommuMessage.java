package com.pojul.fastIM.message.chat;

import com.pojul.fastIM.entity.UserFilter;
import com.pojul.fastIM.entity.Pic;

import java.util.List;

public class TagCommuMessage extends CommunityMessage{

    private String title;
    private String text;
    private List<Pic> pics;
    private List<String> labels;
    private List<ReplyMessage> replys;
    private int thumbsUps;
    private int isEffective;
    private UserFilter userFilter;
    private int hsaThumbsUp;
    private int hasReport;
    private boolean isManagerNotrify;
    private int level;

    public List<ReplyMessage> getReplys() {
        return replys;
    }

    public void setReplys(List<ReplyMessage> replys) {
        this.replys = replys;
    }

    public int getThumbsUps() {
        return thumbsUps;
    }

    public void setThumbsUps(int thumbsUps) {
        this.thumbsUps = thumbsUps;
    }

    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    public UserFilter getUserFilter() {
        return userFilter;
    }

    public void setUserFilter(UserFilter userFilter) {
        this.userFilter = userFilter;
    }

    public int getHsaThumbsUp() {
        return hsaThumbsUp;
    }

    public void setHsaThumbsUp(int hsaThumbsUp) {
        this.hsaThumbsUp = hsaThumbsUp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Pic> getPics() {
        return pics;
    }

    public void setPics(List<Pic> pics) {
        this.pics = pics;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public int getHasReport() {
        return hasReport;
    }

    public void setHasReport(int hasReport) {
        this.hasReport = hasReport;
    }
    
    
    public boolean isManagerNotrify() {
		return isManagerNotrify;
	}

	public void setManagerNotrify(boolean isManagerNotrify) {
		this.isManagerNotrify = isManagerNotrify;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
    public ChatMessage getContent() {
        return this;
    }
}
