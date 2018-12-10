package com.pojul.fastIM.message.response;

import com.pojul.fastIM.entity.Message;
import com.pojul.objectsocket.message.ResponseMessage;

import java.util.List;

public class HistoryChatResp extends ResponseMessage {

    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
