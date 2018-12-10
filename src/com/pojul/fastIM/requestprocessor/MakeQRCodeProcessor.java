package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.MakeQRCodeDao;
import com.pojul.fastIM.message.request.MakeQRCodeReq;
import com.pojul.fastIM.message.request.MoreSubReplyReq;
import com.pojul.fastIM.message.response.MakeQRCodeResp;
import com.pojul.fastIM.message.response.MoreSubReplyResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;
import com.sun.org.glassfish.external.statistics.annotations.Reset;

public class MakeQRCodeProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		MakeQRCodeReq req = (MakeQRCodeReq) request;
		MakeQRCodeResp response = new MakeQRCodeResp();
		response.setMessageUid(req.getMessageUid());
		if(!clientSocket.getChatId().equals(req.getFrom()) || !"460079878303087".equals(req.getFrom())  || 
				req.getCommunRoomUid() == null || req.getMilli() == 0) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		long result = new MakeQRCodeDao().makeQRCode(req.getCommunRoomUid(), req.getMilli());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("success");
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
	}

}
