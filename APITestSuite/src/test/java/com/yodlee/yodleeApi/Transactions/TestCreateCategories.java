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

import java.util.HashMap;
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
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * @author partha sham
 *
 */
public class TestCreateCategories extends Base{

	public static final String CREATE_CATEGORY_POSITIVE = "\\TestData\\CSVFiles\\Transactions\\testCreateSubCategoriesPositive.csv";
    public static final String CREATE_CATEGORY_NEGATIVE = "\\TestData\\CSVFiles\\Transactions\\testCreateSubCategories.csv";
    public static final String UPDATE_CATEGORY = "\\TestData\\CSVFiles\\Transactions\\testUpdateSubCategory.csv";
    public static final String DELETE_CATEGORY = "\\TestData\\CSVFiles\\Transactions\\testdeleteSubCategories.csv";
    public static final String GET_CATEGORY = "\\TestData\\CSVFiles\\Transactions\\testGetSubCategories.csv";
    public static final String CATEGORY_FLOW = "\\TestData\\CSVFiles\\Transactions\\subCategories.csv";

    TransactionUtils transactionUtils = new TransactionUtils();
    Configuration configuration = Configuration.getInstance();
    TransactionsHelper transactionsHelper = new TransactionsHelper();
    JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
    CommonUtils commonUtils = new CommonUtils();
    
