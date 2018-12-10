package com.pojul.fastIM.socketmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.utils.LogUtil;

public class UnLoginSocketManger {
	
	public static HashMap<ClientSocket, Long> unLoginSockets = new HashMap<>();
	public static UnLoginSocketManger unLoginSocketManger;
	public long maxKeepTime = 2 * 60 * 1000;
	private static final String TAG = "UnLoginSocketManger";
	protected boolean stopMonitor = false;
	
	public static UnLoginSocketManger getInstance() {
		if(unLoginSocketManger == null) {
			synchronized (UnLoginSocketManger.class) {
				if(unLoginSocketManger == null) {
					unLoginSocketManger = new UnLoginSocketManger();
					unLoginSocketManger.start();
				}
			}
		}
		return unLoginSocketManger;
	}
	
	public void addUnLoginSocket(ClientSocket clientSocket) {
		synchronized (unLoginSockets) {
			unLoginSockets.put(clientSocket, System.currentTimeMillis());
			LogUtil.i(TAG, "addUnLoginSocket current UnLoginSocket size: " + unLoginSockets.size());
		}
	}
	
	public void removeUnLoginSocket(ClientSocket clientSocket) {
		synchronized (unLoginSockets) {
			unLoginSockets.remove(clientSocket);
			LogUtil.i(TAG, "removedUnLoginSocket current UnLoginSocket size: " + unLoginSockets.size());
		}
	}
	
	public void start(){
		unLoginSocketsMonitor.start();
	}
	
	protected Thread unLoginSocketsMonitor = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!stopMonitor) {
				try {
                    Thread.sleep(maxKeepTime);
                    ArrayList<ClientSocket> keys = getRemovedKey();
    				for(int i = 0; i < keys.size(); i++) {
    					ClientSocket clientSocket = keys.get(i);
    					removeUnLoginSocket(clientSocket);
    					if(clientSocket != null && clientSocket.getmSocket() != null) {
    						clientSocket.closeConn();
    					}
    					LogUtil.i(TAG, "未登录连接超时关闭");
    				}
                } catch (Exception e) {
                    LogUtil.i(getClass().getName(), e.toString());
                    LogUtil.dStackTrace(e);
                }
			}
			
		}
	});
	
	public ArrayList<ClientSocket> getRemovedKey() {
		ArrayList<ClientSocket> clientSockets = new ArrayList<>();
		synchronized (unLoginSockets) {
			for (Entry<ClientSocket, Long> entry : unLoginSockets.entrySet()) {
				if((System.currentTimeMillis() - entry.getValue()) >= maxKeepTime) {
					clientSockets.add(entry.getKey());
				}
				clientSockets.add(entry.getKey());
			}
		}
		return clientSockets;
	}
	
}
