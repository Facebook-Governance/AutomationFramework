/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/ 
package com.yodlee.yodleeApi.helper;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.util.SystemOutLogger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.constants.Container;
import com.yodlee.yodleeApi.constants.JsonPath;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class RoundOffDecimalsHelper {

	private CommonUtils commonUtils=new CommonUtils();
	private DBHelper dbHelper=new DBHelper(); 
	private SFGUtils sfgUtils = new SFGUtils();
	private AccountUtils accountUtil = new AccountUtils();
	private JsonParser jsonParser = new JsonParser();
	
	 public String validateRecurringEvents(Response response,int httpStatusCode,String[] validateAttributes,int decimalPrecision,String yslApiCall) {
	    	Assert.assertEquals(response.getStatusCode(), httpStatusCode);	 
	    	String recurringEventId = null;
	    	JsonObject responseObject = (JsonObject)jsonParser.parse(response.asString());	 
	    	JsonArray accountArr = responseObject.getAsJsonArray("account");
	        JsonArray recurringSeriesArr = null;
	    	try {
	    		int count=0;
	    		do {
	    			recurringSeriesArr = ((JsonObject)accountArr.get(count)).getAsJsonArray("recurringEvents");
	    			count++;
	    		}while(recurringSeriesArr==null);
    	}catch(Exception e) {
    		System.out.println("JsonElement Not Found - No recurring events - Series not generated");
	    		e.printStackTrace();		
	    	}
	    	if(recurringSeriesArr!=null) {
	    		for (int i = 0; i < recurringSeriesArr.size(); i++) {
	    			JsonObject recurringSeriesObj = (JsonObject) recurringSeriesArr.get(0);
	    			validateAttributes[0] = ((JsonObject) recurringSeriesObj.get("amount")).get("amount").getAsString(); // validate amount attribute
	    			validateAttributes[1] = ((JsonObject) recurringSeriesObj.get("averageAmount")).get("amount").getAsString(); // validate averageAmount attribute
	    			validateAttributes[2] = ((JsonObject) recurringSeriesObj.get("lastTransactionAmount")).get("amount").getAsString(); // validate lastTransactionAmount attribute
	    			validateDecimalsInAmount(validateAttributes, decimalPrecision,yslApiCall);
	    			recurringEventId = recurringSeriesObj.get("id").getAsString();
	    		}	
	    	}
	    	return recurringEventId;
	 }
	 public void validateFinCheckIndicators(Response response,int httpStatusCode,String[] validateAttributes,int decimalPrecision,String yslApiCall) {
	    	Assert.assertEquals(response.getStatusCode(), httpStatusCode);	 
	    	JsonObject summaryObject = (JsonObject)jsonParser.parse(response.asString());	
	    	try {
	    		JsonObject indicatorsObject = (JsonObject)summaryObject.get("indicators");
	    		//spending
	    		JsonObject spendingObject = (JsonObject)indicatorsObject.get("spending");
	    		validateAttributes[0] = ((JsonObject)spendingObject.get("averageExpense")).get("amount").getAsString();
	    		validateAttributes[1] = ((JsonObject)spendingObject.get("averageIncome")).get("amount").getAsString();
	    		//emergencySavings
	    		JsonObject emergencySavingsObject = (JsonObject)indicatorsObject.get("savings");
	    		validateAttributes[2] = ((JsonObject)emergencySavingsObject.get("emergencyBalance")).get("amount").getAsString();
	    		//investments
	    		JsonObject investmentsObject = (JsonObject)indicatorsObject.get("investments");
	    		validateAttributes[3] = ((JsonObject)investmentsObject.get("longTermSavings")).get("amount").getAsString();
	    		//Debts
	    		JsonObject monthlyDebtObject = (JsonObject)indicatorsObject.get("debt");
	    		validateAttributes[4] = ((JsonObject)monthlyDebtObject.get("monthlyDebt")).get("amount").getAsString();	
	    		validateDecimalsInAmount(validateAttributes, decimalPrecision,yslApiCall);	    		
	    	}catch(Exception e) {
	    		System.out.println("JsonElement Not Found in Fincheck Summary API response");
	    		e.printStackTrace();
	    	}		
		
	 }
	 
	 public void validateBudgetTrends(Response response,int httpStatusCode,String[] validateAttributes,int decimalPrecision,String yslApiCall) {
	    	Assert.assertEquals(response.getStatusCode(), httpStatusCode);	 
	    	JsonObject responseObject = (JsonObject)jsonParser.parse(response.asString());	
	    	try {
	    		JsonArray budgetHistoryArr = responseObject.getAsJsonArray("budgetHistory");	    		
	    		JsonObject budgetHistoryObject = (JsonObject)budgetHistoryArr.get(0);
	    		JsonArray categoryTypeArray = budgetHistoryObject.getAsJsonArray("categoryTypeData");
	    		JsonObject categoryTypeDataObject = (JsonObject)categoryTypeArray.get(0);
	    		JsonArray historyArray = categoryTypeDataObject.getAsJsonArray("history");
	    		JsonObject historyObject = (JsonObject)historyArray.get(0);
	    		validateAttributes[0] = ((JsonObject)historyObject.get("budgetAmount")).get("amount").getAsString();
	    		validateAttributes[1] = ((JsonObject)historyObject.get("creditTotal")).get("amount").getAsString();
	    		validateAttributes[2] = ((JsonObject)historyObject.get("debitTotal")).get("amount").getAsString();
	    		validateDecimalsInAmount(validateAttributes, decimalPrecision,yslApiCall);	    		
	    	}catch(Exception e) {
	    		System.out.println("JsonElement Not Found in Get BudgetTrends API response");
	    		e.printStackTrace();
	    	}				
	 }
	 
	 public void validateStmtSummary(Response response,int httpStatusCode,String[] validateAttributes,int decimalPrecision,String yslApiCall) {
	    	Assert.assertEquals(response.getStatusCode(), httpStatusCode);	 
	    	JsonObject responseObject = (JsonObject)jsonParser.parse(response.asString());	
	    	try {	    
	    		JsonArray statementArray = responseObject.getAsJsonArray("statement");
	    		JsonObject statementObj = (JsonObject)statementArray.get(0);
	    		validateAttributes[0] = ((JsonObject)statementObj.get("minimumPayment")).get("amount").getAsString();
	    		validateAttributes[1] = ((JsonObject)statementObj.get("newCharges")).get("amount").getAsString();
	    		validateAttributes[2] = ((JsonObject)statementObj.get("amountDue")).get("amount").getAsString();
	    		validateAttributes[3] = ((JsonObject)statementObj.get("lastPaymentAmount")).get("amount").getAsString();	    		
	    		validateDecimalsInAmount(validateAttributes, decimalPrecision,yslApiCall);	    		
	    	}catch(Exception e) {
	    		System.out.println("JsonElement Not Found in Get Statements Summary API response");
	    		e.printStackTrace();
	    	}				
	 }
	 
	 public void  validateDecimalsInAmount(String[] validateAttributes,int decimalPrecision,String yslApiCall) {		
		 for(int i=0;i<validateAttributes.length;i++) {
			 String amountAttributeValue = validateAttributes[i];
			 int index = amountAttributeValue.lastIndexOf('.');
			 if(index != -1 && amountAttributeValue.substring(index + 1).length() == decimalPrecision) {
				 Assert.assertEquals(amountAttributeValue.substring(index + 1).length() , decimalPrecision);
			     System.out.println("Test PASSED - The number " + amountAttributeValue + " has " +amountAttributeValue.substring(index + 1).length() +" digits after dot");
			 }else if(index == -1 ){
				 Assert.assertEquals(index , decimalPrecision-1);
			 }
			 else {
				 System.out.println("API FAILED - "+yslApiCall +"Attrubute Validation Failed --->"+amountAttributeValue);
				 System.out.println("Test FAILED - The number " + amountAttributeValue + " has " +amountAttributeValue.substring(index + 1).length() +" digits after dot");
				 Assert.assertEquals(amountAttributeValue.substring(index + 1).length() , decimalPrecision);			     
			 }
		 }
	 }	 
	 public String sysDateToString() {		 
		 Date date = Calendar.getInstance().getTime();
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		 String dateString = dateFormat.format(date);
		 return dateString;
	 }
	 
	 public char getCobrandDecimalPrecesion() throws SQLException{
		 String precision =null;
		 Connection conn = dbHelper.getDBConnection();		 
		 try {
			 String precisionQuery = "select * from cobrand_acl_value  where cobrand_id = 10000004 and param_acl_id= 7508";
				Statement statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(precisionQuery);
				while (rs.next()) {
					precision = rs.getString("ACL_VALUE");
				}
		 }catch(Exception e) {
			 System.out.println("SQL Exception Found ");
			 e.printStackTrace();
		 }finally {
			 conn.close();
		 }
		 return precision.charAt(precision.length() - 1);
	 }
	 
	 public static  ArrayList<Long> getItemAccountsOfContainers(Session sessionObj) {
			AccountUtils accountUtils = new AccountUtils();
			Response getAccountsResponse = null;
			HashMap<String,ArrayList<Long>> containerAccountsMap = new HashMap<String,ArrayList<Long>>();			
			ArrayList<Long> itemAccountsList = new ArrayList<Long>();
			JSONObject containersObj=null,bankObject=null,creditCardObject= null,investmentObject =null;
			JSONArray containersObjArray = null;
			String[] containersArray = {"Bank","Card","Stocks"};
			getAccountsResponse = accountUtils.getAllAccounts(sessionObj);		
			getAccountsResponse.then().log().all();		
			Container containers = new Container();
			try {
				containersObj = new JSONObject(getAccountsResponse.asString());
				System.out.println("Bank Object : " + containersObj.toString());
				containersObjArray = containersObj.getJSONArray(containers.JSON_PATH_ACCOUNT);				
					for (int count = 0; count < containersObjArray.length(); count++) {
						String container = containersObjArray.getJSONObject(count).getString("CONTAINER");
						Long accountId = containersObjArray.getJSONObject(count).getLong(containers.ID);												
						if(container.equalsIgnoreCase("creditCard"))
							itemAccountsList.add(accountId);								
				 }					

			 } catch (JSONException e) {
				Assert.fail("Failed to create JSONObject of account response.");
				e.printStackTrace();
			}		
			return itemAccountsList;
		  }
	 
}
