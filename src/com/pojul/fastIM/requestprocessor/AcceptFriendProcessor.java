package com.pojul.fastIM.requestprocessor;

import java.util.HashMap;
import java.util.Map.Entry;

import com.pojul.fastIM.dao.AddFriendDao;
import com.pojul.fastIM.message.other.NotifyAcceptFriend;
import com.pojul.fastIM.message.other.NotifyFriendReq;
import com.pojul.fastIM.message.request.AcceptFriendReq;
import com.pojul.fastIM.message.response.AcceptFriendResp;
import com.pojul.fastIM.socketmanager.ClientSocketManager;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class AcceptFriendProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		AcceptFriendReq req = (AcceptFriendReq) request;
		AcceptFriendResp response = new AcceptFriendResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		long result = new AddFriendDao().acceptFriend(req.getReqUserName(), req.getReqedUserName(), req.getType());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("success");
			clientSocket.sendData(response);
			
			HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(req.getReqUserName());
			if(mClientSockets != null && mClientSockets.size() > 0) {
				NotifyAcceptFriend notifyAcceptFriend = new NotifyAcceptFriend();
				notifyAcceptFriend.setFrom(req.getReqedUserName());
				notifyAcceptFriend.setTo(req.getReqUserName());
				notifyAcceptFriend.setReqedUserInfo(req.getReqedUserInfo());
				notifyAcceptFriend.setReqText(req.getReqText());
				notifyAcceptFriend.setType(req.getType());
				notifyAcceptFriend.setReqedUserName(req.getReqedUserName());
				notifyAcceptFriend.setReqUserName(req.getReqUserName());
				for (Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
					ClientSocket mClientSocket = entity.getValue();
					if(mClientSocket != null) {
						mClientSocket.sendData(notifyAcceptFriend);
					}
				}
			}
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
	}

}
