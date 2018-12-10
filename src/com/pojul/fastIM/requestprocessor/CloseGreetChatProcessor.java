package com.pojul.fastIM.requestprocessor;

import java.util.HashMap;
import java.util.Map.Entry;

import com.pojul.fastIM.dao.AddFriendDao;
import com.pojul.fastIM.message.other.NotifyAcceptFriend;
import com.pojul.fastIM.message.other.NotifyChatClosed;
import com.pojul.fastIM.message.request.CloseGreetChatReq;
import com.pojul.fastIM.message.response.CloseGreetChatResp;
import com.pojul.fastIM.socketmanager.ClientSocketManager;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.utils.UidUtil;

public class CloseGreetChatProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		CloseGreetChatReq req = (CloseGreetChatReq) request;
		CloseGreetChatResp response = new CloseGreetChatResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		AddFriendDao addFriendDao = new AddFriendDao();
		long result = addFriendDao.closeGreetChat(req.getFromUserName(), req.getToUserName());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("success");
			clientSocket.sendData(response);
			
			addFriendDao.insertCloseChat(req.getFromUserName(),req.getToUserName());
			HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(req.getToUserName());
			if(mClientSockets != null && mClientSockets.size() > 0) {
				NotifyChatClosed notifyChatClosed = new NotifyChatClosed();
				notifyChatClosed.setFrom(req.getFromUserName());
				notifyChatClosed.setTo(req.getToUserName());
				notifyChatClosed.setChatUid(UidUtil.getSingleChatRoomUid(req.getFromUserName(), req.getToUserName()));
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
