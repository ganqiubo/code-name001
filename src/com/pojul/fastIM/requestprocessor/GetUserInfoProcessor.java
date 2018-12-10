package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.message.request.GetUserInfoReq;
import com.pojul.fastIM.message.response.GetUserInfoResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetUserInfoProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetUserInfoReq req = (GetUserInfoReq) request;
		GetUserInfoResp response = new GetUserInfoResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || 
				 (req.getUserId() == -1 && req.getUserName() == null)) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		User user = null;
		if(req.getUserId() >= 0) {
			user  = new UserDao().getUserInfo(req.getUserId());
		}else {
			user  = new UserDao().getUserInfo(req.getUserName());
		}
		
		if(user != null) {
			response.setCode(200);
			response.setMessage("success");
			response.setUser(user);
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
		
	}

}
