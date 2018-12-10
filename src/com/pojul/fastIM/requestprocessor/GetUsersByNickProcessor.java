package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.message.request.GetUsersByNickReq;
import com.pojul.fastIM.message.response.GetUsersByNickResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetUsersByNickProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		GetUsersByNickReq req = (GetUsersByNickReq) request;
		GetUsersByNickResp response = new GetUsersByNickResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getNickName() == null || req.getNickName().isEmpty()) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		List<User> users = new UserDao().getUsersByNickName(req.getNickName(), req.getStartNum(), req.getNum(), req.getFrom());
		if(users == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setUsers(users);
			clientSocket.sendData(response);
		}
	}

}
