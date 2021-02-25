package com.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date getThePresentUTCTime() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, -5);
		cal.set(Calendar.MINUTE, -30);
		return cal.getTime();
	}

	public static Date getTheDateForGivenCreatedAtString(String date) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
	}

	public static long getTheDifferenceBetweenTheGivenDatesInMinutes(Date d1, Date d2) {
		long difference_In_Time = d2.getTime() - d1.getTime();
		long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
		return difference_In_Minutes;
	}
}
