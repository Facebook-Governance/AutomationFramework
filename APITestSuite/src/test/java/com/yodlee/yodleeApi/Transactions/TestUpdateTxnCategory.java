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

import java.util.LinkedHashMap;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

@Feature(featureName = { FeatureName.TRANSACTIONS })
public class TestUpdateTxnCategory extends Base {

	long providerAccountId;
	public static final String UPDATE_TXN = "\\TestData\\CSVFiles\\Transactions\\UpdateTransaction.csv";
	public static final String UPDATE_TXN_SUB = "\\TestData\\CSVFiles\\Transactions\\UpdateTransactionSub.csv";
	public static final String UPDATE_TXN_NEGATIVE = "\\TestData\\CSVFiles\\Transactions\\UpdateTransactionNegative.csv";
	private static final String dagUserName = "ttt48.site16441.1";
	private static final String dagPassword = "site16441.1";
	private static final String loginFormType = "loginForm";
	String providerId = "16441";
	
	ProviderAccountUtils providerAccountUtils =  new ProviderAccountUtils();
    Configuration configurationObj = Configuration.getInstance();
    CommonUtils commonUtils = new CommonUtils();
    TransactionsHelper transactionsHelper = new TransactionsHelper();
    TransactionUtils transactionUtils = new TransactionUtils();
    JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
    
	@BeforeClass
	@Info(subfeature = { SubFeature.ADD_ACCOUNT }, userStoryId = "")
	public void AddAccount() {
		
		Response getProviderAccountResponse = providerAccountUtils.addProviderAccountStrict(Long.parseLong(providerId),loginFormType, dagUserName,
				dagPassword,configurationObj.getCobrandSessionObj());
		providerAccountId = getProviderAccountResponse.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("provider acct id before class:" + providerAccountId);
	}

