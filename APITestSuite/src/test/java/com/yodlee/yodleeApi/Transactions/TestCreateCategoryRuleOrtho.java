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

package com.yodlee.yodleeApi.Transactions;

import java.io.IOException;
import java.util.HashMap;

import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import org.databene.benerator.anno.Source;

import org.json.JSONException;
import org.testng.annotations.Test;

import io.restassured.response.Response;

/**
 * @author partha sham
 *
 */
public class TestCreateCategoryRuleOrtho extends Base {

	public static final String CREATE_RULE_NEW = "\\TestData\\CSVFiles\\Transactions\\TxnRules\\createTxnRule.csv";
	public static final String CREATE_UPDATE_RULE_NEW = "\\TestData\\CSVFiles\\Transactions\\TxnRules\\Update_Cat_Rule1.csv";
	public static final String CREATE_UPDATE_RULE_Negetive = "\\TestData\\CSVFiles\\Transactions\\TxnRules\\Update_Cat_Rule_Negetive.csv";
	public static final String CTR_SESSIONS = "\\TestData\\CSVFiles\\Transactions\\TxnRules\\CTR_Sessions1.csv";
	public static final String DELETE_CAT_RULE = "\\TestData\\CSVFiles\\Transactions\\TxnRules\\deleteCatRule.csv";

	Configuration configurationObj = Configuration.getInstance();
	TransactionUtils transactionUtils = new TransactionUtils();
	TransactionsHelper transactionsHelper = new TransactionsHelper();
	AccountsHelper accountsHelper = new AccountsHelper();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	CommonUtils commonUtils = new CommonUtils();
	UserHelper userHelper = new UserHelper();
	UserUtils userUtils = new UserUtils();
	
