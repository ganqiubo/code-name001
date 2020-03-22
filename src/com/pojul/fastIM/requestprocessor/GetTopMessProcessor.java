package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.CommunityMessEntityDao;
import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.entity.CommunityMessEntity;
import com.pojul.fastIM.entity.CommunityRoom;
import com.pojul.fastIM.message.request.GetTopMessReq;
import com.pojul.fastIM.message.response.GetTopMessResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetTopMessProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		GetTopMessReq req = (GetTopMessReq) request;
		GetTopMessResp response = new GetTopMessResp();
		response.setMessageUid(req.getMessageUid());
		
		List<CommunityMessEntity> communityMessEntities = new CommunityMessEntityDao()
				.getTopMess(req.getCommunityUid(), req.getNum());
		
		if(communityMessEntities == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("sucess");
			response.setCommunityMessEntities(communityMessEntities);
			clientSocket.sendData(response);

		}
	}

}
