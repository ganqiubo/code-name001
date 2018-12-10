package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.AddFriendDao;
import com.pojul.fastIM.entity.AddFriend;
import com.pojul.fastIM.message.request.GetFriendReqs;
import com.pojul.fastIM.message.response.GetFriendReqsResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetFriendReqsProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		GetFriendReqs req = (GetFriendReqs) request;
		GetFriendReqsResp response = new GetFriendReqsResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		List<AddFriend> addFriends = new AddFriendDao().getAddFriendReqs(req.getFrom(), req.getStartNum(), req.getNum());
		if(addFriends == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setAddFriends(addFriends);
			clientSocket.sendData(response);
		}
	}

}
