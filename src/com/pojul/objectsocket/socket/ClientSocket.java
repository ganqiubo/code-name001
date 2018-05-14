package com.pojul.objectsocket.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.utils.DaoUtil;
import com.pojul.objectsocket.message.BaseMessage;

public class ClientSocket {

	protected Socket mSocket;
	protected SocketReceiver mSocketReceiver;
	protected SocketReceiver.ISocketReceiver recListener;
	protected SocketSender mSocketSender;
	protected SocketSender.ISocketSender senderListener;
	protected String chatId;
	
	public ClientSocket(String host, int port) throws UnknownHostException, IOException  {
		super();
		// TODO Auto-generated constructor stub
		mSocket = new Socket(host, port);
		mSocketSender = new SocketSender(mSocket, this);
		mSocketReceiver = new SocketReceiver(mSocket, this);
	}

	public ClientSocket(Socket mSocket) {
		super();
		this.mSocket = mSocket;
		mSocketSender = new SocketSender(mSocket, this);
		mSocketReceiver = new SocketReceiver(mSocket, this);
	}
	
	public Socket getmSocket() {
		return mSocket;
	}
	public void setmSocket(Socket mSocket) {
		this.mSocket = mSocket;
	}

	public void sendData(BaseMessage data) {
		if(mSocketSender == null) {
			mSocketSender = new SocketSender(mSocket, this);
		}
		mSocketSender.sendMessage(data);
	}
	
	public void sendDataAndClose(BaseMessage data) {
		if(mSocketSender == null) {
			mSocketSender = new SocketSender(mSocket, this);
		}
		mSocketSender.sendMessage(data);
		mSocketSender.closeConnWhenFinish();
	}
	
	public void setSenderListener(SocketSender.ISocketSender mISocketSender) {
		this.senderListener = mISocketSender;
		if(mSocketSender != null) {
			mSocketSender.setSenderListener(mISocketSender);
		}
	}
	
	public void setRecListener(SocketReceiver.ISocketReceiver mISocketReceiver) {
		this.recListener = mISocketReceiver;
		if(recListener != null) {
			mSocketReceiver.setReceiverListener(mISocketReceiver);
		}
	}
	
	public void closeConn() {
		if(mSocket != null) {
			new UserDao().loginOut(chatId);
			try {
				stopRec();
				stopSend();
				mSocket.shutdownInput();
				mSocket.shutdownOutput();
				mSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				if(recListener != null) {
					recListener.onError(e);
				}
			}
			mSocket = null;
		}
	}
	
	public void stopRec() {
		if(mSocketReceiver != null) {
			mSocketReceiver.stopReceive();
		}
	}
	
	public void stopSend() {
		mSocketSender.stopSend();
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	
}
