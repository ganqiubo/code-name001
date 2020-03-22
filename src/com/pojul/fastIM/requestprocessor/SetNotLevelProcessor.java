package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.message.request.SetNotLevelReq;
import com.pojul.fastIM.message.request.UpdateNUFilterReq;
import com.pojul.fastIM.message.response.SetNotLevelResp;
import com.pojul.fastIM.message.response.UpdateNUFilterResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class SetNotLevelProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		SetNotLevelReq req = (SetNotLevelReq) request;
		SetNotLevelResp response = new SetNotLevelResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getLevel() <= 0 
				|| req.getRoomUid() == null || req.getRoomUid().isEmpty()) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new CommunityRoomDao().setNotLevel(req.getFrom(), req.getRoomUid(), req.getLevel());
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
