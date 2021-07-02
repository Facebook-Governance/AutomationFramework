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
import java.util.List;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.annotations.Test;

import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestTransactionsCategoryOrtho extends Base {

	public static final String CREATE_CATEGORY_NEW ="\\TestData\\CSVFiles\\Transactions\\TxnCategory\\createSubCategories.csv";
	public static final String UPDATE_CATEGORY_NEW="\\TestData\\CSVFiles\\Transactions\\TxnCategory\\updateCategory.csv";
	public static final String DELETE_CATEGORY_NEW="\\TestData\\CSVFiles\\Transactions\\TxnCategory\\deleteCategories.csv";
	public static final String GET_CATEGORY="\\TestData\\CSVFiles\\Transactions\\TxnCategory\\testGetSubCategories.csv";
	public static final String SESSIONS_CTC ="\\TestData\\CSVFiles\\Transactions\\TxnCategory\\CategorySessions.csv";
	public static final String SESSIONS_UTC ="\\TestData\\CSVFiles\\Transactions\\TxnCategory\\Sessions_UTC.csv";
	public static final String SESSIONS_GTC ="\\TestData\\CSVFiles\\Transactions\\TxnCategory\\Sessions_GTC.csv";
	public static final String TRANSACTION_LIST ="\\TestData\\CSVFiles\\Transactions\\TxnCategory\\transactionList.csv";
	
	TransactionUtils transactionUtils = new TransactionUtils();
    Configuration configuration = Configuration.getInstance();
    TransactionsHelper transactionsHelper = new TransactionsHelper();
    JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
    CommonUtils commonUtils = new CommonUtils();
	
	@Info(subfeature={SubFeature.CREATE_SUBCATEGORY}, userStoryId = "")
	@Test(dataProvider = "feeder",priority=1)
	@Source(CREATE_CATEGORY_NEW)
	public void testCreateSubCategories_Ortho(String testCaseId, String TestCase, String categoryNameKey, String categoryNameValue, String parentCategoryIdKey, 
			int parentCategoryIdValue, int status, String Enabled, Long DefectID) throws JSONException, InterruptedException {
		
		commonUtils.isTCEnabled(Enabled, testCaseId);
		System.out.println("*************Testing create category**********");
		System.out.println("Executing test case: " + TestCase);
		if(categoryNameKey.equals("null"))categoryNameKey=null;
		if(categoryNameValue.equals("null"))categoryNameValue=null;
		if(parentCategoryIdKey.equals("null"))parentCategoryIdKey=null;
		String strJSON = transactionsHelper.createCategoryObj(categoryNameKey, categoryNameValue, parentCategoryIdKey, parentCategoryIdValue);
        HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
        if(configuration.isHybrid() == false) {
        	httpParam = HttpMethodParameters.builder().build();
            LinkedHashMap<String, String> mapQuery = new LinkedHashMap<>();
            mapQuery.put("categoryParam", strJSON);
            httpParam.setQueryParams(mapQuery);
        }else {
        	httpParam.setBodyParams(strJSON);
        }
		transactionUtils.createTransactionCategory(httpParam, configuration.getCobrandSessionObj());
	}
	
	@Info(subfeature={SubFeature.UPDATE_SUBCATEGORY}, userStoryId = "")
	@Test(dataProvider = "feeder",priority=3)
	@Source(UPDATE_CATEGORY_NEW)
	public void testUpdateSubCategories_Ortho(String testCaseId, String TestCase, String sourceKeyName, String sourceKeyValue,
			String categoryNameKey, String categoryNameValue, String categoryIdKey,  int iterateCategories, int getStatus,
			int updateStatus, String Enabled, Long DefectID) throws JSONException, InterruptedException {
		
		commonUtils.isTCEnabled(Enabled, testCaseId);
		System.out.println("**************Testing update category API**********");
        System.out.println("Executing test case: " + TestCase);
        
        Response response = transactionUtils.getTxnCategories(configuration.getCobrandSessionObj());
        
        JsonPath json = response.jsonPath();
        List<String> categoryId_list = json.get("transactionCategory.id");
        int ids = categoryId_list.size();
        System.out.println("SIZE of sub-cat : " + ids);
        response.then().log().all();
        Integer categoryIdValue = null;
        System.out.println("response from testUpdateSubCategories" + response.toString());
        if(!(TestCase.contentEquals("Null id"))) {      
        	categoryIdValue = json.getInt("transactionCategory[" + iterateCategories + "].id");
        }
        String categoryName = json.getString("transactionCategory[" + iterateCategories + "].categoryName");
        System.out.println("Category ID : " + categoryIdValue + "   Category Name : " + categoryName);
        HttpMethodParameters httpParam =  HttpMethodParameters.builder().build();
        String strJSON = transactionsHelper.updateCategoryObject(sourceKeyName, sourceKeyValue, categoryNameKey, categoryNameValue, categoryIdKey, categoryIdValue);    
        httpParam.setBodyParams(strJSON);
        response = transactionUtils.updateTransactionCategories(httpParam, configuration.getCobrandSessionObj());
        response.then().log().all();
        System.out.println("response from testCreateSubCategories" + response.toString());
        System.out.println("status code expected : "+updateStatus);
        commonUtils.assertStatusCode(response, updateStatus);
	}

	@Info(userStoryId = "", subfeature = {SubFeature.CREATE_SUBCATEGORY})
	@Test(enabled = true, dataProvider = "feeder", priority = 4)
	@Source(SESSIONS_CTC)
	public void createCategorySession_test(String testCaseId, String cobSession,String userSession,String fileName, String filePath, 
			int status, String Enabled, String DefectID) throws JSONException, InterruptedException {
		
		commonUtils.isTCEnabled(Enabled, testCaseId);
		System.out.println("*************Testing create category API-Positive flow**********");
        System.out.println("Executing test case: " + testCaseId);
        
        String strJSON = transactionsHelper.createCategoryObj("categoryName", "c", "parentCategoryId", 3);
        HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
        httpParam = HttpMethodParameters.builder().build();
        LinkedHashMap<String, String> mapQuery = new LinkedHashMap<>();
        mapQuery.put("categoryParam", strJSON);
        httpParam.setQueryParams(mapQuery);
        
        String sessionToken = SessionHelper.getSessionTokenUser(cobSession, "cob", userSession);
		EnvSession envSession = EnvSession.builder().cobSession(sessionToken).path(configuration.getCobrandSessionObj().getPath()).build();
        Response response = transactionUtils.createTransactionCategory(httpParam, envSession);
        jsonAssertionUtil.assertJSON(response, fileName+".json", filePath);
	}
	
	@Info(userStoryId = "", subfeature = {SubFeature.UPDATE_SUBCATEGORY})
	@Test(enabled = true, dataProvider = "feeder", priority = 4)
	@Source(SESSIONS_GTC)
	public void getCategorySession_test(String testCaseId, String cobSession,String userSession,String fileName, String filePath, 
			int status, String Enabled, String DefectID) throws JSONException, InterruptedException {
		
		commonUtils.isTCEnabled(Enabled, testCaseId);
		System.out.println("*************Testing create category API-Positive flow**********");
        System.out.println("Executing test case: " + testCaseId);
        
        String strJSON = transactionsHelper.createCategoryObj("categoryName", "c", "parentCategoryId", 3);
        HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
        httpParam = HttpMethodParameters.builder().build();
        LinkedHashMap<String, String> mapQuery = new LinkedHashMap<>();
        mapQuery.put("categoryParam", strJSON);
        httpParam.setQueryParams(mapQuery);
        
        String sessionToken = SessionHelper.getSessionToken(cobSession, "cob");
        sessionToken = sessionToken + SessionHelper.getSessionToken(userSession, "user");
		EnvSession envSession = EnvSession.builder().cobSession(sessionToken).path(configuration.getCobrandSessionObj().getPath()).build();
		Response response = transactionUtils.getTxnCategories(envSession);
        jsonAssertionUtil.assertJSON(response, fileName+".json", filePath);
	}
	
	@Info(userStoryId = "", subfeature = {SubFeature.UPDATE_SUBCATEGORY})
	@Test(enabled = true, dataProvider = "feeder", priority = 4)
	@Source(SESSIONS_UTC)	
	public void updateCategorySession_test(String testCaseId, String cobSession,String userSession,String fileName, String filePath, int status, String Enabled, String DefectID) throws JSONException, InterruptedException {
		
		commonUtils.isTCEnabled(Enabled, testCaseId);
		HttpMethodParameters httpParam =  HttpMethodParameters.builder().build();
        String strJSON = transactionsHelper.updateCategoryObject("source", "USER", "categoryName", "c", "parentCategoryId", 3);    
        httpParam.setBodyParams(strJSON);
        String sessionToken = SessionHelper.getSessionTokenUser(cobSession, "cob", userSession);
        //sessionToken = sessionToken + SessionHelper.getSessionToken(userSession, "user");
		EnvSession envSession = EnvSession.builder().cobSession(sessionToken).path(configuration.getCobrandSessionObj().getPath()).build();
        Response response = transactionUtils.updateTransactionCategories(httpParam, envSession);
        response.then().log().all();
		jsonAssertionUtil.assertJSON(response, fileName+".json", filePath);
	}
	
	@Info(subfeature={SubFeature.DELETE_SUBCATEGORY}, userStoryId = "")
	@Test(dataProvider = "feeder",priority=5)
	@Source(DELETE_CATEGORY_NEW)
	public void testDeleteSubCategories(String testCaseId, String TestCase, int iterateCategories, int deleteStatus,String fileName,String filePath, String enabled) throws JSONException, InterruptedException {

		commonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println("*********************Testing delete category API******************");
		System.out.println("Executing test case: " + TestCase);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams = transactionsHelper.createPathParam("categoryId", String.valueOf(iterateCategories));
        Response response = transactionUtils.deleteTxnCategory(configuration.getCobrandSessionObj(), httpParams);
        commonUtils.assertStatusCode(response, deleteStatus);
	}
}
