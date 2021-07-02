/*******************************************************************************
*
* * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
*
* *
*
* * This software is the confidential and proprietary information of Yodlee, Inc.
*
* * Use is subject to license terms.
*
*******************************************************************************/
/**
* Sense-YSL - Automation suite of PUT /transactions
* @author  Mahadev
* 
* 
*/

package com.yodlee.app.yodleeApi.Ok2Spend;
import java.util.HashMap;
import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.SenseTransactionHelper;
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.CreateTransactionsRequest;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.ManualTransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.utils.apiUtils.PredictedUtils;

import io.restassured.response.Response;

/**
* @author Mahadev A N
*
*/
public class TestDeleteManualTransaction extends Base {

	private long providerAccountId;
	private UserUtils userUtils = new UserUtils();
	private String providerId = "16441";
	private String strDagUser = "getRecurring.site16441.2";
	private String strDagPassword = "site16441.2";
	private String strLoginFormType = "fieldarray";
	private EnvSession sessionObj = null;
	private Configuration configurationObj = Configuration.getInstance();
	private TransactionUtils transactionUtils = new TransactionUtils();
	private TransactionsHelper transactionsHelper = new TransactionsHelper();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	private AccountsHelper accountHelper = new AccountsHelper();
	private UserHelper userHelper = new UserHelper();
	private ManualTransactionUtils manualTransactionUtils = new ManualTransactionUtils();
	private SenseTransactionHelper senseTransactionsHelper = new SenseTransactionHelper();
	private AccountsHelper accountsHelper = new AccountsHelper();
	
