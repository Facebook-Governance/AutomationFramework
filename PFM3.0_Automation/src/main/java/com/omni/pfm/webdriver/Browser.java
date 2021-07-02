/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.webdriver;

import java.io.*;
/*

 * Bean representing a browser. It contains name, version and platform fields.

 * 

 * @author Ashish Sadhwani

 */

public class Browser {

	private String browsername;

	private String browserVersion;

	private String platformversion;

	private String platformname;

	private String automationname;

	private File apppath;

	private String devicename;

	private String app_package;

	private String app_activity;

	private String browserStackFlag;
	
	private String bs_username;
	
	private String bs_automateKey;
	
	
	private String elpamFlag;
    
	// This string variable should take only these values DESKTOP, TABLET,
	// MOBILE

	private String automationFlag;
	private String appFlag;

	public void AppInitialize(String platform, String platformversion,
			String autoname, File app_path, String device_name, String app_pkg,
			String app_act) {

		this.browsername = "";

		this.platformname = platform;

		this.platformversion = platformversion;

		this.automationname = autoname;

		this.apppath = app_path;

		this.app_activity = app_act;

		this.app_package = app_pkg;

		this.devicename = device_name;

	}

	public void AppInitialize(String platform, String platformversion,
			String autoname, File app_path, String device_name) {

		this.browsername = "";

		this.platformname = platform;

		this.platformversion = platformversion;

		this.automationname = autoname;

		this.apppath = app_path;

		this.devicename = device_name;

	}

	public void BrowserInitialize(String browsername, String platform,
			String platformversion, String device_name) {

		this.browsername = browsername;

		this.platformname = platform;

		this.platformversion = platformversion;

		// this.automationname = autoname;

		this.devicename = device_name;

	}
	
	public void MobileBrowserInitialize(String browsername, String platform,
			String platformversion, String device_name,String appFlag) {

		this.browsername = browsername;

		this.platformname = platform;

		this.platformversion = platformversion;

		// this.automationname = autoname;

		this.devicename = device_name;
		this.appFlag=appFlag;

	}
	
	public void MobileEmulatorBrowserInitialize(String browsername, String platform,
			String platformversion, String device_name,String appFlag,String browserStackFlag) {

		this.browsername = browsername;

		this.platformname = platform;

		this.platformversion = platformversion;

		// this.automationname = autoname;

		this.devicename = device_name;
		this.appFlag=appFlag;
		this.browserStackFlag = browserStackFlag;
	}


	public void BrowserInitialize(String browsername, String browserVersion,
			String platform, String platformversion, String browserStackFlag) {

		this.browsername = browsername;

		this.platformname = platform;

		this.platformversion = platformversion;

		this.browserStackFlag = browserStackFlag;

		this.browserVersion = browserVersion;

	}
	
	public void BrowserInitialize(String browsername, String browserVersion,
			String platform, String platformversion, String browserStackFlag, String elpamFlag, String bs_username, String bs_automateKey) {

		this.browsername = browsername;

		this.platformname = platform;

		this.platformversion = platformversion;

		this.browserStackFlag = browserStackFlag;

		this.browserVersion = browserVersion;
		
		this.elpamFlag = elpamFlag;
		
		this.bs_username = bs_username;
		
		this.bs_automateKey = bs_automateKey;

	}
	
	public void BrowserInitialize(String browsername, String browserVersion,
			String platform, String platformversion, String browserStackFlag, String elpamFlag, String bs_username, String bs_automateKey,String appFlag) {

		this.browsername = browsername;

		this.platformname = platform;

		this.platformversion = platformversion;

		this.browserStackFlag = browserStackFlag;

		this.browserVersion = browserVersion;
		
		this.elpamFlag = elpamFlag;
		
		this.bs_username = bs_username;
		
		this.bs_automateKey = bs_automateKey;
		this.appFlag=appFlag;

	}

	public Browser getBrowser() {

		return this;

	}
	
	public String getBSUsername(){
		return this.bs_username;
	}
	
	public String getBSAutomateKey(){
		return this.bs_automateKey;
	}
	
	public boolean isElpamEnabled(){
		if("true".equalsIgnoreCase(this.elpamFlag)){
			return true;
		}
		else return false;
	}

	public String isBrowserStackEnabled() {

		return browserStackFlag;

	}

	public void setBrowserStackFlag(String browserStackFlag) {

		this.browserStackFlag = browserStackFlag;

	}

	public void setBrowserVersion(String browserVersion) {

		this.browserVersion = browserVersion;

	}

	public String getBrowserVersion() {

		return browserVersion;

	}

	public String getAutomationFlag() {

		return automationFlag;

	}

	public void setAutomationFlag(String flag) {

		this.automationFlag = flag;

	}

	public void BrowserInitialize(String browsername) {

		this.browsername = browsername;

	}

	public String getBrowserName() {

		return browsername;

	}

	public String getPlatformName() {

		return platformname;

	}

	public String getPlatformVersion() {

		return platformversion;

	}

	public String getDeviceName() {

		return devicename;

	}

	public String getAutomationName() {

		return automationname;

	}

	public File getAppPath() {

		return apppath;

	}

	public String getAppPackage() {

		return app_package;

	}

	public String getAppActivity() {

		return app_activity;

	}

	public void setVersion(String version) {

		this.platformversion = version;

	}

	public void setPlatform(String platform) {

		this.platformname = platform;

	}

	public void setName(String name) {

		this.browsername = name;

	}
	public String getappFlag() {

		return appFlag;

	}

	

}
