package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.message.request.UpdateUserInfoReq;
import com.pojul.fastIM.message.response.UpdateUserInfoResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class UpdateUserInfoProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		UpdateUserInfoReq req = (UpdateUserInfoReq) request;
		UpdateUserInfoResp response = new UpdateUserInfoResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getUser() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new UserDao().updateUserInfo(req.getFrom(), req.getUser());
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
