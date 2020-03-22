package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.message.request.EditCommuPhotoReq;
import com.pojul.fastIM.message.response.EditCommuPhotoResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.utils.Constant;

public class EditCommuPhotoProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		EditCommuPhotoReq req = (EditCommuPhotoReq) request;
		EditCommuPhotoResp response = new EditCommuPhotoResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new CommunityRoomDao().editCommuPhoto(req.getRoomUid(), req.getPhoto().getFilePath(), req.getRawName());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("sucess");
			response.setPhotoPath((Constant.BASE_URL + req.getPhoto().getFilePath()));
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
	}

}
