package com.pojul.fastIM.requestprocessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.pojul.fastIM.dao.NearByPeopleDao;
import com.pojul.fastIM.dao.RecommendDao;
import com.pojul.fastIM.entity.Filter;
import com.pojul.fastIM.entity.UserFilter;
import com.pojul.fastIM.message.other.NotifyHasRecommend;
import com.pojul.fastIM.message.request.UpdateNUFilterReq;
import com.pojul.fastIM.message.response.UpdateNUFilterResp;
import com.pojul.fastIM.socketmanager.ClientSocketManager;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class UpdateNUFilterProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		UpdateNUFilterReq req = (UpdateNUFilterReq) request;
		UpdateNUFilterResp response = new UpdateNUFilterResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom()) || req.getFilter() == null || req.getFilter().getUserFilter() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		
		long result = new NearByPeopleDao().updateNearbyUserFilter(req.getFilter(), req.getFrom());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("success");
			clientSocket.sendData(response);
			
			UserFilter filter = req.getFilter().getUserFilter();
			if(filter.isWhiteListEnabled() && filter.getWhiteListNames() != null && 
					filter.getWhiteListNames().size() > 0) {
				List<String> whites = filter.getWhiteListNames();
				new RecommendDao().insertRecomdUsers(req.getFrom(), whites);
				for (int i = 0; i < whites.size(); i++) {
					String userName = whites.get(i);
					if(userName == null) {
						continue;
					}
					HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(userName);
					if(mClientSockets != null && mClientSockets.size() > 0) {
						for (Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
							ClientSocket mClientSocket = entity.getValue();
							if(mClientSocket != null) {
								NotifyHasRecommend notifyHasRecommend = new NotifyHasRecommend();
								notifyHasRecommend.setRecommendtype(2);
								notifyHasRecommend.setToUserName(userName);
								mClientSocket.sendData(notifyHasRecommend);
							}
						}
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
