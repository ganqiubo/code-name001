package com.pojul.fastIM.socketmanager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.mysql.jdbc.log.Log;
import com.pojul.objectsocket.utils.LogUtil;

public class ServerSocketManager {

	private static ServerSocketManager mServerSocketManager;
	private static Thread ServerSocketThread;
	private static ServerSocket mServerSocketc;
	private static int ServerSocketPort = 57142;
	private static final String TAG = "ServerSocketManager";
	
	public static ServerSocketManager getInstance() {
		if(mServerSocketManager == null) {
			synchronized (ServerSocketManager.class) {
				if(mServerSocketManager == null) {
					mServerSocketManager = new ServerSocketManager();
				}
			}
		}
		return mServerSocketManager;
	}
	
	
	public void startServerSocket() {
		if(mServerSocketc == null || !mServerSocketc.isBound() || mServerSocketc.isClosed()) {
			//while(!bindServerSocket()) {
			bindServerSocket();
			//}
			startAccept();
		}
	}
	
	private boolean bindServerSocket() {
		try {
			//int tempPort = Util.getRandomPort();
			mServerSocketc = new ServerSocket(ServerSocketPort);
			System.out.println("bindServerSocket success");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("bindServerSocket fail: " + e.getMessage());
			return false;
		}
	}
	
	private void startAccept() {
		ServerSocketThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while(true) {
						Socket newClientSocket = mServerSocketc.accept();
						newClientSocket.setSoTimeout(0);
						ClientSocketManager.getInstance().createClientSocket(newClientSocket);
						LogUtil.i(TAG, "receive new socket from ip: " + 
								newClientSocket.getInetAddress() + " ;port: " + 
								newClientSocket.getPort());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		});
		ServerSocketThread.start();
	}
	
}
