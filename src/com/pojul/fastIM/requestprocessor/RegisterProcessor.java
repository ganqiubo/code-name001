package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.message.request.RegisterReq;
import com.pojul.fastIM.message.response.RegisterResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class RegisterProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		RegisterReq req = (RegisterReq) request;
		RegisterResp response = new RegisterResp();
		response.setMessageUid(req.getMessageUid());
		
		if(req.getFrom() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendDataAndClose(response);
			return;
		}
		
		String result = new UserDao().register(req.getNickName(), req.getImei(), req.getImsi(), req.getBirthday(), 
				req.getBirthdayType(), req.getSex(), req.getPasswd());
		if("success".equals(result)) {
			response.setCode(200);
			response.setMessage("success");
			clientSocket.sendDataAndClose(response);
		}else {
			response.setCode(101);
			response.setMessage(result);
			clientSocket.sendDataAndClose(response);
		}
		
	}

}
