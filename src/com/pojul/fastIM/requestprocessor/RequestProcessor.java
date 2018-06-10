package com.pojul.fastIM.requestprocessor;

import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public interface RequestProcessor {

	public void process(RequestMessage request, ClientSocket clientSocket);
	
}
