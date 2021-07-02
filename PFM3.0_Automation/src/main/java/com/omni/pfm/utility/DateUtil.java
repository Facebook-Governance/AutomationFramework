/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DateUtil {

	public static String date(int i)

	{

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, i);

		return (dateFormat.format(cal.getTime()));

	}

	public static String getDate(String format, int i)

	{

		DateFormat dateFormat = new SimpleDateFormat(format);

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, i);

		return (dateFormat.format(cal.getTime()));

	}

	public static String getMonthDate(String format, int i)

	{

		DateFormat dateFormat = new SimpleDateFormat(format);

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, i);

		return (dateFormat.format(cal.getTime()));

	}

	public static String getFirstDateOfMonth(String format, int i)

	{

		DateFormat dateFormat = new SimpleDateFormat(format);

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

		cal.add(Calendar.MONTH, i);

		cal.set(Calendar.DATE, 1);

		return (dateFormat.format(cal.getTime()));

	}

	public static String getLastDateOfMonth(String format, int i)

	{

		DateFormat dateFormat = new SimpleDateFormat(format);

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, i);

		cal.set(Calendar.DATE, 1);

		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		return (dateFormat.format(cal.getTime()));

	}

	public static String getCurrentWeekDates(String format, int i)

	{

		DateFormat dateFormat = new SimpleDateFormat(format);

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

		cal.add(Calendar.DATE, i);

		return (dateFormat.format(cal.getTime()));

	}

	public static String getYearDates(String format, int x)

	{

		DateFormat year = new java.text.SimpleDateFormat("yyyy");

		String y = year.format(new Date());

		int i = Integer.parseInt(y);

		DateFormat dateFormat = new SimpleDateFormat(format);

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, i - 1);

		if (x == 0)

		{

			cal.set(Calendar.YEAR, i);

			cal.set(Calendar.MONTH, 0);

			cal.set(Calendar.DAY_OF_MONTH, 1);

		}

		else if (x == 1)

		{

			cal.set(Calendar.YEAR, i);

			cal.set(Calendar.MONTH, 11);

			cal.set(Calendar.DAY_OF_MONTH, 31);

		}

		else if (x == -1)

		{

			cal.set(Calendar.MONTH, 0);

			cal.set(Calendar.DAY_OF_MONTH, 1);

		}

		else if (x == -2)

		{

			cal.set(Calendar.MONTH, 11);

			cal.set(Calendar.DAY_OF_MONTH, 31);

		}

		return (dateFormat.format(cal.getTime()));

	}

	public int generateRandomNum()

	{

		return ((int) (10000000.0 * Math.random()));

	}

	/**
	 * Convert the Current Date into the given format and time zone
	 * 
	 * @param format
	 *            - Format for the date to be converted e.g., "MMMM dd, YYYY" =
	 *            August 01, 2016
	 * @param timeZone
	 *            - TimeZone to convert from system time zone e.g., GMT-8:00 ,
	 *            IST+5:30
	 * @return String of the date converted into the given time zone and format
	 */
	public static String convertToTimeZone(String format, String timeZone) {
		SimpleDateFormat formatter;
		if (!GenericUtil.isNull(format)) {
			formatter = new SimpleDateFormat(format);
		} else
			formatter = new SimpleDateFormat();
		if (!GenericUtil.isNull(timeZone)) {
			formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		}
		return formatter.format(new Date());
	}

	public static String formatCurrentDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}

	public static String getMonthYearFromDate(String date) {

		DateFormat inputDF = new SimpleDateFormat("MM/dd/yyyy");

		String val = "";

		try {

			Date inpDate = inputDF.parse(date);

			Calendar cal = Calendar.getInstance();

			cal.setTime(inpDate);

			val = new SimpleDateFormat("MMM").format(cal.getTime()) + " " + cal.get(Calendar.YEAR);

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return val;

	}

	// Anindita

	public static String currentYear()

	{

		DateFormat dateFormat = new SimpleDateFormat("yyyy");

		Calendar cal = Calendar.getInstance();

		cal.get(Calendar.YEAR);

		return (dateFormat.format(cal.getTime()));

	}

	// Lavanya

	public static String getPreviousMonth() {
		DateFormat dateFormat = new SimpleDateFormat("MMM");

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, -1);

		return (dateFormat.format(cal.getTime()));
	}

	public static String getDate() {
		Date dNow = new Date();

		SimpleDateFormat ft = new SimpleDateFormat(PropsUtil.getDataPropertyValue("DateFormat"));

		Calendar c = Calendar.getInstance();

		c.add(Calendar.DATE, 2);

		return ft.format(dNow).toString();
	}

	public static String getMonthsWithYear(int i) {
		DateFormat dateFormat = new SimpleDateFormat("MMM YYYY");

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, i);

		return (dateFormat.format(cal.getTime()));
	}

	public static String get_Month() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MMMMMMMMM");
		String month = formatter.format(date);
		String parts = month.substring(0, 3);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String curMonth = parts + " " + year;
		System.out.println("Current Month is:" + curMonth);
		return curMonth;
	}
	
	public static String get_CurrentMonth_Year() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM YYYY");
		String month = formatter.format(date);
		String curMonth = month;
		System.out.println("Current Month and Year is:" + curMonth);
		return curMonth;
	}
	
	
	public static String getMonthName(String s1) throws ParseException {
	    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
	    SimpleDateFormat sdf1 = new SimpleDateFormat("MMM");
	    Date d = sdf.parse(s1);
	    String s = sdf1.format(d);
	    return s;
	  }
	

	public static String getPrevDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,0);
		return dateFormat.format(cal.getTime());
	}
	
	
	public static String getNextDayDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,1);
		return dateFormat.format(cal.getTime());
	}
	
	
	public static String getNextMonthDateFromCurrentMonth() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH,1);
		return dateFormat.format(cal.getTime());
	}
	
	
	
	//Abhinandan-SUSTQA

	public static String getWeekDate(int weekNumber, int DAY,String dateFormat) throws ParseException {

		String finalDate;

		Calendar calendar = Calendar.getInstance();               
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH,month-1);
		calendar.set(Calendar.DAY_OF_MONTH,1);  
		calendar.set(Calendar.WEEK_OF_MONTH,weekNumber);
		int day = (DAY-calendar.get(Calendar.DAY_OF_WEEK)); 

		if(day<0){
			calendar.add(Calendar.DATE,7+(day));
		}else{
			calendar.add(Calendar.DATE,day);
		}
		System.out.println("First date is "+sdf.format(calendar.getTime()));


		return finalDate=sdf.format(calendar.getTime());
	}

	/*@abhi - SUSTQA
	 * 
	 * Method to return list of previous months name ,based on number of months as a parameter
	 */
	
	public static List<String> givenListOfPreviousMonth(int noOFmonths,String monthLength){
		
		YearMonth thisMonth    = YearMonth.now();
		List<String> monthsList = new ArrayList<String>();
		DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern(monthLength);	
	    
		for(int i=0;i<=noOFmonths;i++){
			
			monthsList.add(thisMonth.minusMonths(i).format(monthYearFormatter).toString());
			
		}
		return monthsList;
		
	}
	

	public static String convertDateFormat(String oldDateFormat,String newDateFormat,String Inpudate) throws ParseException{

		String outPut;

		DateFormat srcDf = new SimpleDateFormat(oldDateFormat);

		// parse the date string into Date object
		Date date = srcDf.parse(Inpudate);

		DateFormat destDf = new SimpleDateFormat(newDateFormat);

		// format the date into another format
		outPut = destDf.format(date);


		return outPut;

	}

	//Added by Mallika for Budget Summary
	
		public static String getMonth(int month) {
			DateFormat dateFormat = new SimpleDateFormat("MMM");

			Calendar cal = Calendar.getInstance();

			cal.add(Calendar.MONTH, month);

			return (dateFormat.format(cal.getTime()));
		} 

}