	@Info(subfeature = { SubFeature.CREATE_TRANSACTION_CATEGORISATION_RULE }, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source(CREATE_RULE_NEW)
	public void CreateTxnRule(String TestCaseId, String TestCaseName, String categoryId, String priority,
			String descriptionOperation, String descriptionValue, String amountOperation, String amountValue,
			String filePath, String fileName, int statusCode, long DefectID, String Enabled)
			throws JSONException, IOException {
		
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		System.out.println("************************Categorization rule creation**********************");
		System.out.println("executing test case" + TestCaseId);

		String ruleJson = transactionsHelper.createRuleObject(Integer.valueOf(categoryId), Integer.valueOf(priority), descriptionOperation,
				descriptionValue, amountOperation, amountValue, "SYSTEM");

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("ruleParam", ruleJson);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		Response res = transactionUtils.createTranxCategorizationRule(httpParams,
				configurationObj.getCobrandSessionObj());
		res.then().log().all();
		if (res.getStatusCode() == 201) {
			System.out.println("Rule created successfully");
			Response response = transactionUtils.getTranxCategorizationRule(configurationObj.getCobrandSessionObj());
			System.out.println("filePath :"+ filePath +" fileName :"+fileName);
			//commonUtils.saveJson(response.prettyPrint(), filePath, fileName + ".json");
			System.out.println("fileName ::"+fileName+" filePath ::"+filePath);
			jsonAssertionUtil.assertJSON(response, res.getStatusCode(), fileName, filePath);
		} else {
			System.out.println("Rule creation failed");
		}
	}

	@Info(subfeature = { SubFeature.UPDATE_TRANSACTION_CATEGORISATION_RULE }, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 3)
	@Source(CREATE_UPDATE_RULE_NEW)
	public void updateTranxCategorizationRule(int TestCaseId, String TestCaseName, String categoryId, String priority,
			String descriptionOperation, String descriptionValue, String amountOperation, String amountValue,
			int statusCode, String updateDescriptionOperation, String UpdateDescriptionValue,
			String updateAmountOperation, String updateAmountvalue, int updatedStatusCode, String ResFile,
			String FilePath, int DefectID, String Enabled) throws InterruptedException, JSONException, IOException {

		commonUtils.isTCEnabled(Enabled, String.valueOf(TestCaseId));
		
		String password = "Test@123";
		String email = "test@yodlee.com";
		String firstName = "Envestnet";
		String lastName = "yodlee";
		String address1 = "Address One";
		String address2 = "";
		String state = "";
		String city = "";
		String zip = "";
		String country = "";
		String currency = "USD";
		String timeZone = "PST";
		String dateFormat = "MM/dd/yyyy";
		String json = userHelper.createUserRegistrationObject(password, email, firstName, lastName, address1, address2, state, city, zip, country, currency, timeZone, dateFormat);
		HttpMethodParameters httpParamsBody =  HttpMethodParameters.builder().build();
		httpParamsBody.setBodyParams(json);
		Response response = (Response) userUtils.userRegistrationResponse(configurationObj.getCobrandSessionObj(), httpParamsBody);
		System.out.println("Response after the registering user" + response.prettyPrint());
		String newUserSession = response.jsonPath().getString("user.session.userSession");
		EnvSession envSession=EnvSession.builder().build();
		envSession.setCobSession(configurationObj.getCobrandSessionObj().getCobSession());
		envSession.setPath(configurationObj.getCobrandSessionObj().getPath());
		envSession.setUserSession(newUserSession);
		System.out.println(" ************CREATE RULE WITH DESC AND AMOUNT AND UPDATE WITH DESC AND AMOUNT***********  ");
		System.out.println(" --- Calling Create Rule    ----");
		System.out.println("Executing test case:" + TestCaseId);

		String ruleJson = transactionsHelper.createRuleObject(Integer.valueOf(categoryId), Integer.valueOf(priority), descriptionOperation,
				descriptionValue, amountOperation, amountValue, "SYSTEM");

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("ruleParam", ruleJson);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);

		System.out.println(" --- Creating Transaction Categorization Rule ----");
		Response res = transactionUtils.createTranxCategorizationRule(httpParams, envSession);
		
		if (res.getStatusCode() == 201) {
			
			System.out.println(
					" --- Rules Created Successfully for Test Method - 1.   Now calling GET Transaction Categorization Rules --- ");
			Long userDefinedRuleId = transactionsHelper.getCategorizationRuleId(TestCaseId, envSession);
			ruleJson = transactionsHelper.createRuleObject(Integer.valueOf(categoryId), Integer.valueOf(priority), updateDescriptionOperation,
					UpdateDescriptionValue, updateAmountOperation, updateAmountvalue, "SYSTEM");
			HashMap<String, Object> pathParam = new HashMap<String, Object>();
			pathParam.put("userDefinedRuleId", userDefinedRuleId);
			HttpMethodParameters httpPathParams = HttpMethodParameters.builder().build();
			httpPathParams.setPathParams(pathParam);
			httpPathParams.setBodyParams(ruleJson);

			Response updateResponse = transactionUtils.updateTranxCategorizationRule(httpPathParams, envSession);
			commonUtils.assertStatusCode(updateResponse, updatedStatusCode);
			Response getResponse = transactionUtils.getTranxCategorizationRule(envSession);
			userUtils.unRegisterUser(envSession);
			jsonAssertionUtil.assertResponse(getResponse, 200, ResFile+".json", FilePath);
			
		}
	}
	
	@Info(subfeature = { SubFeature.UPDATE_TRANSACTION_CATEGORISATION_RULE }, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 3)
	@Source(CREATE_UPDATE_RULE_Negetive)
	public void updateTranxCategorizationRuleNegetive(int TestCaseId, String TestCaseName, String categoryId, String priority,
			String descriptionOperation, String descriptionValue, String amountOperation, String amountValue,
			int statusCode, String updateDescriptionOperation, String UpdateDescriptionValue,
			String updateAmountOperation, String updateAmountvalue, int updatedStatusCode, String ResFile,
			String FilePath, int DefectID, String Enabled) throws InterruptedException, JSONException, IOException {

		commonUtils.isTCEnabled(Enabled, String.valueOf(TestCaseId));
		System.out
				.println(" ************CREATE RULE WITH DESC AND AMOUNT AND UPDATE WITH DESC AND AMOUNT***********  ");
		System.out.println(" --- Calling Create Rule    ----");
		System.out.println("Executing test case:" + TestCaseId);

		String ruleJson = transactionsHelper.createRuleObject(Integer.valueOf(categoryId), Integer.valueOf(priority), descriptionOperation,
				descriptionValue, amountOperation, amountValue, "SYSTEM");

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("ruleParam", ruleJson);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);

		System.out.println(" --- Creating Transaction Categorization Rule ----");
		Response res = transactionUtils.createTranxCategorizationRule(httpParams,
				configurationObj.getCobrandSessionObj());
		
