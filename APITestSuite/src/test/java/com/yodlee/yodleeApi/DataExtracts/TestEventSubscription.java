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

package com.yodlee.yodleeApi.DataExtracts;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.CobrandHelper;
import com.yodlee.yodleeApi.helper.DataExtractsHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.CobrandUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;
import org.databene.benerator.anno.Source;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TestEventSubscription extends Base {

	CommonUtils commonUtils = new CommonUtils();
	Configuration configurationObj = Configuration.getInstance();
	CobrandUtils cobrandUtils = new CobrandUtils();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	DataExtractsHelper dataExtractsHelper = new DataExtractsHelper();
	CobrandHelper cobrandHelper = new CobrandHelper();

	@Test(dataProvider = "feeder", alwaysRun = true, priority = 1)
	@Source("\\TestData\\CSVFiles\\DataExtracts\\eventsubscription.csv")
	public void eventSubscriptionWithPathandQuery(String tcid, String tcName, int status, String cobSession,
			String eventName, String callbackURL, String filePath, String resFile, String defectID, String enabled) {

		commonUtils.isTCEnabled(enabled, tcid);
		Map<String, String> queryParam = new HashMap<>();
		queryParam.put("callbackUrl", callbackURL);

		String str;
		if (cobSession.equalsIgnoreCase("valid")) {
			str = configurationObj.getCobrandSessionObj().getCobSession();
			if (!tcName.contains("already")) { // Scenario which tests for already subscribed
				 
				/*EnvSession envSession = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());*/
				
				EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
						.path(configurationObj.getCobrandSessionObj().getPath()).build();
				
				HttpMethodParameters pathParam = cobrandHelper.createPathParam("eventName", eventName);
				Response response = cobrandUtils.deleteNotificationEvent(pathParam, envSession);
			} else { // If 'already' is present, subscribe once
			
				HttpMethodParameters httpParams = cobrandHelper
						.createInputForCreateOrUpdateNotificationEvents(eventName, callbackURL);
				/*EnvSession Session = new EnvSession(str,
						configurationObj.getCobrandSessionObj().getPath());*/
				
				EnvSession session = EnvSession.builder().cobSession(str)
						.path(configurationObj.getCobrandSessionObj().getPath()).build();
				Response response = cobrandUtils.createNotificationEvent(httpParams, session);
			}

		} else
			str = cobSession;

		HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(eventName,
				callbackURL);
		/*EnvSession session = new EnvSession(str,
				configurationObj.getCobrandSessionObj().getPath());*/
		EnvSession session = EnvSession.builder().cobSession(str)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = cobrandUtils.createNotificationEvent(httpParams, session);
		Assert.assertEquals(response.statusCode(), status, "Response is : " + response.asString());

		jsonAssertionUtil.assertResponse(response, response.getStatusCode(), resFile, filePath);
	}

	@Test(dataProvider = "feeder", alwaysRun = true, priority = 3)
	@Source("\\TestData\\CSVFiles\\DataExtracts\\eventsubscription.csv")
	public void eventSubscriptionWithPathandBody(String tcid, String tcName, int status, String cobSession,
			String eventName, String callbackURL, String filePath, String resFile, String defectID, String enabled) {

		commonUtils.isTCEnabled(enabled, tcid);

		String str;
		if (cobSession.equalsIgnoreCase("valid")) {
			str = configurationObj.getCobrandSessionObj().getCobSession();
			
		} else {
			str = cobSession;
		}
		
			
			EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
					.path(configurationObj.getCobrandSessionObj().getPath()).build();
			HttpMethodParameters pathParam = cobrandHelper.createPathParam("eventName", eventName);
			Response response1 = cobrandUtils.deleteNotificationEvent(pathParam, envSession);
			HttpMethodParameters httpParams = cobrandHelper
					.createInputForCreateOrUpdateNotificationEvents(eventName, callbackURL);
		
			
			EnvSession session = EnvSession.builder().cobSession(str)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();
			
			Response response = cobrandUtils.createNotificationEvent(httpParams, session);
			
		if (tcName.contains("already")) {
			 response = cobrandUtils.createNotificationEvent(httpParams, session);
		}

		System.out.println("response.statusCode() priority 3:"+response.statusCode());
		Assert.assertEquals(response.statusCode(), status, "Response : " + response.asString());

		if (response.getStatusCode() != 201) {
			jsonAssertionUtil.assertResponse(response, response.getStatusCode(), resFile, filePath);
		}

	}

	@Test(dataProvider = "feeder", alwaysRun = true, priority = 0)
	@Source("\\TestData\\CSVFiles\\DataExtracts\\deletesubscription.csv")
	public void DeleteSubscriptionWithQueryParam(String testCaseId, String tcName, int status, String cobSession,
			String eventName, String callbackURL, String filePath, String resFile, String defectId, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseId);

		EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		HttpMethodParameters pathParam = cobrandHelper.createPathParam("eventName", eventName);
		Response response = cobrandUtils.deleteNotificationEvent(pathParam, envSession);
		
		Map<String, String> queryParam = new HashMap<>();
		queryParam.put("callbackUrl", callbackURL);
		 Map<String, Object> pathParams = new HashMap<>();
	        pathParams.put("eventName", eventName);
		
		String str;
		if (cobSession.equalsIgnoreCase("valid"))
			str = configurationObj.getCobrandSessionObj().getCobSession();
		else
			str = cobSession;

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setPathParams(pathParams);
		httpMethodParameters.setQueryParams(queryParam);
		
		EnvSession session = EnvSession.builder().cobSession(str)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response createResponse = cobrandUtils.createNotificationEvent(httpMethodParameters, session);

		System.out.println(createResponse.statusCode());
		Assert.assertEquals(createResponse.statusCode(), status);

		if (createResponse.getStatusCode() != 201) {
			jsonAssertionUtil.assertResponse(createResponse, createResponse.getStatusCode(), resFile, filePath);
		}
	}

	@Test(dataProvider = "feeder", alwaysRun = true, priority = 2)
	@Source("\\TestData\\CSVFiles\\DataExtracts\\deletesubscription.csv")
	public void DeleteSubscriptionWithBodyParam(String testCaseId, String tcName, int status, String cobSession,
			String eventName, String callbackURL, String filePath, String resFile, String defectId, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseId);

		EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		HttpMethodParameters pathParam = cobrandHelper.createPathParam("eventName", eventName);
		Response response = cobrandUtils.deleteNotificationEvent(pathParam, envSession);
		
		JSONObject params = new JSONObject();
		JSONObject callbackParam = new JSONObject();
		callbackParam.put("callbackUrl", callbackURL);
		params.put("event", callbackParam);
		System.out.println(callbackURL.toString() + " " + callbackParam.toJSONString());

		String str;
		if (cobSession.equalsIgnoreCase("valid"))
			str = configurationObj.getCobrandSessionObj().getCobSession();
		else
			str = cobSession;

		HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(eventName,
				callbackURL);
		
		EnvSession session = EnvSession.builder().cobSession(str).userSession(configurationObj.getCobrandSessionObj().getUserSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		Response createResponse = cobrandUtils.createNotificationEvent(httpParams, session);

		System.out.println(createResponse.statusCode());
		Assert.assertEquals(status, createResponse.statusCode());

		if (response.getStatusCode() != 201) {
			
			jsonAssertionUtil.assertResponse(createResponse, createResponse.getStatusCode(), resFile, filePath);

		}

	}

	@Test(dataProvider = "feeder", alwaysRun = true, priority = 6)
	@Source("\\TestData\\CSVFiles\\DataExtracts\\deletesubscriptionallcases.csv")
	public void DeleteEventSubscription(String testCaseId, String tcName, int status, String cobSession,
			String eventName, String filePath, String resFile, String defectId, String enabled) {
		commonUtils.isTCEnabled(enabled, testCaseId);
		
		System.out.println("testCaseId:: "+ testCaseId +"tcName::" +tcName);
		
	
		EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		HttpMethodParameters pathParam = cobrandHelper.createPathParam("eventName", eventName);
		Response response = cobrandUtils.deleteNotificationEvent(pathParam, envSession);
		response.then().log().all();
		
		System.out.println(response.statusCode());
		Assert.assertEquals(response.statusCode(), status);

		
		jsonAssertionUtil.assertResponse(response, response.getStatusCode(), resFile, filePath);

	}

	@Test(dataProvider = "feeder", alwaysRun = true, priority = 4)
	@Source("\\TestData\\CSVFiles\\DataExtracts\\updatesubscription.csv")
	public void UpdateSubScriptionWithPathAndQueryParam(String tcid, String tcName, int status, String cobSession,
			String eventName, String callbackURL, String filePath, String resFile, String defectID, String enabled) {
		commonUtils.isTCEnabled(enabled, tcid);
		Map<String, String> queryParam = new HashMap<>();
		queryParam.put("callbackUrl", callbackURL);
		Map<String, String> pathParam = new HashMap<>();
		pathParam.put("eventName", eventName);
		String str;
		if (cobSession.equalsIgnoreCase("valid"))
			str = configurationObj.getCobrandSessionObj().getCobSession();
		else
			str = cobSession;

		
		HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(eventName,
				callbackURL);
		
		EnvSession session = EnvSession.builder().cobSession(str)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		Response createResponse = cobrandUtils.createNotificationEvent(httpParams, session);
		
		
		 httpParams=cobrandHelper.createInputForCreateOrUpdateNotificationEvents(eventName, callbackURL);
		Response response = cobrandUtils.putNotificationEvent(httpParams, session);

		System.out.println(response.statusCode());
		response.then().log().all();
		Assert.assertEquals(status, response.statusCode());
		
		if (response.getStatusCode() != 204) {		
			jsonAssertionUtil.assertResponse(response, response.getStatusCode(), resFile, filePath);
		}
	}

	@Test(dataProvider = "feeder", alwaysRun = true, priority = 5)
	@Source("\\TestData\\CSVFiles\\DataExtracts\\updatesubscription.csv")
	public void UpdateSubscriptionWithPathandBodyParam(String tcid, String tcName, int status, String cobSession,
			String eventName, String callbackURL, String filePath, String resFile, String defectID, String enabled) {

		commonUtils.isTCEnabled(enabled, tcid);

		

		Map<String, String> pathParam = new HashMap<>();
		pathParam.put("eventName", eventName);
		String str;
		if (cobSession.equalsIgnoreCase("valid"))
			str = configurationObj.getCobrandSessionObj().getCobSession();
		else
			str = cobSession;
		
		
		HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(eventName,
				callbackURL);
		
		EnvSession session = EnvSession.builder().cobSession(str)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		Response createResponse = cobrandUtils.createNotificationEvent(httpParams, session);
		
		
		 session = EnvSession.builder().cobSession(str).userSession(configurationObj.getCobrandSessionObj().getUserSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		
		
		
		// httpParams=cobrandHelper.createInputForCreateOrUpdateNotificationEvents(eventName, callbackURL);
		Response response = cobrandUtils.putNotificationEvent(httpParams, session);

		System.out.println(response.statusCode());

		response.then().log().all();
		Assert.assertEquals(status, response.statusCode());

		if (response.getStatusCode() != 204) {
			
			jsonAssertionUtil.assertResponse(response, response.getStatusCode(), resFile, filePath);

		}
	}

	@Test(dataProvider = "feeder", alwaysRun = true, priority = 7)
	@Source("\\TestData\\CSVFiles\\DataExtracts\\eventsubscribewithuserandcobsession.csv")
	public void eventSubscriptionWithPathandQueryAndUserSession(String tcName, int status, String cobSession,
			String userSession, String eventName, String callbackURL, String tcid, String filePath, String resFile) {
		Map<String, String> queryParam = new HashMap<>();
		queryParam.put("callbackUrl", callbackURL);
		Map<String, String> pathParam = new HashMap<>();
		pathParam.put("eventName", eventName);
		String str, str1;
		if (cobSession.equalsIgnoreCase("valid"))
			str = configurationObj.getCobrandSessionObj().getCobSession();
		else
			str = cobSession;

		if (userSession.equalsIgnoreCase("valid"))
			str1 = configurationObj.getCobrandSessionObj().getUserSession();
		else
			str1 = userSession;
	
		HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(eventName,
				callbackURL);
		
		EnvSession session = EnvSession.builder().cobSession(str).userSession(str1)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		Response createResponse = cobrandUtils.createNotificationEvent(httpParams, session);

		System.out.println(createResponse.statusCode());
		Assert.assertEquals(status, createResponse.statusCode());

		createResponse.then().log().all();
		if (createResponse.getStatusCode() != 201) {
			jsonAssertionUtil.assertResponse(createResponse, createResponse.getStatusCode(), resFile, filePath);

		}
	}
}