package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.message.request.EditCommuDetailReq;
import com.pojul.fastIM.message.response.EditCommuDetailResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class EditCommuDetailProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		EditCommuDetailReq req = (EditCommuDetailReq) request;
		EditCommuDetailResp response = new EditCommuDetailResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getDetail() == null 
				|| req.getDetail().isEmpty()) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new CommunityRoomDao().editCommuDetail(req.getRoomUid(), req.getDetail());
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
