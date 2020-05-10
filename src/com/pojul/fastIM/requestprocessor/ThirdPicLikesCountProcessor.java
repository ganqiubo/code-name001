package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.PicCommentDao;
import com.pojul.fastIM.dao.UploadPicDao;
import com.pojul.fastIM.entity.PicComment;
import com.pojul.fastIM.entity.ThirdPicLikes;
import com.pojul.fastIM.message.request.ThirdPicLikesCountReq;
import com.pojul.fastIM.message.response.ThirdPicLikesCountResp;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class ThirdPicLikesCountProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		ThirdPicLikesCountReq req = (ThirdPicLikesCountReq) request;
		ThirdPicLikesCountResp response = new ThirdPicLikesCountResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		if(!"unsplash".equals(req.getGallery()) && !"pexels".equals(req.getGallery()) 
				 && !"pixabay".equals(req.getGallery()) ) {
			response.setCode(101);
			response.setMessage("不支持图库类型");
			clientSocket.sendData(response);
			return;
		}
		List<ThirdPicLikes> thirdPicLikes = new UploadPicDao().thirdPicLikes(req.getFrom(), 
				req.getGallery(), req.getUids(), req.getUrls());
		if(thirdPicLikes == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			/*if("unsplash".equals(req.getGallery())) {
				for (int i = 0; i < thirdPicLikes.size(); i++) {
					ThirdPicLikes thirdPicLike = thirdPicLikes.get(i);
					List<PicComment> picComments = new PicCommentDao().getTop3Comments(thirdPicLike.getUid(), req.getFrom());
					thirdPicLike.setPicComments(picComments);
				}
			}*/
			response.setCode(200);
			response.setMessage("success");
			response.setThirdPicLikes(thirdPicLikes);
			clientSocket.sendData(response);
		}
	}

}
