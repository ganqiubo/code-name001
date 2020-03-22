package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.MakeQRCodeDao;
import com.pojul.fastIM.dao.UserDao;
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
			String[] userInfos = new UserDao().createCommunManager(req.getRoomUid());
			if(userInfos != null && userInfos.length == 2) {
				response.setAccount(userInfos[0]);
				response.setPasswd(userInfos[1]);
				response.setCode(200);
				response.setMessage("success");
				clientSocket.sendData(response);
			}else {
				response.setCode(101);
				response.setMessage("invilid");
				clientSocket.sendData(response);
			}
		}else {
			response.setCode(101);
			response.setMessage("invilid code");
			clientSocket.sendData(response);
		}
	}

}
