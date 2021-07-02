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

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;


/**
 * @author partha sham
 *
 */
public class TestTransactionOrtho extends Base{

	private static final String TRANSACTION_COUNT_NEW = "\\TestData\\CSVFiles\\Transactions\\Txns\\getTxnsCount.csv";
	private static final String TRANSACTION_COUNT_NEG_NEW = "\\TestData\\CSVFiles\\Transactions\\Txns\\getTxnsCountNegative.csv";
	private static final String TRANSACTION_NEW = "\\TestData\\CSVFiles\\Transactions\\Txns\\GetTransactionsFinal.csv";
	private static final String TRANSACTION_NEG_NEW = "\\TestData\\CSVFiles\\Transactions\\Txns\\GetTransactionsNegative.csv";
	private static final String GT_SESSIONS = "\\TestData\\CSVFiles\\Transactions\\Txns\\GT_Sessions.csv";
	private static final String GTC_SESSIONS = "\\TestData\\CSVFiles\\Transactions\\Txns\\GTC_Sessions.csv";
	private static final String TRANSACTION_RECON = "\\TestData\\CSVFiles\\Transactions\\Txns\\GetTxnsReconciliation.csv";
	private static final String TRANSACTION_COUNT_RECON = "\\TestData\\CSVFiles\\Transactions\\Txns\\GetTxnsCountReconciliation.csv";
	private static final String TRANSACTION_NEW_POS = "\\TestData\\CSVFiles\\Transactions\\Txns\\GetTxnsFinalPositive.csv";
	private static final String TRANSACTION_COUNT_NEW_POS = "\\TestData\\CSVFiles\\Transactions\\Txns\\GetTxnsCountFinalPositive.csv";
	private static final String HLC="10000004,10000009,10000012,10000015,10000016,10000017,10000020";
	private static final String  TRANSACTION_BANK_CREDIT = "\\TestData\\CSVFiles\\Transactions\\Txns\\GetTxnForBankCreditCard.csv";
	private static final String TRANSACTION_COUNT_BANK_CREDIT = "\\TestData\\CSVFiles\\Transactions\\Txns\\getTxnsCountForBankCredit.csv";
	private static String strProviderId = "16441";
	private static String strDagUser = "Finaltxns.site16441.2";
	private static String strDagPassword = "site16441.2";
	private static String strLoginFormType = "loginform";
	private static long providerAccountId;
	private static String fileNamePrefix="";
	private static boolean isTDEnable=false, isReconcileEnable=true, isMergedCatEnabled=false, isGeoLocationEnabled=false,isSimpleDescEnabled=false;
	
	ProviderAccountUtils providerAccountUtils =  new ProviderAccountUtils();
    Configuration configurationObj = Configuration.getInstance();
    CommonUtils commonUtils = new CommonUtils();
	AccountUtils accountUtils = new AccountUtils();
	AccountsHelper accountsHelper = new AccountsHelper();
	TransactionUtils transactionUtils = new TransactionUtils();
	TransactionsHelper transactionsHelper = new TransactionsHelper();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	
	@BeforeClass
	@Info(userStoryId = "", subfeature = {SubFeature.ADD_ACCOUNT})
	public void AddAccount() throws JSONException{
		
		Response getProviderAccountResponse = providerAccountUtils.addProviderAccountStrict(Long.parseLong(strProviderId),strLoginFormType, strDagUser,
				strDagPassword,configurationObj.getCobrandSessionObj());
		providerAccountId = getProviderAccountResponse.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("provider acct id before class:" + providerAccountId);
	}

