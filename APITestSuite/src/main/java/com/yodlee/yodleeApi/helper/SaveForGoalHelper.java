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

import org.json.JSONArray;
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

public class SaveForGoalHelper {

	CommonUtils commonUtils=new CommonUtils();
	DBHelper dbHelper=new DBHelper(); 
	SFGUtils sfgUtils = new SFGUtils();
	AccountUtils accountUtil = new AccountUtils();
	JsonParser jsonParser = new JsonParser();
	
	String[][] goalStatus = {{"NOT_STARTED","1"},{"IN_PROGRESS","2"},{"COMPLETED","3"},{"REALIZED","4"}};

	public String sfgDateFormate(String date) throws ParseException
	{
		if(!date.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date fmtdDate = sdf.parse(commonUtils.getOffsetDate(date)+" "+"00:00:00");
			String formatedDate=sdf.format(fmtdDate);
			return formatedDate;	
		}
		return null;
	}
	public String sfgInvaildDateFormate(String date,String dategormate) throws ParseException
	{

		String dataValue ;
		SimpleDateFormat sdf = new SimpleDateFormat(dategormate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String dateAddition[] = date.split(",");
		cal.add(Calendar.YEAR, Integer.parseInt(dateAddition[0]));
		cal.add(Calendar.MONTH, Integer.parseInt(dateAddition[1]));
		cal.add(Calendar.DATE, Integer.parseInt(dateAddition[2]));
		dataValue = sdf.format(cal.getTime())+" "+"00:00:00";
		return dataValue;
	}
	
		public String createGoalJson(String name, String categoryId, String startDate, String targetDate,
			String recurringAmount, String recurringAmountCurency, String targetAmount, String targetAmountCurency,
			String frequency, String status, String fundSetupStatus, String isFTSelected,String methodName) {
		JSONObject goalObj = new JSONObject();
		JSONObject recAmtObj = new JSONObject();
		JSONObject trgAmtObj = new JSONObject();
		JSONObject goalObj1 = new JSONObject();
		recAmtObj.put(Container.AMOUNT, recurringAmount);
		recAmtObj.put(Container.CURRENCY, recurringAmountCurency);
		trgAmtObj.put(Container.AMOUNT, targetAmount);
		trgAmtObj.put(Container.CURRENCY, targetAmountCurency);

		goalObj.put(Container.NAME, name);
		goalObj.put(Container.CATEGORYID, categoryId);
		goalObj.put(Container.STARTDATE, startDate);
		if(methodName.equals("createGoalInvalidTargateDate")) {
		 goalObj.put(Container.TARGATEDATE, targetDate);
		}else {
		  goalObj.put(Container.RECURRINGAMOUNT, recAmtObj);		 
		  goalObj.put(Container.FREQUENCY, frequency);
		}
		goalObj.put(Container.STATUS, status);
		 goalObj.put(Container.TARGATEAMOUNT, trgAmtObj);
		goalObj.put(Container.FUNDINGSETUPSTATUS, fundSetupStatus);
		goalObj.put(Container.ISFTSELECTED, isFTSelected);

		goalObj1.put(Container.GOAL, goalObj);
		String userRegisterObjJSON = goalObj1.toString();
		return userRegisterObjJSON;

	}
		
		public String createGoalJsonForRecurringAmountAndTargetDate(String name, String categoryId, String startDate, String targetDate,
				String recurringAmount, String recurringAmountCurency, String targetAmount, String targetAmountCurency,
				String frequency, String status, String fundSetupStatus, String isFTSelected) {
			JSONObject goalObj = new JSONObject();
			JSONObject recAmtObj = new JSONObject();
			JSONObject trgAmtObj = new JSONObject();
			JSONObject goalObj1 = new JSONObject();
			recAmtObj.put(Container.AMOUNT, recurringAmount);
			recAmtObj.put(Container.CURRENCY, recurringAmountCurency);
			trgAmtObj.put(Container.AMOUNT, targetAmount);
			trgAmtObj.put(Container.CURRENCY, targetAmountCurency);
			goalObj.put(Container.NAME, name);
			goalObj.put(Container.CATEGORYID, categoryId);
			goalObj.put(Container.STARTDATE, startDate);
			if(targetDate == null)
				targetDate="";
			if(!targetDate.isEmpty()) {
			 goalObj.put(Container.TARGATEDATE, targetDate);
			}
			if(!recurringAmount.isEmpty()) {
			  goalObj.put(Container.RECURRINGAMOUNT, recAmtObj);					  
			}
			if(!frequency.isEmpty()) {
				goalObj.put(Container.FREQUENCY, frequency);
			}
			goalObj.put(Container.STATUS, status);
			 goalObj.put(Container.TARGATEAMOUNT, trgAmtObj);
			goalObj.put(Container.FUNDINGSETUPSTATUS, fundSetupStatus);
			goalObj.put(Container.ISFTSELECTED, isFTSelected);

			goalObj1.put(Container.GOAL, goalObj);
			String userRegisterObjJSON = goalObj1.toString();
			return userRegisterObjJSON;

		}


	public String updateGoalBodyParam(String GoalName,String CategoryID,String TargetAmount,String TargetAmountCurency,String RecurringAmount,
			String RecurringAmountCurrency,String Frequency,String StartDate,String TargetDate,String Status,
			String FundSetupStatus,String isFTSelected){

		JSONObject goalObj = new JSONObject();
		JSONObject recAmtObj = new JSONObject();
		JSONObject trgAmtObj = new JSONObject();
		JSONObject goalObj1 = new JSONObject();

		recAmtObj.put("amount", RecurringAmount);
		recAmtObj.put("currency", RecurringAmountCurrency);
		trgAmtObj.put("amount", TargetAmount);
		trgAmtObj.put("currency", TargetAmountCurency);

		goalObj.put("name", GoalName);
		goalObj.put("categoryId", CategoryID);
		goalObj.put("startDate", StartDate);
		goalObj.put("targetDate",TargetDate);
		goalObj.put("recurringAmount",recAmtObj);
		goalObj.put("targetAmount", trgAmtObj);
		goalObj.put("frequency", Frequency);
		goalObj.put("status", Status);
		goalObj.put("fundSetupStatus", FundSetupStatus);
		goalObj.put("isFTSelected", isFTSelected);

		goalObj1.put("goal", goalObj);
		String updateGoalBodyParam = goalObj1.toString();
		return updateGoalBodyParam;	
	}


	public String createDestinationAccount(int itemAccountId,int amount,String currency) {

		JSONObject parentObj=new JSONObject();
		JSONObject goalaccountObj = new JSONObject();
		JSONArray goalaccountArrObj = new JSONArray();

		JSONObject initialAllocation = new JSONObject();

		initialAllocation.put("amount",amount);
		initialAllocation.put("currency",currency);

		goalaccountObj.put("id",itemAccountId);
		goalaccountObj.put("initialAllocation",initialAllocation);
		goalaccountArrObj.put(goalaccountObj);
		parentObj.put("goalAccount",goalaccountArrObj);
		String createDestAccountBodyParam=parentObj.toString();

		return createDestAccountBodyParam;
	}


	public String createFundingRule(int goalAccountId,String RecurringAmount,String RecurringAmountCurrency,String frequency,String isAutoTransferRule,String startDate,String endDate){

		JSONObject parentObj=new JSONObject();
		JSONObject fundingRuleObj = new JSONObject();
		JSONObject recurringAmountObj = new JSONObject();
		JSONArray fundingRuleArrObj = new JSONArray();

		recurringAmountObj.put("amount", RecurringAmount);
		recurringAmountObj.put("currency", RecurringAmountCurrency);
		fundingRuleObj.put("recurringAmount", recurringAmountObj);

		fundingRuleObj.put("goalAccountId",goalAccountId);
		fundingRuleObj.put("recurringAmount", recurringAmountObj);
		fundingRuleObj.put("frequency",frequency);
		fundingRuleObj.put("isAutoTransferRule",isAutoTransferRule);
		fundingRuleObj.put("startDate",startDate);
		fundingRuleObj.put("endDate",endDate);

		fundingRuleArrObj.put(fundingRuleObj);

		parentObj.put("fundingRule",fundingRuleArrObj);
		String createFundingRuleBodyParam=parentObj.toString();
		return createFundingRuleBodyParam;
	}
	
	public String createGoal(String bodyParam,Session sessionObj) throws ParseException {

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		
		httpMethodParameters.setBodyParams(bodyParam);
		Response response = sfgUtils.createGoal(httpMethodParameters, sessionObj);

		String getGoalId = response.jsonPath().getString(JsonPath.getGoalId);
		System.out.println(getGoalId);
		return getGoalId;
	}
	
	
	public String createGoalWithGoalName(String goalName,String bodyParam,Session sessionObj) throws ParseException {

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		
		httpMethodParameters.setBodyParams(bodyParam);
		Response response = sfgUtils.createGoal(httpMethodParameters, sessionObj);

		String getGoalId = response.jsonPath().getString(JsonPath.getGoalId);
		System.out.println(getGoalId);
		return getGoalId;
	}
	
	
	public Response deleteFundingRule(Session sessionObj,String goalId,String FundingRuleId) {
		HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();	

		HashMap<String, Object> getGoalQueryParam =new HashMap<>();
		getGoalQueryParam.put("id", FundingRuleId);
		
		HashMap<String, Object> getGoalPathParam =new HashMap<>();
		getGoalPathParam.put("goalId", goalId);
		
		httpmethodParam.setPathParams(getGoalPathParam);
		httpmethodParam.setQueryParams(getGoalQueryParam);
		
		Response deleteFundingRuleResp=sfgUtils.deleteFundingRule(httpmethodParam, sessionObj);
		return deleteFundingRuleResp;	
	}
	
	
	public Response createDestAccount(String goalId,String createDestAccountBodyParam, Session sessionObj) throws SQLException{
		HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();
		
		HashMap<String, Object> createDestAccountPathParam =new HashMap<>(); 
		createDestAccountPathParam.put("goalId", goalId);

		httpmethodParam.setPathParams(createDestAccountPathParam);
		httpmethodParam.setBodyParams(createDestAccountBodyParam);

		Response createDestAccountResp=sfgUtils.createDestinationAccount(httpmethodParam, sessionObj);
		return createDestAccountResp;
	}	
	
	public String createFundingRule(String goalId,String createFundingRuleBodyParam,Session sessionObj) throws SQLException, ParseException {
		HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();

		HashMap<String, Object> createFundingRulePathParam =new HashMap<>();
		createFundingRulePathParam.put("goalId", goalId);

		httpmethodParam.setPathParams(createFundingRulePathParam);
		httpmethodParam.setBodyParams(createFundingRuleBodyParam);

		Response createFundingRuleResp=sfgUtils.createFundingRule(httpmethodParam, sessionObj);	
		String getFundingRuleId = createFundingRuleResp.jsonPath().getString(JsonPath.getFundingRuleId);
		return getFundingRuleId;
	}
	
	public void updateGoal(String goalId,String updateBodyParams,Session sessionObj) {
		HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();

		HashMap<String, Object> pathParam = new HashMap<>();
		pathParam.put("goalIdUpdate", goalId);
		httpmethodParam.setPathParams(pathParam);

		httpmethodParam.setBodyParams(updateBodyParams);
		sfgUtils.updateGoal(httpmethodParam, sessionObj);
	}
	
	public void updateGoal(String goalId,Session sessionObj,String goalStatus) {
		HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();

		JSONObject updateGoalObj = new JSONObject();
		JSONObject goalObj = new JSONObject();		
		goalObj.put("status", goalStatus);
		updateGoalObj.put("goal", goalObj);
		HashMap<String, Object> pathParam = new HashMap<>();
		pathParam.put("goalIdUpdate", goalId);
		httpmethodParam.setPathParams(pathParam);
		httpmethodParam.setBodyParams(updateGoalObj.toString());
		Response response = sfgUtils.updateGoal(httpmethodParam, sessionObj);
		if(response.getStatusCode()==204) {
			System.out.println("Goal updated successfully");
		}else {
			System.err.print("Goal Updation FAILED");
			Assert.fail();
		}
	}
	
	public Response getGoalDetails(String goalId,Session sessionObj) {
		HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();
		
		LinkedHashMap<String, Object> getGoalDetailsPathParam =new LinkedHashMap<>();
		getGoalDetailsPathParam.put("goalId", goalId);
		
		LinkedHashMap<String, Object> getGoalQueryParam =new LinkedHashMap<>();
		getGoalQueryParam.put("include", "accounts,fundingRules");

		httpmethodParam.setQueryParams(getGoalQueryParam);
		httpmethodParam.setPathParams(getGoalDetailsPathParam);

		Response getAllGoalDetailsResp=sfgUtils.getGoalWithFundingRules(httpmethodParam, sessionObj);
		return getAllGoalDetailsResp;
	}
	
	public Response deleteGoalAccounts(String goalId,int accountId,Session sessionObj) {

		LinkedHashMap<String, Object> deleteGoalAccountPathParam =new LinkedHashMap<>();
		deleteGoalAccountPathParam.put("goalId", goalId);

		HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();

		LinkedHashMap<String, Object> deleteGoalAccountQueryParam =new LinkedHashMap<>();

		deleteGoalAccountQueryParam.put("id", accountId);
		httpmethodParam.setQueryParams(deleteGoalAccountQueryParam);
		httpmethodParam.setPathParams(deleteGoalAccountPathParam);

		Response deleteGoalAccountResp=sfgUtils.deleteGoalAccounts(httpmethodParam, sessionObj);
		return deleteGoalAccountResp;
	}
	
	
	public Response getAllGoalsRespectiveToAcnt(String itemAccountIds,Session sessionObj) {

		HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();

		LinkedHashMap<String, Object> getGoalQueryParam = new LinkedHashMap<>();

		getGoalQueryParam.put("include", "freeAvailableBalance,goals");
		getGoalQueryParam.put("accountId", itemAccountIds);

		httpmethodParam.setQueryParams(getGoalQueryParam);

		Response getAllGoalsResponse = accountUtil.getAccounts(httpmethodParam, sessionObj);
		return getAllGoalsResponse;
	}
	
	
	public Response createDestAccount(int accountId, String goalId,	Session sessionObj) throws SQLException {

		LinkedHashMap<String, Object> createDestAccountPathParam = new LinkedHashMap<>();
		HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();

		createDestAccountPathParam.put("goalId", goalId);
		String createDestAccountBodyParam =createDestinationAccount(accountId, 1000, "USD");

		httpmethodParam.setPathParams(createDestAccountPathParam);
		httpmethodParam.setBodyParams(createDestAccountBodyParam);

		Response createDestAccountResp = sfgUtils.createDestinationAccount(httpmethodParam, sessionObj);
		return createDestAccountResp;
	}

	
	public List<Integer> getItemAccountIds(String UserName) {
		List<Integer> itemAccountIdList=new ArrayList<>();

		DBHelper dbHelper=new DBHelper(); 
		Connection conn=dbHelper.getDBConnection();
		Statement st = null;
		ResultSet rs = null;

		String getItemAccountIdQuery="select item_account_id from item_account where cache_item_id in (select cache_item_id from mem_item where mem_id in (select mem_id from mem where login_name='"+UserName+"') and sum_info_id in (20549,\r\n" + 
				"20559)) order by item_account_id asc";
		try {
			st = conn.createStatement();
			rs =st.executeQuery(getItemAccountIdQuery);
			while (rs.next()) {
				itemAccountIdList.add(rs.getInt("item_account_id"));
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbHelper.closeConnectionStatementResultSet(conn, st, rs);
		}
		return itemAccountIdList;
	}
	public TreeMap<Long,Long> getAvailableBalances(Response accountsResponse) {
		JSONObject account = new JSONObject(accountsResponse.asString());
		TreeMap<Long, Long> accountsMap = new TreeMap<Long, Long>();
		JSONArray accountsArray = account.getJSONArray("account");
		for (int i = 0; i < accountsArray.length(); i++) {
			if(accountsArray.getJSONObject(i).getString("CONTAINER").equalsIgnoreCase("bank"))
			accountsMap.put(accountsArray.getJSONObject(i).getLong("id"),accountsArray.getJSONObject(i).getJSONObject("availableBalance").getLong("amount"));
			if(accountsArray.getJSONObject(i).getString("CONTAINER").equalsIgnoreCase("investment"))
				accountsMap.put(accountsArray.getJSONObject(i).getLong("id"),accountsArray.getJSONObject(i).getJSONObject("balance").getLong("amount"));
		}
		return accountsMap;
	}
	
	
	
	
	public HashMap<String, ArrayList<String>> readNegativeExpectedValues() {
		HashMap<String, ArrayList<String>> negativeTestValuesMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> negativeTestList;
		try {
			JsonArray negativeTestsArr = (JsonArray) jsonParser.parse(new FileReader(System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\CSVFiles\\SaveForGoal\\ExpectedNegativeTests.json"));
			for (Object negativeTest : negativeTestsArr) {
				negativeTestList = new ArrayList<>();
				JsonObject negativeExpense = (JsonObject) negativeTest;
				String testCaseId = negativeExpense.get("testCaseId").getAsString();
				String statusCode = negativeExpense.get("httpStatuCode").getAsString();
				String yslErrorCode = negativeExpense.get("yslErrorCode").getAsString();
				String errorMessage = negativeExpense.get("errorMessage").getAsString();
				negativeTestList.add(statusCode);
				negativeTestList.add(yslErrorCode);
				negativeTestList.add(errorMessage);
				negativeTestValuesMap.put(testCaseId, negativeTestList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return negativeTestValuesMap;
	}
	
	 public String prepareGoalsForTest(Session sessionObj ) throws ParseException {
	    	String bodyParams1 = createGoalJson("goal-"+System.currentTimeMillis(), "1", sfgDateFormate("0,0,1"),
					sfgDateFormate("1,0,0"), "500", "USD", "13000", "USD", "MONTHLY", "NOT_STARTED", "NONE", "FALSE","");
	       String goalId = createGoal(bodyParams1, sessionObj);
	        return goalId;
	    }
	 
	 public void changeAccountStatus(Set<Long> accountIds) {
			List<Long> itemAccounts = new ArrayList(accountIds);
			Connection conn=dbHelper.getDBConnection();
			Statement st = null;
			ResultSet rs = null;
			String goalUpdInProgress="update item_account set item_account_status_id=2 where item_account_id="+itemAccounts.get(0).toString();//INACTIVE
			String goalUpdCompleted="update item_account set item_account_status_id=5 where item_account_id="+itemAccounts.get(1).toString();//TO_BE_CLOSED
			String goalUpdDeleted="update item_account set item_account_status_id=6 where item_account_id="+itemAccounts.get(2).toString();//CLOSED
			try {
				st = conn.createStatement();
				st.executeQuery(goalUpdInProgress);
				st.executeQuery(goalUpdCompleted);
				st.executeQuery(goalUpdDeleted);
				conn.commit();
			}catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				dbHelper.closeConnectionStatementResultSet(conn, st, rs);
			}		
		}
	 
	 @SuppressWarnings("static-access")
		public String createDestinationAccountJson(int totalDestAccountCnt,Set<Long> itemAccountsSet,String amount,String currency,String siteContainer,ArrayList<String> goalIdsList) {
			JSONObject destinationAccountObj = new JSONObject();
			JSONArray goalAccountArrayObj = new JSONArray();
			Container  container=new Container();
			String[] amounts = amount.split(" , ");
			String[] currencies = currency.split(" , ");
			List<Long> itemAccounts = new ArrayList(itemAccountsSet);
			if(totalDestAccountCnt==1) {
				JSONObject goalDestAccountObj = new JSONObject();
				JSONObject initialAllocationObj = new JSONObject();
				String[] siteContainers = siteContainer.split(",");
				if(siteContainers[0].equalsIgnoreCase("bank")) {	
					goalDestAccountObj.put(container.DESTACCOUNTID,itemAccounts.get(3));
				if(siteContainers.length>1) {	
					if(siteContainers[1].equalsIgnoreCase("inprogress"))
					    changeGoalStatus(goalIdsList.get(0), goalStatus[1][0],goalStatus[1][1]);
					if(siteContainers[1].equalsIgnoreCase("completed")) 
						changeGoalStatus(goalIdsList.get(1), goalStatus[2][0],goalStatus[2][1]);
					if(siteContainers[1].equalsIgnoreCase("deleted")) 
						changeGoalStatus(goalIdsList.get(2), siteContainers[1],"");
					
					if(siteContainers[1].contains("closed")) {
						goalDestAccountObj.put(container.DESTACCOUNTID,itemAccounts.get(2));
					}
					if(siteContainers[1].contains("inactive")) {
						goalDestAccountObj.put(Container.DESTACCOUNTID,itemAccounts.get(0));
					}
					if(siteContainers[1].contains("tobeclosed")) {
						goalDestAccountObj.put(Container.DESTACCOUNTID,itemAccounts.get(1));
					}
					if(siteContainers[1].contains("invalid")) {
						goalDestAccountObj.put(container.DESTACCOUNTID,11111L);
					}	
				  }
			    }
				if(siteContainer.equalsIgnoreCase("investment")) {					
					goalDestAccountObj.put(container.DESTACCOUNTID,itemAccounts.get(5));
			    }
				initialAllocationObj.put(container.AMOUNT, Long.parseLong(amounts[0]));
				initialAllocationObj.put(container.CURRENCY,currency);
				goalDestAccountObj.put("initialAllocation", initialAllocationObj);
				goalAccountArrayObj.put(goalDestAccountObj);
				destinationAccountObj.put("goalAccount", goalAccountArrayObj);
			}
	        if(totalDestAccountCnt>1) {
				for(int i=0;i<totalDestAccountCnt;i++) {
					JSONObject goalDestAccountObj = new JSONObject();
					JSONObject initialAllocationObj = new JSONObject();
					if(siteContainer.equalsIgnoreCase("bank")) {					
						goalDestAccountObj.put(container.DESTACCOUNTID,itemAccounts.get(i+3));
				    }
					if(siteContainer.equalsIgnoreCase("investment")) {					
						goalDestAccountObj.put(container.DESTACCOUNTID,itemAccounts.get(i+5));
				    }else {
				    	//combination of bank and investment
				    	if(i==0) {//bank
				    	goalDestAccountObj.put(container.DESTACCOUNTID,itemAccounts.get(i+3));
				    	}else {
				    		goalDestAccountObj.put(container.DESTACCOUNTID,itemAccounts.get(i+6));
				    	}
				    }
					initialAllocationObj.put(container.AMOUNT, Long.parseLong(amounts[i].toString()));
					initialAllocationObj.put(container.CURRENCY,currencies[i].toString());
					goalDestAccountObj.put("initialAllocation", initialAllocationObj);
					goalAccountArrayObj.put(goalDestAccountObj);							
				}
				destinationAccountObj.put("goalAccount", goalAccountArrayObj);
			}
	        return destinationAccountObj.toString();
		}
	
	 public void validateAddDestAccountResponse(Response response,int totalDestAccounts,int httpStatusCode,String testCaseId,HashMap<String,ArrayList<String>> negativeExpectedValuesMap) {
	    	Assert.assertEquals(response.getStatusCode(), httpStatusCode);
	    	JsonParser jsonParser = new JsonParser();
	    	JsonObject responseObject = (JsonObject)jsonParser.parse(response.asString());
	    	
	    	if(response.getStatusCode()==500 || response.getStatusCode()==404 || response.getStatusCode()==401 ) {
	    		Assert.fail("Tests Failed ...http response 500 OR 404 OR 401");
	    	}
	    	if(response.getStatusCode()==201) {
	    	  try {
	    		JsonArray resultArray = responseObject.getAsJsonArray("goalAccount");
	    		try {
	    			if(resultArray.size()>=1) {
	    			  if(resultArray.size()==1) {
	    				  JsonObject goalObject = resultArray.get(0).getAsJsonObject();
	    				  goalObject.get("id");
	    			  }    			
	    			  else {
	      				//more than one goals created - assertion
	    				  Assert.assertEquals(resultArray.size(), totalDestAccounts);
	      				for(int i=0;i<resultArray.size();i++) {
	      				  JsonObject goalObject = resultArray.get(i).getAsJsonObject();
	      				  goalObject.get("id");
	      				}
	      			}
	    		}    			
	    	}catch(Exception e) {
	    			System.out.println("userGoalId is not created");
	    			Assert.fail();
	    		}
	    	  }catch(Exception e) {
	    		  System.out.println("String RESULT  is not found in the API Response");
	  			Assert.fail();
	    	  }
	    	}
	    	//Negative tests assertion    	
	    	if(response.getStatusCode()==400) {
	    		if(Integer.parseInt(negativeExpectedValuesMap.get(testCaseId).get(0)) == httpStatusCode ) {
	    			if(!responseObject.get("errorCode").getAsString().equals(negativeExpectedValuesMap.get(testCaseId).get(1))){
						Assert.fail(testCaseId +"  Failed errorCode doesn't match... Expected errorCode : "+negativeExpectedValuesMap.get(testCaseId).get(1) +" Actual errorCode : "+responseObject.get("errorCode").getAsString());
					}
	    			//if(!responseObject.get("errorMessage").getAsString().equals(negativeExpectedValuesMap.get(testCaseId).get(2))) {
	    			if(!responseObject.get("errorMessage").getAsString().contains(negativeExpectedValuesMap.get(testCaseId).get(2))) {
						Assert.fail(testCaseId +"  Failed errorMessage doesn't match... Expected errorMessage :  "+negativeExpectedValuesMap.get(testCaseId).get(2) +" Actual errorMessage  : "+responseObject.get("errorMessage").getAsString());
					}
	    		}
	      	}
	    }	
		
	 public void validateCatFreqResponse(Response response,String getType,int count) {
	    	JsonParser parser = new JsonParser();
	    	JsonObject jsonObj = (JsonObject)parser.parse(response.asString());
	    	JsonArray responseArr = null;
	    	if(getType.equalsIgnoreCase("categories")) {
	    		try {
			         responseArr = jsonObj.getAsJsonArray("goalCategory");
			          Assert.assertEquals(count, responseArr.size());
	    		}catch(Exception e) {
	    			Assert.fail("goalCategory[] not found in the goal category response");
	    		}
	    	}
	    	if(getType.equalsIgnoreCase("frequencies")) {
	    		try {
			          responseArr = jsonObj.getAsJsonArray("frequency");
			          Assert.assertEquals(count, responseArr.size());
	    		}catch(Exception e) {
	    			Assert.fail("frequencies[] not found in the goal category response");
	    		}
	    	}
	    	
	    }
	 
	 public void changeGoalStatus(String goalId,String status,String statusId) {
			Connection conn=dbHelper.getDBConnection();
			Statement st = null;
			ResultSet rs = null;
			String goalUpdate = null;
			if(status.equalsIgnoreCase("DELETED")) {		  
			  goalUpdate="update user_goal set is_deleted=1 where user_goal_id="+goalId;
			}else {		
			  goalUpdate="update user_goal set goal_status_id="+statusId+" where user_goal_id="+goalId;
			}
			try {
				st = conn.createStatement();
				st.executeQuery(goalUpdate);			
				conn.commit();
			}catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				dbHelper.closeConnectionStatementResultSet(conn, st, rs);
			}		
		}
		
	 
	 public void  updateGoalForFundingRuleUpdate(String Status,String goalId,Session sessionObj){

			JSONObject goalObj = new JSONObject();
			JSONObject goalObj1 = new JSONObject();
			goalObj.put("status", Status);
			goalObj1.put("goal", goalObj);
			String updateGoalBodyParam = goalObj1.toString();
			LinkedHashMap<String, Object> pathParam = new LinkedHashMap<>();
			pathParam.put("goalIdUpdate", goalId);
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setPathParams(pathParam);		
			httpMethodParameters.setBodyParams(updateGoalBodyParam);
			sfgUtils.updateGoal(httpMethodParameters, sessionObj);	
				
		}
	 
	 
	 public String updateFundingRule(Set<Long> itemAccountsSet,String recurringAmount,String recurringAmountCurrency,String frequency,String siteContainer,int totalFundingRuleCnt,ArrayList<String> goalIdsList,Session sessionObj){
			
			JSONObject updateFundingRuleObj=new JSONObject();
			JSONObject fundingRuleObj = new JSONObject();
			JSONObject recurringAmountObj = new JSONObject();
			JSONArray fundingRuleArrObj = new JSONArray();
			Container  container=new Container();
			String[] amounts = recurringAmount.split(" , ");
			String[] currencies = recurringAmountCurrency.split(" , ");
			List<Long> itemAccounts = new ArrayList(itemAccountsSet);
			if(totalFundingRuleCnt==1) {			
				String[] siteContainers = siteContainer.split(",");
				if(siteContainers[0].equalsIgnoreCase("bank")) {	
					fundingRuleObj.put(container.FUNDINGRULEUPDATEID,itemAccounts.get(0));
				if(siteContainers.length>1) {	
					if(siteContainers[1].equalsIgnoreCase("inprogress"))
						updateGoalForFundingRuleUpdate("IN_PROGRESS",goalIdsList.get(0),sessionObj);
					if(siteContainers[1].equalsIgnoreCase("completed")) 
						updateGoalForFundingRuleUpdate("COMPLETED",goalIdsList.get(0),sessionObj);
					if(siteContainers[1].equalsIgnoreCase("deleted")) 
						updateGoalForFundingRuleUpdate("DELETED",goalIdsList.get(0),sessionObj);
					
					if(siteContainers[1].contains("closed")) {
						fundingRuleObj.put(container.FUNDINGRULEUPDATEID,itemAccounts.get(2));
					}
					if(siteContainers[1].contains("inactive")) {
						fundingRuleObj.put(Container.FUNDINGRULEUPDATEID,itemAccounts.get(0));
					}
					if(siteContainers[1].contains("tobeclosed")) {
						fundingRuleObj.put(Container.FUNDINGRULEUPDATEID,itemAccounts.get(1));
					}
					if(siteContainers[1].contains("invalid")) {
						fundingRuleObj.put(container.FUNDINGRULEUPDATEID,11111L);
					}	
				  }
			    }
				if(siteContainer.equalsIgnoreCase("investment")) {					
					fundingRuleObj.put(container.FUNDINGRULEUPDATEID,itemAccounts.get(5));
			    }
				recurringAmountObj.put(container.AMOUNT, Long.parseLong(amounts[0]));
				recurringAmountObj.put(container.CURRENCY,recurringAmountCurrency);
				fundingRuleObj.put("recurringAmount", recurringAmountObj);
				fundingRuleObj.put("frequency",frequency);
				fundingRuleArrObj.put(fundingRuleObj);
				updateFundingRuleObj.put("fundingRule", fundingRuleArrObj);
			}
	        if(totalFundingRuleCnt>1) {
				for(int i=0;i<totalFundingRuleCnt;i++) {
					JSONObject fundingRuleObj1 = new JSONObject();
					JSONObject recurringAmountObj1 = new JSONObject();
					if(siteContainer.equalsIgnoreCase("bank")) {					
						fundingRuleObj1.put(container.FUNDINGRULEUPDATEID,itemAccounts.get(i+3));
				    }
					if(siteContainer.equalsIgnoreCase("investment")) {					
						fundingRuleObj1.put(container.FUNDINGRULEUPDATEID,itemAccounts.get(i+5));
				    }else {
				    	//combination of bank and investment
				    	if(i==0) {//bank
				    		fundingRuleObj1.put(container.FUNDINGRULEUPDATEID,itemAccounts.get(i+3));
				    	}else {
				    		fundingRuleObj1.put(container.FUNDINGRULEUPDATEID,itemAccounts.get(i+6));
				    	}
				    }
					recurringAmountObj1.put(container.AMOUNT, Long.parseLong(amounts[i].toString()));
					recurringAmountObj1.put(container.CURRENCY,currencies[i].toString());
					fundingRuleObj1.put("recurringAmount", recurringAmountObj1);
					fundingRuleObj1.put("frequency",frequency);
					fundingRuleArrObj.put(fundingRuleObj1);						
				}
	         updateFundingRuleObj.put("fundingRule", fundingRuleArrObj);
			}
	        return updateFundingRuleObj.toString();		
			
			/* List<Long> itemAccounts = new ArrayList(itemAccountsSet);
			JSONObject updateFundingRuleObj=new JSONObject();
			JSONObject fundingRuleObj = new JSONObject();
			JSONObject recurringAmountObj = new JSONObject();
			JSONArray fundingRuleArrObj = new JSONArray();
			recurringAmountObj.put("amount", recurringAmount);
			recurringAmountObj.put("currency", recurringAmountCurrency);
			fundingRuleObj.put("recurringAmount", recurringAmountObj);
			fundingRuleObj.put("goalAccountId",itemAccounts.get(0).toString());
			fundingRuleObj.put("frequency",frequency);		
			fundingRuleArrObj.put(fundingRuleObj);
			updateFundingRuleObj.put("fundingRule",fundingRuleArrObj);
			return updateFundingRuleObj.toString();*/
	}
			
	 
	 public String createFundingRulesForUpdate(int goalAccountId,String goalId,Session sessionObj) throws ParseException{
	    	JsonParser parser = new JsonParser();
	    	String createFundingRuleBodyParam =createFundingRule(goalAccountId, "300", "USD", "WEEKLY", "false", sfgDateFormate("0,0,1"),sfgDateFormate("1,0,0"));
	    	LinkedHashMap<String, Object> createFundingRulePathParam = new LinkedHashMap<>();
	    	HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();
	    	createFundingRulePathParam.put("goalId",goalId);
	    	httpmethodParam.setPathParams(createFundingRulePathParam);
			httpmethodParam.setBodyParams(createFundingRuleBodyParam);
			Response createFundingRuleResp = sfgUtils.createFundingRule(httpmethodParam, sessionObj);
			JsonObject fundingRuleObj = (JsonObject)parser.parse(createFundingRuleResp.asString());
			JsonArray createdFRuleArray = fundingRuleObj.getAsJsonArray("fundingRule");
			JsonObject obj =(JsonObject) createdFRuleArray.get(0);
			return obj.get("id").getAsString();
		}
	 
	 public void validateCreateGoalResponse(Response response,int httpStatusCode,String testCaseId,HashMap<String,ArrayList<String>> negativeExpectedValuesMap) {
	    	Assert.assertEquals(response.getStatusCode(), httpStatusCode);
	    	JsonParser jsonParser = new JsonParser();
	    	JsonObject responseObject = (JsonObject)jsonParser.parse(response.asString());	    	
	    	if(response.getStatusCode()==500 || response.getStatusCode()==404 || response.getStatusCode()==401 ) {
	    		Assert.fail("Tests Failed ...http response 500 OR 404 OR 401");
	    	}
	    	if(response.getStatusCode()==201) {
	    	  try {
	    		JsonArray resultArray = responseObject.getAsJsonArray("goal");	
	    		JsonObject obj = (JsonObject)resultArray.get(0);
	    		Long goalId = obj.get("id").getAsLong();
	    	  }catch(Exception e) {
	    		  System.out.println("String goal  is not found in the API Response");
	  			Assert.fail();
	    	  }
	    	}
	    	//Negative tests assertion    	
	    	if(response.getStatusCode()==400) {
	    		if(Integer.parseInt(negativeExpectedValuesMap.get(testCaseId).get(0)) == httpStatusCode ) {
	    			if(!responseObject.get("errorCode").getAsString().equals(negativeExpectedValuesMap.get(testCaseId).get(1))){
						Assert.fail(testCaseId +"  Failed errorCode doesn't match... Expected errorCode : "+negativeExpectedValuesMap.get(testCaseId).get(1) +" Actual errorCode : "+responseObject.get("errorCode").getAsString());
					}
	    			//if(!responseObject.get("errorMessage").getAsString().equals(negativeExpectedValuesMap.get(testCaseId).get(2))) {
	    			if(!responseObject.get("errorMessage").getAsString().contains(negativeExpectedValuesMap.get(testCaseId).get(2))) {
						Assert.fail(testCaseId +"  Failed errorMessage doesn't match... Expected errorMessage :  "+negativeExpectedValuesMap.get(testCaseId).get(2) +" Actual errorMessage  : "+responseObject.get("errorMessage").getAsString());
					}
	    		}
	      	}
	    }
	 
	 public void validateGetGoalsResposne(Response response,String intialAllocationAmt) {
	    	JsonObject responseObject = (JsonObject)jsonParser.parse(response.asString());	    	
	    	if(response.getStatusCode()==500 || response.getStatusCode()==404 || response.getStatusCode()==401 || response.getStatusCode()==400) {
	    		Assert.fail("Tests Failed ...http response 500 OR 404 OR 401 OR 400");
	    	}
	    	if(response.getStatusCode()==200) {
	    	  try {
	    		JsonArray goalsArray = responseObject.getAsJsonArray("goal");	
	    		JsonObject obj = (JsonObject)goalsArray.get(0);
	    		JsonArray goalAccountArray = obj.getAsJsonArray("goalaccount");
	    		JsonObject goalAccountArrayObj=(JsonObject)goalAccountArray.get(0);
	    		Assert.assertEquals(((JsonObject)goalAccountArrayObj.get("initialAllocationAmount")).get("amount").getAsInt(), Integer.parseInt(intialAllocationAmt));
	    	  }catch(Exception e) {
	    		  System.out.println("String goal  is not found in the API Response");
	  			Assert.fail();
	    	  }
	    	}	    	
	    }
	 
	 
	 public String prepareGoalsToCheckFreeAvailableBal(Session sessionObj ) throws ParseException {
	    	String bodyParams1 = createGoalJson("goal-"+System.currentTimeMillis(), "1", sfgDateFormate("0,0,1"),
					sfgDateFormate("1,0,0"), "500", "USD", "36000", "USD", "MONTHLY", "NOT_STARTED", "NONE", "FALSE","");
	       String goalId = createGoal(bodyParams1, sessionObj);
	        return goalId;
	    }
	 public void updateGoalToPastDate(String goalId,int pastDate)
	 {

			DBHelper dbHelper=new DBHelper(); 
			Connection conn=dbHelper.getDBConnection();
			Statement st = null;
			ResultSet rs = null;

			String getItemAccountIdQuery=" update user_goal set start_date = start_date - "+pastDate+",row_created = row_created -"+pastDate+"where user_goal_id="+goalId;
			System.out.println(getItemAccountIdQuery);
			try {
				st = conn.createStatement();
				rs =st.executeQuery(getItemAccountIdQuery);
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				dbHelper.closeConnectionStatementResultSet(conn, st, rs);
			}
		
	 }
	 
}
