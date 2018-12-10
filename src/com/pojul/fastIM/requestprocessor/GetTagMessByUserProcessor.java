package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.CommunityMessEntityDao;
import com.pojul.fastIM.entity.CommunityMessEntity;
import com.pojul.fastIM.message.request.GetTagMessByUserReq;
import com.pojul.fastIM.message.response.GetTagMessByUserResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetTagMessByUserProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		GetTagMessByUserReq req = (GetTagMessByUserReq) request;
		GetTagMessByUserResp response = new GetTagMessByUserResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		List<CommunityMessEntity> communityMessEntities = new CommunityMessEntityDao().getTagMessByUser(
				req.getFrom(), req.getUserName(), req.getNum(), req.getLastTagMessid());
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