	@Info(subfeature = {SubFeature.GET_TRANSACTIONS}, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source(TRANSACTION_BANK_CREDIT)
	public void getTransactionsForBankCreditCard(String TestCaseId, String containerKey, String container, String baseTypeKey, String baseType, String categoryTypeKey, String categoryType,
			String keywordKey, String keyword, String categoryKey, String TDECategory,String NonTDECategory, String categoryIdKey,String categoryIds,
			String fromDateKey, String fromDate,String toDateKey, String toDate, String highLevelCategoryIdKey , String highLevelCategoryId,
			String typeKey, String TDEType,String NonTDEType, String   accountReconTypeKey,String accountReconType, String skipKey, String skip,
			String topKey,String top, int status, String filePath, String Enabled, String  DefectID, String accountIdKey,String Features, String UniqueID) {
		
		
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		
		System.out.println("*****************Testing transaction API**************");
		System.out.println("Executing test case : " + TestCaseId);
		if(highLevelCategoryId.equalsIgnoreCase("valid")) {
			highLevelCategoryId=HLC;
		}
		System.out.println("############### "+highLevelCategoryId);
		Set<Long> accountIds = new HashSet<>();
		Map<String, Object> mapQueryParam = accountsHelper.createQueryParamsForGetAccounts(String.valueOf(status), container, String.valueOf(providerAccountId), "", accountReconType, "", "");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(mapQueryParam);
		Response res = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());
		try {
			List<Long> list = res.jsonPath().getList("account.id");
			Set<Long> ids = new HashSet<>(list);
			accountIds.addAll(ids);
		} catch (Exception e) {
			logger.info("Account id not coming...");
		}
	
		String category=NonTDECategory;
		String type=NonTDEType;
		if(isTDEnable) {
			category=TDECategory;
			type=TDEType;
		}

		LinkedHashMap<String, String> params = transactionsHelper.createMapForTxns(containerKey,container, baseTypeKey,baseType,categoryTypeKey,categoryType,
				keywordKey,keyword,categoryKey,category,categoryIdKey,categoryIds,
				fromDateKey,fromDate,toDateKey,toDate,highLevelCategoryIdKey ,highLevelCategoryId,
				typeKey,type, accountReconTypeKey,accountReconType, skipKey,skip,
				topKey,top, accountIdKey,accountIds);
		httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(params);
		Response response = transactionUtils.getAllTransactions(httpParams, configurationObj.getCobrandSessionObj());
		try {
			int size = response.jsonPath().getList("transaction").size();
			System.out.println("No. of transactions : " +size);
			if(size>500) {
				Assert.fail("FAILED: More than 500 transactions coming in response.....");
			}
		}catch(Exception e) {
			System.out.println("Empty response");
		}
		response.then().log().all();
		fileNamePrefix = transactionsHelper.createFileNamePrefix(isTDEnable, isGeoLocationEnabled, isSimpleDescEnabled, isMergedCatEnabled);
		jsonAssertionUtil.assertJSON(response, fileNamePrefix + UniqueID+ ".json", filePath);
	}

	@Info(subfeature = {SubFeature.GET_TRANSACTIONS}, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source(TRANSACTION_NEW)
	public void getTransactionsForOtherContainers(String TestCaseId, String containerKey, String container, String baseTypeKey, String baseType, String categoryTypeKey, String categoryType,
			String keywordKey, String keyword, String categoryKey,String category, String categoryIdKey,String categoryIds,
			String fromDateKey, String fromDate,String toDateKey, String toDate, String highLevelCategoryIdKey , String highLevelCategoryId,
			String typeKey, String type, String   accountReconTypeKey,String accountReconType, String skipKey, String skip,
			String topKey,String top, int status, String filePath, String Enabled, String  DefectID, String accountIdKey,String Features, String UniqueID) {
		
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		
		fromDate=fromDate.replaceAll("//","-");
		System.out.println("*****************Testing transaction API**************");
		System.out.println("Executing test case : " + TestCaseId);
		if(highLevelCategoryId.equalsIgnoreCase("valid")) {
			highLevelCategoryId=HLC;
		}
		System.out.println("############### "+highLevelCategoryId);
		Set<Long> accountIds = new HashSet<>();
		Map<String, Object> mapQueryParam = accountsHelper.createQueryParamsForGetAccounts(String.valueOf(status), container, String.valueOf(providerAccountId), "", accountReconType, "", "");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(mapQueryParam);
		Response res = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj()); 
		try {
			List<Long> list = res.jsonPath().getList("account.id");
			Set<Long> ids = new HashSet<>(list);
			accountIds.addAll(ids);
		} catch (Exception e) {
			logger.info("Account id not coming...");
		}

		LinkedHashMap<String, String> params = transactionsHelper.createMapForTxns(containerKey,container, baseTypeKey,baseType,categoryTypeKey,categoryType,
				keywordKey,keyword,categoryKey,category,categoryIdKey,categoryIds,
				fromDateKey,fromDate,toDateKey,toDate,highLevelCategoryIdKey ,highLevelCategoryId,
				typeKey,type, accountReconTypeKey,accountReconType, skipKey,skip,
				topKey,top, accountIdKey,accountIds);

		httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(params);
		Response response = transactionUtils.getAllTransactions(httpParams, configurationObj.getCobrandSessionObj());
		try {
			int size = response.jsonPath().getList("transaction").size();
			System.out.println("No. of transactions : " +size);
			if(size>500) {
				Assert.fail("FAILED: More than 500 transactions coming in response.....");
			}
		}catch(Exception e) {
			System.out.println("Empty response");
		}
		response.then().log().all();
		jsonAssertionUtil.assertJSON(response, fileNamePrefix + UniqueID+ ".json", filePath);
	}
	
