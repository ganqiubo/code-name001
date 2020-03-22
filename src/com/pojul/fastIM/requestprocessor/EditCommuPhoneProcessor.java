package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.message.request.EditCommuPhoneReq;
import com.pojul.fastIM.message.response.EditCommuPhoneResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class EditCommuPhoneProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		EditCommuPhoneReq req = (EditCommuPhoneReq) request;
		EditCommuPhoneResp response = new EditCommuPhoneResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getPhone() == null 
				|| req.getPhone().isEmpty()) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new CommunityRoomDao().editCommuPhone(req.getRoomUid(), req.getPhone());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("success");
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}	
	}

}
