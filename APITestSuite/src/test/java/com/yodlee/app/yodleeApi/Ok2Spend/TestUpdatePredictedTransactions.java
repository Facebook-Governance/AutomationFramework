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
* @author  Shashi Kiran (skiran1@yodlee.com)
* @version 1.0
* @since   2016-11-20
*/
package com.yodlee.app.yodleeApi.Ok2Spend;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.SenseTransactionHelper;
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.CreateTransactionsRequest;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.PredictedUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import io.restassured.response.Response;
import java.util.HashMap;
import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
* @author Mahadev
*
*/
public class TestUpdatePredictedTransactions extends Base {

	private long providerAccountId;
	private String providerId = "16441";
	private String strDagUser = "gerRecNew.site16441.1";
	private String strDagPassword = "site16441.1";
	private String strLoginFormType = "fieldarray";
	private EnvSession sessionObj = null;
	private Configuration configurationObj = Configuration.getInstance();
	private UserUtils userUtils = new UserUtils();
	private PredictedUtils predictedUtils = new PredictedUtils();
	private TransactionUtils transactionUtils = new TransactionUtils();
	private TransactionsHelper transactionsHelper = new TransactionsHelper();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private CommonUtils commonUtils = new CommonUtils();
	private JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	private UserHelper userHelper = new UserHelper();		
	private SenseTransactionHelper senseTransactionsHelper = new SenseTransactionHelper();

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
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\UpdateTransactions.csv")
	public void testUpdateTransactionsForManual(String testcaseId, String testCase, String amount, String currency,
			String consumer, String baseType, int category, String categorySource, String date, String memo,
			String accountId, String checkNumber, String reconStatus, String sourceType, String transactionType,
			int httpStatus, String ResFile,String FilePath,String Enabled) throws Exception {
		
		if ("false".equalsIgnoreCase(Enabled)) {
			throw new SkipException("Skipping Test Case ==> " + testCase);
		}
		System.out.println("Test Case Id is " + testcaseId + " test case is " + testCase);

		CreateTransactionsRequest createTransactionRequest = transactionsHelper.createManualTxnObject(210.23, currency,
				"consumer", "CREDIT", 29, "SYSTEM", "0,0,1", "memo", accountId, "1223", "DEPOSIT", sessionObj);
		
		HttpMethodParameters bodyParam = HttpMethodParameters.builder().build();
		String jsonObj = createTransactionRequest.getJsonString();
		bodyParam.setBodyParams(jsonObj);
		Response createResponse = transactionUtils.createManualTxns(bodyParam, sessionObj);
		String bodyParamForUpdate = senseTransactionsHelper.createPredictedTxnObjectUpdate(accountId, category,
				categorySource, consumer, memo, amount, baseType, reconStatus, checkNumber, date, sourceType,
				transactionType, currency);
	
		createTransactionRequest.setTransactionId(createResponse);

		HashMap<String, Object> pathParam = new HashMap<String, Object>();
		pathParam.put("userTransactionId", createTransactionRequest.getTransactionId());
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(pathParam);
		httpParams.setBodyParams(bodyParamForUpdate);
		Response response = predictedUtils.updatePredictedTransactions(httpParams, sessionObj);
		jsonAssertionUtil.assertJSON(response, httpStatus, ResFile, FilePath);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\UTSystem.csv")
	public void testUpdateTransactionsForSystem(String testcaseId, String testCase, String amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String date, String memo,
			String container, String checkNumber, String reconStatus, String sourceType, String transactionType,
			int httpStatusCode, String enabled) throws Exception {
		if ("false".equalsIgnoreCase(enabled)) {
			throw new SkipException("Skipping Test Case ==> " + testCase);
		}
		System.out.println("Test Case Id is " + testcaseId + " test case is " + testCase);

		String updateTransactionRequest = senseTransactionsHelper.createPredictedTxnObjectUpdate(container, categoryId,
				categorySource, consumer, memo, amount, baseType, reconStatus, checkNumber, date, "SYSTEM_PREDICTED",
				transactionType, currency);

		Integer transactionId = senseTransactionsHelper.getSystemTransactionId(sessionObj, container);
		HashMap<String, Object> pathParam = new HashMap<String, Object>();
		pathParam.put("predictedTransactionId", transactionId);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(pathParam);
		httpParams.setBodyParams(updateTransactionRequest);
		Response response = predictedUtils.updatePredictedTransactions(httpParams, sessionObj);
		commonUtils.assertStatusCode(response, httpStatusCode);
	}
	 @AfterClass(alwaysRun = true)
		public void unRegisteredUser() {
			userUtils.unRegisterUser(sessionObj);

		}
}
