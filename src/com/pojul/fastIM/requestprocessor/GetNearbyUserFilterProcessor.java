package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.NearByPeopleDao;
import com.pojul.fastIM.entity.NearbyUserFilter;
import com.pojul.fastIM.message.request.GetNearbyUserFilterReq;
import com.pojul.fastIM.message.response.GetNearbyUserFilterResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetNearbyUserFilterProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetNearbyUserFilterReq req = (GetNearbyUserFilterReq) request;
		GetNearbyUserFilterResp response = new GetNearbyUserFilterResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		NearbyUserFilter filter = new NearByPeopleDao().getNearbyUserFilter(req.getFrom());
		if(filter == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setFilter(filter);
			clientSocket.sendData(response);
		}
		
	}

}