	@BeforeClass(alwaysRun = true)
	public void AddAccount() throws JSONException {
		User userInfo = User.builder().build();
		userInfo.setUsername("YSLSense" + System.currentTimeMillis());
		userInfo.setPassword("Test@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);

		Response getProviderAccountResponse = providerAccountUtils.addProviderAccountStrict(Long.parseLong(providerId),
				strLoginFormType, strDagUser, strDagPassword, sessionObj);
		providerAccountId = getProviderAccountResponse.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("provider acct id before class:" + providerAccountId);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\DeleteTransactionManual.csv")
	public void deleteTransactionManual(String tcid, String tcName, int httpStatusCode, String resFile,
			String resFilePath, String container, String enabled) throws Exception {
		System.out.println("tcNametcName::"+tcName);
		if ("false".equalsIgnoreCase(enabled)) {
			throw new SkipException("Skipping Test Case ==> " + tcName);
		}
		CreateTransactionsRequest createTransactionRequest = transactionsHelper.createManualTxnObject(210.23, "USD",
				"consumer", "CREDIT", 29, "SYSTEM", "0,0,1", "memo", container, "12", "DEPOSIT", sessionObj);
		HttpMethodParameters bodyParam = HttpMethodParameters.builder().build();
		String jsonObj = createTransactionRequest.getJsonString();
		bodyParam.setBodyParams(jsonObj);
		Response response = transactionUtils.createManualTxns(bodyParam, sessionObj);
		createTransactionRequest.setTransactionId(response);
		HashMap<String, Object> pathParam = new HashMap<String, Object>();
		pathParam.put("userTransactionId", createTransactionRequest.getTransactionId());
		HttpMethodParameters httpPathParams = HttpMethodParameters.builder().build();
		httpPathParams.setPathParams(pathParam);
		Response deleteResponse = manualTransactionUtils.deleteManualTransaction(httpPathParams, sessionObj);
		jsonAssertionUtil.assertJSON(deleteResponse, deleteResponse.getStatusCode(), resFile, resFilePath);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\DeleteTransaction.csv")
	public void deleteTxnSystem(String tcid, String tcName, int httpStatusCode, String resFile, String resFilePath,
			String container, String enabled) throws Exception {
		System.out.println("tcNametcName::"+tcName);
		String accountId="";
		
		Long accountString = accountsHelper.getAccountId(container, sessionObj);
		System.out.println("accountString.toString()::"+accountString.toString());
		
		if ("false".equalsIgnoreCase(enabled)) {
			throw new SkipException("Skipping Test Case ==> " + tcName);
		}
		CreateTransactionsRequest createTransactionRequest = transactionsHelper.createManualTxnObject(210.23, "USD",
				"consumer", "CREDIT", 29, "SYSTEM", "0,0,1", "memo", container, "12", "DEPOSIT", sessionObj);
		HttpMethodParameters bodyParam = HttpMethodParameters.builder().build();
		String jsonObj = createTransactionRequest.getJsonString();
		bodyParam.setBodyParams(jsonObj);
		Response response = transactionUtils.createManualTxns(bodyParam, sessionObj);
		createTransactionRequest.setTransactionId(response);
		
		long systemTransactionId = senseTransactionsHelper.getTransactionId(sessionObj, accountString.toString(), container);
		System.out.println("systemTransactionId::"+systemTransactionId);
				
		HashMap<String, Object> pathParam = new HashMap<String, Object>();
		pathParam.put("systemTransactionId", systemTransactionId);
		HttpMethodParameters httpPathParams = HttpMethodParameters.builder().build();
		httpPathParams.setPathParams(pathParam);
		Response deleteResponse = manualTransactionUtils.deleteManualTransaction(httpPathParams, sessionObj);
		jsonAssertionUtil.assertJSON(deleteResponse, deleteResponse.getStatusCode(), resFile, resFilePath);
	}

	/* Trying to delete a paid instance */
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\DeleteTransactionPaid.csv")
	public void deleteTransactionPaid(String tcid, String tcName, int httpStatusCode, String resFile,
			String resFilePath, String container, String enabled) throws Exception {
		System.out.println("tcNametcName::"+tcName);

		if ("false".equalsIgnoreCase(enabled)) {
			throw new SkipException("Skipping Test Case ==> " + tcName);
		}
		CreateTransactionsRequest createTransactionRequest = transactionsHelper.createManualTxnObject(210.23, "USD",
				"consumer", "CREDIT", 29, "SYSTEM", "0,0,1", "memo", container, "12", "DEPOSIT", sessionObj);
		HttpMethodParameters bodyParam = HttpMethodParameters.builder().build();
		String jsonObj = createTransactionRequest.getJsonString();
		bodyParam.setBodyParams(jsonObj);
		Response response = transactionUtils.createManualTxns(bodyParam, sessionObj);
		createTransactionRequest.setTransactionId(response);
		HashMap<String, Object> pathParam = new HashMap<String, Object>();
		pathParam.put("userTransactionId", createTransactionRequest.getTransactionId());
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(pathParam);
		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("ruleParam", jsonObj);
		httpParams.setQueryParams(queryParam);
		transactionUtils.updateTransaction(httpParams, sessionObj);
		Response deleteResponse = manualTransactionUtils.deleteManualTransaction(httpParams, sessionObj);
		jsonAssertionUtil.assertJSON(deleteResponse, deleteResponse.getStatusCode(), resFile, resFilePath);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\DeleteTransactionDeleted.csv")
	public void deleteTransactioAlreadyDeleted(String tcid, String tcName, int httpStatusCode, String resFile,
			String resFilePath, String container, String enabled) throws Exception {
		System.out.println("tcNametcName::"+tcName);

		if ("false".equalsIgnoreCase(enabled)) {
			throw new SkipException("Skipping Test Case ==> " + tcName);
		}
		CreateTransactionsRequest createTransactionRequest = transactionsHelper.createManualTxnObject(210.23, "USD",
				"consumer", "CREDIT", 29, "SYSTEM", "0,0,1", "memo", container, "12", "DEPOSIT", sessionObj);
		HttpMethodParameters bodyParam = HttpMethodParameters.builder().build();
		String jsonObj = createTransactionRequest.getJsonString();
		bodyParam.setBodyParams(jsonObj);
		Response response = transactionUtils.createManualTxns(bodyParam, sessionObj);
		createTransactionRequest.setTransactionId(response);
		HashMap<String, Object> pathParam = new HashMap<String, Object>();
		pathParam.put("userTransactionId", createTransactionRequest.getTransactionId());
		HttpMethodParameters httpPathParams = HttpMethodParameters.builder().build();
		httpPathParams.setPathParams(pathParam);
		Response deleteResponse = manualTransactionUtils.deleteManualTransaction(httpPathParams, sessionObj);
		System.out.println("deleteResponse::"+deleteResponse);
		Response deleteResAgain = manualTransactionUtils.deleteManualTransaction(httpPathParams, sessionObj);
		System.out.println("deleteResAgain::"+deleteResAgain);
		jsonAssertionUtil.assertJSON(deleteResAgain, deleteResAgain.getStatusCode(), resFile, resFilePath);
	}

	/* Negative test cases for delete Transaction API */
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\DeleteTransactionNegative.csv")
	public void deleteTransactionNegative(String tcid, String tcName, int httpStatusCode, String resFile,
			String resFilePath, String transactionId, String container, String enabled) throws Exception {
		System.out.println("tcNametcName::"+tcName);

		if ("false".equalsIgnoreCase(enabled)) {
			throw new SkipException("Skipping Test Case ==> " + tcName);
		}
		EnvSession envSession = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		Integer iTransactionId = transactionsHelper.getSystemTransactionId(envSession, container);
		HashMap<String, Object> pathParam = new HashMap<String, Object>();
		pathParam.put("userTransactionId", iTransactionId);
		HttpMethodParameters httpPathParams = HttpMethodParameters.builder().build();
		httpPathParams.setPathParams(pathParam);
		Response deleteResponse = manualTransactionUtils.deleteManualTransaction(httpPathParams, sessionObj);
		jsonAssertionUtil.assertJSON(deleteResponse, deleteResponse.getStatusCode(), resFile, resFilePath);
	}

	/* Passing alphabets for transaction id in delete API */
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\DeleteAlphabetId.csv")
	public void deleteTxnAlhpabet(String tcid, String tcName, int httpStatusCode, String resFile, String resFilePath,
			String transactionId, String container, String enabled) throws Exception {
		System.out.println("tcNametcName::"+tcName);

		if ("false".equalsIgnoreCase(enabled)) {
			throw new SkipException("Skipping Test Case ==> " + tcName);
		}
		HashMap<String, Object> pathParam = new HashMap<String, Object>();
		pathParam.put("userTransactionId", transactionId);
		HttpMethodParameters httpPathParams = HttpMethodParameters.builder().build();
		httpPathParams.setPathParams(pathParam);
		Response deleteResponse = manualTransactionUtils.deleteManualTransaction(httpPathParams, sessionObj);
		jsonAssertionUtil.assertJSON(deleteResponse, deleteResponse.getStatusCode(), resFile, resFilePath);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\DeleteTransactionAggregated.csv")
	public void deleteTransactionAggregated(String tcid, String tcName, int httpStatusCode, String resFile,
			String resFilePath, String transaction, String container, String enabled) throws Exception {
		System.out.println("tcNametcName::"+tcName);

		if ("false".equalsIgnoreCase(enabled)) {
			throw new SkipException("Skipping Test Case ==> " + tcName);
		}
		Long transactionId = transactionsHelper.getTransactionId(sessionObj,
				String.valueOf(accountHelper.getAccountId(container, sessionObj)), container);
		HashMap<String, Object> pathParam = new HashMap<String, Object>();
		pathParam.put("userTransactionId", transactionId);
		HttpMethodParameters httpPathParams = HttpMethodParameters.builder().build();
		httpPathParams.setPathParams(pathParam);
		Response deleteResponse = manualTransactionUtils.deleteManualTransaction(httpPathParams, sessionObj);
		jsonAssertionUtil.assertJSON(deleteResponse, deleteResponse.getStatusCode(), resFile, resFilePath);
	}
	

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);
	}
}
