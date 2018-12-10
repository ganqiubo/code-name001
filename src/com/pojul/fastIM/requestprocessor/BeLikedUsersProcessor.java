package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.ExtendUser;
import com.pojul.fastIM.message.request.BeLikedUsersReq;
import com.pojul.fastIM.message.response.BeLikedUsersResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class BeLikedUsersProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		BeLikedUsersReq req = (BeLikedUsersReq) request;
		BeLikedUsersResp response = new BeLikedUsersResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		List<ExtendUser> extendUsers = new UserDao().beLikedUses(req.getUserName(), req.getNum(), req.getLastLikedId());
		if(extendUsers == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setExtendUsers(extendUsers);
			clientSocket.sendData(response);
		}
		
	}

}
