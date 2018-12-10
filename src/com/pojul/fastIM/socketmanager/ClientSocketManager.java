package com.pojul.fastIM.socketmanager;

import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.If;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mysql.jdbc.log.LogUtils;
import com.pojul.fastIM.dao.AddFriendDao;
import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.dao.NotifyReplyMessDao;
import com.pojul.fastIM.dao.RecommendDao;
import com.pojul.fastIM.dao.ReplyMessageDao;
import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.dao.UserMessageDao;
import com.pojul.fastIM.entity.AddFriend;
import com.pojul.fastIM.entity.LoginUserInfo;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.entity.UserMessage;
import com.pojul.fastIM.message.chat.ChatMessage;
import com.pojul.fastIM.message.chat.CommunityMessage;
import com.pojul.fastIM.message.chat.ReplyMessage;
import com.pojul.fastIM.message.chat.SubReplyMessage;
import com.pojul.fastIM.message.other.NotifyAcceptFriend;
import com.pojul.fastIM.message.other.NotifyChatClosed;
import com.pojul.fastIM.message.other.NotifyFriendReq;
import com.pojul.fastIM.message.other.NotifyHasRecommend;
import com.pojul.fastIM.message.other.NotifyReplyMess;
import com.pojul.fastIM.message.request.GetFriendsRequest;
import com.pojul.fastIM.message.request.LoginByTokenReq;
import com.pojul.fastIM.message.request.LoginMessage;
import com.pojul.fastIM.message.request.LoginoutMessage;
import com.pojul.fastIM.message.request.RegisterReq;
import com.pojul.fastIM.message.request.UpdateUserPhotoReq;
import com.pojul.fastIM.message.request.UploadPicReq;
import com.pojul.fastIM.message.response.LoginResponse;
import com.pojul.fastIM.message.response.UploadPicResp;
import com.pojul.fastIM.requestprocessor.GetFriendsProcessor;
import com.pojul.fastIM.transmitor.CommunityMessTransmitor;
import com.pojul.fastIM.transmitor.RequestTransmitor;
import com.pojul.fastIM.transmitor.UserMessageTransmitor;
import com.pojul.objectsocket.message.BaseMessage;
import com.pojul.objectsocket.message.MessageHeader;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.message.StringFile;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.socket.SocketReceiver;
import com.pojul.objectsocket.socket.SocketSender;
import com.pojul.objectsocket.utils.LogUtil;
import com.pojul.objectsocket.utils.UidUtil;

public class ClientSocketManager {

