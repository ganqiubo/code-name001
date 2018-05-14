package com.pojul.fastIM.utils;

import java.util.UUID;

public class Util {

	public static String getChatId() {
		return (System.currentTimeMillis() + UUID.randomUUID().toString());
	}
	
	
}
