/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
/*package com.omni.pfm.webdriver;



import org.openqa.selenium.WebDriver;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;



import com.gargoylesoftware.htmlunit.BrowserVersion;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;

import com.gargoylesoftware.htmlunit.WebClient;





Class extending HtmlUnitDriver to support BASIC authentication

 * 

 * @author Ashish Sadhwani

 

public class AuthenticatedHtmlUnitDriver extends HtmlUnitDriver {

	private static String USERNAME;

	private static String PASSWORD;



	private AuthenticatedHtmlUnitDriver() {

	}



	public static WebDriver create(String username, String password) {

		USERNAME = username;

		PASSWORD = password;

		return new AuthenticatedHtmlUnitDriver();

	}



	@Override

	protected WebClient newWebClient(BrowserVersion browserVersion) {

		WebClient client = super.newWebClient(browserVersion);

		DefaultCredentialsProvider provider = new DefaultCredentialsProvider();

		provider.addCredentials(USERNAME, PASSWORD);

		client.setCredentialsProvider(provider);

		return client;

	}

}

*/