	@Info(userStoryId="", subfeature = {SubFeature.GET_TRANSACTIONS})
	@Test(enabled=true, dataProvider = "feeder",priority = 2)
	@Source(TRANSACTION_NEW_POS)
	public void getTxnPositiveScenarios(String TestCaseId, String containerKey, String container, String baseTypeKey, String baseType, String categoryTypeKey, String categoryType,
			String keywordKey, String keyword, String categoryKey, String TDECategory,String NonTDECategory, String categoryIdKey,String categoryIds,
			String fromDateKey, String fromDate,String toDateKey, String toDate, String highLevelCategoryIdKey , String highLevelCategoryId,
			String typeKey, String TDEType, String NonTDEType, String   accountReconTypeKey,String accountReconType, String skipKey, String skip,
			String topKey,String top, int   status, String filePath, String Enabled, String  DefectID, String accountIdKey,String accountIdValue, String accountIdValid,String Features, String UniqueID) {
		
		commonUtils.isTCEnabled(Enabled, TestCaseId);
			
		System.out.println("TestCase id is " + TestCaseId );
		
		if(highLevelCategoryId.equalsIgnoreCase("valid")) {
			highLevelCategoryId=HLC;
		}
	
		String category=NonTDECategory;
		String type=NonTDEType;
		if(isTDEnable) {

			category=TDECategory;
			type=TDEType;
		}
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Set<Long> accountIds = new HashSet<>();
		if((accountIdKey!=null && accountIdKey!="") && (accountIdValue==null || accountIdValue.equalsIgnoreCase("")) )
		{
			Map<String, Object> mapQueryParam = accountsHelper.createQueryParamsForGetAccounts("ACTIVE", container, String.valueOf(providerAccountId), "", accountReconType, "", "");
			
			httpParams.setQueryParams(mapQueryParam);
			Response res = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());
			try {
				List<Long> list = res.jsonPath().getList("account.id");
				Set<Long> ids = new HashSet<>(list);
				accountIds.addAll(ids);
				for (Long id : accountIds) {
					System.out.println("Id is "+id);
				}
			} catch (Exception e) {
				logger.info("Account id not coming...");
			}

		}
		else if((accountIdKey!=null && accountIdKey!="") && (accountIdValue!=null && !accountIdValue.equalsIgnoreCase("")))
		{
			try {
				accountIds.add(Long.parseLong(accountIdValue));
			} catch (NumberFormatException e) {
				logger.error("Failed to parse AccountId Value : " + e);
				// Assert.fail("Failed to create JSONObject of account response.");
				Assert.assertFalse(false, "Failed to create JSONObject of account response.");
			}
			if(accountIdValid.equalsIgnoreCase("multiple"))
			{
				accountIds.add((long) 989898);
			}
		}

