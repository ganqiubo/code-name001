package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.ReportReasonDao;
import com.pojul.fastIM.message.request.ReportReasonReq;
import com.pojul.fastIM.message.response.ReportReasonResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class ReportReasonProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		ReportReasonReq req = (ReportReasonReq) request;
		ReportReasonResp response = new ReportReasonResp();
		response.setMessageUid(req.getMessageUid());
		List<String> reasons = new ReportReasonDao().getReasons();
		if(reasons == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
			return;
		}
		if(reasons.size() > 0){
			response.setCode(200);
			response.setMessage("sucess");
			response.setReportReasons(reasons);
			clientSocket.sendData(response);
		}else {
			response.setCode(201);
			response.setMessage("未查询到数据");
			clientSocket.sendData(response);
		}
	}

}