	@Test(dataProvider = Constants.FEEDER, priority = 2)
	@Source(UPDATE_TXN)
	@Info(subfeature = { SubFeature.UPDATE_TRANSACTION }, userStoryId = "")
	public void updateTransaction(String TestCaseId, String TestCase, String container, int categoryId, String consumer,
			String memo, String ResFile, String FilePath, String enabled) throws JSONException {

		System.out.println("*************************TestCase= " + TestCase + " ****************************");
		commonUtils.isTCEnabled(enabled, TestCaseId);
		try {
			// Creating HttpParams
			LinkedHashMap<String, String> queryParam = transactionsHelper.createInputForGetAllTransactions(
					"2010-01-01", null, container, null, null, null, null, null, null, null, null, null, "1");
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setQueryParams(queryParam);

			// API hit: GET all transactions
			
			Response getTrnxResponse = transactionUtils.getAllTransactions(httpMethodParameters,
					Configuration.getInstance().getCobrandSessionObj());
			String transactionId = getTrnxResponse.jsonPath().getString("transaction[0].id");
			System.out.println("transactionId of  container is" + transactionId);

			// Creating httpPrams
			String jsonInput = transactionsHelper.createInputforUpdateTransaction("SYSTEM", container, categoryId,
					consumer, memo);
			LinkedHashMap<String, Object> pathParam = new LinkedHashMap<>();
			pathParam.put("transactionId", transactionId);
			
			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(jsonInput);
			httpParams.setPathParams(pathParam);
			
			// API hit:updating a particular transaction
			Response updateResponse = transactionUtils.updateTransaction(httpParams, Configuration.getInstance().getCobrandSessionObj());

			// API hit: calling again GET Transactions
			Response getTranxResponse = transactionUtils.getAllTransactions(httpMethodParameters, Configuration.getInstance().getCobrandSessionObj());
			System.out.println("Category ::"+getTranxResponse.jsonPath().get("transaction.categoryId"));
			// Asserting response now
			commonUtils.assertStatusCode(updateResponse, HttpStatus.NO_CONTENT_204);
			transactionsHelper.validateUpdatedTransaction(getTranxResponse, FilePath, ResFile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(dataProvider = Constants.FEEDER, priority = 2)
	@Source(UPDATE_TXN_SUB)
	@Info(subfeature = { SubFeature.UPDATE_TRANSACTION_CATEGORY }, userStoryId = "")
	public void updateTransactionUserCategory(String TestCaseId, String TestCase, String container,
			int parentCategoryId, String consumer, String memo, String ResFile, String FilePath, String enabled)
			throws JSONException {

		System.out.println("*************************TestCase= " + TestCase + " ****************************");
		commonUtils.isTCEnabled(enabled, TestCaseId);
		String categoryName = "NewSubCato" + System.currentTimeMillis();

		// Creating HttpParams and hitting POST /transactions/categories
		HttpMethodParameters httpParams = transactionsHelper.createCategoryParams(categoryName,
				String.valueOf(parentCategoryId));
		transactionUtils.createTransactionCategory(httpParams, Configuration.getInstance().getCobrandSessionObj());

		// Hitting GET /transactions/categories
		Response getTrnxCatResp = transactionUtils.getTxnCategories(Configuration.getInstance().getCobrandSessionObj());
		int categoryId = getTrnxCatResp.jsonPath().getInt("transactionCategory[43].id");

		try {
			// Creating HttpParams and GET all transactions
			LinkedHashMap<String, String> queryParam = transactionsHelper.createInputForGetAllTransactions(
					"2010-01-01", null, container, null, null, null, null, null, null, null, null, null, "1");

			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setQueryParams(queryParam);
			Response getTrnxResponse = transactionUtils.getAllTransactions(httpMethodParameters,
					Configuration.getInstance().getCobrandSessionObj());
			String transactionId = getTrnxResponse.jsonPath().getString("transaction[0].id");
			System.out.println("transactionId of  container is" + transactionId);

			// Creating HttpPrams and updating transaction
			String jsonInput = transactionsHelper.createInputforUpdateTransaction("SYSTEM", container, categoryId,
					consumer, memo);
			LinkedHashMap<String, Object> pathParam = new LinkedHashMap<>();
			pathParam.put("transactionId", transactionId);
			HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
			httpParam.setBodyParams(jsonInput);
			transactionUtils.updateTransaction(httpParam, Configuration.getInstance().getCobrandSessionObj());

			// API hit: calling again GET Transactions
			Response getTranxResponse = transactionUtils.getAllTransactions(httpMethodParameters,
					Configuration.getInstance().getCobrandSessionObj());

			// Creating HttpsPrams and Deleting subCategory
			LinkedHashMap<String, Object> pathParams = new LinkedHashMap<>();
			pathParams.put("categoryId", categoryId);
			HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
			httpMethodParams.setPathParams(pathParams);
			transactionUtils.deleteTxnCategory(Configuration.getInstance().getCobrandSessionObj(), httpMethodParams);

			// Asserting response now
			jsonAssertionUtil.assertLenientJSON(getTranxResponse, HttpStatus.OK, ResFile, FilePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(dataProvider = Constants.FEEDER, priority = 2)
	@Info(subfeature = { SubFeature.UPDATE_TRANSACTION }, userStoryId = "")
	@Source(UPDATE_TXN_NEGATIVE)
	public void updateTransactionNegative(String TestCaseId, String TestCase, String container, String updateContainer,
			int categoryId, String categorySource, String consumer, String memo, int updateStatus, String ResFile,
			String FilePath, String enabled) throws JSONException {

		System.out.println("*************************TestCase= " + TestCase + " ****************************");
		commonUtils.isTCEnabled(enabled, TestCaseId);
		try {
			// Creating HttpParams and GET all transactions
			LinkedHashMap<String, String> queryParam = transactionsHelper.createInputForGetAllTransactions(
					"2010-01-01", null, container, null, null, null, null, null, null, null, null, null, "1");

			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setQueryParams(queryParam);
			Response getTrnxResponse = transactionUtils.getAllTransactions(httpMethodParameters,
					Configuration.getInstance().getCobrandSessionObj());
			String transactionId = getTrnxResponse.jsonPath().getString("transaction[0].id");
			System.out.println("transactionId of  container is" + transactionId);

			// Creating HttpPrams and updating transaction
			String jsonInput = transactionsHelper.createInputforUpdateTransaction("SYSTEM", container, categoryId,
					consumer, memo);
			LinkedHashMap<String, Object> pathParam = new LinkedHashMap<>();
			pathParam.put("transactionId", transactionId);
			HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
			httpParam.setBodyParams(jsonInput);
			Response updateTxnResp = transactionUtils.updateTransaction(httpParam,
					Configuration.getInstance().getCobrandSessionObj());

			// Asserting response now
			jsonAssertionUtil.assertLenientJSON(updateTxnResp, updateStatus, ResFile, FilePath);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
