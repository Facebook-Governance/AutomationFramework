/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.omni.pfm.testBase.TestBase;


public class DemoSiteUtil extends TestBase {
	
	Email email = new Email();
	ParseEmail emailParser = new ParseEmail();
	
	public void launchDemoSite() {
		
		String URL = PropsUtil.getEnvPropertyValue("DemoSite_URL").trim();
		d.get(URL);
		SeleniumUtil.waitForPageToLoad();
		d.manage().window().maximize();
	}
	
	public void launchURL(String url) {
		String URL = url.trim();
		d.get(URL);
		SeleniumUtil.waitForPageToLoad();
		d.manage().window().maximize();
	}

	public static String getTomorowsDate() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd");

		String tomorrowAsString = dateFormat.format(tomorrow);
		System.out.println(tomorrowAsString);
		return tomorrowAsString;

	}

	public static String getCurrentDateTime() {

		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
		Date date = new Date();
		String CurrentDateTime = dateFormat.format(date);
		return CurrentDateTime;
	}

	public static String getRandomString(int n) {

		char[] chars = "abcdefghijklmnopqrstuvwxyz123456".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	public static String getRandom10DigitNumber() {

		char[] chars = "1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}
	
	public void gmailDeleteAllEmails(String emailId, String pwd) throws MessagingException {

		email.login(emailId, pwd);

		email.deleteAllMailsFromInbox();

	}

	public String readTextFromEmail(String xPath, String Subject, String contentOfSearch) throws MessagingException, InterruptedException {

		return emailParser.searchContent(email, xPath, Subject,contentOfSearch);

	}

	public static String dateTimeSetter() {

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		DateFormat dateFormat1 = new SimpleDateFormat("hh:mm a zzzz");

		Date date = new Date();

		// Now format the date

		String date1 = dateFormat.format(date);

		String date2 = dateFormat1.format(date);

		String SubmittedValue = date1 + " at " + date2;

		return SubmittedValue;

	}
	
	public static String getBrowserVersion() {

		Capabilities caps = ((RemoteWebDriver) d).getCapabilities();

		String browserVersion = caps.getBrowserName() + " " + caps.getVersion();

		return browserVersion.toLowerCase();

	}

	public static String getOSType() {

		String os = System.getProperty("os.name");
		return os.toLowerCase();

	}

	public static void launchNodeURL() {
		String URL = PropsUtil.getEnvPropertyValue("NodeURL").trim();
		d.get(URL);
	}
	
	

}
