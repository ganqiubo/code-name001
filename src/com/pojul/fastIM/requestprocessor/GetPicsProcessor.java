package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UploadPicDao;
import com.pojul.fastIM.entity.ExtendUploadPic;
import com.pojul.fastIM.entity.UploadPic;
import com.pojul.fastIM.message.request.GetPicsReq;
import com.pojul.fastIM.message.response.GetPicsResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetPicsProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		GetPicsReq req = (GetPicsReq) request;
		GetPicsResp response = new GetPicsResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getPicFilter() == null
				|| req.getPicFilter().getGallery() == null || !"脚步".equals(req.getPicFilter().getGallery())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		List<ExtendUploadPic> uploadPics = new UploadPicDao().getPics(req.getFromId(), req.getPicFilter(), 
				req.getStartNum(), req.getNum(), req.isChoice());
		if(uploadPics != null) {
			response.setCode(200);
			response.setMessage("success");
			response.setUploadPics(uploadPics);
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}

	}

}
