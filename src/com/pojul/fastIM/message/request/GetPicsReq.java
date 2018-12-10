package com.pojul.fastIM.message.request;

import com.pojul.fastIM.entity.PicFilter;
import com.pojul.objectsocket.message.RequestMessage;

public class GetPicsReq extends RequestMessage{

    private PicFilter picFilter;
    private double lastDistance;
    private int num;
    private long startNum;
    private long fromId;
    private boolean isChoice;

    public GetPicsReq() {
        super();
        setRequestUrl("GetPicsReq");
    }

    public PicFilter getPicFilter() {
        return picFilter;
    }

    public void setPicFilter(PicFilter picFilter) {
        this.picFilter = picFilter;
    }

    public double getLastDistance() {
        return lastDistance;
    }

    public void setLastDistance(double lastDistance) {
        this.lastDistance = lastDistance;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getStartNum() {
		return startNum;
	}

	public void setStartNum(long startNum) {
		this.startNum = startNum;
	}

	public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }
}
