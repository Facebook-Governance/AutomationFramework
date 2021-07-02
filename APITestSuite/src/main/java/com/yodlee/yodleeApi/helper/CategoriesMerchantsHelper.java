/*******************************************************************************
 *
 * * Copyright (c) 201 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.yodleeApi.helper;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.yodleeApi.constants.HttpStatus;

import io.restassured.response.Response;

public class CategoriesMerchantsHelper {
	
	
	JsonParser jsonParser = new JsonParser();
	
	public boolean validateAPIResponseForUtilizedCategories(Response utilizedCategoriesResponse,String categoryType,
			int expectedUtilizedCategoiresCnt,String expectedUtilizedCatDetails,int statusCode,String negTestScenario,HashMap<String,ArrayList<String>> negativeExpectedValuesMap) {
		boolean testExecutionStatus=true;
		if(statusCode == HttpStatus.OK) {
			JsonObject utilizedCategoriesObject = (JsonObject) jsonParser.parse(utilizedCategoriesResponse.asString());
			JsonArray categoriesList = utilizedCategoriesObject.getAsJsonArray("category");
			String[] expectedCategoryDetails = expectedUtilizedCatDetails.split("#");
			if(categoriesList!=null && categoriesList.size() > 0) {		
				for(int i=0;i<categoriesList.size();i++) {
					JsonObject categoryObject = categoriesList.get(i).getAsJsonObject();
					String[] categoryDetail = expectedCategoryDetails[i].split(",");
					if(!categoryObject.get("name").getAsString().equals(categoryDetail[0].toString())) {
						System.out.println("Category Name Doesn't Match");
						testExecutionStatus=false;
					}
					if(!(categoryObject.get("id").getAsInt() == Integer.parseInt(categoryDetail[1]))) {
						System.out.println("Category ID Doesn't Match");
						testExecutionStatus=false;
					}
					if(!(categoryObject.get("transactionCount").getAsInt() == Integer.parseInt(categoryDetail[2]))) {
						System.out.println("Category's Transaction Count Doesn't Match");
						testExecutionStatus=false;
					}
				}
			}	
			
		}
		//Negative Tests Validation
		else {
			JsonObject expNegResponseObj = (JsonObject) jsonParser.parse(utilizedCategoriesResponse.asString());
			ArrayList negTestErrorDetails = negativeExpectedValuesMap.get(negTestScenario);
			if(!negTestErrorDetails.get(1).equals(expNegResponseObj.get("errorCode").getAsString())) {
				System.out.println("Error Code Doesn't Match Expected = "+negTestErrorDetails.get(1) +"Actual = "+expNegResponseObj.get("errorCode").getAsString());
				testExecutionStatus=false;
			}
			if(!negTestErrorDetails.get(2).toString().equals(expNegResponseObj.get("errorMessage").getAsString())) {
				System.out.println("Error Message Doesn't Match Expected = "+negTestErrorDetails.get(2) +"Actual = "+expNegResponseObj.get("errorMessage").getAsString());
				testExecutionStatus=false;
			}
		}
		
		return testExecutionStatus;
	}
	
	
	public boolean validateAPIResponseForUtilizedMerchants(Response utilizedMerchantResponse,String categoryType,
			int expectedUtilizedCategoiresCnt,String expectedUtilizedCatDetails,int statusCode,String negTestScenario,HashMap<String,ArrayList<String>> negativeExpectedValuesMap) {
		boolean testExecutionStatus=true;
		if(statusCode == HttpStatus.OK) {
			JsonObject utilizedCategoriesObject = (JsonObject) jsonParser.parse(utilizedMerchantResponse.asString());
			JsonArray merchantsList = utilizedCategoriesObject.getAsJsonArray("merchant");
			String[] expectedCategoryDetails = expectedUtilizedCatDetails.split("#");
			if(merchantsList!=null && merchantsList.size() > 0) {		
				for(int i=0;i<merchantsList.size();i++) {
					JsonObject categoryObject = merchantsList.get(i).getAsJsonObject();
					String[] categoryDetail = expectedCategoryDetails[i].split(",");
					if(!categoryObject.get("name").getAsString().equals(categoryDetail[0].toString())) {
						System.out.println("Merchant Name Doesn't Match");
						testExecutionStatus=false;
					}
					if(!(categoryObject.get("id").getAsInt() == Integer.parseInt(categoryDetail[1]))) {
						System.out.println("Merchant ID Doesn't Match");
						testExecutionStatus=false;
					}
					if(!(categoryObject.get("transactionCount").getAsInt() == Integer.parseInt(categoryDetail[2]))) {
						System.out.println("Merchants's Transaction Count Doesn't Match");
						testExecutionStatus=false;
					}
				}
			}	
			
		}
		//Negative Tests Validation
		else {
			JsonObject expNegResponseObj = (JsonObject) jsonParser.parse(utilizedMerchantResponse.asString());
			ArrayList negTestErrorDetails = negativeExpectedValuesMap.get(negTestScenario);
			if(!negTestErrorDetails.get(1).equals(expNegResponseObj.get("errorCode").getAsString())) {
				System.out.println("Error Code Doesn't Match Expected = "+negTestErrorDetails.get(1) +"Actual = "+expNegResponseObj.get("errorCode").getAsString());
				testExecutionStatus=false;
			}
			if(!negTestErrorDetails.get(2).toString().equals(expNegResponseObj.get("errorMessage").getAsString())) {
				System.out.println("Error Message Doesn't Match Expected = "+negTestErrorDetails.get(2) +"Actual = "+expNegResponseObj.get("errorMessage").getAsString());
				testExecutionStatus=false;
			}
		}
		
		return testExecutionStatus;
	}
	
	public String formatFromAndToDate(int inputDate) {		
		LocalDate date = LocalDate.now();
		LocalDate newDate = LocalDate.now();
		newDate = date.plusDays(inputDate);
	    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String processedDate = newDate.format(formatters);
	    LocalDate parsedDate = LocalDate.parse(processedDate, formatters);
	    System.out.println("date: " + newDate);
	    System.out.println("Text format " + processedDate);
	    System.out.println("parsedDate: " + parsedDate.format(formatters));		
		return processedDate;
	}
	

	public HashMap<String, ArrayList<String>> readNegativeExpectedValues() {
		HashMap<String, ArrayList<String>> negativeTestValuesMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> negativeTestList;
		try {

			JsonArray negativeTestsArr = (JsonArray) jsonParser.parse(new FileReader(System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\CSVFiles\\TransactionCategoriesMerchants\\UtilizedCategoriesMerchantsNegativeTests.json"));
			for (Object negativeTest : negativeTestsArr) {
				negativeTestList = new ArrayList<>();
				JsonObject negativeExpense = (JsonObject) negativeTest;
				String negativeTestScenario = negativeExpense.get("negativeTest").getAsString();
				String statusCode = negativeExpense.get("httpStatuCode").getAsString();
				String yslErrorCode = negativeExpense.get("yslErrorCode").getAsString();
				String errorMessage = negativeExpense.get("errorMessage").getAsString();				
				negativeTestList.add(statusCode);
				negativeTestList.add(yslErrorCode);
				negativeTestList.add(errorMessage);
				//negativeTestValuesMap.put(testCaseId, negativeTestList);
				negativeTestValuesMap.put(negativeTestScenario, negativeTestList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return negativeTestValuesMap;
	}
	
}
