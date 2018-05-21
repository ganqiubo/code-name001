package com.pojul.fastIM.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getFormatDate() {
		Date ss = new Date();  
		SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format0.format(ss.getTime());
	}
	
}
