package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.ExtendUser;
import com.pojul.fastIM.message.request.BeFollowedUsersReq;
import com.pojul.fastIM.message.response.BeFollowedUsersResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class BeFollowedUsersProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		BeFollowedUsersReq req = (BeFollowedUsersReq) request;
		BeFollowedUsersResp response = new BeFollowedUsersResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		List<ExtendUser> extendUsers = new UserDao().beFollowedUses(req.getUserName(), req.getNum(), req.getLastFollowedId());
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
