package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.ReplyMessageDao;
import com.pojul.fastIM.message.request.ReplyMessReq;
import com.pojul.fastIM.message.response.ReplyMessReqResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class ReplyMessReqProcessor implements RequestProcessor {

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		ReplyMessReq req = (ReplyMessReq) request;
		ReplyMessReqResp response = new ReplyMessReqResp();
		response.setMessageUid(req.getMessageUid());
		
		if(req.getFrom() == null || req.getReplyMessUid() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		long result = -1;
		if(req.getReqCode() == 1) {
			result = new ReplyMessageDao().replyMessReq(req.getFrom(), req.getReplyMessUid());
		}else if(req.getReqCode() == 2) {
			result = new ReplyMessageDao().replyMessUnReq(req.getFrom());
		}
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("sucess");
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
	}
	
}