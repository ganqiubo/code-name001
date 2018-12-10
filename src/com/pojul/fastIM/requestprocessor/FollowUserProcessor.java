package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.FollowUserDao;
import com.pojul.fastIM.message.request.FollowUserReq;
import com.pojul.fastIM.message.response.FollowUserResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class FollowUserProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		FollowUserReq req = (FollowUserReq) request;
		FollowUserResp response = new FollowUserResp();
		response.setMessageUid(req.getMessageUid());

		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new FollowUserDao().followUser(req.getFrom(), req.getFollowedUserName(), req.getType());
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
