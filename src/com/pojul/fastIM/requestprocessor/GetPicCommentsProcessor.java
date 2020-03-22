package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.PicCommentDao;
import com.pojul.fastIM.entity.PicComment;
import com.pojul.fastIM.message.request.GetPicCommentsReq;
import com.pojul.fastIM.message.response.GetPicCommentsResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetPicCommentsProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetPicCommentsReq req = (GetPicCommentsReq) request;
		GetPicCommentsResp response = new GetPicCommentsResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getPicId() == null ||
				req.getPicId().isEmpty()) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		PicCommentDao picCommentDao = new PicCommentDao();
		List<PicComment> picComments = picCommentDao.getComments(req.getPage(), 
				req.getPicId(), req.getNum(), req.getFrom());
		if(picComments == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			long commentNum = picCommentDao.getCommentNum(req.getPicId());
			for (int i = 0; i < picComments.size(); i++) {
				PicComment picComment = picComments.get(i);
				List<PicComment> subComments =  picCommentDao.getTopSubComment(picComment.getId());
				picComment.setSubComments(subComments);
			}
			response.setTotalComments(commentNum);
			response.setCode(200);
			response.setMessage("success");
			response.setPicComments(picComments);
			clientSocket.sendData(response);
		}
	}

}
