package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.LikeUserDao;
import com.pojul.fastIM.entity.LikeFollowInfo;
import com.pojul.fastIM.message.request.LikeFollowInfoReq;
import com.pojul.fastIM.message.response.LikeFollowInfoResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class LikeFollowInfoProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		LikeFollowInfoReq req = (LikeFollowInfoReq) request;
		LikeFollowInfoResp response = new LikeFollowInfoResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		LikeFollowInfo likeFollowInfo = new LikeUserDao().likeFollow(req.getFrom(), req.getBeUserName());
		if(likeFollowInfo == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("sucess");
			response.setLikeCount(likeFollowInfo.getLikeCount());
			response.setFollowCount(likeFollowInfo.getFollowCount());
			clientSocket.sendData(response);
		}
		
	}

}
