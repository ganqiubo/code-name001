package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.message.request.UpdateUserPhotoReq;
import com.pojul.fastIM.message.response.UpdateUserPhotoResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.utils.Constant;

public class UpdateUserPhotoProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		UpdateUserPhotoReq req = (UpdateUserPhotoReq) request;
		UpdateUserPhotoResp response = new UpdateUserPhotoResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new UserDao().updateUserPhoto(req.getStringFile().getFilePath(),
				req.getPhotoType(), req.getFrom(), req.getRawPhotoName());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("sucess");
			response.setPhotoPath((Constant.BASE_URL + req.getStringFile().getFilePath()));
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
	}

}
