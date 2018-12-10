package com.pojul.fastIM.requestprocessor;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.message.request.ExperienceReq;
import com.pojul.fastIM.message.response.ExperienceResp;
import com.pojul.fastIM.utils.DateUtil;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class ExperienceProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		ExperienceReq req = (ExperienceReq) request;
		ExperienceResp response = new ExperienceResp();
		response.setMessageUid(req.getMessageUid());
		
		if(!clientSocket.getChatId().equals(req.getFrom())) {
			response.setCode(101);
			response.setMessage("非法访问");
			clientSocket.sendData(response);
			return;
		}
		UserDao userDao = new UserDao();
		if(!userDao.canExperience(req.getFrom())) {
			response.setCode(101);
			response.setMessage("体验机会已被使用");
			clientSocket.sendData(response);
			return;
		}
		long result = userDao.updateExperience(req.getFrom(), 0);
		if(result >= 0) {
			response.setCode(200);
			response.setMessage("success");
			String validTime = DateUtil.getFormatDate( (System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000L) );
			response.setValidTime(validTime);
			clientSocket.sendData(response);
		}else {
			response.setCode(101);
			response.setMessage("系统错误");
			clientSocket.sendData(response);
		}
		
	}

}
