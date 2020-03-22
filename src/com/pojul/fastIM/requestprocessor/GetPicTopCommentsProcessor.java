package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.PicCommentDao;
import com.pojul.fastIM.entity.PicComment;
import com.pojul.fastIM.message.request.GetPicTopCommentsReq;
import com.pojul.fastIM.message.response.GetPicTopCommentsResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetPicTopCommentsProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetPicTopCommentsReq req = (GetPicTopCommentsReq) request;
		GetPicTopCommentsResp response = new GetPicTopCommentsResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getPicId() == null || 
				req.getPicId().isEmpty()) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		List<PicComment> picComments = new PicCommentDao().getTop3Comments(req.getPicId(), req.getFrom());
		if(picComments == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else{
			response.setCode(200);
			response.setMessage("success");
			response.setPicComments(picComments);
			clientSocket.sendData(response);
		}
	}

}
