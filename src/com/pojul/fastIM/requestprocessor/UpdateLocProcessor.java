package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.NearByPeopleDao;
import com.pojul.fastIM.message.request.UpdateLocReq;
import com.pojul.fastIM.message.request.UpdateUserInfoReq;
import com.pojul.fastIM.message.response.UpdateLocResp;
import com.pojul.fastIM.message.response.UpdateUserInfoResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class UpdateLocProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		UpdateLocReq req = (UpdateLocReq) request;
		UpdateLocResp response = new UpdateLocResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getNearByPeople() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new NearByPeopleDao().updateLoc(req.getFrom(), req.getNearByPeople());
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
