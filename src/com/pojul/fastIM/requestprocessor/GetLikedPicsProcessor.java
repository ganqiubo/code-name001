package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UploadPicDao;
import com.pojul.fastIM.entity.SimpleUploadPic;
import com.pojul.fastIM.message.request.GetLikedPicsReq;
import com.pojul.fastIM.message.response.GetLikedPicsResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetLikedPicsProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		GetLikedPicsReq req = (GetLikedPicsReq) request;
		GetLikedPicsResp response = new GetLikedPicsResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getGallery() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		List<SimpleUploadPic> simpleUploadPics = new UploadPicDao().getLikedPics(req.getGallery(), req.getNum(), req.getStartNum(), req.getFrom(), req.getUserId());
		if(simpleUploadPics == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);		
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setSimpleUploadPics(simpleUploadPics);
			clientSocket.sendData(response);
		}
		
	}

}