		if (res.getStatusCode() == 201) {
			
			System.out.println(
					" --- Rules Created Successfully for Test Method - 1.   Now calling GET Transaction Categorization Rules --- ");
			Long userDefinedRuleId = transactionsHelper.getCategorizationRuleId(TestCaseId, configurationObj.getCobrandSessionObj());
			ruleJson = transactionsHelper.createRuleObject(Integer.valueOf(categoryId), Integer.valueOf(priority), updateDescriptionOperation,
					UpdateDescriptionValue, updateAmountOperation, updateAmountvalue, "SYSTEM");
			HashMap<String, Object> pathParam = new HashMap<String, Object>();
			pathParam.put("userDefinedRuleId", userDefinedRuleId);
			HttpMethodParameters httpPathParams = HttpMethodParameters.builder().build();
			httpPathParams.setPathParams(pathParam);
			httpPathParams.setBodyParams(ruleJson);

			Response updateResponse = transactionUtils.updateTranxCategorizationRule(httpPathParams, configurationObj.getCobrandSessionObj());
			commonUtils.assertStatusCode(updateResponse, updatedStatusCode);
		}
	}

	@Info(subfeature = { SubFeature.CREATE_TRANSACTION_CATEGORISATION_RULE }, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 4)
	@Source(CTR_SESSIONS)
	public void CreateTxnRule_Sessions_Test(String testCaseId, String cobSessionKey, String cobSession,
			String userSessionKey, String userSession, String fileName, String filePath, int status, String Enabled,
			String DefectID) throws JSONException, InterruptedException {
		
		System.out.println("*************Executing test case : " + testCaseId);
		String ruleJson = transactionsHelper.createRuleObject(1, 3, "stringEquals", "remme", "numberGreaterThan",
				"550.00","SYSTEM");
		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("ruleParam", ruleJson);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		String sessionToken = SessionHelper.getSessionTokenUser(cobSession, "cob",userSession);
		EnvSession session = EnvSession.builder().cobSession(sessionToken).path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response createRes = transactionUtils.createTranxCategorizationRule(httpParams,	session);
		commonUtils.saveJson(createRes.prettyPrint(), filePath, fileName + ".json");
		jsonAssertionUtil.assertJSON(createRes, createRes.getStatusCode(), fileName, filePath);
	}

	@Info(subfeature = { SubFeature.UPDATE_TRANSACTION_CATEGORISATION_RULE }, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 5)
	@Source(CTR_SESSIONS)
	public void UpdateTxnRule_Sessions_Test(String testCaseId, String cobSessionKey, String cobSession,
			String userSessionKey, String userSession, String fileName, String filePath, int status, String Enabled,
			String DefectID) throws JSONException, InterruptedException {
		commonUtils.isTCEnabled(Enabled, testCaseId);
		System.out.println("*************Executing test case : " + testCaseId);
		String categorizationRuleId = "12341234";
		String ruleJson = transactionsHelper.createRuleObject(1, 3, "stringEquals", "remember", "numberGreaterThan",
				"55.00", "SYSTEM");
		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("ruleParam", ruleJson);
		queryParam.put("categorizationRuleId", categorizationRuleId);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		String sessionToken = SessionHelper.getSessionTokenUser(cobSession, "cob",userSession);
		EnvSession session = EnvSession.builder().cobSession(sessionToken).path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response updateRes = transactionUtils.createTranxCategorizationRule(httpParams, session);
		commonUtils.saveJson(updateRes.prettyPrint(), filePath, fileName + ".json");
		jsonAssertionUtil.assertJSON(updateRes, updateRes.getStatusCode(), fileName, filePath);
	}

	@Info(subfeature = { SubFeature.GET_TRANSACTION_CATEGORISATION_RULE }, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 6)
	@Source(CTR_SESSIONS)
	public void GetTxnRules_Sessions_Test(String testCaseId, String cobSessionKey, String cobSession,
			String userSessionKey, String userSession, String fileName, String filePath, int status, String Enabled,
			String DefectID) throws JSONException, InterruptedException {
		commonUtils.isTCEnabled(Enabled, testCaseId);
		System.out.println("*************Executing test case : " + testCaseId);
		String sessionToken = SessionHelper.getSessionTokenUser(cobSession, "cob",userSession);
		EnvSession session = EnvSession.builder().cobSession(sessionToken).path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = transactionUtils.createTranxCategorizationRule(null, session);
		commonUtils.saveJson(response.prettyPrint(), filePath, fileName + ".json");
		jsonAssertionUtil.assertJSON(response, response.getStatusCode(), fileName, filePath);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 7)
	@Source(CTR_SESSIONS)
	public void DeleteTxnRules_Sessions_Test(String testCaseId, String cobSessionKey, String cobSession,
			String userSessionKey, String userSession, String fileName, String filePath, int status, String Enabled,
			String DefectID) throws JSONException, InterruptedException {
		commonUtils.isTCEnabled(Enabled, testCaseId);
		System.out.println("*************Executing test case : " + testCaseId);
		String categorizationRuleId = "12341234";
		System.out.println("*** Calling Delete Method *** ");
		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("ruleParam", categorizationRuleId);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		String sessionToken = SessionHelper.getSessionTokenUser(cobSession, "cob",userSession);
		EnvSession session = EnvSession.builder().cobSession(sessionToken).path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = transactionUtils.createTranxCategorizationRule(httpParams, session);
		commonUtils.saveJson(response.prettyPrint(), filePath, fileName + ".json");
		jsonAssertionUtil.assertJSON(response, response.getStatusCode(), fileName, filePath);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 9)
	@Source(CTR_SESSIONS)
	public void RunAllTxnRules_Sessions_Test(String testCaseId, String cobSessionKey, String cobSession,
			String userSessionKey, String userSession, String fileName, String filePath, int status, String Enabled,
			String DefectID) throws JSONException, InterruptedException {
		commonUtils.isTCEnabled(Enabled, testCaseId);
		System.out.println("*************Executing test case : " + testCaseId);
		HashMap<String, String> runAllRule = new HashMap<String, String>();
		runAllRule.put("action", "run");
		System.out.println(" *** Run All Rule *** ");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(runAllRule);
		String sessionToken = SessionHelper.getSessionTokenUser(cobSession, "cob",userSession);
		EnvSession session = EnvSession.builder().cobSession(sessionToken).path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = transactionUtils.createTranxCategorizationRule(httpParams, session);
		commonUtils.saveJson(response.prettyPrint(), filePath, fileName + ".json");
		jsonAssertionUtil.assertJSON(response, response.getStatusCode(), fileName, filePath);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 8)
	@Source(CTR_SESSIONS)
	public void RunOneTxnRule_Sessions_Test(String testCaseId, String cobSessionKey, String cobSession,
			String userSessionKey, String userSession, String fileName, String filePath, int status, String Enabled,
			String DefectID) throws JSONException, InterruptedException {
		commonUtils.isTCEnabled(Enabled, testCaseId);
		System.out.println("*************Executing test case : " + testCaseId);
		HashMap<String, String> runOneRule = new HashMap<String, String>();
		runOneRule.put("action", "run");
		System.out.println(" *** Run One Rule *** ");
		String categorizationRuleId = "12341234";
		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("categorizationRuleId", categorizationRuleId);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		String sessionToken = SessionHelper.getSessionTokenUser(cobSession, "cob",userSession);
		EnvSession session = EnvSession.builder().cobSession(sessionToken).path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = transactionUtils.createTranxCategorizationRule(httpParams, session);
		commonUtils.saveJson(response.prettyPrint(), filePath, fileName + ".json");
		jsonAssertionUtil.assertJSON(response, response.getStatusCode(), fileName, filePath);
	}

	/*@AfterTest
	public void RunAndDeleteTranxCategorizationRule() throws JSONException {

		System.out.println(" ******  Calling Run All Rule method  ******");
		HttpMethodParameters httpParams = transactionsHelper.createPathParam("action", "run");
		System.out.println("****  Updating Transaction Categorization Rules  ****");
		transactionUtils.runAllRuleOnTransaction(httpParams, configurationObj.getCobrandSessionObj());
		System.out.println("****  Deleting Transaction Categorization Rules  ****");
		Long userDefinedRuleId = transactionsHelper.getCategorizationRuleId(configurationObj.getCobrandSessionObj());
		HashMap<String, Object> pathParam = new HashMap<String, Object>();
		pathParam.put("userDefinedRuleId", userDefinedRuleId);
		HttpMethodParameters httpPathParams = HttpMethodParameters.builder().build();
		httpPathParams.setPathParams(pathParam);
		transactionUtils.deleteCategorizationRule(httpPathParams, configurationObj.getCobrandSessionObj());
	}*/
}
