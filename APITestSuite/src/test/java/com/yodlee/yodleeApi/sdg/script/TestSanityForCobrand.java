/*******************************************************************************
 *
 * * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.yodleeApi.sdg.script;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.module.jsv.JsonSchemaValidator;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.helper.CobrandHelper;
import com.yodlee.yodleeApi.pojo.Cobrand;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.CobrandUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * This class is used to check all the basic functionality of a cobrand.
 * 
 * @author Rahul Kumar
 *
 */
public class TestSanityForCobrand extends Base {

	String completeFilePath = null;
	String dagUN = "YSLSchemaUser.site16441.1";
	String dagPwd = "site16441.1";
	String cobSession;
	CommonUtils commonUtils = new CommonUtils();
	Configuration configurationObj = Configuration.getInstance();
	CobrandUtils cobrandUtils = new CobrandUtils();
	CobrandHelper cobrandHelper = new CobrandHelper();

	@BeforeClass
	public void testSetup() throws IOException {

		String filePath = "..src\\test\\resources\\TestData\\JSONFiles\\Sdg\\Sanity\\schemaJsons\\";
		String responseJsonFile = "cobrandLogin_Post.json";
		completeFilePath = commonUtils.getPath(filePath + responseJsonFile);
	}

	@Test(enabled = true, dataProvider = "feeder", groups = "login")
	@Source("\\TestData\\CSVFiles\\Sanity\\cobrandLogin.csv")
	public void testCobrandLogin(String testCaseId, String testCaseName, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseId);

		// Creating cobrand object
		Cobrand cobrandObj = Cobrand.builder().build();
		cobrandObj.setCobrandLogin(configurationObj.getCobrandObj().getCobrandLogin());
		cobrandObj.setCobrandPassword(configurationObj.getCobrandObj().getCobrandPassword());
		cobrandObj.setLocale(Constants.LOCALE_US);

		// Doing cobrandLogin
		HttpMethodParameters httpParms = cobrandHelper.createInputForCobrandLogin(cobrandObj);
		Response cobrandLoginResponse = (Response) cobrandUtils.getCobrandLoginResponse(httpParms,
				configurationObj.getCobrandSessionObj().getPath());

