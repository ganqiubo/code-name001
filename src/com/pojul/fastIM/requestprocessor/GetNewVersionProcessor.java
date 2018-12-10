package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.NewVersionDao;
import com.pojul.fastIM.entity.NewVersion;
import com.pojul.fastIM.message.request.GetNewVersionReq;
import com.pojul.fastIM.message.response.GetNewVersionResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetNewVersionProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetNewVersionReq req = (GetNewVersionReq) request;
		GetNewVersionResp response = new GetNewVersionResp();
		response.setMessageUid(req.getMessageUid());

		NewVersion newVersion = new NewVersionDao().getNewVersion();
		if(newVersion == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setNewVersion(newVersion);
			clientSocket.sendData(response);
		}
	}

}
