package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.UploadPicDao;
import com.pojul.fastIM.message.request.ThumbupUploadPicReq;
import com.pojul.fastIM.message.response.ThumbupUploadPicResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class ThumbupUploadPicProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		ThumbupUploadPicReq req = (ThumbupUploadPicReq) request;
		ThumbupUploadPicResp response = new ThumbupUploadPicResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		if(!"脚步".equals(req.getGallery()) && !"unsplash".equals(req.getGallery()) && !"pexels".equals(req.getGallery())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;

		}
		long result = -1;
		switch (req.getGallery()) {
		case "脚步":
			result = new UploadPicDao().thumbupUploadPic(req.getThumbupUpUserId(), req.getUploadPicId());
			break;
		case "unsplash":
			result = new UploadPicDao().thumbupUploadPicUnsplash(req.getFrom(),
					req.getUrl(), req.getUid());
			break;
		case "pexels":
			result = new UploadPicDao().thumbupUploadPicPexels(req.getFrom(),
					req.getUrl());
			break;
		}
		
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
