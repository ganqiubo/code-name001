package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.message.request.GetUsersByNameReq;
import com.pojul.fastIM.message.response.GetUsersByNameResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetUsersByNameProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetUsersByNameReq req = (GetUsersByNameReq) request;
		GetUsersByNameResp response = new GetUsersByNameResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getUserNames() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		List<User> users = new UserDao().getusersByName(req.getUserNames());
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
