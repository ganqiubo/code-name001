package com.pojul.fastIM.requestprocessor;

import java.util.HashMap;
import java.util.Map.Entry;

import com.pojul.fastIM.dao.AddFriendDao;
import com.pojul.fastIM.message.other.NotifyFriendReq;
import com.pojul.fastIM.message.request.FriendReq;
import com.pojul.fastIM.message.response.FriendResp;
import com.pojul.fastIM.socketmanager.ClientSocketManager;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class FriendReqProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		FriendReq req = (FriendReq) request;
		FriendResp response = new FriendResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getAddFriend() == null ||  req.getAddFriend().getReqUserInfo() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
			
		AddFriendDao addFriendDao = new AddFriendDao();
		int reqstatus = addFriendDao.friendReqStatus(req.getAddFriend().getReqUserName(), 
				req.getAddFriend().getReqedUserName(), req.getAddFriend().getReqType());
		if(reqstatus == 0) {
			response.setCode(201);
			response.setMessage("您已向对方发送了请求");
			clientSocket.sendData(response);
			return;
		}else if(reqstatus == 1){
			response.setCode(202);
			response.setMessage("对方已接受了您的请求");
			clientSocket.sendData(response);
			return;
		}else if(reqstatus == -3) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = -1;
		if(reqstatus == -1) {
			result = addFriendDao.updateNewReq(req.getAddFriend().getReqUserName(),
					req.getAddFriend().getReqedUserName(), 0, req.getAddFriend().getReqType(), req.getAddFriend().getReqText());
		}else {
			result = addFriendDao.insertFriendReq(req.getAddFriend());
		}

		if(result >= 0) {
			response.setCode(200);
			response.setMessage("success");
			clientSocket.sendData(response);
			
			HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(req.getAddFriend().getReqedUserName());
			if(mClientSockets != null && mClientSockets.size() > 0) {
				NotifyFriendReq notifyFriendReq = new NotifyFriendReq();
				notifyFriendReq.setAddFriend(req.getAddFriend());
				notifyFriendReq.setFrom(req.getAddFriend().getReqUserName());
				notifyFriendReq.setTo(req.getAddFriend().getReqedUserName());
				for (Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
					ClientSocket mClientSocket = entity.getValue();
					if(mClientSocket != null) {
						mClientSocket.sendData(notifyFriendReq);
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
