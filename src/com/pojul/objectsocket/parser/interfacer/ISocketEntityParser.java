package com.pojul.objectsocket.parser.interfacer;

import java.io.IOException;

public interface ISocketEntityParser {
	public void onParser(byte[] mBytes) throws IOException;
	
	public void onParserFinish() throws IOException ;

	public void onParserError();
}
