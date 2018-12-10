package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.LikeUserDao;
import com.pojul.fastIM.message.request.LikeUserReq;
import com.pojul.fastIM.message.response.LikeUserResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class LikeUserProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		LikeUserReq req = (LikeUserReq) request;
		LikeUserResp response = new LikeUserResp();
		response.setMessageUid(req.getMessageUid());

		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new LikeUserDao().likeUser(req.getFrom(), req.getLikedUserName(), req.getType());
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
