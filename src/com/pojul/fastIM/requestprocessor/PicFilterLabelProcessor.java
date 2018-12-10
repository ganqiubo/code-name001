package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UploadPicDao;
import com.pojul.fastIM.message.request.PicFilterLabelReq;
import com.pojul.fastIM.message.response.PicFilterLabelResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class PicFilterLabelProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		PicFilterLabelReq req = (PicFilterLabelReq) request;
		PicFilterLabelResp response = new PicFilterLabelResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		List<String> labels = new UploadPicDao().picFilterLabels();
		if(labels != null) {
			response.setCode(200);
			response.setMessage("success");
			response.setLabels(labels);
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
		
	}

}
