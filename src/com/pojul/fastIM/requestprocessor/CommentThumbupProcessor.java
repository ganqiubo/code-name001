package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.PicCommentDao;
import com.pojul.fastIM.message.request.CommentThumbupReq;
import com.pojul.fastIM.message.response.CommentThumbupResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class CommentThumbupProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		CommentThumbupReq req = (CommentThumbupReq) request;
		CommentThumbupResp response = new CommentThumbupResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getCommentId() == null 
				|| req.getCommentId().isEmpty()) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		long result = new PicCommentDao().thumbUpComment(req.getCommentId(), req.getFrom());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("success");
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
	}

}
