package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.RecommendDao;
import com.pojul.fastIM.entity.CommunityMessEntity;
import com.pojul.fastIM.message.request.GetRecomdMessReq;
import com.pojul.fastIM.message.request.GetUsersByNickReq;
import com.pojul.fastIM.message.response.GetRecomdMessResp;
import com.pojul.fastIM.message.response.GetUsersByNickResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetRecomdMessProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		GetRecomdMessReq req = (GetRecomdMessReq) request;
		GetRecomdMessResp response = new GetRecomdMessResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		List<CommunityMessEntity> communityMessEntities = new RecommendDao().getRecomdMess(req.getFrom());
		if(communityMessEntities == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setCommunityMessEntities(communityMessEntities);
			clientSocket.sendData(response);
		}
		
	}

}
