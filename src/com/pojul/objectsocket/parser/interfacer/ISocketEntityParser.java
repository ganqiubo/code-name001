package com.pojul.objectsocket.parser.interfacer;

import java.io.IOException;

import com.pojul.objectsocket.message.BaseMessage;

public interface ISocketEntityParser {
	public void onParser(byte[] mBytes, BaseMessage message, long totalLength) throws IOException;
	
	public void onParserFinish(BaseMessage message) throws IOException ;

	public void onParserError(BaseMessage message, Exception e);
}
