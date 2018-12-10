package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.ExtendUser;
import com.pojul.fastIM.message.request.LikedUsersReq;
import com.pojul.fastIM.message.response.LikedUsersResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class LikedUsersProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		LikedUsersReq req = (LikedUsersReq) request;
		LikedUsersResp response = new LikedUsersResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		UserDao userDao = new UserDao();
		List<ExtendUser> extendUsers = userDao.likedUses(req.getUserName(), req.getNum(), req.getLastLikedId());
		if(extendUsers == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setExtendUsers(extendUsers);
			if(req.getLastLikedId() < 0) {
				response.setBeLikedCount(userDao.beLikedUserCount(req.getUserName()));
			}
			clientSocket.sendData(response);
		}
	}

}
