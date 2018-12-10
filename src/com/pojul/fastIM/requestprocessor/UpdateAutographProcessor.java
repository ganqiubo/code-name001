package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.message.request.UpdateAutographReq;
import com.pojul.fastIM.message.response.UpdateAutographResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class UpdateAutographProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		UpdateAutographReq req = (UpdateAutographReq) request;
		UpdateAutographResp response = new UpdateAutographResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getAutograph() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new UserDao().updateAutograph(req.getAutograph(), req.getFrom());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("sucess");
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
	}

}
