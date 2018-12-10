package com.pojul.fastIM.requestprocessor;
import java.util.List;

import com.pojul.fastIM.dao.ReplyMessageDao;
import com.pojul.fastIM.message.chat.ReplyMessage;
import com.pojul.fastIM.message.request.GetReplysReq;
import com.pojul.fastIM.message.response.GetReplysResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetReplysProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		GetReplysReq req = (GetReplysReq) request;
		GetReplysResp response = new GetReplysResp();
		response.setMessageUid(req.getMessageUid());
		if(req.getFrom() == null || req.getReplyTagMessUid() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		List<ReplyMessage> replyMessages = new ReplyMessageDao().getReplys(req.getLastMessUid(),
				req.getReplyTagMessUid(), req.getNum());
		if(replyMessages == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		if(replyMessages.size() <= 0) {
			response.setCode(201);
			response.setMessage("为查询到数据");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("sucess");
			response.setReplyMessages(replyMessages);
			clientSocket.sendData(response);
		}
	}

}