	private static final String TAG = "ClientSocketManager";
	public static ArrayList<String> supportPlatform = new ArrayList<String>() {
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
			HashMap<String, ClientSocket> clientMaps = clientSockets.get(mClientSocket.getChatId());
			if (clientMaps == null) {
				HashMap<String, ClientSocket> tempClientMap = new HashMap<String, ClientSocket>();
				clientSockets.put(mClientSocket.getChatId(), tempClientMap);
			}else {
				ClientSocket tempClient = clientMaps.get(mClientSocket.getDeviceType());
				if(tempClient != null) {
					tempClient.setChatId((tempClient.getChatId() + "_invalid"));
					tempClient.closeConn();
					//new UserDao().updateLoginStatus(mClientSocket.getChatId(), mClientSocket.getDeviceType(), 1);
				}
			}
			clientSockets.get(mClientSocket.getChatId()).put(mClientSocket.getDeviceType(), mClientSocket);
			LogUtil.i(TAG, "addClientSocket current online users size: " + clientSockets.size());
		}
	}

	public boolean isClientLogin(String chatId, String deviceType) {
		synchronized (clientSockets) {
			HashMap<String, ClientSocket> tempClientMap = clientSockets.get(chatId);
			if(tempClientMap == null) {
				return false;
			}
			
			ClientSocket tempClientSocket = tempClientMap.get(deviceType);
			if(tempClientSocket != null && tempClientSocket.getmSocket() != null) {
				return true;
			}
			return false;
		}
	}
	
	public void RemoveClientSocket(ClientSocket mClientSocket) {
		synchronized (clientSockets) {
			if(clientSockets.containsKey(mClientSocket.getChatId())) {
				HashMap<String, ClientSocket> devicesSockets = clientSockets.get(mClientSocket.getChatId());
				if(devicesSockets.containsKey(mClientSocket.getDeviceType())) {
					if(devicesSockets.size() <= 1) {
						clientSockets.remove(mClientSocket.getChatId());
					}else {
						devicesSockets.remove(mClientSocket.getDeviceType());
					}
				}
			}
			LogUtil.i(TAG, "RemoveClientSocket current clientSockets size: " + clientSockets.size());
		}
	}

	public void createClientSocket(Socket mSocket) {
		ClientSocket mClientSocket = new ClientSocket(mSocket);
		//mClientSocket.setHeartbeat(1 * 1000);
		/*try {
			mClientSocket.getmSocket().setSoTimeout((int)(9 * 60 * 1000));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			LogUtil.i(TAG, e.toString());
			LogUtil.dStackTrace(e);
		}*/
		UnLoginSocketManger.getInstance().addUnLoginSocket(mClientSocket);
		onMessageRecListener(mClientSocket);
		onMessageSendListener(mClientSocket);
		onStatusChangedListener(mClientSocket);
	}

	protected void LoginoutMessage(ClientSocket mClientSocket, LoginoutMessage message) {
		// TODO Auto-generated method stub
		mClientSocket.closeConn();
		RemoveClientSocket(mClientSocket);
	}

	protected void Login(ClientSocket mClientSocket, LoginMessage message) {
		// TODO Auto-generated method stub
		int code = 100;
		if (message.getDeviceType() == null || supportPlatform.indexOf(message.getDeviceType()) == -1 || message.getUserName() == null) {
			UnLoginSocketManger.getInstance().removeUnLoginSocket(mClientSocket);
			mClientSocket.sendDataAndClose(new LoginResponse(code, "不支持的设备类型", "", message.getMessageUid(), null, ""));
			return;
		}
		String tokenId = UidUtil.getTokenid(message.getUserName());
		String loginInfo = new UserDao().loginByUserName(message, tokenId);
		if ("success".equals(loginInfo) || "该用户已登陆".equals(loginInfo)) {
			/*if(isClientLogin(message.getUserName(), message.getDeviceType())) {
				UnLoginSocketManger.getInstance().removeUnLoginSocket(mClientSocket);
				mClientSocket.sendDataAndClose(new LoginResponse(code, "该用户已登陆", "", message.getMessageUid(), null, ""));
				return;
			}*/
			loginInfo = "success";
			UnLoginSocketManger.getInstance().removeUnLoginSocket(mClientSocket);
			mClientSocket.setChatId(message.getUserName());
			mClientSocket.setDeviceType(message.getDeviceType());
			mClientSocket.setTokenId(tokenId);
			addClientSocket(mClientSocket);
			LogUtil.i(TAG, "login success");
			code = 200;
			User user = new UserDao().getUserInfo(message.getUserName());
			mClientSocket.sendData(new LoginResponse(code, loginInfo, message.getUserName(), 
					message.getMessageUid(), user, mClientSocket.getTokenId()));
			sendUnSendMessage(mClientSocket);
		} else {
			UnLoginSocketManger.getInstance().removeUnLoginSocket(mClientSocket);
			mClientSocket.sendDataAndClose(new LoginResponse(code, loginInfo, "", message.getMessageUid(), null, ""));
		}

	}

	public void sendUnSendMessage(ClientSocket mClientSocket) {
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
					LogUtil.i(getClass().getName(), "非法的消息内容");
				}
			}
		}
		List<NotifyReplyMess> notifyReplyMesses = new NotifyReplyMessDao().getUnSendMessage(mClientSocket.getChatId());
		if(notifyReplyMesses != null && notifyReplyMesses.size() > 0) {
			for (int i = 0; i < notifyReplyMesses.size(); i++) {
				NotifyReplyMess notifyReplyMess = notifyReplyMesses.get(i);
				if(notifyReplyMess == null) {
					continue;
				}
				mClientSocket.sendData(notifyReplyMess);
			}
		}
		
		List<AddFriend> addFriendReqs = new AddFriendDao().getUnNotifiedFriendReq(mClientSocket.getChatId());
		if(addFriendReqs != null && addFriendReqs.size() > 0) {
			for (int i = 0; i < addFriendReqs.size(); i++) {
				NotifyFriendReq notifyFriendReq = new NotifyFriendReq();
				AddFriend addFriend = addFriendReqs.get(i);
				if(addFriend == null){
					continue;
				}
				notifyFriendReq.setFrom(addFriend.getReqUserName());
				notifyFriendReq.setTo(addFriend.getReqedUserName());
				notifyFriendReq.setAddFriend(addFriend);
				mClientSocket.sendData(notifyFriendReq);
			}
		}
		
		List<NotifyAcceptFriend> notifyAcceptFriends = new AddFriendDao().getUnNotiftAcceptFriends(mClientSocket.getChatId());
		if(notifyAcceptFriends != null && notifyAcceptFriends.size() > 0) {
			for (int i = 0; i < notifyAcceptFriends.size(); i++) {
				NotifyAcceptFriend notifyAcceptFriend = notifyAcceptFriends.get(i);
				if(notifyAcceptFriend == null){
					continue;
				}
				notifyAcceptFriend.setFrom(notifyAcceptFriend.getReqedUserName());
				notifyAcceptFriend.setTo(notifyAcceptFriend.getReqUserName());
				mClientSocket.sendData(notifyAcceptFriend);
			}
		}
		
		List<NotifyChatClosed> notifyChatCloseds = new AddFriendDao().getUnNotifyChatClosed(mClientSocket.getChatId());
		if(notifyChatCloseds != null && notifyChatCloseds.size() > 0) {
			for (int i = 0; i < notifyChatCloseds.size(); i++) {
				NotifyChatClosed notifyChatClosed = notifyChatCloseds.get(i);
				mClientSocket.sendData(notifyChatClosed);
			}
		}
		
		RecommendDao recommendDao = new RecommendDao();
		boolean hasRecommendMess = recommendDao.hasUnNotifyMess(mClientSocket.getChatId());
		if(hasRecommendMess) {
			NotifyHasRecommend mess = new NotifyHasRecommend();
			mess.setRecommendtype(1);
			mess.setToUserName(mClientSocket.getChatId());
			mClientSocket.sendData(mess);
		}
		boolean hasRecomdUser = recommendDao.hasUnNotifyUser(mClientSocket.getChatId());
		if(hasRecomdUser) {
			NotifyHasRecommend mess = new NotifyHasRecommend();
			mess.setRecommendtype(2);
			mess.setToUserName(mClientSocket.getChatId());
			mClientSocket.sendData(mess);
		}
	}
	

	protected void onMessageRecListener(ClientSocket mClientSocket) {
		// TODO Auto-generated method stub
		mClientSocket.setRecListener(new SocketReceiver.ISocketReceiver() {

			boolean isFirstRead = true;
			UserMessageTransmitor mTransmitor = new UserMessageTransmitor();
			CommunityMessTransmitor communityMessTransmitor = new CommunityMessTransmitor();

			@Override
			public void onReadHead(MessageHeader header) {
				// TODO Auto-generated method stub
				if (UploadPicReq.class.getName().equals(header.getClassName())) {
					//mClientSocket.setSaveFilePath("D:\\websource\\uploadPic\\", "http://192.168.0.106:8080/resources/uploadPic/");
					mClientSocket.setSaveFilePath("/root/websource/uploadPic/", "http://47.93.31.206:8080/resources/uploadPic/");
				} else if(UpdateUserPhotoReq.class.getName().equals(header.getClassName())){
					//mClientSocket.setSaveFilePath("D:\\websource\\photo\\", "photo/");
					mClientSocket.setSaveFilePath("/root/websource/photo/", "photo/");
				}else {
					mClientSocket.setSaveFilePath("/root/websource/", "http://47.93.31.206:8080/resources/");
				}
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
				if (isFirstRead && !((message instanceof RegisterReq || message instanceof LoginMessage) || (message instanceof LoginByTokenReq))) {
					UnLoginSocketManger.getInstance().removeUnLoginSocket(mClientSocket);
					mClientSocket.closeConn();
					return;
				}
				isFirstRead = false;
				if(message instanceof RequestMessage) {
					dealRequest(mClientSocket, message);
				}else if(message instanceof ChatMessage){
					dealChatMessage(mClientSocket, (ChatMessage)message, mTransmitor, communityMessTransmitor);
				}
			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				LogUtil.i(TAG, e.toString());
			}
		});
	}

	protected void dealChatMessage(ClientSocket mClientSocket, ChatMessage message, 
			UserMessageTransmitor mTransmitor, CommunityMessTransmitor communityMessTransmitor) {
		// TODO Auto-generated method stub
		
		if(message instanceof CommunityMessage){
			communityMessTransmitor.transmitMessage((CommunityMessage)message);
		}else if((message instanceof ReplyMessage)){
			communityMessTransmitor.transmitReplyMess((ReplyMessage)message);
		}else if((message instanceof SubReplyMessage)){
			communityMessTransmitor.transmitSubReplyMess((SubReplyMessage)message);
		}else {
			mTransmitor.transmitMessage(message, mClientSocket);
		}
	}

	protected void dealRequest(ClientSocket mClientSocket, BaseMessage message) {
		// TODO Auto-generated method stub
		if (message instanceof LoginMessage) {
			Login(mClientSocket, (LoginMessage) message);
		}else if (message instanceof LoginoutMessage) {
			LoginoutMessage(mClientSocket, (LoginoutMessage) message);
		}else {
			RequestTransmitor.getInstance().transmit((RequestMessage)message, mClientSocket);
		}
	}

	protected void onMessageSendListener(ClientSocket mClientSocket) {
		// TODO Auto-generated method stub
		mClientSocket.setSenderListener(new SocketSender.ISocketSender() {
			
			@Override
			public void onSendFinish(BaseMessage mBaseMessage) {
				// TODO Auto-generated method stub
				if(mBaseMessage instanceof ChatMessage) {
					new UserMessageDao().updateSendStatus( ((ChatMessage)mBaseMessage).getMessageUid(), mClientSocket.getChatId(), true);
				} else if(mBaseMessage instanceof NotifyReplyMess) {
					new NotifyReplyMessDao().updateSendStatus(mBaseMessage.getMessageUid(), 1);
				}else if(mBaseMessage instanceof NotifyFriendReq) {
					AddFriend addFriend = ((NotifyFriendReq) mBaseMessage).getAddFriend();
					new AddFriendDao().updateFriendReqedNotify(addFriend.getReqUserName(), addFriend.getReqedUserName(), addFriend.getReqType(), 1);
				}else if(mBaseMessage instanceof NotifyAcceptFriend) {
					NotifyAcceptFriend message = (NotifyAcceptFriend) mBaseMessage;
					new AddFriendDao().updateFriendReqNotify(message.getReqUserName(), 
							message.getReqedUserName(), message.getType(), 1);
				}else if(mBaseMessage instanceof NotifyChatClosed) {
					new AddFriendDao().updateChatClosedNotify(((NotifyChatClosed)mBaseMessage).getChatUid());
				}else if(mBaseMessage instanceof NotifyHasRecommend) {
					NotifyHasRecommend mess = (NotifyHasRecommend) mBaseMessage;
					if(mess.getRecommendtype() == 1) {
						new RecommendDao().updateMessNotified(mess.getToUserName());
					}else if(mess.getRecommendtype() == 2) {
						new RecommendDao().updateUserNotified(mess.getToUserName());
					}
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
	
	protected void onStatusChangedListener(ClientSocket mClientSocket) {
		mClientSocket.setmOnStatusChangedListener(new ClientSocket.OnStatusChangedListener() {
			
			@Override
			public void onConnClosed() {
				// TODO Auto-generated method stub
				new UserDao().loginOut(mClientSocket.getChatId(), mClientSocket.getDeviceType());
				new CommunityRoomDao().communityMessageUnReq(mClientSocket.getChatId());
				new ReplyMessageDao().replyMessUnReq(mClientSocket.getChatId());
				RemoveClientSocket(mClientSocket);
				UnLoginSocketManger.getInstance().removeUnLoginSocket(mClientSocket);
			}
		});
	}
	
}
