package com.pojul.fastIM.requestprocessor;

import java.util.HashMap;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.LoginStatus;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.message.request.LoginByTokenReq;
import com.pojul.fastIM.message.response.LoginResponse;
import com.pojul.fastIM.socketmanager.ClientSocketManager;
import com.pojul.fastIM.socketmanager.UnLoginSocketManger;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.utils.LogUtil;
import com.pojul.objectsocket.utils.UidUtil;

public class LoginByTokenProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		LoginByTokenReq req = (LoginByTokenReq) request;
		//LoginResponse response = new LoginResponse();
		//response.setMessageUid(req.getMessageUid());
		
		if(req.getFrom() == null) {
			UnLoginSocketManger.getInstance().removeUnLoginSocket(clientSocket);
			clientSocket.sendDataAndClose(new LoginResponse(100, "非法登陆", "", req.getMessageUid(), null, ""));
			return;
		}
		if (req.getDeviceType() == null || ClientSocketManager.supportPlatform.indexOf(req.getDeviceType()) == -1) {
			UnLoginSocketManger.getInstance().removeUnLoginSocket(clientSocket);
			clientSocket.sendDataAndClose(new LoginResponse(100, "不支持的设备类型", "", req.getMessageUid(), null, ""));
			return;
		}
		HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(req.getFrom());
		String lastToken = "";
		ClientSocket tempClientSocket = null;
		if(mClientSockets == null || (tempClientSocket = mClientSockets.get(req.getDeviceType())) == null) {
			lastToken = new UserDao().getLastToken(req.getFrom(), req.getDeviceType());
			if(lastToken != null && !lastToken.equals(req.getTokenId())) {
				UnLoginSocketManger.getInstance().removeUnLoginSocket(clientSocket);
				clientSocket.sendDataAndClose(new LoginResponse(100, "非法登陆", "", req.getMessageUid(), null, ""));
				return;
			}
			tokenLoginOk(req, clientSocket);
		}else {
			lastToken = tempClientSocket.getTokenId();
			if(lastToken != null && !lastToken.equals(req.getTokenId())) {
				UnLoginSocketManger.getInstance().removeUnLoginSocket(clientSocket);
				clientSocket.sendDataAndClose(new LoginResponse(100, "非法登陆", "", req.getMessageUid(), null, ""));
				return;
			}
			//ClientSocketManager.getInstance().RemoveClientSocket(tempClientSocket);
			tempClientSocket.closeConn();
			tokenLoginOk(req, clientSocket);
		}
		
	}

	private void tokenLoginOk(LoginByTokenReq req, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		UnLoginSocketManger.getInstance().removeUnLoginSocket(clientSocket);
		String newToken = UidUtil.getTokenid(req.getFrom());
		clientSocket.setChatId(req.getFrom());
		clientSocket.setDeviceType(req.getDeviceType());
		clientSocket.setTokenId(newToken);
		ClientSocketManager.getInstance().addClientSocket(clientSocket);
		LogUtil.i("LoginByTokenProcessor", "token login success");
		UserDao userDao = new UserDao();
		User user = userDao.getUserInfo(req.getFrom());
		if(userDao.exitLoginStatus(req.getFrom(), req.getDeviceType())) {
			userDao.setLoginStatus(user.getId(), req.getDeviceType(), 1, newToken);
		}else {
			LoginStatus loginStatus = new LoginStatus();
			loginStatus.setUserId(user.getId());
			loginStatus.setLoginStatus(1);
			loginStatus.setChatId(user.getUserName());
			loginStatus.setLoginDevice("");
			loginStatus.setDeviceType(req.getDeviceType());
			userDao.insertLoginStatus(loginStatus, newToken);
		}
		clientSocket.sendData(new LoginResponse(200, "success", req.getFrom(), 
				req.getMessageUid(), user, clientSocket.getTokenId()));
		ClientSocketManager.getInstance().sendUnSendMessage(clientSocket);
	}

}
