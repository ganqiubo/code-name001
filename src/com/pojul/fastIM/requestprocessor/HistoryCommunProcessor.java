package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.CommunityMessEntityDao;
import com.pojul.fastIM.entity.CommunityMessEntity;
import com.pojul.fastIM.message.request.HistoryCommunReq;
import com.pojul.fastIM.message.response.HistoryCommunResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class HistoryCommunProcessor implements RequestProcessor {

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		HistoryCommunReq req = (HistoryCommunReq) request;
		HistoryCommunResp response = new HistoryCommunResp();
		response.setMessageUid(req.getMessageUid());
		CommunityMessEntityDao dao = new CommunityMessEntityDao();
		long lastMilli = -1;
		if(req.getLastMessageUid() != null) {
			lastMilli = dao.getTimeMilli(req.getLastMessageUid());
		}
		if(lastMilli <= 0) {
			lastMilli = System.currentTimeMillis();
		}
		List<CommunityMessEntity> communityMessEntities = dao.getHistory(lastMilli, req.getRoomUid(), 
				req.getNum(), req.getMessageFilter(), req.getFrom());
		if(communityMessEntities == null) {
			response.setCode(100);
			response.setMessage("系统错误");
		}else if(communityMessEntities.size() <= 0) {
			response.setCode(201);
			response.setMessage("未查询到数据");
		}else {
			response.setCode(200);
			response.setMessage("sucesses");
			response.setHistoryCommuMessList(communityMessEntities);
		}
		clientSocket.sendData(response);
		
	}

}
