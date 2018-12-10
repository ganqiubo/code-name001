package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UploadPicDao;
import com.pojul.fastIM.entity.UploadPic;
import com.pojul.fastIM.message.request.UserUploadPicReq;
import com.pojul.fastIM.message.response.UserUploadPicResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class UserUploadPicProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		UserUploadPicReq req = (UserUploadPicReq) request;
		UserUploadPicResp response = new UserUploadPicResp();
		response.setMessageUid(req.getMessageUid());

		List<UploadPic> uploadPics = new UploadPicDao().uesrUploadPic(req.getUserId(), req.getStartNum(), req.getNum());
		if(uploadPics == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		response.setCode(200);
		response.setMessage("sucesses");
		response.setUploadPics(uploadPics);
		clientSocket.sendData(response);
	}

}
