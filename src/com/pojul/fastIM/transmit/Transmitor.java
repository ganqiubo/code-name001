package com.pojul.fastIM.transmit;

import com.pojul.fastIM.socketmanager.ClientSocketManager;
import com.pojul.objectsocket.message.BaseMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class Transmitor {

	public void transmitMessage(BaseMessage message) {
		synchronized (ClientSocketManager.clientSockets) {
			ClientSocket mClientSocket = ClientSocketManager.clientSockets.get(message.getTo());
			if(mClientSocket != null) {
				mClientSocket.sendData(message);
			}
		}
	}
	
}
