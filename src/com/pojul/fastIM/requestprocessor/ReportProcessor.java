package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.ReportDao;
import com.pojul.fastIM.message.request.ReportReq;
import com.pojul.fastIM.message.response.ReportResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class ReportProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		ReportReq req = (ReportReq) request;
		ReportResp response = new ReportResp();
		response.setMessageUid(req.getMessageUid());
		
		if(req.getReport() == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		long result = new ReportDao().insertReport(req.getReport());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("sucess");
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}
	}
	
	
	
}
