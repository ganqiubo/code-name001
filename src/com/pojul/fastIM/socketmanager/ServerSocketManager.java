package com.pojul.fastIM.socketmanager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
					/*try {
						System.setOut(new PrintStream(new FileOutputStream("D:\\eclipse_console\\logs\\log.txt")));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					mServerSocketManager = new ServerSocketManager();
				}
			}
		}
		return mServerSocketManager;
	}
	
	
	public void startServerSocket() {
		if(mServerSocketc == null || !mServerSocketc.isBound() || mServerSocketc.isClosed()) {
			while(!bindServerSocket()) {
				bindServerSocket();
			}
			startAccept();
		}
	}
	
	private boolean bindServerSocket() {
		try {
			//int tempPort = Util.getRandomPort();
			mServerSocketc = new ServerSocket(ServerSocketPort);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
						ClientSocketManager.getInstance().createClientSocket(newClientSocket);
						LogUtil.i(TAG, "receive new socket from ip: " + 
								newClientSocket.getInetAddress() + " ;port: " + 
								newClientSocket.getPort());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		ServerSocketThread.start();
	}
	
}
