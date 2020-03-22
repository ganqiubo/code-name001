package com.pojul.fastIM.transmitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.tomcat.jni.File;

import com.pojul.fastIM.dao.CommunityMessEntityDao;
import com.pojul.fastIM.dao.CommunityRoomDao;
import com.pojul.fastIM.dao.ManageNotifyUsersDao;
import com.pojul.fastIM.dao.NotifyReplyMessDao;
import com.pojul.fastIM.dao.RecommendDao;
import com.pojul.fastIM.dao.ReplyMessageDao;
import com.pojul.fastIM.dao.SubReplyMessageDao;
import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.dao.UserFilterDao;
import com.pojul.fastIM.entity.ReplyReqUsers;
import com.pojul.fastIM.entity.StringVal;
import com.pojul.fastIM.entity.TagMessInfo;
import com.pojul.fastIM.entity.User;
import com.pojul.fastIM.message.chat.ChatMessage;
import com.pojul.fastIM.message.chat.CommunityMessage;
import com.pojul.fastIM.message.chat.ReplyMessage;
import com.pojul.fastIM.message.chat.SubReplyMessage;
import com.pojul.fastIM.message.chat.TagCommuMessage;
import com.pojul.fastIM.message.other.NotifyHasRecommend;
import com.pojul.fastIM.message.other.NotifyManagerNotify;
import com.pojul.fastIM.message.other.NotifyReplyMess;
import com.pojul.fastIM.socketmanager.ClientSocketManager;
import com.pojul.fastIM.utils.DateUtil;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.utils.LogUtil;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;

public class CommunityMessTransmitor {

	public void transmitMessage(CommunityMessage message) {
		if(message.getTo() == null || message.getTo().split("_").length != 2 || message.getFrom() == null) {
			LogUtil.i("非法的社区消息");
			return;
		}
		new CommunityMessEntityDao().insertMessage(message);
		if(message instanceof TagCommuMessage) {
			transmitTag((TagCommuMessage)message);
		}else {
			transmitNormal(message);
		}
		
	}

	private void transmitTag(TagCommuMessage message) {
		// TODO Auto-generated method stub
		List<User> reqUsers = new UserFilterDao().commuRoomUserFilter(message.getUserFilter(), message.getTo());//new CommunityRoomDao().queryReqUsers(message.getTo());
		if(reqUsers != null) {
			for(int i =0; i<reqUsers.size(); i++) {
				User toUser = reqUsers.get(i);
				if(toUser.getUserName().equals(message.getFrom())) {
					continue;
				}
				HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(toUser.getUserName());
				if(mClientSockets != null && mClientSockets.size() > 0) {
					for ( Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
						ClientSocket mClientSocket = entity.getValue();
						if(mClientSocket != null) {
							mClientSocket.sendData(message);
						}
					}
				}
			}
		}
		
		if(message.getUserFilter() != null && message.getUserFilter().isFilterEnabled() && 
				message.getUserFilter().isWhiteListEnabled() && message.getUserFilter().getWhiteListNames().size() > 0){
			List<String> whites = message.getUserFilter().getWhiteListNames();
			new RecommendDao().insertRecommendMess(message.getMessageUid(), whites, message.getFrom());
			for (int i = 0; i < whites.size(); i++) {
				String userName = whites.get(i);
				if(userName == null) {
					continue;
				}
				HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(userName);
				if(mClientSockets != null && mClientSockets.size() > 0) {
					for (Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
						ClientSocket mClientSocket = entity.getValue();
						if(mClientSocket != null) {
							NotifyHasRecommend notifyHasRecommend = new NotifyHasRecommend();
							notifyHasRecommend.setRecommendtype(1);
							notifyHasRecommend.setToUserName(userName);
							mClientSocket.sendData(notifyHasRecommend);
						}
					}
				}
			}
		}
		
		checkManagerNotify(message);
		
	}

	private void checkManagerNotify(TagCommuMessage message) {
		// TODO Auto-generated method stub
		/*if(message.getLabels() == null) {
			return;
		}
		boolean isNotifyMess = false;
		for (int i = 0; i < message.getLabels().size(); i++) {
			if("公告通知".equals(message.getLabels().get(i))) {
				isNotifyMess = true;
				break;
			}
		}
		if(!isNotifyMess) {
			return;
		}
		if(new CommunityRoomDao().isManager(message.getFrom(), message.getuid)) {
			
		}*/
		if(!message.isManagerNotrify()) {
			return;
		}
		List<StringVal> users = new UserDao().getManagerNotifyUsers(message.getTo(), message.getLevel());
		NotifyManagerNotify notify = new NotifyManagerNotify();
		notify.setFrom(message.getFrom());
		notify.setManager(message.getFrom());
		notify.setManagerNickname(message.getNickName());
		notify.setCommuRoomUid(message.getTo());
		notify.setMessUid(message.getMessageUid());
		notify.setMessageTitle(message.getTitle());
		notify.setTimeMill(System.currentTimeMillis());
		notify.setManagerPhoto(message.getPhoto().getFilePath());
		for (int i = 0; i < users.size(); i++) {
			String user = users.get(i).getValue();
			notify.setTo(user);
			notify.setUserName(user);
			if(message.getFrom().equals(user)) {
				continue;
			}
			HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(user);
			if(mClientSockets != null && mClientSockets.size() > 0) {
				for (Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
					ClientSocket mClientSocket = entity.getValue();
					if(mClientSocket != null) {
						mClientSocket.sendData(notify);
					}
				}
			}else {
				new ManageNotifyUsersDao().insertNotify(notify);
			}
		}
	}

