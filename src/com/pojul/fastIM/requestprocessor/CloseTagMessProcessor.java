package com.pojul.fastIM.requestprocessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.pojul.fastIM.dao.CommunityMessEntityDao;
import com.pojul.fastIM.dao.ReplyMessageDao;
import com.pojul.fastIM.entity.ReplyReqUsers;
import com.pojul.fastIM.message.chat.TagCloseMessage;
import com.pojul.fastIM.message.request.CloseTagMessReq;
import com.pojul.fastIM.message.response.CloseTagMessResp;
import com.pojul.fastIM.socketmanager.ClientSocketManager;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class CloseTagMessProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		CloseTagMessReq req = (CloseTagMessReq) request;
		CloseTagMessResp response = new CloseTagMessResp();
		response.setMessageUid(req.getMessageUid());
		
		if(req.getTagMessUid() == null || req.getFrom() == null || 
				!req.getFrom().equals(clientSocket.getChatId())) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		long result = new CommunityMessEntityDao().CloseTagMess(req.getTagMessUid(), clientSocket.getChatId());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("sucess");
			clientSocket.sendData(response);
			
			notifyOthers(req, clientSocket);
			
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
	}

	private void notifyOthers(CloseTagMessReq req, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		List<ReplyReqUsers> reqUsers = new ReplyMessageDao().getReqMessUsers(req.getTagMessUid());
		if(reqUsers == null || reqUsers.size() <= 0) {
			return;
		}
		TagCloseMessage tagCloseMessage = new TagCloseMessage();
		tagCloseMessage.setTagMessageUid(req.getTagMessUid());
		tagCloseMessage.setFrom(clientSocket.getChatId());
		
		for(int i =0; i< reqUsers.size(); i++) {
			String toUser = reqUsers.get(i).getUserName();
			if(toUser == null || toUser.equals(clientSocket.getChatId())) {
				continue;
			}
			HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(toUser);
			if(mClientSockets != null && mClientSockets.size() > 0) {
				for ( Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
					ClientSocket mClientSocket = entity.getValue();
					if(mClientSocket != null) {
						mClientSocket.sendData(tagCloseMessage);
					}
				}
			}
		}
	}

}
