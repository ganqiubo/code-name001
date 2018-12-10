package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.CommunityMessEntityDao;
import com.pojul.fastIM.entity.CommunityMessEntity;
import com.pojul.fastIM.message.request.GetTagMessByUidReq;
import com.pojul.fastIM.message.response.GetTagMessByUidResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetTagMessByUidProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		GetTagMessByUidReq req = (GetTagMessByUidReq) request;
		GetTagMessByUidResp response = new GetTagMessByUidResp();
		response.setMessageUid(req.getMessageUid());
		
		CommunityMessEntity communityMessEntity = new CommunityMessEntityDao().getTagMessByUid(req.getTagMessUid());
		if(communityMessEntity == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("sucess");
			response.setCommunityMessEntity(communityMessEntity);
			clientSocket.sendData(response);
		}
	
	}

}
