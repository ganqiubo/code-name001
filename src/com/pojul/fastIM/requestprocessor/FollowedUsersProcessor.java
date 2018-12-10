package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.ExtendUser;
import com.pojul.fastIM.message.request.FollowedUsersReq;
import com.pojul.fastIM.message.response.FollowedUsersResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class FollowedUsersProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		FollowedUsersReq req = (FollowedUsersReq) request;
		FollowedUsersResp response = new FollowedUsersResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		UserDao userDao = new UserDao();
		List<ExtendUser> extendUsers = userDao.followedUses(req.getUserName(), req.getNum(), req.getLastFollowedId());
		if(extendUsers == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setExtendUsers(extendUsers);
			if(req.getLastFollowedId() < 0) {
				response.setBeFollowedCount(userDao.beFollowedUserCount(req.getUserName()));
			}
			clientSocket.sendData(response);
		}
	}

}
