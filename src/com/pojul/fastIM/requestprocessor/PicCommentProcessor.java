package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.PicCommentDao;
import com.pojul.fastIM.message.request.PicCommentReq;
import com.pojul.fastIM.message.response.PicCommentResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class PicCommentProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		PicCommentReq req = (PicCommentReq) request;
		PicCommentResp response = new PicCommentResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getUploadPicId() == null 
				|| req.getUploadPicId().isEmpty() || req.getText() == null || req.getText().isEmpty() 
				|| req.getGallery() == null || req.getGallery().isEmpty()) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		long result = new PicCommentDao().picComment(req.getFrom(), 
				req.getUploadPicId(), 
				req.getText(), 
				req.getLevel(), 
				req.getOneLevelId(), 
				req.getGallery());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("success");
			response.setInsertId(result);
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);	
		}
	}

}
