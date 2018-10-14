package com.chuan.mybatis.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTool {
	public static String dateAdd(String date , int add) {
		String resultDate = "";
		try {
			java.util.Date sdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdate);
			calendar.add(Calendar.DATE, add);
			sdate = calendar.getTime();
			resultDate = new SimpleDateFormat("yyyy-MM-dd").format(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}
	public static boolean isWeekend(String bDate)  {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date bdate = null;
		try {
			bdate = format1.parse(bDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else{
            return false;
        }
 
 }
}
