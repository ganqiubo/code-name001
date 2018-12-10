package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UserMessageDao;
import com.pojul.fastIM.entity.Message;
import com.pojul.fastIM.message.request.HistoryChatReq;
import com.pojul.fastIM.message.response.HistoryChatResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class HistoryChatProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		HistoryChatReq historyChatReq = (HistoryChatReq)request;
		List<Message> messages = new UserMessageDao().getHistoryChat(historyChatReq.getLastSendTime(), 
				historyChatReq.getRoomUid(), historyChatReq.getNum());
		HistoryChatResp response = new HistoryChatResp();
		response.setMessageUid(request.getMessageUid());
		if(messages == null) {
			response.setCode(100);
			response.setMessage("系统错误");
		}else if(messages.size() <= 0) {
			response.setCode(201);
			response.setMessage("未查询到数据");
		}else {
			response.setCode(200);
			response.setMessage("sucesses");
			response.setMessages(messages);
		}
		clientSocket.sendData(response);
	}

}
