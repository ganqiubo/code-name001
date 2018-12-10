package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.entity.CommunityRoom;
import com.pojul.fastIM.message.request.GetCommunRoomByMessReq;
import com.pojul.fastIM.message.response.GetCommunRoomByMessResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetCommunRoomByMessProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetCommunRoomByMessReq req = (GetCommunRoomByMessReq) request;
		GetCommunRoomByMessResp response = new GetCommunRoomByMessResp();
		response.setMessageUid(req.getMessageUid());
		
		if(req.getCommunMessUid() == null || req.getFrom() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		CommunityRoom communityRoom = new CommunityRoomDao().getChatRoomByMessage(req.getCommunMessUid());
		if(communityRoom != null) {
			response.setCode(200);
			response.setMessage("sucess");
			response.setCommunityRoom(communityRoom);
			clientSocket.sendData(response);
			return;
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
	}

}