		if (cobrandLoginResponse != null) {

			Assert.assertEquals(HttpStatus.OK, cobrandLoginResponse.statusCode(),
					"Wrong Status code for cobrand login");
			File file = new File(completeFilePath);
			assertThat(cobrandLoginResponse.asString(), JsonSchemaValidator.matchesJsonSchema(file));
			cobSession = cobrandLoginResponse.jsonPath().get("session.cobSession");
			System.out.println("cobSession value is ::" + cobSession);
		}
	}

	@Test(enabled = true, dataProvider = "feeder", dependsOnGroups = "login", groups = "deleteInitial")
	@Source("\\TestData\\CSVFiles\\Sanity\\cobrandDeleteNotificationEvents.csv")
	public void cobrandDeleteNotificationEventCleanupTest(String testCaseId, String testCaseName, String eventName,
			String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseId);

		// Creating httpMethodParameters
		HttpMethodParameters httpMethodParameters = cobrandHelper.createPathParam("eventName", eventName);

		// Calling deleteNotificationEvent
		EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = (Response) cobrandUtils.deleteNotificationEvent(httpMethodParameters, envSession);

		if (response != null) {
			Assert.assertEquals(HttpStatus.OK, response.statusCode(), "Wrong status code after subscription deletion");
		}
	}

	@Test(enabled = true, dataProvider = "feeder", dependsOnGroups = "deleteInitial", groups = "post")
	@Source("\\TestData\\CSVFiles\\Sanity\\cobrandPostNotificationEvents.csv")
	public void cobrandPostNotificationEventsTest(String testCaseId, String testCaseName, String eventName,
			String callBackUrl, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseId);

		// Creating httpMethodParameters
		HttpMethodParameters httpMethodParameters = cobrandHelper
				.createInputForCreateOrUpdateNotificationEvents(eventName, callBackUrl);

		// Calling createNotificationEvent
		EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = cobrandUtils.createNotificationEvent(httpMethodParameters, envSession);

		if (response != null) {

			Assert.assertEquals(HttpStatus.CREATED, response.statusCode(),
					"Wrong Status code for cobrand post config notification events");
			Assert.assertTrue(response.body().asString().isEmpty(),
					"Body is not empty - " + response.body().asString());
		}
	}

	@Test(enabled = true, dataProvider = "feeder", dependsOnGroups = "post", groups = "get")
	@Source("\\TestData\\CSVFiles\\Sanity\\cobrandGetNotificationEvents.csv")
	public void cobrandGetNotificationEventsTest(String testCaseId, String testCaseName, String eventName,
			String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseId);
		String filePath = "..src\\test\\resources\\TestData\\JSONFiles\\Sdg\\Sanity\\schemaJsons\\";
		String responseJsonFile = "cobrand_getConfigNotificationEvents.json";
		String completeFilePath = commonUtils.getPath(filePath + responseJsonFile);

		// Creating httpMethodParameters
		HttpMethodParameters httpMethodParameters = cobrandHelper.createInputForGetNotificationEvents("eventName",
				eventName);

		// Calling getNotificationEvent
		EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = cobrandUtils.getNotificationEvents(httpMethodParameters, envSession);

		if (response != null) {
			System.out.println("Response is not null");
			File file = new File(completeFilePath);
			assertThat(response.asString(), JsonSchemaValidator.matchesJsonSchema(file));
		}
	}

	@Test(enabled = true, dataProvider = "feeder", dependsOnGroups = "get", groups = "put")
	@Source("\\TestData\\CSVFiles\\Sanity\\cobrandPutNotificationEvents.csv")
	public void cobrandPutNotificationEventsTest(String testCaseId, String testCaseName, String eventName,
			String callBackUrl, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseId);

		// Creating httpMethodParameters
		HttpMethodParameters httpMethodParameters = cobrandHelper
				.createInputForCreateOrUpdateNotificationEvents(eventName, callBackUrl);

		// Calling updateNotificationEvent
		EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = cobrandUtils.putNotificationEvent(httpMethodParameters, envSession);

		if (response != null) {
			Assert.assertEquals(HttpStatus.CREATED, response.statusCode(),
					"Wrong Status code for cobrand post config notification events");
			Assert.assertTrue(response.body().asString().isEmpty(),
					"Body is not empty - " + response.body().asString());
		}
	}

	@Test(enabled = true, dataProvider = "feeder", dependsOnGroups = "put", groups = "delete")
	@Source("\\TestData\\CSVFiles\\Sanity\\cobrandDeleteNotificationEvents.csv")
	public void cobrandDeleteNotificationEventsTest(String testCaseId, String testCaseName, String eventName,
			String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseId);

		// Creating httpMethodParameters
		HttpMethodParameters httpMethodParameters = cobrandHelper.createPathParam("eventName", eventName);

		// Calling deleteNotificationEvent
		EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = cobrandUtils.deleteNotificationEvent(httpMethodParameters, envSession);

		if (response != null) {
			Assert.assertEquals(HttpStatus.CREATED, response.statusCode(),
					"Wrong Status code for cobrand delete config notification events");
			Assert.assertTrue(response.body().asString().isEmpty(),
					"Body is not empty - " + response.body().asString());
		}
	}

	@Test(enabled = true, dataProvider = "feeder", dependsOnGroups = "delete")
	@Source("\\TestData\\CSVFiles\\Sanity\\cobrandLogout.csv")
	public void cobrandLogoutTest(String testCaseId, String testCaseName, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseId);

		// Calling cobrandLogout
		EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = cobrandUtils.cobrandLogout(envSession);

		if (response != null) {
			Assert.assertEquals(HttpStatus.CREATED, response.statusCode(), "Wrong Status code for cobrand logout");
			Assert.assertTrue(response.body().asString().isEmpty(),
					"Body is not empty - " + response.body().asString());
		}
	}

}
