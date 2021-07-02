/*******************************************************************************
 *
 * * Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.insights.yodleeApi.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.insights.views.pojo.CommonEntity;

import io.restassured.response.Response;
import junit.framework.Assert;

public class NotifiedInsightsDataValidation {

	protected Logger logger = LoggerFactory.getLogger(NotifiedInsightsDataValidation.class);
	InsightsGenerics insightsGenerics = new InsightsGenerics();
	boolean insightsVerificationStatus = true;
	HashMap<Integer,Integer> patchIdAndThresholdValueMap = new HashMap<Integer,Integer>();
	JsonParser jsonParser = new JsonParser();
	FailureReason failureReason = new FailureReason();
	String insightName = null;
	JSONParser parser = new JSONParser();  

	public CommonEntity verifyGeneratedInsights(Response userInsightsResponse,ExpectedResultValues expectedResultValues,CommonEntity commonEntity,boolean isUserSubscribed) {		

		String user="userSubscription",cobrand="cobrandSubscription";
		commonEntity.getExpectedResultValues().setTestNotificationStatus(insightsVerificationStatus);
		insightName = expectedResultValues.getInsightName();
		int expectedTotalNumberOfInsights = expectedResultValues.getNumberOfInsights();
		if (expectedTotalNumberOfInsights == 0) {
			String insightResponse = userInsightsResponse.asString();
			boolean insightsNotAvailable = (insightResponse.equals("{}")) ? true : false;
			if (!insightsNotAvailable) {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
				JsonArray notificationArray = notificationResponseObject.getAsJsonArray("feed");
				failNotificationTest(expectedResultValues.getInsightName(),
						"Expected  0 insights triggered But found "+notificationArray.size() +" Insights for All Account "); 
			}
		}else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("feed");
			if(notificationArray==null) {
				insightsVerificationStatus = false;
				failNotificationTest(expectedResultValues.getInsightName(),
						"Expected  "+expectedTotalNumberOfInsights +" insights triggered But found Empty {}"); 
			}else {
				int actualInsights = notificationArray.size();				
				if (expectedTotalNumberOfInsights != actualInsights) {
					failNotificationTest(expectedResultValues.getInsightName(), "Expected Number of Insights are not generated for All Accounts, Expected = " + expectedTotalNumberOfInsights
							+ " Insights Actual = " + actualInsights +" Insights");
				} 
				int userPatchedEntitiesCount=0;
				for (int i = 0; i < notificationArray.size(); i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!insightObj.get("name").getAsString().equals(insightName))
						failNotificationTest(insightName, "insightName");
					if (!insightObj.get("triggerType").getAsString().equals(expectedResultValues.getTriggerType()))
						failNotificationTest(insightName, "triggerType");				
					if(!verifyCreatedDateTimeStamp(insightName, insightObj.get("createdDate").getAsString())) {
						failNotificationTest(insightName, "InValid createdDate either format is not correct OR created date may be greater than current date");
					}					

				}
			}			
		}

		expectedResultValues.setFailureReason(failureReason);
		expectedResultValues.setTestNotificationStatus(insightsVerificationStatus);
		commonEntity.setExpectedResultValues(expectedResultValues);
		return commonEntity;
	}
	
	public CommonEntity verifyGeneratedInsightsV2(Response userInsightsResponse,ExpectedResultValues expectedResultValues,CommonEntity commonEntity,boolean isUserSubscribed,String accountSchemaDefinitionFile,String viewSchemaFile) {		

		String user="userSubscription",cobrand="cobrandSubscription";
		commonEntity.getExpectedResultValues().setTestNotificationStatus(insightsVerificationStatus);
		insightName = expectedResultValues.getInsightName();
		int expectedTotalNumberOfInsights = expectedResultValues.getNumberOfInsights();
		if (expectedTotalNumberOfInsights == 0) {
			String insightResponse = userInsightsResponse.asString();
			boolean insightsNotAvailable = (insightResponse.equals("{}")) ? true : false;
			if (!insightsNotAvailable) {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
				JsonArray notificationArray = notificationResponseObject.getAsJsonArray("feed");
				failNotificationTest(expectedResultValues.getInsightName(),
						"Expected  0 insights triggered But found "+notificationArray.size() +" Insights for All Account "); 
			}
		}else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("feed");
			if(notificationArray==null) {
				insightsVerificationStatus = false;
				failNotificationTest(expectedResultValues.getInsightName(),
						"Expected  "+expectedTotalNumberOfInsights +" insights triggered But found Empty {}"); 
				expectedResultValues.setFailureReason(failureReason);
				expectedResultValues.setTestNotificationStatus(insightsVerificationStatus);
				commonEntity.setExpectedResultValues(expectedResultValues);
				return commonEntity;				
			}
			if(notificationArray.size()>0 && expectedTotalNumberOfInsights>0){
				int actualInsights = notificationArray.size();	
				if (expectedTotalNumberOfInsights != actualInsights) {
					failNotificationTest(expectedResultValues.getInsightName(), "Expected Number of Insights are not generated for All Accounts, Expected = " + expectedTotalNumberOfInsights
							+ " Insights Actual = " + actualInsights +" Insights");
					expectedResultValues.setFailureReason(failureReason);
					expectedResultValues.setTestNotificationStatus(insightsVerificationStatus);
					commonEntity.setExpectedResultValues(expectedResultValues);
					return commonEntity;
				}
				int userPatchedEntitiesCount=0;
				for (int i = 0; i < notificationArray.size(); i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					String entityType = ((JsonObject)(((JsonArray)(insightObj.getAsJsonArray("subscription"))).get(0))).get("entityType").getAsString();
					String schemaFile = entityType.equalsIgnoreCase("Account")?accountSchemaDefinitionFile:viewSchemaFile;
					if(!validateResponseAgainstSchema(insightObj.toString(),schemaFile)) {
						failNotificationTest(insightName, failureReason.getFailureReason());
						break;
					}					
					if (!insightObj.get("insightName").getAsString().equals(insightName)) {
						failNotificationTest(insightName, "insightName");
						break;
					}
					if (!insightObj.get("triggerType").getAsString().equals(expectedResultValues.getTriggerType())) {
						failNotificationTest(insightName, "triggerType");				
						break;
					}
					if(!verifyCreatedDateTimeStamp(insightName, insightObj.get("createdDate").getAsString())) {
						failNotificationTest(insightName, "InValid createdDate either format is not correct OR created date may be greater than current date");
						break;
					}					

				}
				
			}
				
		}
		expectedResultValues.setFailureReason(failureReason);
		expectedResultValues.setTestNotificationStatus(insightsVerificationStatus);
		commonEntity.setExpectedResultValues(expectedResultValues);
		return commonEntity;
	}
	
	public boolean validateResponseAgainstSchema(String actualResponse , String expectedJsonSchema) {
		boolean validateSchema = true;
		JSONObject jsonSchema = new JSONObject(
			      new JSONTokener(NotifiedInsightsDataValidation.class.getResourceAsStream("/TestData"+File.separator+"CSVFiles"+File.separator+"Insights"+File.separator+"FeedsSchema"+File.separator+expectedJsonSchema)));
			    JSONObject jsonSubject = new JSONObject(new JSONTokener(actualResponse));			    
			    Schema schema = SchemaLoader.load(jsonSchema);
			    try {
			    	 schema.validate(jsonSubject);
			    }catch(ValidationException  e) {
			    	//e.printStackTrace();			    	
			    	failureReason.setFailureReason(e.getMessage());
			    	validateSchema=false;
			    }
			   
		return validateSchema;
	}


	public CommonEntity verifyGeneratedInsightsForPatchedEntites(Response userInsightsResponse,ExpectedResultValues expectedResultValues,
			CommonEntity commonEntity,int expectedTotalNumberOfInsights,String thresholdValue) {		

		String user="userSubscription",cobrand="cobrandSubscription";
		commonEntity.getExpectedResultValues().setTestNotificationStatus(insightsVerificationStatus);
		insightName = expectedResultValues.getInsightName();
		if (expectedTotalNumberOfInsights == 0) {
			String insightResponse = userInsightsResponse.asString();
			boolean insightsNotAvailable = (insightResponse.equals("{}")) ? true : false;
			if (!insightsNotAvailable) {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
				JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
				failNotificationTest(expectedResultValues.getInsightName(),
						"Expected  0 insights triggered But found "+notificationArray.size() +" Insights  Insights for PATCHed Accounts"); 
			}
		}else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
			int actualInsights = notificationArray.size();			
			if (expectedTotalNumberOfInsights != actualInsights) {
				failNotificationTest(expectedResultValues.getInsightName(), "Expected Number of Insights are not generated Post PATCH , Expected = " + expectedTotalNumberOfInsights
						+ " Insights Actual = " + actualInsights +" Insights");
			} else {

				int userPatchedEntitiesCount=0;
				for (int i = 0; i < notificationArray.size(); i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!insightObj.get("name").getAsString().equals(insightName))
						failNotificationTest(insightName, "insightName");
					if (!insightObj.get("triggerType").getAsString().equals(expectedResultValues.getTriggerType()))
						failNotificationTest(insightName, "triggerType");				
					if(!verifyCreatedDateTimeStamp(insightName, insightObj.get("createdDate").getAsString())) {
						failNotificationTest(insightName, "InValid createdDate either format is not correct OR created date may be greater than current date");
					}					
					JsonObject dataEntity = insightObj.getAsJsonObject("data");
					JsonObject levelObject = dataEntity.getAsJsonObject("level");

				}
			}

		}

		expectedResultValues.setFailureReason(failureReason);
		expectedResultValues.setTestNotificationStatus(insightsVerificationStatus);
		commonEntity.setExpectedResultValues(expectedResultValues);
		return commonEntity;
	}
	
	
	public CommonEntity verifyGeneratedInsightsForPatchedEntitesV2(Response userInsightsResponse,ExpectedResultValues expectedResultValues,
			CommonEntity commonEntity,int expectedTotalNumberOfInsights,String thresholdValue,String accountSchemaDefinitionFile,String viewSchemaFile) {		

		String user="userSubscription",cobrand="cobrandSubscription";
		commonEntity.getExpectedResultValues().setTestNotificationStatus(insightsVerificationStatus);
		insightName = expectedResultValues.getInsightName();
		if (expectedTotalNumberOfInsights == 0) {
			String insightResponse = userInsightsResponse.asString();
			boolean insightsNotAvailable = (insightResponse.equals("{}")) ? true : false;
			if (!insightsNotAvailable) {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
				JsonArray notificationArray = notificationResponseObject.getAsJsonArray("feed");
				failNotificationTest(expectedResultValues.getInsightName(),
						"Expected  0 insights triggered But found "+notificationArray.size() +" Insights  Insights for PATCHed Accounts"); 
			}
			else
			{
				expectedResultValues.setTestNotificationStatus(insightsVerificationStatus);
				commonEntity.setExpectedResultValues(expectedResultValues);
				return commonEntity;
			}
		}else {

			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("feed");
			if(notificationArray==null) {
				insightsVerificationStatus = false;
				failNotificationTest(expectedResultValues.getInsightName(),
						"Expected  "+expectedTotalNumberOfInsights +" insights triggered But found Empty {}"); 
				expectedResultValues.setFailureReason(failureReason);
				expectedResultValues.setTestNotificationStatus(insightsVerificationStatus);
				commonEntity.setExpectedResultValues(expectedResultValues);
				return commonEntity;				
			}
			if(notificationArray.size()>0 && expectedTotalNumberOfInsights>0){
				int actualInsights = notificationArray.size();	
				if (expectedTotalNumberOfInsights != actualInsights) {
					failNotificationTest(expectedResultValues.getInsightName(), "Expected Number of Insights are not generated for All Accounts, Expected = " + expectedTotalNumberOfInsights
							+ " Insights Actual = " + actualInsights +" Insights");
					expectedResultValues.setFailureReason(failureReason);
					expectedResultValues.setTestNotificationStatus(insightsVerificationStatus);
					commonEntity.setExpectedResultValues(expectedResultValues);
					return commonEntity;
				}
				int userPatchedEntitiesCount=0;
				for (int i = 0; i < notificationArray.size(); i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					String entityType = ((JsonObject)(((JsonArray)(insightObj.getAsJsonArray("subscription"))).get(0))).get("entityType").getAsString();
					String schemaFile = entityType.equalsIgnoreCase("Account")?accountSchemaDefinitionFile:viewSchemaFile;
					if(!validateResponseAgainstSchema(insightObj.toString(),schemaFile)) {
						failNotificationTest(insightName, failureReason.getFailureReason());
						break;
					}					
					if (!insightObj.get("insightName").getAsString().equals(insightName)) {
						failNotificationTest(insightName, "insightName");
						break;
					}
					if (!insightObj.get("triggerType").getAsString().equals(expectedResultValues.getTriggerType())) {
						failNotificationTest(insightName, "triggerType");				
						break;
					}
					if(!verifyCreatedDateTimeStamp(insightName, insightObj.get("createdDate").getAsString())) {
						failNotificationTest(insightName, "InValid createdDate either format is not correct OR created date may be greater than current date");
						break;
					}					

				}
				
			}
				
		
		}
		expectedResultValues.setFailureReason(failureReason);
		expectedResultValues.setTestNotificationStatus(insightsVerificationStatus);
		commonEntity.setExpectedResultValues(expectedResultValues);
		return commonEntity;
	}

	public void failNotificationTest(String insightName, String expectedAttribute) {
		logger.info("Test FAILED for insight " + insightName + " Reason  " + expectedAttribute
				+ " values doesn't match");	
		failureReason.setFailureReason(expectedAttribute);
		insightsVerificationStatus = false;
	}

	public boolean verifyCreatedDateTimeStamp(String insightName, String notificationDate) {

		Date currentDate = new Date();
		Date insightGeneratedDate = null;
		boolean validateDate = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			insightGeneratedDate = sdf.parse(notificationDate.substring(0, 10));
			Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z");
			validateDate = p.matcher(notificationDate).matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!sdf.format(insightGeneratedDate).equals(sdf.format(currentDate))) {
			logger.info("Insight generated date is not matching with current Date");	
			validateDate = false;
		}
		return validateDate;
	}

	public CommonEntity verifyData_emergencySavingsInsight(Response userInsightsResponse,CommonEntity commonEntity,String expTotalAvaialableBalance,String expAvgSpendignAmount,String expSpendingRunway) {
		commonEntity.getExpectedResultValues().setTestNotificationStatus(insightsVerificationStatus);		
		JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
		JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
		HashMap<String, String> values = new HashMap<String,String>();
		JsonArray valuesArray = null, keysArray= null;

		for (int i = 0; i < notificationArray.size(); i++) {
			JsonObject insightObj = (JsonObject) notificationArray.get(i);
			JsonObject dataEntity = insightObj.getAsJsonObject("data");
			JsonObject modelEntity = dataEntity.getAsJsonObject("model");
			keysArray = modelEntity.getAsJsonArray("keys");
			valuesArray  = modelEntity.getAsJsonArray("values");
			System.out.println("Average Spending Amount: " + valuesArray.get(2).getAsString());	
		}

		for(int i = 0 ; i<keysArray.size();i++)
			values.put(keysArray.get(i).getAsString(),valuesArray.get(i).getAsString());
		
		if(!expTotalAvaialableBalance.equals(values.get("accountLevel[0].totalAvailableBalance.amount")))
			failNotificationTest(insightName, "TotalAvailableBalance Amount");
		if(!expAvgSpendignAmount.equals(values.get("accountLevel[0].averageSpending.amount")))
			failNotificationTest(insightName, "Average Spending Amount");	
		if(!expSpendingRunway.equals(values.get("accountLevel[0].spendingRunway")))
			failNotificationTest(insightName, "Spending Runway");
		
		commonEntity.getExpectedResultValues().setTestNotificationStatus(insightsVerificationStatus);
		return commonEntity;

	}
}
