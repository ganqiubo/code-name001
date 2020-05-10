package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.UploadPicDao;
import com.pojul.fastIM.message.request.CollectUploadPicReq;
import com.pojul.fastIM.message.response.CollectUploadPicResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class CollectUploadPicProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		CollectUploadPicReq req = (CollectUploadPicReq) request;
		CollectUploadPicResp response = new CollectUploadPicResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		if(!"脚步".equals(req.getGallery()) && !"unsplash".equals(req.getGallery()) && !"pexels".equals(req.getGallery())
				&& !"pixabay".equals(req.getGallery())) {
			response.setCode(101);
			response.setMessage("不支持图库类型");
			clientSocket.sendData(response);
			return;

		}
		long result = -1;
		switch (req.getGallery()) {
		case "脚步":
			result = new UploadPicDao().collectUploadPic(req.getCollectUserId(), req.getUploadPicId(), req.getType());
			break;
		case "unsplash":
		case "pixabay":
			result = new UploadPicDao().collectUploadPicUnsplash(req.getFrom(),
					req.getUrl(), req.getUid(), req.getType(), req.getGallery());
			break;
		case "pexels":
			result = new UploadPicDao().collectUploadPicPexels(req.getFrom(),
					req.getUrl(), req.getType());
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
