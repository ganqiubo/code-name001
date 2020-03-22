package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.PicDao;
import com.pojul.fastIM.entity.RecomdPic;
import com.pojul.fastIM.message.request.GetRecomdPicReq;
import com.pojul.fastIM.message.response.GetRecomdPicResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetRecomdPicProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		
		GetRecomdPicReq req = (GetRecomdPicReq) request;
		GetRecomdPicResp response = new GetRecomdPicResp();
		response.setMessageUid(req.getMessageUid());
		
		List<RecomdPic> recomdPics = new PicDao().getRecomdPic();
		if(recomdPics == null) {
			response.setCode(101);
			response.setMessage("fail");
			clientSocket.sendData(response);
		}else {
			response.setCode(200);
			response.setMessage("success");
			response.setRecomdPics(recomdPics);
			clientSocket.sendData(response);
		}
		
	}

}
