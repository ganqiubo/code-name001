package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.CommunThumbUpDao;
import com.pojul.fastIM.message.request.CommunThumbReq;
import com.pojul.fastIM.message.request.CommunityMessageReq;
import com.pojul.fastIM.message.response.CommunThumbResp;
import com.pojul.fastIM.message.response.CommunityMessageResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

public class CommunThumbProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		CommunThumbReq req = (CommunThumbReq) request;
		CommunThumbResp response = new CommunThumbResp();
		response.setMessageUid(req.getMessageUid());
		
		if(req.getCommunMessageUid() == null || req.getFrom() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		long result = new CommunThumbUpDao().insertThumbUp(req.getCommunMessageUid(), req.getFrom());
		if(result >= 0) {
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

}
