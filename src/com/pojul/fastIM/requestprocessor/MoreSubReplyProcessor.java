package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.MoreSubReplyDao;
import com.pojul.fastIM.entity.MoreSubReply;
import com.pojul.fastIM.message.request.MoreSubReplyReq;
import com.pojul.fastIM.message.response.MoreSubReplyResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class MoreSubReplyProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		MoreSubReplyReq req = (MoreSubReplyReq) request;
		MoreSubReplyResp response = new MoreSubReplyResp();
		response.setMessageUid(req.getMessageUid());
		if(req.getReplyMessUid() == null || req.getFrom() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		List<MoreSubReply> moreSubReplies = new MoreSubReplyDao()
				.getMoreSubReply(req.getReplyMessUid(), req.getLastMilli(), req.getNum());
		if(moreSubReplies == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		response.setCode(200);
		response.setMessage("success");
		response.setMoreSubReplies(moreSubReplies);
		clientSocket.sendData(response);
	}

}
