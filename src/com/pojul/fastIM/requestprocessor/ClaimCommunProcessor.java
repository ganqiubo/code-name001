package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.MakeQRCodeDao;
import com.pojul.fastIM.message.request.ClaimCommunReq;
import com.pojul.fastIM.message.response.ClaimCommunResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class ClaimCommunProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		ClaimCommunReq req = (ClaimCommunReq) request;
		ClaimCommunResp response = new ClaimCommunResp();
		response.setMessageUid(req.getMessageUid());
		if(!clientSocket.getChatId().equals(req.getFrom()) || 
				req.getRoomUid() == null || req.getMilli() == 0) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		MakeQRCodeDao makeQRCodeDao = new MakeQRCodeDao();
		long result = makeQRCodeDao.checkClaimCommun(req.getRoomUid(), req.getMilli());
		if(result >= 0) {
			
		}else {
			response.setCode(101);
			response.setMessage("invilid code");
			clientSocket.sendData(response);
			return;
		}
	}

}
