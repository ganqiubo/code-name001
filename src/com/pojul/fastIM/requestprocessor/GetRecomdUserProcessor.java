package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.RecommendDao;
import com.pojul.fastIM.entity.LocUser;
import com.pojul.fastIM.message.request.GetRecomdUserReq;
import com.pojul.fastIM.message.response.GetRecomdUserResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetRecomdUserProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetRecomdUserReq req = (GetRecomdUserReq) request;
		GetRecomdUserResp response = new GetRecomdUserResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		List<LocUser> locUsers = new RecommendDao().getRecomdUser(req.getFrom());
		if(locUsers == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("sucess");
			response.setRecomdUsers(locUsers);
			clientSocket.sendData(response);
		}
	}

}
