package com.pojul.fastIM.socketmanager;

import java.net.Socket;
import java.util.LinkedHashMap;
import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.entity.response.LoginResponse;
import com.pojul.fastIM.entity.response.Response;
import com.pojul.fastIM.transmit.Transmitor;
import com.pojul.fastIM.utils.Util;
import com.pojul.objectsocket.message.BaseMessage;
import com.pojul.objectsocket.message.LoginMessage;
import com.pojul.objectsocket.message.LoginoutMessage;
import com.pojul.objectsocket.message.MessageHeader;
import com.pojul.objectsocket.message.StringFile;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.socket.SocketReceiver;
import com.pojul.objectsocket.utils.LogUtil;

import sun.rmi.runtime.Log;

public class ClientSocketManager {

	private static final String TAG = "ClientSocketManager";
	
	private static ClientSocketManager mClientSocketManager;
	public static LinkedHashMap<String, ClientSocket> clientSockets = new LinkedHashMap<String, ClientSocket>();
	
	public static ClientSocketManager getInstance() {
		if(mClientSocketManager == null) {
			synchronized (ClientSocketManager.class) {
				if(mClientSocketManager == null) {
					mClientSocketManager = new ClientSocketManager();
				}
			}
		}
		return mClientSocketManager;
	}
	
	public void addClientSocket(ClientSocket mClientSocket) {
		synchronized (clientSockets) {
			clientSockets.put(mClientSocket.getChatId(), mClientSocket);
			LogUtil.i(TAG, "addClientSocket current clientSockets size: " + clientSockets.size());
		}
	}

	public void RemoveClientSocket(ClientSocket mClientSocket) {
		synchronized(clientSockets) {
			clientSockets.remove(mClientSocket.getChatId());
			LogUtil.i(TAG, "RemoveClientSocket current clientSockets size: " + clientSockets.size());
		}
	}
	
	public void createClientSocket(Socket mSocket) {
		ClientSocket mClientSocket = new ClientSocket(mSocket);
		mClientSocket.setRecListener(new SocketReceiver.ISocketReceiver() {
			
			boolean isFirstRead = true;
			Transmitor mTransmitor = new Transmitor();
			
			@Override
			public void onReadHead(MessageHeader header) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onReadFinish() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String onReadFile(StringFile mStringFile) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void onReadEntity(BaseMessage message) {
				// TODO Auto-generated method stub
				if(isFirstRead && !(message instanceof LoginMessage)) {
					mClientSocket.closeConn();
					return;
				}
				isFirstRead = false;
				if(message instanceof LoginMessage) {
					Login(mClientSocket, (LoginMessage)message);
				}else if(message instanceof LoginoutMessage) {
					LoginoutMessage(mClientSocket, (LoginoutMessage)message);
				}else {
					mTransmitor.transmitMessage(message);
				}
				
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				LogUtil.i(TAG, e.toString());
			}
		});
	}

	protected void LoginoutMessage(ClientSocket mClientSocket, LoginoutMessage message) {
		// TODO Auto-generated method stub
		mClientSocket.closeConn();
		RemoveClientSocket(mClientSocket);
	}

	protected void Login(ClientSocket mClientSocket, LoginMessage message) {
		// TODO Auto-generated method stub
		String loginInfo = new UserDao().loginByUserName(message);
		int code = 100;
		if("success".equals(loginInfo)) {
			mClientSocket.setChatId(message.getUserName());
			addClientSocket(mClientSocket);
			LogUtil.i(TAG, "login success");
			code = 200;
			mClientSocket.sendData(new LoginResponse(code, loginInfo, message.getUserName()));
		}else {
			mClientSocket.sendDataAndClose(new LoginResponse(code, loginInfo,""));
		}
		
	}
	
}
