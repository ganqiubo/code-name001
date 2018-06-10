package com.pojul.fastIM.transmitor;

import java.util.HashMap;
import com.pojul.fastIM.requestprocessor.GetConversionInfoProcessor;
import com.pojul.fastIM.requestprocessor.GetFriendsProcessor;
import com.pojul.fastIM.requestprocessor.RequestProcessor;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.utils.LogUtil;

public class RequestTransmitor {

	private static RequestTransmitor mRequestTransmitor;

	public static RequestTransmitor getInstance() {
		if (mRequestTransmitor == null) {
			synchronized (RequestTransmitor.class) {
				if (mRequestTransmitor == null) {
					mRequestTransmitor = new RequestTransmitor();
				}
			}
		}
		return mRequestTransmitor;
	}
	
	public void transmit(RequestMessage request, ClientSocket clientSocket){
		 Class<?> clazz;
		try {
			clazz = Class.forName(urlClasses.get(request.getRequestUrl()));
			((RequestProcessor)clazz.newInstance()).process(request, clientSocket);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			LogUtil.i(getClass().getName(), "no RequestProcessor found: " + e.getMessage());
			LogUtil.dStackTrace(e);
		}
	}
	
	public static HashMap<String, String> urlClasses = new HashMap<String, String>(){{
		put("GetFriends", GetFriendsProcessor.class.getName());
		put("GetConversionInfo", GetConversionInfoProcessor.class.getName());
	}};
	
}
