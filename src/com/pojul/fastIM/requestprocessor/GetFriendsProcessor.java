package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.FriendDao;
import com.pojul.fastIM.entity.Friend;
import com.pojul.fastIM.message.request.GetFriendsRequest;
import com.pojul.fastIM.message.response.GetFriendsResponse;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetFriendsProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetFriendsRequest mRequest = (GetFriendsRequest)request;
		
		List<Friend> friends = new FriendDao().getFriends(mRequest.getUserName());
		GetFriendsResponse response = new GetFriendsResponse();
		response.setMessageUid(request.getMessageUid());
		if(friends == null) {
			response.setCode(100);
			response.setMessage("系统错误");
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setFriends(friends);
		}
		clientSocket.sendData(response);
	}

	
	
}
