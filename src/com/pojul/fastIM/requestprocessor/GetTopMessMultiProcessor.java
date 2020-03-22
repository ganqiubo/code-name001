package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.CommunityMessEntityDao;
import com.pojul.fastIM.entity.CommunityMessEntity;
import com.pojul.fastIM.entity.CommunityRoom;
import com.pojul.fastIM.message.request.GetTopMessMultiReq;
import com.pojul.fastIM.message.response.GetTopMessMultiResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetTopMessMultiProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetTopMessMultiReq req = (GetTopMessMultiReq) request;
		GetTopMessMultiResp response = new GetTopMessMultiResp();
		response.setMessageUid(req.getMessageUid());
		
		CommunityMessEntityDao dao = new CommunityMessEntityDao();
		List<CommunityMessEntity> communityMessEntities = dao.getTopMessMulti(req.getCommunityUids(), req.getNum());
		List<CommunityRoom> communityRooms = dao.getCommunityRooms(req.getCommunityUids());
		if(communityMessEntities == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("sucess");
			response.setCommunityMessEntities(communityMessEntities);
			response.setCommunityRooms(communityRooms);
			clientSocket.sendData(response);

		}

	}

}
