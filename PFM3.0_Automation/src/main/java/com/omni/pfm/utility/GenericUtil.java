/**
 *
 */
package com.omni.pfm.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.constants.PropertyConstants;

/**
 * Copyright (c) 2019 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author sbhat1
 */
public class GenericUtil {

	private static final Logger logger = LoggerFactory.getLogger(GenericUtil.class);

	public static boolean isNull(String s) {
		if (s == null || s.trim().equals("")) {
			return true;
		} else
			return false;
	}

	/**
	 * Check if all the params are not null and String params are not empty
	 * 
	 * @param params VarArg parameters that need to be verified.
	 * @return true if all the parameters passed are not null and String params are
	 *         not empty.
	 */
	public static boolean checkParamsNotNull(Object... params) {
		logger.info("==> Entering checkAllParamsNotNull method");
		boolean status = true;
		int index = 0;
		for (Object param : params) {
			if (param == null) {
				logger.error("Param [{}] is null", index);
				status = false;
			} else {
				if (param instanceof String) {
					if (((String) param).trim().equals("")) {
						logger.error("String Param [{}] is empty", index);
						status = false;
					}
				}
			}
			index++;
		}
		logger.info("<== Exiting checkAllParamsNotNull method..");
		return status;
	}

	public static void writeUserToFile(String env, String userName) {
		logger.info("==> Entering writeUserToFile method.");
		final String newLine = System.getProperty("line.separator");
		String environment = "";
		String envUrl = null;
		if ("no".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_SAML_LOGIN"))) {
			envUrl = PropsUtil.getEnvPropertyValue("cnf_base_url");
		} else {
			envUrl = PropsUtil.getEnvPropertyValue("SAML_BASE_URL");
		}

		if (envUrl.contains("autonprmc") && envUrl.contains("ymcbeta")) {
			environment = "Auto Dev";
		} else if (envUrl.contains("autopfmc")) {
			environment = "Auto NPR";
		} else if (envUrl.contains("mainhtmlmc")) {
			environment = "Main Line";
		} else if (envUrl.contains("autostab")) {
			environment = "Auto Stab";
		} else if (envUrl.contains("test2")) {
			environment = "Stage";
		}

		if (checkParamsNotNull(env, userName)) {
			logger.debug("Env = [{}], Username = [{}]", env, userName);
			PrintWriter pw = null;
			File dir = null;
			File file;
			try {
				dir = new File(
						PropertyConstants.OUTPUT_FOLDER + File.separator + PropertyConstants.USER_DIRECTORY_PATH);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				file = new File(dir.getAbsolutePath() + File.separator + env + ".txt");
				if (file.createNewFile()) {
					logger.info("File does not exist. Creating new File.");
				}
				logger.info("File Path is : {}", file.getAbsoluteFile());
				if (file != null) {
					// check if file already has user added
					if (!checkIfStringPressentInFile(file, userName)) {
						file.setWritable(true);
						pw = new PrintWriter(new FileOutputStream(file, true));
						if (pw != null) {
							pw.append(userName + "\t" + environment + newLine);
							logger.info("Added user [{}] to the file.", userName);
						}
					}
				}

			} catch (IOException e) {
				logger.error("Exception while writing user to file : {}", e);
			} finally {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			}
		} else {
			logger.error("Environment or username is null or empty.");
		}
		logger.info("<== Entering writeUserToFile method.");
	}

	public static String formatDate(String format, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static void main(String[] args) {
		writeUserToFile("ycom", "user1");
	}

	public static boolean checkIfStringPressentInFile(File file, String searchQuery) {
		if (file != null && searchQuery != null) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.contains(searchQuery.trim())) {
						return true;
					} else {
					}
				}
			} catch (Exception ex) {
				logger.error("Error while reading file: {}", ex.toString());
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (Exception e) {
					logger.error("Exception while closing bufferedreader: " + e.toString());
				}
			}
		}
		return false;
	}

	public static int getRandomNumber(int range) {
		Random random = new Random();
		int number = random.nextInt(range);
		return number;
	}

	/**
	 * Add's or subtract's given number of days from given date object
	 * 
	 * @param Date
	 * @param dateOperations
	 * @param count
	 * @return
	 * @author kongara.sravan
	 */
	public static Date modifyTheGivenDate(Date Date, dateOperations dateOperations, dateParameters dateParameters,
			int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(Date);
		count = dateOperations == dateOperations.ADDITION ? count : -count;
		switch (dateParameters) {
		case Days:
			c.add(Calendar.DATE, count);
			break;
		case Hours:
			c.add(Calendar.HOUR_OF_DAY, count);
			break;
		case Minutes:
			c.add(Calendar.MINUTE, count);
			break;
		case Months:
			c.add(Calendar.MONTH, count);
			break;
		case Years:
			c.add(Calendar.YEAR, count);
			break;
		default:
			break;
		}
		return c.getTime();
	}

	public enum dateOperations {
		ADDITION, SUBTRACTION;
	}

	public enum dateParameters {
		Days, Minutes, Months, Years, Hours;
	}

	public static String returnGivenDateInGivenFormat(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static ArrayList<String> returnSaturdaysBetweenFromGivenDateToToday(Date date) {
		String weekDateFormatInChart = "MMM dd";
		ArrayList<String> saturdaysPresent = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		Date nextSaturdayDate = modifyTheGivenDate(date, dateOperations.ADDITION, dateParameters.Days, 7 - dayOfWeek);
		Date todayDate = new Date();
		if (nextSaturdayDate.after(todayDate) || nextSaturdayDate.equals(todayDate)) {
			saturdaysPresent.add(returnGivenDateInGivenFormat(date, weekDateFormatInChart));
			saturdaysPresent.add(returnGivenDateInGivenFormat(todayDate, weekDateFormatInChart));
			return saturdaysPresent;
		}
		Date updatedDate = nextSaturdayDate;
		Date modifiedDate = updatedDate;
		do {
			String saturday = returnGivenDateInGivenFormat(updatedDate, weekDateFormatInChart);
			saturdaysPresent.add(saturday);
			modifiedDate = updatedDate;
			updatedDate = modifyTheGivenDate(updatedDate, dateOperations.ADDITION, dateParameters.Days, 7);
		} while (!updatedDate.after(todayDate));
		if (modifiedDate.before(todayDate)) {
			String saturday = returnGivenDateInGivenFormat(todayDate, weekDateFormatInChart);
			saturdaysPresent.add(saturday);
		}
		return saturdaysPresent;
	}

	public static ArrayList<String> returnMonthsBetweenFromGivenDateToToday(Date date) {
		String firstMonthDateFormatInChart = "MMM yyyy";
		String nextMonthsDateFormatInChart = "MMM";
		ArrayList<String> months = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date todaysDate = new Date();
		Date updatedDate = date;
		months.add(returnGivenDateInGivenFormat(updatedDate, firstMonthDateFormatInChart));
		updatedDate = modifyTheGivenDate(updatedDate, dateOperations.ADDITION, dateParameters.Months, 1);
		while (!updatedDate.after(todaysDate)) {
			months.add(returnGivenDateInGivenFormat(updatedDate, nextMonthsDateFormatInChart));
			updatedDate = modifyTheGivenDate(updatedDate, dateOperations.ADDITION, dateParameters.Months, 1);
		}
		return months;
	}

	public static int getNumberOfDaysInPreviousMonth(int month) {
		Date date = modifyTheGivenDate(new Date(), dateOperations.SUBTRACTION, dateParameters.Months, month);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		logger.info("Date is set to :: " + c.getTime());
		logger.info("Maximum number of days in given month is :: " + c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}
