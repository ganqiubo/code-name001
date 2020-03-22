package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.message.request.FellowCommuReq;
import com.pojul.fastIM.message.request.FollowedUsersReq;
import com.pojul.fastIM.message.response.FellowCommuResp;
import com.pojul.fastIM.message.response.FollowedUsersResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class FellowCommuProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		FellowCommuReq req = (FellowCommuReq) request;
		FellowCommuResp response = new FellowCommuResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getRoomUid() == null 
				|| req.getRoomUid().isEmpty()) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		long result = -1;
		if(req.getMode() == 0) {
			result = new CommunityRoomDao().followCommu(req.getFrom(), req.getRoomUid());
		} else {
			result = new CommunityRoomDao().cancelFollowCommu(req.getFrom(), req.getRoomUid());
		}
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("success");
			clientSocket.sendData(response);
		} else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
		
	}

}