		LinkedHashMap<String, String> params = transactionsHelper.createMapForTxns(containerKey,container, baseTypeKey,baseType,categoryTypeKey,categoryType,
				keywordKey,keyword,categoryKey,category,categoryIdKey,categoryIds,
				fromDateKey,fromDate,toDateKey,toDate,highLevelCategoryIdKey ,highLevelCategoryId,
				typeKey,type, accountReconTypeKey,accountReconType, skipKey,skip,
				topKey,top, accountIdKey,accountIds);

		httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(params);
		Response response = transactionUtils.getAllTransactions(httpParams, configurationObj.getCobrandSessionObj());
		response.then().log().all();
		try {
			int size = response.jsonPath().getList("transaction").size();
			System.out.println("No. of transactions : " +size);
			if(size>500) {
				Assert.fail("FAILED: More than 500 transactions coming in response.....");
			}
		}catch(Exception e) {
			System.out.println("Empty response");
		}
		jsonAssertionUtil.assertJSON(response, fileNamePrefix + UniqueID+ ".json", filePath);
	}

	@Info(subfeature = {SubFeature.GET_TRANSACTIONS}, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 3)
	@Source(TRANSACTION_RECON)
	public void getTransactionsReconciliation(String TestCaseId, String containerKey, String container, String baseTypeKey, String baseType, String categoryTypeKey, String categoryType,
			String keywordKey, String keyword, String categoryKey, String category, String categoryIdKey,String categoryIds,
			String fromDateKey, String fromDate,String toDateKey, String toDate, String highLevelCategoryIdKey , String highLevelCategoryId,
			String typeKey, String type, String   accountReconTypeKey,String accountReconType, String skipKey, String skip,
			String topKey,String top, int   status, String filePath, String Enabled, String  DefectID, String accountIdKey,String Features, String ReconFileName, String UnReconFileName) {
		
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		
		fromDate=fromDate.replaceAll("//","-");
		toDate=toDate.replaceAll("//", "-");
		System.out.println("*****************Testing transaction API**************");
		System.out.println("Executing test case : " + TestCaseId);
		if(highLevelCategoryId.equalsIgnoreCase("valid")) {
			highLevelCategoryId=HLC;
		}
		Set<Long> accountIds = new HashSet<>();
		Map<String, Object> mapQueryParam = accountsHelper.createQueryParamsForGetAccounts(String.valueOf(status), container, String.valueOf(providerAccountId), "", accountReconType, "", "");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(mapQueryParam);
		Response res = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj()); 
		try {
			List<Long> list = res.jsonPath().getList("account.id");
			Set<Long> ids = new HashSet<>(list);
			accountIds.addAll(ids);
		} catch (Exception e) {
			logger.info("Account id  not coming....");
		}
		String fileName=UnReconFileName;
		if(isReconcileEnable) {
			fileName=ReconFileName;
		}
		if(isMergedCatEnabled) {
			fileName="MC-"+fileName;
		}

		LinkedHashMap<String, String> params = transactionsHelper.createMapForTxns(containerKey,container, baseTypeKey,baseType,categoryTypeKey,categoryType,
				keywordKey,keyword,categoryKey,category,categoryIdKey,categoryIds,
				fromDateKey,fromDate,toDateKey,toDate,highLevelCategoryIdKey ,highLevelCategoryId,
				typeKey,type, accountReconTypeKey,accountReconType, skipKey,skip,
				topKey,top, accountIdKey,accountIds);

		httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(params);

		Response response = transactionUtils.getAllTransactions(httpParams, configurationObj.getCobrandSessionObj());
		response.then().log().all();
		try {
			int size = response.jsonPath().getList("transaction").size();
			System.out.println("No. of transactions : " +size);
			if(size>500) {
				Assert.fail("FAILED: More than 500 transactions coming in response.....");
			}
		}catch(Exception e) {
			System.out.println("Empty response");
		}
		jsonAssertionUtil.assertJSON(response, fileName+".json", filePath);
	}

	@Info(subfeature = {SubFeature.GET_TRANSACTIONS}, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 4)
	@Source(TRANSACTION_NEG_NEW)
	public void getTransactionsNegative(String TestCaseId, String containerKey, String container, String baseTypeKey, String baseType, String categoryTypeKey, String categoryType,
			String keywordKey, String keyword, String categoryKey, String category, String categoryIdKey,String categoryIds,
			String fromDateKey, String fromDate,String toDateKey, String toDate, String highLevelCategoryIdKey , String highLevelCategoryId,
			String typeKey, String type, String   accountReconTypeKey,String accountReconType, String skipKey, String skip,
			String topKey,String top, int   status, String filePath, String Enabled, String  DefectID, String accountIdKey,String Features,String fileName) {
		
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		fromDate=fromDate.replaceAll("//","-");
		toDate=toDate.replaceAll("//", "-");
		System.out.println("*****************Testing transaction API**************");
		System.out.println("Executing test case : " + TestCaseId);
		Set<Long> accountIds = new HashSet<>();
		LinkedHashMap<String, String> params = transactionsHelper.createMapForTxns(containerKey,container, baseTypeKey,baseType,categoryTypeKey,categoryType,
				keywordKey,keyword,categoryKey,category,categoryIdKey,categoryIds,
				fromDateKey,fromDate,toDateKey,toDate,highLevelCategoryIdKey ,highLevelCategoryId,
				typeKey,type, accountReconTypeKey,accountReconType, skipKey,skip,
				topKey,top, accountIdKey,accountIds);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(params);

		Response response = transactionUtils.getAllTransactions(httpParams, configurationObj.getCobrandSessionObj());
		response.then().log().all();
		jsonAssertionUtil.assertJSON(response, fileNamePrefix+".json", filePath);
	}

	@Info(subfeature = {SubFeature.GET_TRANSACTIONS_COUNT}, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 5)
	@Source(TRANSACTION_COUNT_BANK_CREDIT)
	public void getTransactionsCountForBankCreditCard(String TestCaseId, String containerKey, String container, String baseTypeKey, String baseType, String categoryTypeKey, String categoryType,
			String keywordKey, String keyword, String categoryKey, String TDECategory,String NonTDECategory, String categoryIdKey,String categoryIds,
			String fromDateKey, String fromDate,String toDateKey, String toDate, String highLevelCategoryIdKey , String highLevelCategoryId,
			String typeKey, String TDEType,String NonTDEType, String   accountReconTypeKey,String accountReconType,
			int   status, String filePath, String Enabled, String  DefectID, String accountIdKey,String Features, String UniqueID) {
		
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		
		System.out.println("*****************Testing transaction API**************");
		System.out.println("Executing test case : " + TestCaseId);
		if(highLevelCategoryId.equalsIgnoreCase("valid")) {
			highLevelCategoryId=HLC;
		}
		Set<Long> accountIds = new HashSet<>();
		Map<String, Object> mapQueryParam = accountsHelper.createQueryParamsForGetAccounts("", container, String.valueOf(providerAccountId), "", accountReconType, "", "");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(mapQueryParam);
		Response res = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj()); 
		try {
			List<Long> list = res.jsonPath().getList("account.id");
			Set<Long> ids = new HashSet<>(list);
			accountIds.addAll(ids);
		} catch (Exception e) {

		}
		
		String category=NonTDECategory;
		String type=NonTDEType;
		if(isTDEnable) {		
			category=TDECategory;
			type=TDEType;
		}

		LinkedHashMap<String, String> params = transactionsHelper.createMapForTxns(containerKey,container, baseTypeKey,baseType,categoryTypeKey,categoryType,
				keywordKey,keyword,categoryKey,category,categoryIdKey,categoryIds,
				fromDateKey,fromDate,toDateKey,toDate,highLevelCategoryIdKey ,highLevelCategoryId,
				typeKey,type, accountReconTypeKey,accountReconType, "","",
				"","", accountIdKey,accountIds);

		httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(params);

		Response response = transactionUtils.getAllTransactions(httpParams, configurationObj.getCobrandSessionObj());
		response.then().log().all();
		jsonAssertionUtil.assertJSON(response, fileNamePrefix+UniqueID +".json", filePath);
	}

	@Info(subfeature = {SubFeature.GET_TRANSACTIONS_COUNT}, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 5)
	@Source(TRANSACTION_COUNT_NEW)
	public void getTransactionsCountOtherContainers(String TestCaseId, String containerKey, String container, String baseTypeKey, String baseType, String categoryTypeKey, String categoryType,
			String keywordKey, String keyword, String categoryKey,String category, String categoryIdKey,String categoryIds,
			String fromDateKey, String fromDate,String toDateKey, String toDate, String highLevelCategoryIdKey , String highLevelCategoryId,
			String typeKey, String type, String   accountReconTypeKey,String accountReconType,
			int   status, String filePath, String Enabled, String  DefectID, String accountIdKey,String Features, String UniqueID) {
		
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		fromDate=fromDate.replaceAll("//","-");
		toDate=toDate.replaceAll("//", "-");
		System.out.println("*****************Testing transaction API**************");
		System.out.println("Executing test case : " + TestCaseId);
		if(highLevelCategoryId.equalsIgnoreCase("valid")) {
			highLevelCategoryId=HLC;
		}
		Set<Long> accountIds = new HashSet<>();
		Map<String, Object> mapQueryParam = accountsHelper.createQueryParamsForGetAccounts("", container, String.valueOf(providerAccountId), "", accountReconType, "", "");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(mapQueryParam);
		Response res = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());
		try {
			List<Long> list = res.jsonPath().getList("account.id");
			Set<Long> ids = new HashSet<>(list);
			accountIds.addAll(ids);
		} catch (Exception e) {

		}
		
		LinkedHashMap<String, String> params = transactionsHelper.createMapForTxns(containerKey,container, baseTypeKey,baseType,categoryTypeKey,categoryType,
				keywordKey,keyword,categoryKey,category,categoryIdKey,categoryIds,
				fromDateKey,fromDate,toDateKey,toDate,highLevelCategoryIdKey ,highLevelCategoryId,
				typeKey,type, accountReconTypeKey,accountReconType, "","",
				"","", accountIdKey,accountIds);

		httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(params);
		Response response = transactionUtils.getAllTransactions(httpParams, configurationObj.getCobrandSessionObj());
		response.then().log().all();
		jsonAssertionUtil.assertJSON(response, fileNamePrefix+UniqueID +".json", filePath);
	}
	
	@Info(userStoryId="", subfeature = {SubFeature.GET_TRANSACTIONS})
	@Test(enabled=true, dataProvider = "feeder",priority = 6)
	@Source(TRANSACTION_COUNT_NEW_POS)
	public void getTxnCountPositiveScenarios(String TestCaseId, String containerKey, String container, String baseTypeKey, String baseType, String categoryTypeKey, String categoryType,
			String keywordKey, String keyword, String categoryKey, String TDECategory,String NonTDECategory, String categoryIdKey,String categoryIds,
			String fromDateKey, String fromDate,String toDateKey, String toDate, String highLevelCategoryIdKey , String highLevelCategoryId,
			String typeKey, String TDEType, String NonTDEType, String   accountReconTypeKey,String accountReconType,
			int   status, String filePath, String Enabled, String  DefectID, String accountIdKey,String accountIdValue,
			String accountIdValid,String Features, String UniqueID) {
		
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		fromDate=fromDate.replaceAll("//","-");
		toDate=toDate.replaceAll("//", "-");
		System.out.println("TestCase id is " + TestCaseId );
		if(highLevelCategoryId.equalsIgnoreCase("valid")) {
			highLevelCategoryId=HLC;
		}
		String category=NonTDECategory;
		String type=NonTDEType;
		if(isTDEnable) {
			category=TDECategory;
			type=TDEType;
		}
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Set<Long> accountIds = new HashSet<>();
		if((accountIdKey!=null && accountIdKey!="") && (accountIdValue==null || accountIdValue.equalsIgnoreCase("")) )
		{
			Map<String, Object> mapQueryParam = accountsHelper.createQueryParamsForGetAccounts("ACTIVE", container, String.valueOf(providerAccountId), "", accountReconType, "", "");
			
			httpParams.setQueryParams(mapQueryParam);
			Response res = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());
			try {
				List<Long> list = res.jsonPath().getList("account.id");
				Set<Long> ids = new HashSet<>(list);
				accountIds.addAll(ids);
				for (Long id : accountIds) {
					System.out.println("Id is "+id);
				}
			} catch (Exception e) {
				logger.info("Account id not coming...");
			}

		}
		else if((accountIdKey!=null && accountIdKey!="") && (accountIdValue!=null && !accountIdValue.equalsIgnoreCase("")))
		{
			try {
				accountIds.add(Long.parseLong(accountIdValue));
			} catch (NumberFormatException e) {
				logger.error("Failed to parse AccountId Value : " + e);				
				Assert.assertFalse(false, "Failed to create JSONObject of account response.");
			}
			if(accountIdValid.equalsIgnoreCase("multiple"))
			{
				accountIds.add((long) 989898);
			}
		}

		LinkedHashMap<String, String> params = transactionsHelper.createMapForTxns(containerKey,container, baseTypeKey,baseType,categoryTypeKey,categoryType,
				keywordKey,keyword,categoryKey,category,categoryIdKey,categoryIds,
				fromDateKey,fromDate,toDateKey,toDate,highLevelCategoryIdKey ,highLevelCategoryId,
				typeKey,type, accountReconTypeKey,accountReconType, "","",
				"","", accountIdKey,accountIds);
		System.out.println("HashMap is "+params.toString());
		httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(params);
		Response response = transactionUtils.getAllTransactions(httpParams, configurationObj.getCobrandSessionObj());
		response.then().log().all();
		jsonAssertionUtil.assertJSON(response, fileNamePrefix+UniqueID +".json", filePath);
	}

	@Info(subfeature = {SubFeature.GET_TRANSACTIONS_COUNT}, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 7)
	@Source(TRANSACTION_COUNT_RECON)
	public void getTransactionsCountReconciliation(String TestCaseId, String containerKey, String container, String baseTypeKey, String baseType, String categoryTypeKey, String categoryType,
			String keywordKey, String keyword, String categoryKey, String category, String categoryIdKey,String categoryIds,
			String fromDateKey, String fromDate,String toDateKey, String toDate, String highLevelCategoryIdKey , String highLevelCategoryId,
			String typeKey, String type, String   accountReconTypeKey,String accountReconType,
			int   status, String filePath, String Enabled, String  DefectID, String accountIdKey,String Features, String ReconFileName, String UnReconFileName) {
		
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		fromDate=fromDate.replaceAll("//","-");
		toDate=toDate.replaceAll("//", "-");
		System.out.println("*****************Testing transaction API**************");
		System.out.println("Executing test case : " + TestCaseId);
		if(highLevelCategoryId.equalsIgnoreCase("valid")) {
			highLevelCategoryId=HLC;
		}
		Set<Long> accountIds = new HashSet<>();
		Map<String, Object> mapQueryParam = accountsHelper.createQueryParamsForGetAccounts("", container, String.valueOf(providerAccountId), "", accountReconType, "", "");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(mapQueryParam);
		Response res = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());
		try {
			List<Long> list = res.jsonPath().getList("account.id");
			Set<Long> ids = new HashSet<>(list);
			accountIds.addAll(ids);
		} catch (Exception e) {
			logger.info("Account id not coming...");
		}
		String fileName=UnReconFileName;
		if(isReconcileEnable)
		{
			fileName=ReconFileName;
		}
		if(isMergedCatEnabled) {
			fileName="MC-"+fileName;
		}

		LinkedHashMap<String, String> params = transactionsHelper.createMapForTxns(containerKey,container, baseTypeKey,baseType,categoryTypeKey,categoryType,
				keywordKey,keyword,categoryKey,category,categoryIdKey,categoryIds,
				fromDateKey,fromDate,toDateKey,toDate,highLevelCategoryIdKey ,highLevelCategoryId,
				typeKey,type, accountReconTypeKey,accountReconType, null,null,
				null,null, accountIdKey,accountIds);

		httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(params);
		Response response = transactionUtils.getAllTransactions(httpParams, configurationObj.getCobrandSessionObj());
		response.then().log().all();
		jsonAssertionUtil.assertJSON(response, fileNamePrefix+".json", filePath);
	}

	@Info(subfeature = {SubFeature.GET_TRANSACTIONS_COUNT}, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 8)
	@Source(TRANSACTION_COUNT_NEG_NEW)
	public void getTransactionsCountNegative(String TestCaseId, String containerKey, String container, String baseTypeKey, String baseType, String categoryTypeKey, String categoryType,
			String keywordKey, String keyword, String categoryKey, String category, String categoryIdKey,String categoryIds,
			String fromDateKey, String fromDate,String toDateKey, String toDate, String highLevelCategoryIdKey , String highLevelCategoryId,
			String typeKey, String type, String   accountReconTypeKey,String accountReconType,
			int   status, String filePath, String Enabled, String  DefectID, String accountIdKey,String Features,String fileName) {
		
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		fromDate=fromDate.replaceAll("//","-");
		toDate=toDate.replaceAll("//", "-");
		System.out.println("*****************Testing transaction API**************");
		System.out.println("Executing test case : " + TestCaseId);

		LinkedHashMap<String, String> params = transactionsHelper.createMapForTxns(containerKey,container, baseTypeKey,baseType,categoryTypeKey,categoryType,
				keywordKey,keyword,categoryKey,category,categoryIdKey,categoryIds,
				fromDateKey,fromDate,toDateKey,toDate,highLevelCategoryIdKey ,highLevelCategoryId,
				typeKey,type, accountReconTypeKey,accountReconType,"","",
				"","", "",new HashSet<Long>());

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(params);
		Response response = transactionUtils.getAllTransactions(httpParams, configurationObj.getCobrandSessionObj());
		response.then().log().all();
		jsonAssertionUtil.assertJSON(response, fileName+".json", filePath);
	}

	@Info(userStoryId = "", subfeature = {SubFeature.GET_TRANSACTIONS})
	@Test(enabled = true, dataProvider = "feeder", priority = 9)
	@Source(GT_SESSIONS)
	public void getTxns_Sessions_Test(String testCaseId, String cobSession, String userSession, String fileName, String filePath, int status, 
			String Enabled, String DefectID) throws JSONException, InterruptedException {
		
		commonUtils.isTCEnabled(Enabled, testCaseId);
		String sessionToken = SessionHelper.getSessionTokenUser(cobSession, "cob", userSession);
		EnvSession envSession = EnvSession.builder().cobSession(sessionToken).path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = transactionUtils.getAllTransactions(null, envSession);
		response.then().log().all();
		jsonAssertionUtil.assertJSON(response, fileName+".json", filePath);
	}

	@Info(userStoryId = "", subfeature = {SubFeature.GET_TRANSACTIONS_COUNT})
	@Test(enabled = true, dataProvider = "feeder", priority = 4)
	@Source(GTC_SESSIONS)
	public void getTxnCounts_Sessions_Test(String testCaseId, String cobSession, String userSession, String fileName, String filePath, int status, 
			String Enabled, String DefectID) throws JSONException, InterruptedException {
		 
		commonUtils.isTCEnabled(Enabled, testCaseId);
		String sessionToken = SessionHelper.getSessionTokenUser(cobSession, "cob", userSession);
		EnvSession envSession = EnvSession.builder().cobSession(sessionToken).path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = transactionUtils.getTransactionCount(null, envSession);
		response.then().log().all();
		jsonAssertionUtil.assertJSON(response, fileName+".json", filePath);
	}
}
