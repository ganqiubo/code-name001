package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.UploadPicDao;
import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.UploadPic;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.message.request.UploadPicRecordReq;
import com.pojul.fastIM.message.response.UploadPicRecordResp;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class UploadPicRecordProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		UploadPicRecordReq req = (UploadPicRecordReq) request;
		UploadPicRecordResp response = new UploadPicRecordResp();
		response.setMessageUid(req.getMessageUid());
		User user =  new UserDao().getUserInfo(req.getUserName());
		if(user == null){
			response.setCode(101);
			response.setMessage("用户不存在");
			clientSocket.sendData(response);
			return;
		}
		List<UploadPic> uploadPics = new UploadPicDao().queryRecords(user.getId(), req.getLastSendTime(), req.getNum());
		if(uploadPics == null) {
			response.setCode(100);
			response.setMessage("系统错误");
		}else if(uploadPics.size() <= 0) {
			response.setCode(201);
			response.setMessage("未查询到数据");
		}else {
			response.setCode(200);
			response.setMessage("sucesses");
			response.setUploadPics(uploadPics);
		}
		clientSocket.sendData(response);
	}

}
