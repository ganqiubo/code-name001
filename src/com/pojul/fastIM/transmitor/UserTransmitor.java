package com.pojul.fastIM.transmitor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.pojul.fastIM.dao.ChatRoomDao;
import com.pojul.fastIM.dao.ChatRoomMembersDao;
import com.pojul.fastIM.dao.MessageDao;
import com.pojul.fastIM.entity.ChatRoomMembers;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.message.chat.ChatMessage;
import com.pojul.fastIM.socketmanager.ClientSocketManager;
import com.pojul.fastIM.utils.Constant;
import com.pojul.objectsocket.message.BaseMessage;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.utils.LogUtil;

import sun.text.resources.cldr.om.FormatData_om;

public class UserTransmitor {

	private final static String TAG = "UserTransmitor";
	private MessageDao mmMessageDao = new MessageDao();
			
	public void transmitMessage(ChatMessage message) {
		synchronized (ClientSocketManager.clientSockets) {
			if(message.getChatType() == Constant.CHAT_TYPE_SINGLE) {
				String chatRoomUid = createSingleChatRoom(message);
				transmitSingle(message, message.getTo(), chatRoomUid);
			} else {
				transmitMultiply(message);
			}
			
			//dnsURLContext
			
			/*ClientSocket mClientSocket = ClientSocketManager.clientSockets.get(message.getTo());
			if(mClientSocket != null) {
				mClientSocket.sendData(message);
			}*/
			/*String chatRoomId = getChatRoomId(from, message.getTo(), message.getChatType());
			if(chatRoomId == null) {
				LogUtil.i(TAG, "非法的聊天消息");
				return;
			}*/
		}
	}

	private void transmitMultiply(ChatMessage message) {
		// TODO Auto-generated method stub
		if(message.getChatType() != Constant.CHAT_TYPE_MULTIPLE) {
			LogUtil.i(TAG, "非群聊消息");
			return;
		}
		ChatRoomDao mChatRoomDao = new ChatRoomDao();
		String chatRoomUid = message.getTo();
		int chatRoomId = mChatRoomDao.getChatRoomId(chatRoomUid);
		if(chatRoomId < 0) {
			LogUtil.i(TAG, "群聊室不存在");
			return;
		}
		ChatRoomMembers mChatRoomMembers = new ChatRoomMembersDao().getChatRoomMembers(chatRoomId);
		List<User> users = mChatRoomMembers.getUsers();
		if(users == null) {
			return;
		}
		for(int i =0 ; i < users.size(); i++) {
			User user = users.get(i);
			transmitSingle(message, user.getUserName(), chatRoomUid);
		}
	}

	private String createSingleChatRoom(ChatMessage message) {
		// TODO Auto-generated method stub
		String chatRoomUid = getChatRoomUid(message);
		if(chatRoomUid == null) {
			return null;
		}
		ChatRoomDao mChatRoomDao = new ChatRoomDao();
		if(!mChatRoomDao.chatRoomExits(chatRoomUid)) {
			mChatRoomDao.CreateSingleChatRoom(chatRoomUid, message.getFrom() + "_" + message.getTo());
		}
		return chatRoomUid;
	}

	private void transmitSingle(ChatMessage message, String to, String chatRoomUid) {
		// TODO Auto-generated method stub
		if( to == null || "".equals(to) ) {
			LogUtil.i(TAG, "非法的聊天消息");
			return;
		}
		if(to.equals(message.getFrom())) {
			return;
		}
		LinkedHashMap<String, HashMap<String, ClientSocket>> tempclientSockets = ClientSocketManager.clientSockets;
		HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(to);
		if(mClientSockets != null && mClientSockets.size() > 0) {
			for ( Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
				ClientSocket mClientSocket = entity.getValue();
				if(mClientSocket != null) {
					mClientSocket.sendData(message);
				}
			}
		}
		//保存转发用户消息
		//System.out.println("insertMesage: " + to);
		mmMessageDao.insertMesage(message, false, to, chatRoomUid);
	}

	private String getChatRoomUid(ChatMessage message) {
		// TODO Auto-generated method stub
		String from = message.getFrom();
		String to = message.getTo();
		int chatType = message.getChatType();
		if(from == null || from.equals(to)) {
			return null;
		}
		if(to== null || "".equals(to)) {
			return null;
		}
		if(chatType == Constant.CHAT_TYPE_SINGLE) {
			return from.compareTo(to) >= 0 ? (from + "_" + to) : (to + "_" + from);
		}else {
			return to;
		}
	}
	
}
