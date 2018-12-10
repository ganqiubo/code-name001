package com.pojul.fastIM.message.other;

import com.pojul.objectsocket.message.BaseMessage;
import java.sql.ResultSet;

public class NotifyChatClosed extends BaseMessage{

    private String chatUid;

    public String getChatUid() {
        return chatUid;
    }

    public void setChatUid(String chatUid) {
        this.chatUid = chatUid;
    }

	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		chatUid = getString(rs, "chat_uid");
	}
    
}
