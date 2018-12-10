package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.TagMessLabelsDao;
import com.pojul.fastIM.message.request.GetTagMessLabelsReq;
import com.pojul.fastIM.message.response.GetTagMessLabelsResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetTagMessLabelsProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetTagMessLabelsReq req = (GetTagMessLabelsReq) request;
		GetTagMessLabelsResp response = new GetTagMessLabelsResp();
		response.setMessageUid(req.getMessageUid());
		
		String labels = new TagMessLabelsDao().getLabels();
		if(labels == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("sucess");
			response.setTagMessLabels(labels);
			clientSocket.sendData(response);
		}
	}

}
