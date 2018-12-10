package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.UploadPicDao;
import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.message.request.UploadPicReq;
import com.pojul.fastIM.message.response.UploadPicResp;
import com.pojul.fastIM.utils.DateUtil;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class UploadPicProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		UploadPicReq uploadPicReq = (UploadPicReq) request;
		uploadPicReq.getUploadPic().setUploadPicTime(DateUtil.getFormatDate());
		UploadPicResp response = new UploadPicResp();
		response.setMessageUid(uploadPicReq.getMessageUid());
		User user =  new UserDao().getUserInfo(uploadPicReq.getUserName());
		if(user == null || uploadPicReq.getUploadPic() == null){
			response.setCode(101);
			response.setMessage("用户不存在");
			clientSocket.sendData(response);
			return;
		}
		uploadPicReq.getUploadPic().setUserId(user.getId());
		long result = new UploadPicDao().insertUploadPic(uploadPicReq.getUploadPic());
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("上传成功");
			response.setUploadTime(uploadPicReq.getUploadPic().getUploadPicTime());
			clientSocket.sendData(response);
			return;
		}else {
			response.setCode(101);
			response.setMessage("上传失败");
			clientSocket.sendData(response);
			return;
		}
	}

}
