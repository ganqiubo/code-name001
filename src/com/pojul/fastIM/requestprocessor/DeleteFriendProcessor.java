package com.pojul.fastIM.requestprocessor;

import java.util.HashMap;
import java.util.Map.Entry;

import com.pojul.fastIM.dao.AddFriendDao;
import com.pojul.fastIM.message.other.NotifyChatClosed;
import com.pojul.fastIM.message.request.DeleteFriendReq;
import com.pojul.fastIM.message.response.DeleteFriendResp;
import com.pojul.fastIM.socketmanager.ClientSocketManager;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.utils.UidUtil;

public class DeleteFriendProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		DeleteFriendReq req = (DeleteFriendReq) request;
		DeleteFriendResp response = new DeleteFriendResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		AddFriendDao addFriendDao = new AddFriendDao();
		long result = addFriendDao.deleteFriend(req.getOwner(), req.getFriend());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("successsss");
			clientSocket.sendData(response);
			
			addFriendDao.insertCloseChat(req.getOwner(),req.getFriend());
			HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(req.getFriend());
			if(mClientSockets != null && mClientSockets.size() > 0) {
				NotifyChatClosed notifyChatClosed = new NotifyChatClosed();
				notifyChatClosed.setFrom(req.getOwner());
				notifyChatClosed.setTo(req.getFriend());
				notifyChatClosed.setChatUid(UidUtil.getSingleChatRoomUid(req.getOwner(), req.getFriend()));
				for (Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
					ClientSocket mClientSocket = entity.getValue();
					if(mClientSocket != null) {
						mClientSocket.sendData(notifyChatClosed);
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
