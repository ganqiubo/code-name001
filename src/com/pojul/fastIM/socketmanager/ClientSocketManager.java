package com.pojul.fastIM.socketmanager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.websocket.OnClose;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pojul.fastIM.dao.MessageDao;
import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.dao.UserMessageDao;
import com.pojul.fastIM.entity.Message;
import com.pojul.fastIM.entity.UserMessage;
import com.pojul.fastIM.message.chat.ChatMessage;
import com.pojul.fastIM.message.chat.TextPicMessage;
import com.pojul.fastIM.message.login.LoginMessage;
import com.pojul.fastIM.message.login.LoginoutMessage;
import com.pojul.fastIM.message.response.LoginResponse;
import com.pojul.fastIM.message.response.Response;
import com.pojul.fastIM.transmitor.UserTransmitor;
import com.pojul.fastIM.utils.Util;
import com.pojul.objectsocket.message.BaseMessage;
import com.pojul.objectsocket.message.MessageHeader;
import com.pojul.objectsocket.message.StringFile;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.socket.SocketReceiver;
import com.pojul.objectsocket.socket.SocketSender;
import com.pojul.objectsocket.utils.LogUtil;
import com.sun.org.apache.bcel.internal.generic.I2F;
import sun.rmi.runtime.Log;

public class ClientSocketManager {

	private static final String TAG = "ClientSocketManager";
	private static ArrayList<String> supportPlatform = new ArrayList<String>() {
		{
			add("Android");
			add("windows");
			add("IOS");
			add("ubuntu");
		}
	};

	private static ClientSocketManager mClientSocketManager;
	public static LinkedHashMap<String, HashMap<String, ClientSocket>> clientSockets = new LinkedHashMap<String, HashMap<String, ClientSocket>>();

	public static ClientSocketManager getInstance() {
		if (mClientSocketManager == null) {
			synchronized (ClientSocketManager.class) {
				if (mClientSocketManager == null) {
					mClientSocketManager = new ClientSocketManager();
				}
			}
		}
		return mClientSocketManager;
	}

	public void addClientSocket(ClientSocket mClientSocket) {
		synchronized (clientSockets) {
			if (clientSockets.get(mClientSocket.getChatId()) == null) {
				HashMap<String, ClientSocket> tempClientMap = new HashMap<String, ClientSocket>();
				clientSockets.put(mClientSocket.getChatId(), tempClientMap);
			}
			clientSockets.get(mClientSocket.getChatId()).put(mClientSocket.getDeviceType(), mClientSocket);
			LogUtil.i(TAG, "addClientSocket current online users size: " + clientSockets.size());
		}
	}

	public void RemoveClientSocket(ClientSocket mClientSocket) {
		synchronized (clientSockets) {
			clientSockets.remove(mClientSocket.getChatId());
			LogUtil.i(TAG, "RemoveClientSocket current clientSockets size: " + clientSockets.size());
		}
	}

	public void createClientSocket(Socket mSocket) {
		ClientSocket mClientSocket = new ClientSocket(mSocket);
		onMessageRecListener(mClientSocket);
		onMessageSendListener(mClientSocket);
	}

	protected void LoginoutMessage(ClientSocket mClientSocket, LoginoutMessage message) {
		// TODO Auto-generated method stub
		mClientSocket.closeConn();
		RemoveClientSocket(mClientSocket);
	}

	protected void Login(ClientSocket mClientSocket, LoginMessage message) {
		// TODO Auto-generated method stub
		int code = 100;
		if (supportPlatform.indexOf(message.getDeviceType()) == -1) {
			mClientSocket.sendDataAndClose(new LoginResponse(code, "不支持的设备类型", ""));
			return;
		}
		String loginInfo = new UserDao().loginByUserName(message);
		if ("success".equals(loginInfo)) {
			mClientSocket.setChatId(message.getUserName());
			mClientSocket.setDeviceType(message.getDeviceType());
			addClientSocket(mClientSocket);
			LogUtil.i(TAG, "login success");
			code = 200;
			mClientSocket.sendData(new LoginResponse(code, loginInfo, message.getUserName()));
			
			sendUnSendMessage(mClientSocket);
			
		} else {
			mClientSocket.sendDataAndClose(new LoginResponse(code, loginInfo, ""));
		}

	}

	private void sendUnSendMessage(ClientSocket mClientSocket) {
		// TODO Auto-generated method stub
		//List<Message>
		List<UserMessage> userMessages = new UserMessageDao().getUnSendMessage(mClientSocket.getChatId());
		if(userMessages != null) {
			for(int i = 0; i < userMessages.size(); i++) {
				UserMessage userMessage = userMessages.get(i);
				try {
					BaseMessage mBaseMessage = (BaseMessage) new Gson().fromJson(
							userMessage.getMessage().getMessageContent(), 
							Class.forName(userMessage.getMessage().getMessageClass()));
					mClientSocket.sendData(mBaseMessage);
				} catch (JsonSyntaxException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					LogUtil.i(getClass().getName(), "非法的消息内容");
				}
			}
			
		}
	}
	

	private void onMessageRecListener(ClientSocket mClientSocket) {
		// TODO Auto-generated method stub
		mClientSocket.setRecListener(new SocketReceiver.ISocketReceiver() {

			boolean isFirstRead = true;
			UserTransmitor mTransmitor = new UserTransmitor();

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
				if (isFirstRead && !(message instanceof LoginMessage)) {
					mClientSocket.closeConn();
					return;
				}
				isFirstRead = false;
				if (message instanceof LoginMessage) {
					Login(mClientSocket, (LoginMessage) message);
				} else if (message instanceof LoginoutMessage) {
					LoginoutMessage(mClientSocket, (LoginoutMessage) message);
				} else {
					if (message instanceof ChatMessage) {
						mTransmitor.transmitMessage((ChatMessage) message);
					}

				}

			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				LogUtil.i(TAG, e.toString());
			}
		});
	}

	private void onMessageSendListener(ClientSocket mClientSocket) {
		// TODO Auto-generated method stub
		mClientSocket.setSenderListener(new SocketSender.ISocketSender() {
			
			@Override
			public void onSendFinish(BaseMessage mBaseMessage) {
				// TODO Auto-generated method stub
				if(mBaseMessage instanceof ChatMessage) {
					new UserMessageDao().updateSendStatus( ((ChatMessage)mBaseMessage).getChatUid(), mClientSocket.getChatId(), true);
				}
			}
			
			@Override
			public void onSendFail(BaseMessage mBaseMessage) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSendError(Exception e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onNullMessage(BaseMessage mBaseMessage) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
