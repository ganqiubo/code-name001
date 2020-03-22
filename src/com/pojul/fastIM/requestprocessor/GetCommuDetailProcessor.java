package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.entity.CommunityRoom;
import com.pojul.fastIM.message.request.GetCommuDetailReq;
import com.pojul.fastIM.message.response.GetCommuDetailResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetCommuDetailProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetCommuDetailReq req = (GetCommuDetailReq) request;
		GetCommuDetailResp response = new GetCommuDetailResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getCommuUid() == null || 
				req.getCommuUid().isEmpty()) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		CommunityRoom communityRoom = new CommunityRoomDao().getCommuDeatil(req.getCommuUid(), req.getFrom());
		if(communityRoom == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setRoom(communityRoom);
			clientSocket.sendData(response);
		}
	}

}
