/*******************************************************************************
 *
 * * Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.app.yodleeApi.Insights.V2;

import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.databene.benerator.anno.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.JsonParser;
import com.yodlee.insights.views.pojo.CommonEntity;
import com.yodlee.insights.yodleeApi.utils.AutomationTestResults;
import com.yodlee.insights.yodleeApi.utils.FailureReason;
import com.yodlee.insights.yodleeApi.utils.InsightsCommons;
import com.yodlee.insights.yodleeApi.utils.InsightsConstants;
import com.yodlee.insights.yodleeApi.utils.InsightsDBUtils;
import com.yodlee.insights.yodleeApi.utils.InsightsGenerics;
import com.yodlee.insights.yodleeApi.utils.InsightsHelper;
import com.yodlee.insights.yodleeApi.utils.InsightsLevelVerifications;
import com.yodlee.insights.yodleeApi.utils.InsightsProperties;
import com.yodlee.insights.yodleeApi.utils.NotifiedInsightsDataValidation;
import com.yodlee.insights.yodleeApi.utils.TestInfo;
import com.yodlee.insights.yodleeApi.utils.VisibleAllOver;
import com.yodlee.insights.yodleeApi.utils.v2.InsightsSubscriptionsVerification;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.JwtHelper;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TaskUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.apiUtils.BoardUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightsUtilV1;
import com.yodlee.yodleeApi.utils.apiUtils.InvokerUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import java.util.Iterator;

import io.restassured.response.Response;

public class TestGetUserSubscriptions extends Base {

	public static Long providerAccountId = null;

	protected Logger logger = LoggerFactory.getLogger(TestGetUserSubscriptions.class);
	String loginName, password;
	String userSession = "";
	EnvSession sessionObj = null;
	Configuration configurationObj = Configuration.getInstance();
	UserUtils userUtils = new UserUtils();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	SaveForGoalHelper saveForGoalHelper = new SaveForGoalHelper();
	UserHelper userHelper = new UserHelper();
	JsonAssertionUtil jsonAssertionutil = new JsonAssertionUtil();
	SaveForGoalHelper sfg = new SaveForGoalHelper();
	CommonUtils commontUtils = new CommonUtils();
	JwtHelper jwtHelper = new JwtHelper();
	TaskUtils taskUtils = null;
	JsonParser jsonParser = new JsonParser();
	InsightUtils insightUtils = new InsightUtils();
	InvokerUtils invokerUtils = new InvokerUtils();
	InsightsUtilV1 insightsUtilsV2 = new InsightsUtilV1();
	InsightsHelper insightsHelper = new InsightsHelper();
	InsightsGenerics insightsGenerics = new InsightsGenerics();
	InsightsLevelVerifications insightsLevelVerifications = new InsightsLevelVerifications();
	HashMap<String,ArrayList<String>> negativeExpectedValuesMap;
	String loginUser = null;
	String  jwtUserAuthToken = null;
	FailureReason failureReason = new  FailureReason();
	static Map<String ,String>  notificationsTestSummary = new HashMap<String,String>();
	HashMap<String,String> entityIdsMap = new HashMap<String,String>();	
	HashMap<String,HashMap<String,List>> transactionsIdsMap = new HashMap<String,HashMap<String,List>>();	
	Number userId = null;
	List<TestInfo> testsInfoList;
	String insightExpectedKeys = null;
	InsightsDBUtils insightsDBUtils = null;
	AutomationTestResults automationTestResults = new AutomationTestResults();
	BoardUtils boardUtils = new BoardUtils();	
	String jwtCobAuthToken = "";
	InsightsProperties prop = new InsightsProperties();	
	String cobrandUser=null;
	String buildNo="421",codeCoverage="80";
	Response userInsightsResponse = null;
	InsightsCommons insightsCommons = new InsightsCommons();
	NotifiedInsightsDataValidation validateInsights = null;	
	Response customerSubscriptionResponse = null, getUserSubscriptionResponse = null;
	String envCobrand = null;
	Map<String, HashMap<String, Boolean>> allTestResultsMap = new HashMap<String, HashMap<String, Boolean>>();
	String envUser = null;
	CommonEntity commonEntity = new CommonEntity();
	InsightsSubscriptionsVerification subscriptionsHelper = new InsightsSubscriptionsVerification();
	HttpMethodParameters httpParams = null;

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated GET User subscription API execution");
		insightsDBUtils = new InsightsDBUtils();
		String cobrand = prop.readPropertiesFile().getProperty("envCobrand");
		cobrandUser = prop.readPropertiesFile().getProperty("cobrandUser");
		envCobrand = (configurationObj.getApiVersion().equals("1.1") ? cobrand
				: (configurationObj.getApiVersion().equals("2") ? cobrand.substring(0, cobrand.length() - 2) : null));
		String user = prop.readPropertiesFile().getProperty("envUser");
		envUser = (configurationObj.getApiVersion().equals("1.1") ? user
				: (configurationObj.getApiVersion().equals("2") ? user.substring(0, user.length() - 2) : null));
		User userInfo = User.builder().build();
		userInfo.setUsername("GetUserSub" + System.currentTimeMillis());
		// userInfo.setUsername("ldnuser888"); //sense user and wellness
		loginUser = userInfo.getUsername();
		System.out.println("User ----> " + userInfo.getUsername());
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		// userHelper.getUserSession(userInfo.getUsername(),userInfo.getPassword(),sessionObj)
		// ;
		jwtCobAuthToken = jwtHelper.getJWTToken(cobrandUser, envCobrand);
		jwtCobAuthToken = "Bearer ".concat(jwtCobAuthToken);
		jwtUserAuthToken = jwtHelper.getJWTToken(userInfo.getUsername(), envUser);
		jwtUserAuthToken = "Bearer ".concat(jwtUserAuthToken);
		insightsDBUtils.disableCustomerSubscriptions(envCobrand,jwtCobAuthToken,"true");
		Thread.sleep(10000);
		httpParams = insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null, jwtCobAuthToken, null, null,null, null);
		httpParams.setHttpMethod("GET");
		httpParams.setContentType("application/json");
		customerSubscriptionResponse = insightsUtilsV2.getCustomerSubscriptions(httpParams, null);
	
	}

	public void fetchUsersubscriptions(String insightName) throws IOException {
		httpParams = insightsHelper.getHttpParams(InsightsConstants.GET_USER_SUBSCRIPTION_REQUEST, null, insightName,
				jwtUserAuthToken, null, null, null, null);
		httpParams.setHttpMethod("GET");
		getUserSubscriptionResponse = insightsUtilsV2.getUserSubscriptions(httpParams, null);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionsV2\\UserSubscriptions\\GETUserSubscriptions.csv")
	public void testGetUserSubscriptions(String testCaseId, String testCaseDescription, String insightName,
			String insightTitle, String insightType, String triggerType, String containerSupported,
			String insightDescription, String applicableEntity, String entityType, String isSubscribed,
			String frequency, String duration, String thresholdDetails, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("GET User Subscription Testcases executed for Insight ..." + insightName.toUpperCase()
				+ " TestCaseId = " + testCaseId);
		String schemaFile = insightName + ".json";
		fetchUsersubscriptions(insightName);
		HashMap<String, Boolean> testResultsMap = subscriptionsHelper.verifyUserSubscriptionResponse(
				getUserSubscriptionResponse, customerSubscriptionResponse, insightName, insightTitle, insightType,
				triggerType, containerSupported, insightDescription, applicableEntity, entityType, isSubscribed,
				frequency, duration, thresholdDetails, schemaFile);
		Map<String, Boolean> failedResultMap = testResultsMap.entrySet().stream().filter(map -> false == map.getValue())
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		if (!testResultsMap.isEmpty())
			allTestResultsMap.put(insightName.toUpperCase(), testResultsMap);
		if (failedResultMap.size() > 0) {
			Set<String> failedTestSet = failedResultMap.keySet();
			Iterator<String> itr = failedTestSet.iterator();
			while (itr.hasNext()) {
				System.out.println("Test FAILED while validating testcase " + itr.next());
			}
			Assert.fail();
		}
	}

}
