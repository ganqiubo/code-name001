package com.pojul.fastIM.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String getFormatDate() {
		Date ss = new Date();  
		SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format0.format(ss.getTime());
	}
	
	public static String getFormatDate(long milli) {
		Date ss = new Date(milli);  
		SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format0.format(ss.getTime());
	}
	
	
	public static int getAgeByBirthDay(String birthTimeString) {
		try {
			// 先截取到字符串中的年、月、日
			String strs[] = birthTimeString.trim().split("-");
			int selectYear = Integer.parseInt(strs[0]);
			int selectMonth = Integer.parseInt(strs[1]);
			int selectDay = Integer.parseInt(strs[2]);
			// 得到当前时间的年、月、日
			Calendar cal = Calendar.getInstance();
			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH) + 1;
			int dayNow = cal.get(Calendar.DATE);
	 
			// 用当前年月日减去生日年月日
			int yearMinus = yearNow - selectYear;
			int monthMinus = monthNow - selectMonth;
			int dayMinus = dayNow - selectDay;
	 
			int age = yearMinus;// 先大致赋值
			if (yearMinus < 0) {// 选了未来的年份
				age = 0;
			} else if (yearMinus == 0) {// 同年的，要么为1，要么为0
				if (monthMinus < 0) {// 选了未来的月份
					age = 0;
				} else if (monthMinus == 0) {// 同月份的
					if (dayMinus < 0) {// 选了未来的日期
						age = 0;
					} else if (dayMinus >= 0) {
						age = 1;
					}
				} else if (monthMinus > 0) {
					age = 1;
				}
			} else if (yearMinus > 0) {
				if (monthMinus < 0) {// 当前月>生日月
				} else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
					if (dayMinus < 0) {
					} else if (dayMinus >= 0) {
						age = age + 1;
					}
				} else if (monthMinus > 0) {
					age = age + 1;
				}
			}
			return (age - 1);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}
	
	/**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param date
     * @return true <br/>false
     */
    public static boolean isDateOverdue(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(date);
            dt2 = sdf.parse(getFormatDate());
        } catch (Exception e) {
            return true;
        }
        if (dt1.getTime() <= dt2.getTime()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 把long 转换成 日期 再转换成String类型
     */
    public static String convertLongToDate(long milli) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(milli);
        return sdf.format(date);
    }
	
    /**
     * 转换时间日期格式字串为long型
     *
     * @param time 格式为：yyyy-MM-dd HH:mm:ss的时间日期类型
     */
    public static Long convertTimeToLong(String time) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            return System.currentTimeMillis();
        }
    }
    
}
