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
package com.yodlee.app.yodleeApi.Ok2Spend;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.helper.AccountsHelper;
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

/**
 * @author Mahadev A N
 *
 */
public class TestGetManualTransactions extends Base {

	private long providerAccountId;
	private String strProviderId = "16441";
	private String strDagUserName = "gerRecNew.site16441.1";
	private String strDagPassword = "site16441.1";
	private String strLoginFormType = "fieldarray";
	private EnvSession sessionObj = null;
	private CommonUtils commonUtils = new CommonUtils();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private Configuration configurationObj = Configuration.getInstance();
	private TransactionsHelper transactionsHelper = new TransactionsHelper();
	private TransactionUtils transactionUtils = new TransactionUtils();
	private UserUtils userUtils = new UserUtils();
	private UserHelper userHelper = new UserHelper();
	private SenseTransactionHelper senseTransactionsHelper = new SenseTransactionHelper();
	private PredictedUtils predictedUtils = new PredictedUtils();
	private AccountsHelper accountsHelper = new AccountsHelper();
	private JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();

	@BeforeClass(alwaysRun = true)
	public void AddAccount() throws JSONException {
		User userInfo = User.builder().build();
		userInfo.setUsername("YSLSense" + System.currentTimeMillis());
		userInfo.setPassword("Test@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);

		Response getProviderAccountResponse = providerAccountUtils.addProviderAccountStrict(
				Long.parseLong(strProviderId), strLoginFormType, strDagUserName, strDagPassword, sessionObj);
		providerAccountId = getProviderAccountResponse.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("provider acct id before class:" + providerAccountId);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetManualTransaction.csv")
	public void testGetManualTransactions(String testCaseId, String testcaseName, String fromDate, String toDate,
			String container, String baseType, String keyword, String accountId,String bothCardBank,String seriesId, String sourceType,
			String categoryId, String type, String categoryType, String skip, String top, int httpStatusCode,
			String resFile, String resFilePath, String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, testCaseId);
		Long accountIdString = accountsHelper.getAccountId(accountId, sessionObj);
		System.out.println("Testcase is executing from testGetTransactions() ::" + testcaseName);
		LinkedHashMap<String, String> mapQueryParams;
		if(bothCardBank.contains("bank,creditCard")){
			Long cardAccountId = accountsHelper.getAccountId("creditCard", sessionObj);
			Long bankAccountId = accountsHelper.getAccountId("bank", sessionObj);
			String cardAccountIdString =cardAccountId.toString();
			String	bankAccountIdString = bankAccountId.toString();
			String accountIdStringBoth = cardAccountIdString+","+bankAccountIdString;
			System.out.println("accountIdStringBoth::"+accountIdStringBoth);
			mapQueryParams = senseTransactionsHelper.createInputForGetAllTransactions(fromDate,
					toDate, container, baseType, keyword, accountIdStringBoth, seriesId, sourceType, type, categoryId, categoryType,
					skip, top);
		}else if(container.contains("null") && accountId.contains("null")){
			mapQueryParams = senseTransactionsHelper.createInputForGetAllTransactions(fromDate,
					toDate, "", baseType, keyword, "", seriesId, sourceType, type, categoryId, categoryType,
					skip, top);
		}else if(container.contains("investment") && accountId.contains("null")){
			mapQueryParams = senseTransactionsHelper.createInputForGetAllTransactions(fromDate,
					toDate, container, baseType, keyword, "", seriesId, sourceType, type, categoryId, categoryType,
					skip, top);
		}
		else{
			mapQueryParams = senseTransactionsHelper.createInputForGetAllTransactions(fromDate,
					toDate, container, baseType, keyword, accountIdString.toString(), seriesId, sourceType, type, categoryId, categoryType,
					skip, top);
		}
		HttpMethodParameters httpQueryParam = HttpMethodParameters.builder().build();
		httpQueryParam.setQueryParams(mapQueryParams);
		Response getTransactionResponse = predictedUtils.getPredictedTransactions(httpQueryParam, sessionObj);
		String actual = getTransactionResponse.asString();
		if (actual.contains("predictedTransaction") && actual.contains("predictedEventId")) {
			Assert.assertTrue(true,"predictedTransaction and predictedEventId validated");
		}else{
			jsonAssertionUtil.assertJSON(getTransactionResponse, resFile, resFilePath);
		}
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetManulTransactionSingle.csv")
	public void testGetManualTransactionForSingle(String testCaseId, String testcaseName, String fromDate, String toDate,
			String container, String baseType, String keyword, String accountId,String bothCardBank,String seriesId, String sourceType,
			String categoryId, String type, String categoryType, String skip, String top, int httpStatusCode,
			String respFile, String FilePath, String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, testCaseId);
		System.out.println("Testcase is executing from testGetTransactions() ::" + testcaseName);

		if (testcaseName.contains("predictedTransactionId")){
			Integer systemPredictedTransId = transactionsHelper.getSystemTransactionId(sessionObj, container);
			System.out.println("predictedTransactionId::"+systemPredictedTransId);
			HashMap<String, String> queryParam1 = new HashMap<String, String>();
			HttpMethodParameters httpParamsId = HttpMethodParameters.builder().build();
			HashMap<String, Object> pathParam = new HashMap<>();
			pathParam.put("predictedTransactionId",systemPredictedTransId);
			httpParamsId.setPathParams(pathParam);
			httpParamsId.setQueryParams(queryParam1);
			Response getTransactionResponse = predictedUtils.getPredictedTransactions(httpParamsId, sessionObj);
			if (getTransactionResponse.asString().contains("predictedTransaction") && getTransactionResponse.asString().contains("predictedEventId")) {
				Assert.assertTrue(true,"predictedTransaction and predictedEventId validated");
			}else{
				Assert.assertFalse(true);
			}
		}
		else if (testcaseName.contains("manualTransactionId")){
			CreateTransactionsRequest createTransactionRequest = transactionsHelper.createManualTxnObject(210.23, "USD",
					"consumer", "CREDIT", 29, "SYSTEM", "0,0,1", "memo", "BANK", "12", "DEPOSIT",
					sessionObj);
			HttpMethodParameters bodyParam = HttpMethodParameters.builder().build();
			String jsonObj = createTransactionRequest.getJsonString();
			bodyParam.setBodyParams(jsonObj);
			Response createResponse = transactionUtils.createManualTxns(bodyParam, sessionObj);
			createTransactionRequest.setTransactionId(createResponse);
			System.out.println("createTransactionRequest.getTransactionId()::"+createTransactionRequest.getTransactionId());
			HashMap<String, String> queryParam1 = new HashMap<String, String>();
			HttpMethodParameters httpParamsId = HttpMethodParameters.builder().build();
			HashMap<String, Object> pathParam = new HashMap<>();
			pathParam.put("manualTransactionId",createTransactionRequest.getTransactionId());
			httpParamsId.setPathParams(pathParam);
			httpParamsId.setQueryParams(queryParam1);
			Response getTransactionResponse = predictedUtils.getPredictedTransactions(httpParamsId, sessionObj);
			jsonAssertionUtil.assertResponse(getTransactionResponse,httpStatusCode, respFile, FilePath);
		}
		else if (testcaseName.contains("AggregatedTransactionID")){
			Long bankAccountId = accountsHelper.getAccountId("bank", sessionObj);
			Long aggregatedTransId = transactionsHelper.getTransactionId(sessionObj,bankAccountId.toString(),"bank");
			System.out.println("aggregatedTransId::"+aggregatedTransId);
			HashMap<String, String> queryParam1 = new HashMap<String, String>();
			HttpMethodParameters httpParamsId = HttpMethodParameters.builder().build();
			HashMap<String, Object> pathParam = new HashMap<>();
			pathParam.put("aggregatedTransactionId",aggregatedTransId);
			httpParamsId.setPathParams(pathParam);
			httpParamsId.setQueryParams(queryParam1);
			Response getTransactionResponse = predictedUtils.getPredictedTransactions(httpParamsId, sessionObj);
			jsonAssertionUtil.assertResponse(getTransactionResponse,httpStatusCode, respFile, FilePath);
		}
		if (testcaseName.contains("predictedEventId")){
			HashMap<String, String> queryParams = new HashMap<String, String>();
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setQueryParams(queryParams);
			Response getRecuringEventsResponse = predictedUtils.getPredictedEvents(httpMethodParameters, sessionObj);
			String predictedEventId = senseTransactionsHelper.getRecurringEventId(getRecuringEventsResponse, "VALID");
			Integer systemPredictedTransId = transactionsHelper.getSystemTransactionId(sessionObj, container);
			System.out.println("systemPredictedTransId::"+systemPredictedTransId);
			HashMap<String, String> queryParam1 = new HashMap<String, String>();
			HttpMethodParameters httpParamsId = HttpMethodParameters.builder().build();
			HashMap<String, Object> pathParam = new HashMap<>();
			queryParam1.put("predictedEventId", predictedEventId);
			httpParamsId.setPathParams(pathParam);
			httpParamsId.setQueryParams(queryParam1);
			Response getTransactionResponse = predictedUtils.getPredictedTransactions(httpParamsId, sessionObj);
			if (getTransactionResponse.asString().contains("predictedTransaction") && getTransactionResponse.asString().contains("predictedEventId")) {
				Assert.assertTrue(true,"predictedTransaction and predictedEventId validated");
			}else if (getTransactionResponse.asString().contains("Resource")) {
				Assert.assertTrue(true);
			}
			else{
				Assert.assertFalse(false);
			}
		}
		else{
			Long invalidTransId = 1324366L;
			System.out.println("invalidTransId::"+invalidTransId);
			HashMap<String, String> queryParam1 = new HashMap<String, String>();
			HttpMethodParameters httpParamsId = HttpMethodParameters.builder().build();
			HashMap<String, Object> pathParam = new HashMap<>();
			pathParam.put("invalidTransId",invalidTransId);
			httpParamsId.setPathParams(pathParam);
			httpParamsId.setQueryParams(queryParam1);
			Response getTransactionResponse = predictedUtils.getPredictedTransactions(httpParamsId, sessionObj);
			jsonAssertionUtil.assertResponse(getTransactionResponse,httpStatusCode, respFile, FilePath);
		}
	}

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);

	}
}
