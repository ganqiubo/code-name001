package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.message.request.CommunityMessageReq;
import com.pojul.fastIM.message.response.CommunityMessageResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class CommunityMessageProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		CommunityMessageReq req = (CommunityMessageReq) request;
		CommunityMessageResp response = new CommunityMessageResp();
		response.setMessageUid(req.getMessageUid());
		CommunityRoomDao dao = new CommunityRoomDao();
		long result = -1;
		if(req.getReqCode() == 1) {
			if(!dao.isCommunityExit(req.getUid())) {
				dao.insertCommunity(req.getCommunityRoom());
			}
			result = dao.communityMessageReq(req.getFrom(), req.getUid());
		}else {
			result = dao.communityMessageUnReq(req.getFrom(), req.getUid());
		}
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("sucess");
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