	private void transmitNormal(CommunityMessage message) {
		// TODO Auto-generated method stub
		List<User> reqUsers = new CommunityRoomDao().queryReqUsers(message.getTo());
		if(reqUsers == null) {
			return;
		}
		for(int i =0; i<reqUsers.size(); i++) {
			User toUser = reqUsers.get(i);
			if(toUser.getUserName().equals(message.getFrom())) {
				continue;
			}
			HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(toUser.getUserName());
			if(mClientSockets != null && mClientSockets.size() > 0) {
				for ( Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
					ClientSocket mClientSocket = entity.getValue();
					if(mClientSocket != null) {
						mClientSocket.sendData(message);
					}
				}
			}
		}
		
	}

	public void transmitReplyMess(ReplyMessage message) {
		// TODO Auto-generated method stub
		if(message.getMessageUid() == null || message.getFrom() == null || message.getReplyMessageUid() == null) {
			LogUtil.i("非法的回复消息");
			return;
		}
		
		ReplyMessageDao replyMessageDao = new ReplyMessageDao();
		replyMessageDao.insertReplyMess(message);
		List<ReplyReqUsers> reqUsers = replyMessageDao.getReqMessUsers(message.getReplyMessageUid());
		TagMessInfo owner = replyMessageDao.getRepliedTagOwner(message.getReplyMessageUid());
		if(reqUsers == null || reqUsers.size() <= 0) {
			notifyOwner(owner, message);
			return;
		}
		boolean containOwner = false;
		for(int i =0; i< reqUsers.size(); i++) {
			String toUser = reqUsers.get(i).getUserName();
			if(toUser == null || toUser.equals(message.getFrom())) {
				continue;
			}
			if(toUser.equals(owner.getUserName())) {
				containOwner = true;
			}
			HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(toUser);
			if(mClientSockets != null && mClientSockets.size() > 0) {
				for ( Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
					ClientSocket mClientSocket = entity.getValue();
					if(mClientSocket != null) {
						mClientSocket.sendData(message);
					}
				}
			}
		}
		if(!containOwner) {
			notifyOwner(owner, message);
		}
	}

	private void notifyOwner(TagMessInfo owner, ReplyMessage message) {
		// TODO Auto-generated method stub
		if(owner == null || owner.getIsEffective() == 1 || owner.getTagMess() == null) {
			return;
		}
		NotifyReplyMess notifyReplyMess = new NotifyReplyMess();
		notifyReplyMess.setFrom(message.getFrom());
		notifyReplyMess.setTo(owner.getUserName());
		notifyReplyMess.setSendTime(DateUtil.getFormatDate());
		notifyReplyMess.setIsSend(0);
		notifyReplyMess.setReplyTagMessUid(message.getReplyMessageUid());
		notifyReplyMess.setReplyText(message.getText());
		notifyReplyMess.setTagMessTitle(owner.getTagMess().getTitle());
		notifyReplyMess.setReplyNickName(message.getNickName());
		notifyReplyMess.setReplyType(0);
		notifyReplyMess.setPhoto(message.getPhoto().getFilePath());
		new NotifyReplyMessDao().insertNotify(notifyReplyMess);
		
		HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(owner.getUserName());
		if(mClientSockets != null && mClientSockets.size() > 0) {
			for ( Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
				ClientSocket mClientSocket = entity.getValue();
				if(mClientSocket != null) {
					mClientSocket.sendData(notifyReplyMess);
				}
			}
		}
	}

	public void transmitSubReplyMess(SubReplyMessage message) {
		// TODO Auto-generated method stub
		if(message.getMessageUid() == null || message.getFrom() == null || message.getReplyMessageUid() == null) {
			LogUtil.i("非法的回复消息");
			return;
		}
		new SubReplyMessageDao().insertSubReplyMess(message);
		ReplyMessageDao replyMessageDao = new ReplyMessageDao();
		List<ReplyReqUsers> reqUsers = replyMessageDao.getReqMessUsers(message.getReplyTagMessUid());
		if(reqUsers == null || reqUsers.size() <= 0) {
			return;
		}
		for(int i =0; i< reqUsers.size(); i++) {
			String toUser = reqUsers.get(i).getUserName();
			if(toUser == null || toUser.equals(message.getFrom())) {
				continue;
			}
			HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(toUser);
			if(mClientSockets != null && mClientSockets.size() > 0) {
				for ( Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
					ClientSocket mClientSocket = entity.getValue();
					if(mClientSocket != null) {
						mClientSocket.sendData(message);
					}
				}
			}
		}
	}
	
}
