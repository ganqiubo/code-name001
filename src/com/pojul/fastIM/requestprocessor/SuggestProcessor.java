package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.SuggestDao;
import com.pojul.fastIM.message.request.SuggestReq;
import com.pojul.fastIM.message.response.SuggestResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class SuggestProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		SuggestReq req = (SuggestReq) request;
		SuggestResp response = new SuggestResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new SuggestDao().insertSuggest(req.getFrom(), req.getContent());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("sucesses");
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
	}

}
