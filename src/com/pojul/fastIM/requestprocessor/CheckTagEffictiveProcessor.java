package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.CommunityMessEntityDao;
import com.pojul.fastIM.message.request.CheckTagEffictiveReq;
import com.pojul.fastIM.message.response.CheckTagEffictiveResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class CheckTagEffictiveProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		CheckTagEffictiveReq req = (CheckTagEffictiveReq) request;
		CheckTagEffictiveResp response = new CheckTagEffictiveResp();
		response.setMessageUid(req.getMessageUid());
		
		if(req.getTagMessUid() == null || req.getFrom() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		int result = new CommunityMessEntityDao().checkTagEffictive(req.getTagMessUid());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("sucess");
			response.setEffictive(result);
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
		
	}

}