    @Test(dataProvider = "feeder", priority = 1)
    @Source(CREATE_CATEGORY_POSITIVE)
    @Info(subfeature = {SubFeature.CREATE_SUBCATEGORY}, userStoryId = "")
    public void testCreateSubCategoriesPositiveFlow(String testCaseId, String TestCase, String categoryNameKey, String categoryNameValue, String parentCategoryIdKey, 
    		int parentCategoryIdValue, int status, String enabled) throws JSONException, InterruptedException {
        
    	System.out.println("*************Testing create category API-Positive flow**********");
        System.out.println("Executing test case: " + TestCase);
        
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

    @Test(dataProvider = "feeder", priority = 2)
    @Source(CREATE_CATEGORY_NEGATIVE)
    @Info(subfeature = {SubFeature.CREATE_SUBCATEGORY}, userStoryId = "")
    public void testCreateSubCategories(String testCaseId, String TestCase, String categoryNameKey, String categoryNameValue, String parentCategoryIdKey, int parentCategoryIdValue, 
    		String resFile, String filePath, int status, String enabled) throws JSONException, InterruptedException {
        
    	System.out.println("**************Testing create category API-negative flow************");
        System.out.println("Executing test case: " + TestCase);
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
        Response response = transactionUtils.createTransactionCategory(httpParam, configuration.getCobrandSessionObj());
        response.then().log().all();
        System.out.println("response from testCreateSubCategories" + response.toString());
        System.out.println("status code expected : "+status);
        jsonAssertionUtil.assertJSON(response, status, resFile, filePath);
    }

    @Test(dataProvider = "feeder", priority = 3)
    @Source(UPDATE_CATEGORY)
    @Info(subfeature = {SubFeature.UPDATE_SUBCATEGORY}, userStoryId = "")
    public void testUpdateSubCategories(String testCaseId, String TestCase, String sourceKeyName, String sourceKeyValue, String categoryNameKey, 
    		String categoryNameValue, String categoryIdKey, int iterateCategories, int getStatus, int updateStatus, String enabled) throws JSONException, InterruptedException {
       
    	CommonUtils commonUtils = new CommonUtils();
    	commonUtils.isTCEnabled(enabled, testCaseId);
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

    @Test(dataProvider = "feeder", priority = 4)
    @Source(DELETE_CATEGORY)
    @Info(subfeature = {SubFeature.DELETE_SUBCATEGORY}, userStoryId = "")
    public void testDeleteSubCategories(String testCaseId, String TestCase, int iterateCategories, int getStatus, int deleteStatus, String enabled) throws JSONException, InterruptedException {
        
    	System.out.println("*********************Testing delete category API******************");
        System.out.println("Executing test case: " + TestCase);            
               
        String strJSON = transactionsHelper.createCategoryObj("categoryName", "Sub1 - Automotive Expenses", "parentCategoryId", 3);
        HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
        if(configuration.isHybrid() == false) {
        	httpParam = HttpMethodParameters.builder().build();
            LinkedHashMap<String, String> mapQuery = new LinkedHashMap<>();
            mapQuery.put("categoryParam", strJSON);
            httpParam.setQueryParams(mapQuery);
        }else {
        	httpParam.setBodyParams(strJSON);
        }
        Response response = transactionUtils.createTransactionCategory(httpParam, configuration.getCobrandSessionObj());
        response.then().log().all();
        response = transactionUtils.getTxnCategories(configuration.getCobrandSessionObj());
        JsonPath json = response.jsonPath();
        
        List<String> categoryId_list = json.get("transactionCategory.id");
        int ids = categoryId_list.size();
        System.out.println("SIZE of sub-cat : " + ids);
        HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
        int subCategoryId = json.getInt("transactionCategory[" + iterateCategories + "].id");
        String categoryName = json.getString("transactionCategory[" + iterateCategories + "].categoryName");
        System.out.println("Category ID : " + subCategoryId + "   Category Name : " + categoryName);
        httpParams = transactionsHelper.createPathParam("categoryId", String.valueOf(subCategoryId));
        response = transactionUtils.deleteTxnCategory(configuration.getCobrandSessionObj(), httpParams);
        commonUtils.assertStatusCode(response, deleteStatus);
    }

    @Test(dataProvider = "feeder", priority = 5)
    @Source(GET_CATEGORY)
    @Info(subfeature = {SubFeature.GET_SUBCATEGORY}, userStoryId = "")
    public void testGetSubCategories(String testCaseId, String TestCase, int getStatus, String enabled, String ResFile, String FilePath)
    		throws JSONException, InterruptedException {
        
    	System.out.println("******************get category API*************");
        System.out.println("Executing testcase :" + TestCase);
        Response response = transactionUtils.getTxnCategories(configuration.getCobrandSessionObj());
        response.jsonPath();
        System.out.println("actual response ::"+response.statusCode());
        jsonAssertionUtil.assertJSON(response, getStatus, ResFile, FilePath);
    }

    @Test(dataProvider = "feeder", priority = 6)
    @Source(CATEGORY_FLOW)
    @Info(subfeature = {SubFeature.SUBCATEGORY_FLOW}, userStoryId = "")
    public void testSubCategories(String TCId, String TCTitle, int status1, int status2, int status3, int status4)
    		throws JSONException, InterruptedException {
        
    	System.out.println("****************************Category flow -happy path testing*************************");
        System.out.println("Executing testcase :" + TCTitle);
        String subCategoryName = "New SubCategory " + System.currentTimeMillis();        
        String strJSON = transactionsHelper.createCategoryObj("categoryName", subCategoryName, "parentCategoryId", status1);
        
        HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
        if(configuration.isHybrid() == false) {
        	httpParam = HttpMethodParameters.builder().build();
            LinkedHashMap<String, String> mapQuery = new LinkedHashMap<>();
            mapQuery.put("categoryParam", strJSON);
            httpParam.setQueryParams(mapQuery);
        }else {
        	httpParam.setBodyParams(strJSON);
        }
        Response response = transactionUtils.createTransactionCategory(httpParam, configuration.getCobrandSessionObj());
        response.then().log().all();
        Response getTxnCateRes = transactionUtils.getTxnCategories(configuration.getCobrandSessionObj());
        int subCategoryId = transactionsHelper.getUserCategoryID(getTxnCateRes);        
        String updatedSubCategoryName = "Updated SubCategory " + System.currentTimeMillis();
        strJSON = transactionsHelper.createCategoryObj("categoryName", updatedSubCategoryName, "parentCategoryId", subCategoryId);
        if(configuration.isHybrid() == false) {
        	httpParam = HttpMethodParameters.builder().build();
            LinkedHashMap<String, String> mapQuery = new LinkedHashMap<>();
            mapQuery.put("categoryParam", strJSON);
            httpParam.setQueryParams(mapQuery);
        }else {
        	httpParam.setBodyParams(strJSON);
        }
        
        transactionUtils.updateTransactionCategories(httpParam, configuration.getCobrandSessionObj());
        response = transactionUtils.getTxnCategories(configuration.getCobrandSessionObj());
        HashMap<String, Object> mapPath = new HashMap<>();
        mapPath.put("categoryId", subCategoryId);
        HttpMethodParameters httpPath = HttpMethodParameters.builder().build();
        httpPath.setPathParams(mapPath);
        transactionUtils.deleteTxnCategory(configuration.getCobrandSessionObj(), httpPath);
        response = transactionUtils.getTxnCategories(configuration.getCobrandSessionObj());
    }
}
