package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.NearByPeopleDao;
import com.pojul.fastIM.entity.LocUser;
import com.pojul.fastIM.message.request.GetNearByPeopleReq;
import com.pojul.fastIM.message.response.GetNearByPeopleResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetNearByPeopleProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		GetNearByPeopleReq req = (GetNearByPeopleReq) request;
		GetNearByPeopleResp response = new GetNearByPeopleResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getLatLonRange() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		List<LocUser> locUsers = new NearByPeopleDao().getNearByPeople(req.getUserSelectFilter(), req.getNum(), req.getStartNum(), req.getLatLonRange());
		if(locUsers == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setLocUsers(locUsers);
			clientSocket.sendData(response);
		}
		
	}

}
