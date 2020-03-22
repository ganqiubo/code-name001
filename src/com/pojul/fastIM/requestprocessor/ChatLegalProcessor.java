package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.AddFriendDao;
import com.pojul.fastIM.dao.ChatRoomDao;
import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.message.request.ChatLegalReq;
import com.pojul.fastIM.message.response.ChatLegalResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class ChatLegalProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		ChatLegalReq req = (ChatLegalReq) request;
		ChatLegalResp response = new ChatLegalResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		UserDao userDao = new UserDao();
		if(userDao.isCommunityManager(req.getUserNameOwn()) || 
				userDao.isCommunityManager(req.getUserNameFriend())) {
			User friend = userDao.getUserInfo(req.getUserNameFriend());
			response.setCode(200);
			response.setMessage("success");
			response.setLegal(1);
			response.setUser(friend);
			clientSocket.sendData(response);
			return;
		}
		
		int legal = new AddFriendDao().chatLegal(req.getUserNameOwn(), req.getUserNameFriend());
		if(legal == 3) {
			response.setCode(200);
			response.setMessage("success");
			response.setLegal(3);
			clientSocket.sendData(response);
			return;
		}
		User friend = userDao.getUserInfo(req.getUserNameFriend());
		response.setCode(200);
		response.setMessage("success");
		response.setLegal(legal);
		response.setUser(friend);
		clientSocket.sendData(response);
	}

}
