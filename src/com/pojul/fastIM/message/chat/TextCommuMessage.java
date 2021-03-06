package com.pojul.fastIM.message.chat;

import com.pojul.fastIM.entity.Filter;
import com.pojul.fastIM.entity.Pic;

import java.util.List;

public class TextCommuMessage extends CommunityMessage{

    private TextChatMessage textChatMessage;

    public TextChatMessage getTextChatMessage() {
        return textChatMessage;
    }

    public void setTextChatMessage(TextChatMessage textChatMessage) {
        this.textChatMessage = textChatMessage;
    }

    @Override
    public ChatMessage getContent() {
        return textChatMessage;
    }

    @Override
    public void setContent(ChatMessage chatMessage) {
        this.textChatMessage = (TextChatMessage) chatMessage;
    }

}
